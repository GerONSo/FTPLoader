package ru.geron.ftploader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;

import ru.geron.ftploader.utils.CryptUtils;


public class CodeScannerActivity extends AppCompatActivity {
    private App app;
    private CodeScanner codeScanner;
    private static final int RC_PERMISSION = 10;
    private boolean permissionGranted;
    private String codeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);
        app = (App) getApplicationContext();
        Intent intent = getIntent();
        codeType = intent.getStringExtra("type");
        codeScanner = new CodeScanner(this, findViewById(R.id.scanner));

        if (codeType.equals("QR")) {
            codeScanner.setFormats(CodeScanner.TWO_DIMENSIONAL_FORMATS);
        } else {
            codeScanner.setFormats(CodeScanner.ONE_DIMENSIONAL_FORMATS);
        }

        codeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            if (codeType.equals("QR")) {
                Intent returnIntent = new Intent();
                String decryptedResult = CryptUtils.decrypt(result.getText());
                returnIntent.putExtra("ftpResult", decryptedResult);
                setResult(RESULT_OK, returnIntent);
                finish();
            } else {
                String productID = result.getText();
                String[] splittedResult = productID.split("\r\n", 7);
                if (splittedResult.length != 1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.incorrect_barcode), Toast.LENGTH_SHORT).show();
                } else {
                    app.setProductId(productID);
                }
            }
            finish();
        }));
        codeScanner.setErrorCallback(error -> runOnUiThread(
                () -> Toast.makeText(this, getString(R.string.scanner_error, error), Toast.LENGTH_LONG).show()));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionGranted = false;
        } else {
            permissionGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
                codeScanner.startPreview();
            } else {
                permissionGranted = false;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionGranted) {
            codeScanner.startPreview();
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

}
