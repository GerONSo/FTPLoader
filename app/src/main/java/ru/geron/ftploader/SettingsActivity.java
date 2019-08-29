package ru.geron.ftploader;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.widget.Toast;

import java.util.List;

import ru.geron.ftploader.adapters.SettingsViewAdapter;
import ru.geron.ftploader.data.FtpEntity;
import ru.geron.ftploader.viewModels.SettingViewModel;

public class SettingsActivity extends AppCompatActivity {

    public static final int ADD_FTP_REQUEST = 1;
    public static final int EDIT_FTP_REQUEST = 2;
    public static final String ID = "ID";
    public static final String IP_ADDRESS = "IP-address";
    public static final String PORT = "Port";
    public static final String DIRECTORY = "Directory";
    public static final String LOGIN = "Login";
    public static final String PASSWORD = "Password";
    public static final String NAME = "Name";


    private static final int RC_PERMISSION = 10;

    private SettingViewModel settingViewModel;
    private SettingsViewAdapter adapter;


    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        recyclerView = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new SettingsViewAdapter();
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RC_PERMISSION);
            }
            Intent intent = new Intent(SettingsActivity.this, CodeScannerActivity.class);
            intent.putExtra("type", "QR");
            startActivityForResult(intent, ADD_FTP_REQUEST);
        });

        settingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        settingViewModel.getAllFtp().observe(this, new Observer<List<FtpEntity>>() {
            @Override
            public void onChanged(@Nullable List<FtpEntity> ftps) {
                adapter.notifyDataSetChanged();
                adapter.submitList(ftps);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                settingViewModel.delete(adapter.getFtpAt(viewHolder.getAdapterPosition()));
                Toast.makeText(SettingsActivity.this, "Ftp server deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(ftp -> {

            Intent intent = new Intent(SettingsActivity.this, EditFtpActivity.class);
            intent.putExtra(NAME, ftp.getName());
            intent.putExtra(ID, ftp.getId());
            intent.putExtra(IP_ADDRESS, ftp.getIp());
            intent.putExtra(PORT, ftp.getPort());
            intent.putExtra(DIRECTORY, ftp.getDirectory());
            intent.putExtra(LOGIN, ftp.getLogin());
            intent.putExtra(PASSWORD, ftp.getPassword());
            startActivityForResult(intent, EDIT_FTP_REQUEST);
        });

        adapter.setOnCheckListener(ftp -> {
            settingViewModel.setActiveFtp(ftp);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FTP_REQUEST && resultCode == RESULT_OK) {
            String result = data.getStringExtra("ftpResult");

            FtpEntity ftp = FtpEntity.parseResult(result);
            if(ftp == null) {
                Toast.makeText(this, "Incorrect QR code", Toast.LENGTH_SHORT).show();
                return;
            }
            settingViewModel.insert(ftp);

            Toast.makeText(this, "Ftp server added", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == EDIT_FTP_REQUEST) {
            String name = data.getStringExtra(SettingsActivity.NAME);
            int id = data.getIntExtra(SettingsActivity.ID, 0);
            String ipAddress = data.getStringExtra(SettingsActivity.IP_ADDRESS);
            String port = data.getStringExtra(SettingsActivity.PORT);
            String directory = data.getStringExtra(SettingsActivity.DIRECTORY);
            String login = data.getStringExtra(SettingsActivity.LOGIN);
            String password = data.getStringExtra(SettingsActivity.PASSWORD);

            FtpEntity ftp = settingViewModel.getFtp(id);
            ftp.setName(name);
            ftp.setIp(ipAddress);
            ftp.setPort(port);
            ftp.setDirectory(directory);
            ftp.setLogin(login);
            ftp.setPassword(password);
            settingViewModel.update(ftp);
        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

}
