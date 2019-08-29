package ru.geron.ftploader.data;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ftp_table")
public class FtpEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String result;
    private String ip;
    private String port;
    private String connection_type;
    private String directory;
    private String login;
    private String password;
    private int active;
    private String name;

       public FtpEntity(String name, String result, String ip, String port, String connection_type,
                     String directory, String login, String password, int active) {
        this.result = result;
        this.ip = ip;
        this.port = port;
        this.connection_type = connection_type;
        this.directory = directory;
        this.login = login;
        this.password = password;
        this.active = active;
        this.name = name;
    }

    public static FtpEntity parseResult(String result) {
        String updated_result = "";
        for(int i = 0; i < result.length(); i++) {
            if(i > 0 && result.charAt(i - 1) == '\\' && result.charAt(i) == 'n') {
                continue;
            }
            else if(i < result.length() && result.charAt(i) == '\\' && result.charAt(i + 1) == 'n') {
                updated_result += '%';
            }
            else {
                updated_result += result.charAt(i);
            }
        }
        String[] splittedResult = updated_result.split("%");
        FtpEntity entity;
        if (splittedResult.length != 7) {
            Log.e("FTP error", "Wrong QR format");
            return null;
        } else {
            entity = new FtpEntity(splittedResult[0], result, splittedResult[1], splittedResult[2], splittedResult[3], splittedResult[4],
                    splittedResult[5], splittedResult[6], 0);
            entity.name = splittedResult[0];
            entity.result = result;
            entity.ip = splittedResult[1];
            entity.port = splittedResult[2];
            entity.connection_type = splittedResult[3];
            entity.directory = splittedResult[4];
            entity.login = splittedResult[5];
            entity.password = splittedResult[6];
        }
        return entity;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getActive() {
        return active;
    }

    public int getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getConnection_type() {
        return connection_type;
    }

    public String getDirectory() {
        return directory;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
