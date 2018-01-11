/*
 * Copyright (c) 2018 S Praveen Kumar
 */

package com.arunsoft.commonutils;


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

/**
 * Utilities for working with Input and Output Streams
 * <p>
 * Methods for copying, reading and writing Streams are available.
 */
public class StreamUtils {
    /**
     * Copies the inputStream content to outputStream
     *
     * @param src  The source input stream
     * @param dest The destination - output stream
     * @throws IOException Thrown when there is an error either in reading or writing the streams
     */
    public static void copy(@NonNull InputStream src, @NonNull OutputStream dest) throws IOException {
        int len;
        byte[] buf = new byte[1024];
        while ((len = src.read(buf)) != -1) {
            dest.write(buf, 0, len);
        }
    }

    /**
     * Reads the input stream and returns the content text as a String. This assumes, default encoding.
     *
     * @param in The stream to be read
     * @return The String containing the content of the stream
     * @throws IOException Thrown when there is an error reading the stream
     */
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

    /**
     * Reads the contents of a input stream(text content) and returns them as array of lines.
     *
     * @param in The input stream to be read from
     * @return An array of strings where each element represents a line in the file.
     * @throws IOException Thrown when there is an error reading the stream
     */
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

    /**
     * Writes the given String content to the output stream.
     *
     * @param out     The output stream to be written to
     * @param content The text to be written
     * @throws IOException Thrown when there is an error writing to the stream.
     */
    public static void write(@NonNull OutputStream out, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        BufferedReader reader = new BufferedReader(new StringReader(content));
        char[] buf = new char[1024];
        int len;
        while ((len = reader.read(buf)) != -1)
            writer.write(buf, 0, len);
        writer.close();
        reader.close();
    }
}
