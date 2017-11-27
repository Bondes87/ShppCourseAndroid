package com.dbondarenko.shpp.personalnotes.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.R;

/**
 * File: DeleteNoteDialogFragment.java
 * Created by Dmitro Bondarenko on 27.11.2017.
 */
public class DeleteNoteDialogFragment extends DialogFragment {

    private static final String LOG_TAG = DeleteNoteDialogFragment.class.getSimpleName();

    public DeleteNoteDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateDialog()");
        AlertDialog.Builder infoDialog = new AlertDialog.Builder(getActivity());
        infoDialog.setTitle(R.string.text_delete_note);
        infoDialog.setMessage(R.string.text_for_dialog_to_delete_note);
        infoDialog.setIcon(R.mipmap.ic_info);
        infoDialog.setPositiveButton(R.string.button_ok,
                (dialog, whichButton) ->
                        getTargetFragment().onActivityResult(getTargetRequestCode(),
                                Activity.RESULT_OK, getActivity().getIntent()));
        infoDialog.setNegativeButton(R.string.button_cancel,
                (dialog, whichButton) ->
                        getTargetFragment().onActivityResult(getTargetRequestCode(),
                                Activity.RESULT_CANCELED, getActivity().getIntent()));
        return infoDialog.create();
    }
}