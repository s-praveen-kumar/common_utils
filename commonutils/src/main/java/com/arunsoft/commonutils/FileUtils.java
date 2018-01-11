/*
 * Copyright (c) 2018 S Praveen Kumar
 *
 * Licensed under MIT license
 */

package com.arunsoft.commonutils;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Utilities for working with files
 * <p>
 * This class consists of static methods for working with files.
 * Methods for copying, reading and writing files are available.
 * Most methods indirectly use methods from StreamUtils. This is just for convenience. :)
 */
public class FileUtils {
    /**
     * Copies File from src to dest
     *
     * @param src  The source file (File to be copied)
     * @param dest The destination file
     * @throws IOException Thrown when there is an error either in reading or writing the file
     */
    public static void copy(@NonNull File src, @NonNull File dest) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dest);
        StreamUtils.copy(inStream, outStream);
        inStream.close();
        outStream.close();
    }

    /**
     * Sane as copy(File src, File dest) but this takes the string path as argument
     *
     * @param srcPath  Path of the source file
     * @param destPath Path of the destination file
     * @throws IOException Thrown when there is an error either in reading or writing the file
     */
    public static void copy(@NonNull String srcPath, @NonNull String destPath) throws IOException {
        FileInputStream inStream = new FileInputStream(srcPath);
        FileOutputStream outStream = new FileOutputStream(destPath);
        StreamUtils.copy(inStream, outStream);
        inStream.close();
        outStream.close();
    }

    /**
     * Reads text file and returns the text as a String. This assumes, default encoding.
     *
     * @param in The file to be read
     * @return The String containing the content of the file
     * @throws IOException Thrown when there is an error reading the file
     */
    public static String read(@NonNull File in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        reader.close();
        return sb.toString();
    }

    /**
     * Same as read(File) but takes the path of the file as argument
     *
     * @param path The path of the file to be read
     * @return The String containing the content of the file
     * @throws IOException Thrown when there is an error reading the file
     */
    public static String read(@NonNull String path) throws IOException {
        return read(new File(path));
    }

    /**
     * Reads the contents of the text file and returns them as array of lines.
     *
     * @param in The text file to be read from
     * @return An array of strings where each element represents a line in the file.
     * @throws IOException Thrown when there is an error reading the file
     */
    public static String[] readLines(@NonNull File in) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * Same as readLines(File) but takes the path of the file as argument
     *
     * @param path Path of the text file to be read from
     * @return An array of strings where each element represents a line in the file.
     * @throws IOException Thrown when there is an error reading the file
     */
    public static String[] readLines(@NonNull String path) throws IOException {
        return readLines(new File(path));
    }

    /**
     * Writes the given String content to the text file.
     *
     * @param out     The file to be written to
     * @param content The text to be written
     * @throws IOException Thrown when there is an error writing to the file.
     */
    public static void write(@NonNull File out, String content) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(out);
        StreamUtils.write(outputStream, content);
        outputStream.close();
    }
}
