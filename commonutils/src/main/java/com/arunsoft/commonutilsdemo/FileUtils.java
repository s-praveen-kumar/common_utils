/*
 * Copyright (c) 2018 S Praveen Kumar
 */

package com.arunsoft.commonutilsdemo;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {
    public static void copy(@NonNull File src, @NonNull File dest) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dest);
        StreamUtils.copy(inStream, outStream);
        inStream.close();
        outStream.close();
    }

    public static void copy(@NonNull String srcPath, @NonNull String destPath) throws IOException {
        FileInputStream inStream = new FileInputStream(srcPath);
        FileOutputStream outStream = new FileOutputStream(destPath);
        StreamUtils.copy(inStream, outStream);
        inStream.close();
        outStream.close();
    }

    public static String read(@NonNull File in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        return sb.toString();
    }

    public static String read(@NonNull String path) throws IOException {
        return read(new File(path));
    }

    public static String[] readLines(@NonNull File in) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static String[] readLines(@NonNull String path) throws IOException {
        return readLines(new File(path));
    }

}
