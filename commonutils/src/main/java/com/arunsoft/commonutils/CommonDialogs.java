/*
 * Copyright (c) 2018 S Praveen Kumar
 */

package com.arunsoft.commonutils;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

public class CommonDialogs {

    public static void InfoDialog(@NonNull Context c, String title, @Nullable String msg, @Nullable String closeText) {
        if (closeText == null)
            closeText = "Close";
        createDialog(c, title, msg).setPositiveButton(closeText, null).create().show();
    }

    public static void ConfirmationDialog(@NonNull Context c, String title, @Nullable String msg, String yesButtonText, String noButtonText, final ResponseListener listener) {
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

    public static void ConfirmationDialog(@NonNull Context c, String title, @Nullable String msg, final ResponseListener listener) {
        ConfirmationDialog(c, title, msg, "Yes", "No", listener);
    }

    public static void InputTextDialog(@NonNull Context c, String title, @Nullable String msg, boolean multiLine, final TextEnteredListener listener) {
        final EditText editText = new EditText(c);
        editText.setSingleLine(!multiLine);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = c.getResources().getDimensionPixelSize(R.dimen.dialog_view_margin);
        params.rightMargin = c.getResources().getDimensionPixelSize(R.dimen.dialog_view_margin);
        FrameLayout layout = new FrameLayout(c);
        layout.addView(editText,params);
        editText.setLayoutParams(params);
        createDialog(c, title, msg).setView(layout).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onTextEntered(editText.getText().toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                listener.onCanceled();
            }
        }).create().show();
    }

    public abstract static class ResponseListener {
        public abstract void onResponse(boolean response);
    }

    public abstract static class TextEnteredListener {
        public abstract void onTextEntered(String text);

        public abstract void onCanceled();
    }

    private static AlertDialog.Builder createDialog(@NonNull Context c, String title,@Nullable String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c).setTitle(title);
        if(msg!=null)
            builder.setMessage(msg);
        return builder;
    }
}
