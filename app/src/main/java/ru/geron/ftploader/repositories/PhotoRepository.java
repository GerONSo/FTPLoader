package ru.geron.ftploader.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import ru.geron.ftploader.data.SendBitmap;
import ru.geron.ftploader.data.FtpDao;
import ru.geron.ftploader.data.FtpEntity;
import ru.geron.ftploader.data.FtpLoaderDb;

public class PhotoRepository {

    private MutableLiveData<ArrayList<SendBitmap>> images;
    private MutableLiveData<ArrayList<File>> files;
    private FtpEntity activeFtp;
    private FtpDao ftpDao;

    public PhotoRepository(Application application) {
        files = new MutableLiveData<>();
        images = new MutableLiveData<>();
        FtpLoaderDb database = FtpLoaderDb.getInstance(application);
        ftpDao = database.ftpDao();
        activeFtp = ftpDao.getActive();
    }

    public MutableLiveData<ArrayList<SendBitmap>> getImages() {
        return images;
    }

    public void remove(int position) {
        ArrayList<SendBitmap> newImages = images.getValue();
        ArrayList<File> newFiles = files.getValue();
        newImages.remove(position);
        newFiles.remove(position);
        images.setValue(newImages);
        files.setValue(newFiles);
    }

    public void swap(int i, int j) {
        ArrayList<SendBitmap> newImages = images.getValue();
        ArrayList<File> newFiles = files.getValue();
        Collections.swap(newImages, i, j);
        Collections.swap(newFiles, i, j);
        images.setValue(newImages);
        files.setValue(newFiles);
    }

    public void setImages(MutableLiveData<ArrayList<SendBitmap>> images) {
        this.images = images;
    }

    public void addImages(SendBitmap bitmap) {
        ArrayList<SendBitmap> newImages = images.getValue();
        if (newImages == null) {
            newImages = new ArrayList<>();
        }
        Collections.reverse(newImages);
        newImages.add(bitmap);
        Collections.reverse(newImages);
        images.setValue(newImages);
    }

    public MutableLiveData<ArrayList<File>> getFiles() {
        return files;
    }

    public void reverseFiles() {
        ArrayList<File> newFiles = files.getValue();
        if (newFiles == null) {
            newFiles = new ArrayList<>();
        }
        Collections.reverse(newFiles);

        files.setValue(newFiles);
    }

    public void addFiles(File file) {
        ArrayList<File> newFiles = files.getValue();
        if (newFiles == null) {
            newFiles = new ArrayList<>();
        }
        Collections.reverse(newFiles);
        newFiles.add(file);
        Collections.reverse(newFiles);
        files.setValue(newFiles);
    }

    public void setFiles(MutableLiveData<ArrayList<File>> files) {
        this.files = files;
    }

    public FtpEntity getActive(){
        return activeFtp;
    }
}

