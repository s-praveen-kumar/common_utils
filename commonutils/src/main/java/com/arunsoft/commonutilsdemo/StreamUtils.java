/*
 * Copyright (c) 2018 S Praveen Kumar
 */

package com.arunsoft.commonutilsdemo;


import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;

public class StreamUtils {
    public static void copy(@NonNull InputStream src, @NonNull OutputStream dest) throws IOException {
        int len;
        byte[] buf = new byte[1024];
        while ((len = src.read(buf)) != -1) {
            dest.write(buf, 0, len);
        }
    }

    public static String read(@NonNull InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        reader.close();
        return sb.toString();
    }

    public static String[] readLines(@NonNull InputStream in) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines.toArray(new String[lines.size()]);
    }

    public static void write(@NonNull OutputStream out, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        BufferedReader reader = new BufferedReader(new StringReader(content));
        char[] buf = new char[1024];
        int len;
        while((len = reader.read(buf))!=-1)
            writer.write(buf,0,len);
        writer.close();
        reader.close();
    }
}
