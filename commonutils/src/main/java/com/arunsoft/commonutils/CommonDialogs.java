/*
 * Copyright (c) 2018 S Praveen Kumar
 */

package com.arunsoft.commonutils;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

public class CommonDialogs {

    public static void InfoDialog(@NonNull Context c, String title, String msg, @Nullable String closeText) {
        if (closeText == null)
            closeText = "Close";
        new AlertDialog.Builder(c).setMessage(msg).setTitle(title).setPositiveButton(closeText, null).create().show();
    }

    public static void ConfirmationDialog(@NonNull Context c, String title, String msg, String yesButtonText, String noButtonText, final ResponseListener listener) {
        new AlertDialog.Builder(c).setTitle(title).setMessage(msg).setPositiveButton(yesButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onResponse(true);
            }
        }).setNegativeButton(noButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onResponse(false);
            }
        }).create().show();
    }

    public static void ConfirmationDialog(@NonNull Context c, String title, String msg, final ResponseListener listener) {
        ConfirmationDialog(c, title, msg, "Yes", "No", listener);
    }

    public abstract static class ResponseListener {
        abstract void onResponse(boolean response);
    }
}
