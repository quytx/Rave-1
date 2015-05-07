package com.rave.rave;

import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ImgEncode {
    public static String encodeImage(String path) {
        File file = new File(path);
        byte[] imageData = null;
        try {
            // Reading a Image file from file system
            FileInputStream imageInFile = new FileInputStream(file);
            imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);

            // Converting Image byte array into Base64 String
            return Base64.encodeToString(imageData, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            Log.d("ImageConverting", "File not found!");
            return null;
        } catch (IOException ioe) {
            Log.d("ImageConverting", "IO exception!");
            return null;
        }

    }
}