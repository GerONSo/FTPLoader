package ru.geron.ftploader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.net.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class ScanBRActivity extends AppCompatActivity {

    Button scanBRButton;
    private App app;
    private static final int RC_PERMISSION = 10;
    private Toolbar toolbar;
    private ImageButton nextButton;
    private EditText productEditText;
    private LinearLayout mainLayout;

    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_br);
        toolbar = findViewById(R.id.toolbar);
        nextButton = findViewById(R.id.btn_next);
        productEditText = findViewById(R.id.et_product_id);
        mainLayout = findViewById(R.id.main_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        app = (App) getApplicationContext();
        scanBRButton = findViewById(R.id.scan_br_button);
        scanBRButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RC_PERMISSION);
            }
            Intent intent = new Intent(ScanBRActivity.this, CodeScannerActivity.class);
            intent.putExtra("type", "BR");
            startActivity(intent);
        });
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(ScanBRActivity.this, AddPhotoActivity.class);
            String productId = String.valueOf(productEditText.getText());
            if(!productId.equals("")) {
                app.setProductId(String.valueOf(productEditText.getText()));
                startActivity(intent);
            }
            else {
                Snackbar snackbar = Snackbar.make(mainLayout, R.string.non_empty_product_id, Snackbar.LENGTH_SHORT);
                View view = snackbar.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextColor(mainLayout.getResources().getColor(R.color.snackbar_text));
                snackbar.show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!app.getProductId().equals("")) {
            Intent intent = new Intent(ScanBRActivity.this, AddPhotoActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
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
                    Intent intent = new Intent(ScanBRActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
