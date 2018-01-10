/*
 * Copyright (c) 2018 S Praveen Kumar
 */

package com.arunsoft.commonutils;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;

public class CommonDialogs {

    public static void infoDialog(@NonNull Context c, String title, @Nullable String msg, @Nullable String closeText) {
        if (closeText == null)
            closeText = "Close";
        createDialog(c, title, msg).setPositiveButton(closeText, null).create().show();
    }

    public static void confirmationDialog(@NonNull Context c, String title, @Nullable String msg, String yesButtonText, String noButtonText, final ResponseListener listener) {
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

    public static void confirmationDialog(@NonNull Context c, String title, @Nullable String msg, final ResponseListener listener) {
        confirmationDialog(c, title, msg, "Yes", "No", listener);
    }

    public static void inputTextDialog(@NonNull Context c, String title, @Nullable String msg, boolean multiLine, int inputType, final TextEnteredListener listener) {
        final EditText editText = new EditText(c);
        editText.setSingleLine(!multiLine);
        editText.setInputType(inputType);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = c.getResources().getDimensionPixelSize(R.dimen.dialog_view_margin);
        params.rightMargin = c.getResources().getDimensionPixelSize(R.dimen.dialog_view_margin);
        FrameLayout layout = new FrameLayout(c);
        layout.addView(editText, params);
        editText.setLayoutParams(params);
        final AlertDialog dialog = createDialog(c, title, msg).setView(layout).setPositiveButton("Ok", null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                listener.onCanceled();
            }
        }).create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener.validate(editText.getText().toString())) {
                            listener.onTextEntered(editText.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    public static void inputTextDialog(@NonNull Context c, String title, @Nullable String msg, boolean multiLine, final TextEnteredListener listener) {
        inputTextDialog(c, title, msg, multiLine, InputType.TYPE_CLASS_TEXT, listener);
    }

    public static void singleChoiceListDialog(@NonNull Context c, String title, @Nullable String msg, final String[] items, final int selected, final ItemSelectedListener listener){
        final int[] choice = {selected};
        createDialog(c,title,msg).setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                choice[0] = selected;
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onCancelled();
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onItemSelected(choice[0],items[choice[0]]);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                listener.onCancelled();
            }
        }).create().show();
    }

    public abstract static class ResponseListener {
        public abstract void onResponse(boolean response);
    }

    public abstract static class TextEnteredListener {
        public abstract void onTextEntered(String text);

        public abstract boolean validate(String text);

        public abstract void onCanceled();
    }

    public abstract static class ItemSelectedListener{
        public abstract void onItemSelected(int pos, String item);
        public abstract void onCancelled();
    }

    private static AlertDialog.Builder createDialog(@NonNull Context c, String title, @Nullable String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c).setTitle(title);
        if (msg != null)
            builder.setMessage(msg);
        return builder;
    }
}
