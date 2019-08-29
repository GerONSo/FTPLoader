package ru.geron.ftploader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


import ru.geron.ftploader.adapters.ListViewAdapter;
import ru.geron.ftploader.data.FtpEntity;
import ru.geron.ftploader.data.SendBitmap;
import ru.geron.ftploader.utils.FileUtils;
import ru.geron.ftploader.viewModels.AddPhotoViewModel;


public class AddPhotoActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 404;
    private static final int REQUEST_PERMISSION_CAMERA = 505;
    private static final int REQUEST_PERMISSION_INTERNET = 606;
    private static final String CONTENT_AUTHORITY = "ru.geron.ftploader.fileprovider";
    App app;
    private AddPhotoViewModel addPhotoViewModel;
    Toolbar toolbar;
    RecyclerView listView;
    FloatingActionButton fab;
    LinearLayout mainLayout;
    Button uploadButton;
    ProgressBar progress;

    FileUtils fileUtils = new FileUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        app = (App) getApplicationContext();
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);
        mainLayout = findViewById(R.id.main_layout);
        uploadButton = findViewById(R.id.upload_button);
        progress = findViewById(R.id.progress);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        final ListViewAdapter adapter = new ListViewAdapter();
        listView.setAdapter(adapter);

        addPhotoViewModel = ViewModelProviders.of(this).get(AddPhotoViewModel.class);
        addPhotoViewModel.getImages().observe(this, new Observer<ArrayList<SendBitmap>>() {
            @Override
            public void onChanged(ArrayList<SendBitmap> images) {
                adapter.notifyDataSetChanged();
                adapter.submitList(images);
            }
        });

        fab.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(AddPhotoActivity.this, fab);
            popupMenu.inflate(R.menu.fab_menu);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.gallery:
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
                        }
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            getImageFromGallery();
                        }
                        break;
                    case R.id.camera:
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                        }
                        getImageFromCamera();
                        break;
                }
                return true;
            });
        });

        uploadButton.setOnClickListener(v -> {
            while (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_PERMISSION_INTERNET);
            }
            FtpEntity tmp = addPhotoViewModel.getActive();
            if (addPhotoViewModel.getActive() == null) {
                Snackbar snackbar = Snackbar.make(mainLayout, R.string.scan_qr_to_get_ftp_info, Snackbar.LENGTH_SHORT);
                View view = snackbar.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextColor(mainLayout.getResources().getColor(R.color.snackbar_text));
                snackbar.show();
            } else if (addPhotoViewModel.getImages().getValue() == null) {
                Snackbar snackbar = Snackbar.make(mainLayout, R.string.list_image_empty, Snackbar.LENGTH_SHORT);
                View view = snackbar.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextColor(mainLayout.getResources().getColor(R.color.snackbar_text));
                snackbar.show();
            } else {
                Task task = new Task();
                task.execute(mainLayout);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.UP | ItemTouchHelper.DOWN) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        addPhotoViewModel.swap(i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        addPhotoViewModel.swap(i, i - 1);
                    }
                }
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                addPhotoViewModel.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                View menuItemView = findViewById(R.id.action_settings);
                PopupMenu popupMenu = new PopupMenu(this, menuItemView);
                popupMenu.inflate(R.menu.action_bar);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    Intent intent = new Intent(AddPhotoActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        addPhotoViewModel.setImages(new MutableLiveData<>());
        addPhotoViewModel.setFiles(new MutableLiveData<>());
        app.setProductId("");
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GALLERY:
                    Uri imageUri = data.getData();
                    try {
                        File file = fileUtils.getFileFromGallery(getFilesDir(), imageUri, getContentResolver(), mainLayout);
                        addPhotoViewModel.addFiles(file);
                        addPhotoViewModel.addImages(fileUtils.getImage(file));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(mainLayout, Html.fromHtml(getString(R.string.cannot_add_image)), Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case REQUEST_CAMERA:
                    try {
                        SendBitmap temp = fileUtils.getImageFromCamera(mainLayout);
                        addPhotoViewModel.addImages(temp);
                    } catch (Exception e) {
                        Snackbar.make(mainLayout, Html.fromHtml(getString(R.string.cannot_add_image)), Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void getImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_GALLERY);
    }

    private void getImageFromCamera() {
        File file = fileUtils.getTempPhotoFile(getFilesDir());
        addPhotoViewModel.reverseFiles();
        addPhotoViewModel.addFiles(file);
        addPhotoViewModel.reverseFiles();
        Uri contentUri = FileProvider.getUriForFile(this, CONTENT_AUTHORITY, file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public void setProgressVisible() {
        progress.setVisibility(View.VISIBLE);
    }

    public void setProgressGone() {
        progress.setVisibility(View.GONE);
    }

    class Task extends AsyncTask<LinearLayout, Void, Boolean> {

        private boolean flag;
        private LinearLayout layout;

        @Override
        protected void onPreExecute() {
            setProgressVisible();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(LinearLayout... layouts) {
            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            flag = true;
            layout = layouts[0];
            for (File file : addPhotoViewModel.getFiles().getValue()) {
                boolean cur = this.upload(file);
                if (!cur) {
                    flag = false;
                }
            }
            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            return flag;
        }

        boolean upload(File file) {
            try {
                FtpEntity info = addPhotoViewModel.getActive();
                FTPClient client = new FTPClient();
                client.connect(info.getIp(), Integer.valueOf(info.getPort()));
                client.login(info.getLogin(), info.getPassword());
                if (info.getConnection_type().equals("activ")) {
                    client.enterLocalActiveMode();
                } else {
                    client.enterLocalPassiveMode();
                }
                client.changeWorkingDirectory(info.getDirectory());
                boolean isIdDirectory = client.changeWorkingDirectory(app.getProductId());
                if (!isIdDirectory) {
                    client.makeDirectory(app.getProductId());
                    client.changeWorkingDirectory(app.getProductId());
                }
                boolean isPhotoDirectory = client.changeWorkingDirectory("photo");
                if (!isPhotoDirectory) {
                    client.makeDirectory("photo");
                    client.changeWorkingDirectory("photo");
                }

                client.setFileType(FTP.BINARY_FILE_TYPE);
                BufferedInputStream buffIn;
                buffIn = new BufferedInputStream(new FileInputStream(file));
                String name = UUID.randomUUID().toString() + ".jpg";
                client.storeFile(name, buffIn);
                buffIn.close();
                client.logout();
                client.disconnect();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            String message;
            if (!flag) {
                message = getString(R.string.error_loading);
            } else {
                message = getString(R.string.successful_upload);
            }
            setProgressGone();
            try {
                for (File file : addPhotoViewModel.getFiles().getValue()) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            new AlertDialog.Builder(new ContextThemeWrapper(AddPhotoActivity.this, R.style.dialogStyle))
                    .setPositiveButton("Ok", (dialog, which) -> {
                        addPhotoViewModel.setImages(new MutableLiveData<>());
                        addPhotoViewModel.setFiles(new MutableLiveData<>());
                        app.setProductId("");
                        NavUtils.navigateUpFromSameTask(AddPhotoActivity.this);
                    })
                    .setMessage(message)
                    .show();
        }
    }
}
