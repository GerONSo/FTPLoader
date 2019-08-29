package ru.geron.ftploader.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.ArrayList;

import ru.geron.ftploader.data.SendBitmap;
import ru.geron.ftploader.data.FtpEntity;
import ru.geron.ftploader.repositories.PhotoRepository;

public class AddPhotoViewModel extends AndroidViewModel {

    private PhotoRepository repository;
    private MutableLiveData<ArrayList<SendBitmap>> images;
    private MutableLiveData<ArrayList<File>> files;
    private FtpEntity activeFtp;

    public AddPhotoViewModel(@NonNull Application application) {
        super(application);
        repository = new PhotoRepository(application);
        activeFtp = repository.getActive();
        images = repository.getImages();
        files = repository.getFiles();
    }

    public FtpEntity getActive() {
        return activeFtp;
    }

    public MutableLiveData<ArrayList<SendBitmap>> getImages() {
        return images;
    }

    public void remove(int position) {
        repository.remove(position);
    }

    public void swap(int i, int j) {
        repository.swap(i, j);
    }

    public void setImages(MutableLiveData<ArrayList<SendBitmap>> newImages) {
        repository.setImages(newImages);
    }

    public void addImages(SendBitmap bitmap) {
        repository.addImages(bitmap);
    }

    public MutableLiveData<ArrayList<File>> getFiles() {
        return files;
    }

    public void reverseFiles() {
        repository.reverseFiles();
    }

    public void addFiles(File file) {
        repository.addFiles(file);
    }

    public void setFiles(MutableLiveData<ArrayList<File>> newFiles) {
        repository.setFiles(newFiles);
    }
}
