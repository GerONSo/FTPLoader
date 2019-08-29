package ru.geron.ftploader.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;

import com.google.android.material.snackbar.Snackbar;

import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.geron.ftploader.data.SendBitmap;

public class FileUtils {

    private LinearLayout layout;
    private File imageFile;

    public File getFileFromGallery(File filesDir, Uri uri, ContentResolver resolver, LinearLayout layout) {
        this.layout = layout;
        File file = getTempPhotoFile(filesDir);
        createFileByContentUri(uri, file, resolver, layout);
        return file;
    }

    public SendBitmap getImageFromCamera(LinearLayout layout) {
        this.layout = layout;
        return getImage(imageFile);
    }

    public SendBitmap getImage(File file) {
        File mainImage;
        mainImage = file;
        Point size = new Point();
        size.set(700, 700);
        int maxSide = Math.max(size.x, size.y);
        Bitmap bitmap = null;
        try {
            bitmap = ImageUtils.getScaledBitmap(mainImage, maxSide, maxSide);
        } catch (Exception e) {
            LinearLayout layout = this.layout;
            Snackbar snackbar = Snackbar.make(layout, "Cannot get image...", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        return new SendBitmap(bitmap, BitmapFactory.decodeFile(file.getPath()));
    }

    public File getTempPhotoFile(File filesDir) {
        imageFile = getNewImageFile(filesDir, "tmp_", ".jpg");
        return imageFile;
    }

    private static void createFileByContentUri(Uri src, File dst, ContentResolver contentResolver, LinearLayout layout) {
        try (InputStream in = contentResolver.openInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(layout, "Error creating File by Content Uri", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    private static File getNewImageFile(File dir, String prefix, String suffix) {
        return new File(dir, prefix +
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) +
                suffix);
    }
}
