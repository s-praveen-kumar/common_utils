/*
 * Copyright (c) 2018 S Praveen Kumar
 */

package com.arunsoft.commonutils;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

public class CommonDialogs {

    public static void InfoDialog(@NonNull Context c, String title, String msg, @Nullable String closeText) {
        if (closeText == null)
            closeText = "Close";
        createDialog(c, title, msg).setPositiveButton(closeText, null).create().show();
    }

    public static void ConfirmationDialog(@NonNull Context c, String title, String msg, String yesButtonText, String noButtonText, final ResponseListener listener) {
        createDialog(c, title, msg).setPositiveButton(yesButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onResponse(true);
            }
        }).setNegativeButton(noButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onResponse(false);
            }
        }).setCancelable(false).create().show();
    }

    public static void ConfirmationDialog(@NonNull Context c, String title, String msg, final ResponseListener listener) {
        ConfirmationDialog(c, title, msg, "Yes", "No", listener);
    }

    public static void InputTextDialog(@NonNull Context c, String title, String msg, boolean multiLine, final TextEnteredListener listener) {
        final EditText editText = new EditText(c);
        editText.setSingleLine(!multiLine);
        createDialog(c, title, msg).setView(editText).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onTextEntered(editText.getText().toString());
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                listener.onCanceled();
            }
        }).create().show();
    }

    public abstract static class ResponseListener {
        abstract void onResponse(boolean response);
    }

    public abstract static class TextEnteredListener {
        abstract void onTextEntered(String text);

        abstract void onCanceled();
    }

    private static AlertDialog.Builder createDialog(@NonNull Context c, String title, String msg) {
        return new AlertDialog.Builder(c).setMessage(msg).setTitle(title);
    }
}
