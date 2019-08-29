package ru.geron.ftploader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditFtpActivity extends AppCompatActivity {

    int id;
    String ipAddress;
    String port;
    String directory;
    String login;
    String password;
    String name;
    EditText et_ip_address;
    EditText et_port;
    EditText et_directory;
    EditText et_login;
    EditText et_password;
    EditText et_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ftp);
        Intent intent = getIntent();
        name = intent.getStringExtra(SettingsActivity.NAME);
        id = intent.getIntExtra(SettingsActivity.ID, 0);
        ipAddress = intent.getStringExtra(SettingsActivity.IP_ADDRESS);
        port = intent.getStringExtra(SettingsActivity.PORT);
        directory = intent.getStringExtra(SettingsActivity.DIRECTORY);
        login = intent.getStringExtra(SettingsActivity.LOGIN);
        password = intent.getStringExtra(SettingsActivity.PASSWORD);

        et_name = findViewById(R.id.et_name);
        et_ip_address = findViewById(R.id.et_ip_address);
        et_port = findViewById(R.id.et_port);
        et_directory = findViewById(R.id.et_dir);
        et_login = findViewById(R.id.et_login);
        et_password = findViewById(R.id.et_password);

        et_name.setText(name);
        et_ip_address.setText(ipAddress);
        et_port.setText(port);
        et_directory.setText(directory);
        et_login.setText(login);
        et_password.setText(password);

        findViewById(R.id.fab).setOnClickListener(v -> {

            Intent returnIntent = new Intent(EditFtpActivity.this, SettingsActivity.class);
            returnIntent.putExtra(SettingsActivity.NAME, et_name.getText().toString());
            returnIntent.putExtra(SettingsActivity.ID, id);
            returnIntent.putExtra(SettingsActivity.IP_ADDRESS, et_ip_address.getText().toString());
            returnIntent.putExtra(SettingsActivity.PORT, et_port.getText().toString());
            returnIntent.putExtra(SettingsActivity.DIRECTORY, et_directory.getText().toString());
            returnIntent.putExtra(SettingsActivity.LOGIN, et_login.getText().toString());
            returnIntent.putExtra(SettingsActivity.PASSWORD, et_password.getText().toString());
            setResult(RESULT_OK, returnIntent);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
