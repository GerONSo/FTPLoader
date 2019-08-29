package ru.geron.ftploader.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.geron.ftploader.repositories.SettingsRepository;
import ru.geron.ftploader.data.FtpEntity;

public class SettingViewModel extends AndroidViewModel {
    private SettingsRepository repository;
    private LiveData<List<FtpEntity>> allFtp;


    public SettingViewModel(@NonNull Application application) {
        super(application);
        repository = new SettingsRepository(application);
        allFtp = repository.getAllFtp();
    }

    public void insert(FtpEntity ftp) {
        repository.insert(ftp);
    }

    public void update(FtpEntity ftp) {
        repository.update(ftp);
    }

    public void delete(FtpEntity ftp) {
        repository.delete(ftp);
    }

    public void deleteAllFtp() {
        repository.deleteAll();
    }

    public FtpEntity getFtp(int id) {
        return repository.getFtp(id);
    }

    public FtpEntity getActive() {
        return repository.getActive();
    }

    public void setActiveFtp(FtpEntity ftp) {
        repository.setActive(ftp);
    }

    public LiveData<List<FtpEntity>> getAllFtp() {
        return allFtp;
    }
}
