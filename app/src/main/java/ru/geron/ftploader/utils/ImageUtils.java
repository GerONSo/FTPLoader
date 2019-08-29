package ru.geron.ftploader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static Bitmap getScaledBitmap(File file, int destWidth, int destHeight) throws IOException {
        InputStream is = new FileInputStream(file);
        BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
        sizeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, sizeOptions);
        is.close();
        int srcWidth = sizeOptions.outWidth;
        int srcHeight = sizeOptions.outHeight;
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            double widthScale = (double) srcWidth / destWidth;
            double heightScale = (double) srcHeight / destHeight;
            inSampleSize = (int) Math.round(Math.max(widthScale, heightScale));
        }
        BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
        scaleOptions.inSampleSize = inSampleSize;
        is = new FileInputStream(file);
        return BitmapFactory.decodeStream(is, null, scaleOptions);
    }
}
