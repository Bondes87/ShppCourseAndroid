package com.dbondarenko.shpp.personalnotes.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.utils.Util;

/**
 * File: InfoDialogFragment.java
 * The fragment that displays a message in the form of a dialog box.
 * Created by Dmitro Bondarenko on 08.11.2017.
 */
public class InfoDialogFragment extends DialogFragment {

    private static final String LOG_TAG = InfoDialogFragment.class.getSimpleName();

    private String dialogMessage;

    public InfoDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            dialogMessage = args.getString(Constants.KEY_DIALOG_MESSAGE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateDialog()");
        AlertDialog.Builder infoDialog = new AlertDialog.Builder(getActivity());
        infoDialog.setTitle(R.string.text_hint);
        infoDialog.setMessage(dialogMessage);
        infoDialog.setPositiveButton(android.R.string.ok, (dialog, whichButton) -> {
        });
        return infoDialog.create();
    }

    public static InfoDialogFragment newInstance(String dialogMessage) {
        Log.d(LOG_TAG, "newInstance()");
        Util.checkForNull(dialogMessage);
        InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_DIALOG_MESSAGE, dialogMessage);
        infoDialogFragment.setArguments(args);
        return infoDialogFragment;
    }
}