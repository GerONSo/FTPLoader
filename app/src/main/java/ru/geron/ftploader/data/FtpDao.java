package ru.geron.ftploader.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FtpDao {
    @Insert
    void insert(FtpEntity ftp);

    @Update
    void update(FtpEntity ftp);

    @Delete
    void delete(FtpEntity ftp);

    @Query("DELETE FROM ftp_table")
    void deleteAll();

    @Query("SELECT * FROM ftp_table ORDER BY id")
    LiveData<List<FtpEntity>> getAllFtp();

    @Query("SELECT * FROM ftp_table WHERE id = (SELECT active FROM ftp_table limit 1)")
    FtpEntity getActive();

    @Query("UPDATE ftp_table SET active = :active_id")
    void setActive(int active_id);

    @Query("SELECT * FROM ftp_table WHERE id = :id")
    FtpEntity getFtp(int id);
}
