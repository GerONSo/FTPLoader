package ru.geron.ftploader.data;

import android.graphics.Bitmap;

public class SendBitmap {
    public Bitmap compressedBitmap;
    public Bitmap fullBitmap;

    public SendBitmap(Bitmap compressedBitmap, Bitmap fullBitmap) {
        this.compressedBitmap = compressedBitmap;
        this.fullBitmap = fullBitmap;
    }
}
