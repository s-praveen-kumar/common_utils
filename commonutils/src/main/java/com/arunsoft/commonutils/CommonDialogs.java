/*
 * Copyright (c) 2018 S Praveen Kumar
 *
 * Licensed under MIT license
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
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Commonly used dialogs
 * <p>
 * This class consists of static methods for creating commonly used dialogs with ease.
 * Can be used to create info dialogs, confirmation dialogs and most importantly text input dialog.
 * All Dialogs are created and shown immediately and asynchronously.
 */
public class CommonDialogs {

    /**
     * Simple dialog to show information to the user.
     *
     * @param c         The context for creating dialog
     * @param title     Dialog title
     * @param msg       Message to be shown on the dialog (could be null)
     * @param closeText Text for the close button (default - close)
     */
    public static void infoDialog(@NonNull Context c, String title, @Nullable String msg, @Nullable String closeText) {
        if (closeText == null)
            closeText = "Close";
        createDialog(c, title, msg).setPositiveButton(closeText, null).create().show();
    }

    /**
     * Simple dialog with 2 choices. Can be used for confirmation, but it's not limited to this.
     *
     * @param c             The context for creating dialog
     * @param title         Dialog title
     * @param msg           Message to be shown on the dialog (could be null)
     * @param yesButtonText Text for positive button
     * @param noButtonText  Text for negative button
     * @param listener      Listener to get callbacks with the user's response
     */
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

    /**
     * Simple dialog with 2 choices. Can be used for confirmation. It shows a 'Yes' and 'No' button
     *
     * @param c        The context for creating dialog
     * @param title    Dialog title
     * @param msg      Message to be shown on the dialog (could be null)
     * @param listener Listener to get callbacks with the user's response
     */
    public static void confirmationDialog(@NonNull Context c, String title, @Nullable String msg, final ResponseListener listener) {
        confirmationDialog(c, title, msg, "Yes", "No", listener);
    }

    /**
     * Dialog with an EditText. Can be used to get text input from user such as file names.
     * It isn't restricted to text. By setting the right inputType, it can be used for numbers or password
     * It also supports a validator in callback, which you can use to validate user input.
     *
     * @param c         The context for creating dialog
     * @param title     Dialog title
     * @param msg       Message to be shown on the dialog (could be null)
     * @param multiLine Whether or not the user can enter multiline text
     * @param inputType InputType for the EditText. Should be from InputType{android.text.InputType}
     * @param listener  Listener to get callbacks with user's response
     */
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

    /**
     * Dialog with an EditText. Can be used to get text input from user such as file names.
     * It also supports a validator in callback, which you can use to validate user input.
     *
     * @param c         The context for creating dialog
     * @param title     Dialog title
     * @param msg       Message to be shown on the dialog (could be null)
     * @param multiLine Whether or not the user can enter multiline text
     * @param listener  Listener to get callbacks with user's response
     */
    public static void inputTextDialog(@NonNull Context c, String title, @Nullable String msg, boolean multiLine, final TextEnteredListener listener) {
        inputTextDialog(c, title, msg, multiLine, InputType.TYPE_CLASS_TEXT, listener);
    }

    /**
     * Dialog that lets user choose 1 option from multiple. It shows radio buttons letting user to choose one.
     *
     * @param c        The context for creating dialog
     * @param title    Dialog title
     * @param msg      Message to be shown on the dialog (could be null)
     * @param items    The array og the options to display
     * @param selected The currently selected option. Should be within the range of the items array
     * @param listener Listener to get callbacks with user's choice
     */
    public static void singleChoiceListDialog(@NonNull Context c, String title, @Nullable String msg, final String[] items, final int selected, final ItemSelectedListener listener) {
        final int[] choice = {selected};
        createDialog(c, title, msg).setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                choice[0] = i;
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onCancelled();
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onItemSelected(choice[0], items[choice[0]]);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                listener.onCancelled();
            }
        }).create().show();
    }

    public abstract static class ResponseListener {
        /**
         * Called when the user responded
         *
         * @param response The response of the user. true = positive(yes) false = negative(no)
         */
        public abstract void onResponse(boolean response);
    }

    public abstract static class TextEnteredListener {
        /**
         * Called when the user hit the Ok button and the input is validated to be proper
         *
         * @param text The text the user entered
         */
        public abstract void onTextEntered(String text);

        /**
         * This method allows you to validate the user's input.
         * It is called everytime the user hit the Ok button. Return true if the input is valid else false.
         * Also you must take care of showing a message to the user that the input is invalid. If this returns false, the dialog stays.
         * If you choose not to implement this, make sure you return true
         *
         * @param text The text the user entered and needs to be validated
         * @return Return true if the input is va;id, else false.
         */
        public abstract boolean validate(String text);

        /**
         * Called when the user cancelled the operation
         */
        public abstract void onCanceled();
    }

    public abstract static class ItemSelectedListener {
        /**
         * Called when the user selected the option
         *
         * @param pos  Position of the option selected
         * @param item The option the user selected.
         */
        public abstract void onItemSelected(int pos, String item);

        /**
         * Called when the user cancelled the dialog.
         */
        public abstract void onCancelled();
    }

    private static AlertDialog.Builder createDialog(@NonNull Context c, String title, @Nullable String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c).setTitle(title);
        if (msg != null)
            builder.setMessage(msg);
        return builder;
    }
}
