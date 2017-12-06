package com.dbondarenko.shpp.personalnotes.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.database.firebase.FirebaseManager;
import com.dbondarenko.shpp.personalnotes.database.sqlitebase.SQLiteManager;
import com.dbondarenko.shpp.personalnotes.listeners.OnEventNoteListener;
import com.dbondarenko.shpp.personalnotes.listeners.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.models.Note;
import com.dbondarenko.shpp.personalnotes.models.NoteFirebaseModel;
import com.dbondarenko.shpp.personalnotes.models.NoteSQLiteModel;
import com.dbondarenko.shpp.personalnotes.utils.SharedPreferencesManager;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteFragment extends Fragment {

    private static final String LOG_TAG = NoteFragment.class.getSimpleName();

    @BindView(R.id.editTextMessage)
    EditText editTextMessage;
    @BindView(R.id.progressBarActionsWithNote)
    ProgressBar progressBarActionsWithNote;

    private OnEventNoteListener onEventNoteListener;
    private DatabaseManager databaseManager;
    private Note note;
    private long datetime;
    private int notePosition;

    public NoteFragment() {
    }

    @Override
    public void onAttach(Context context) {
        Log.d(LOG_TAG, "onAttach()");
        Util.checkForNull(context);
        super.onAttach(context);
        if (context instanceof OnEventNoteListener) {
            onEventNoteListener = (OnEventNoteListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            note = bundle.getParcelable(Constants.KEY_NOTE);
            notePosition = bundle.getInt(Constants.KEY_NOTE_POSITION);
        }
        Util.enableBackStackButton(
                ((AppCompatActivity) getContext()).getSupportActionBar(),
                true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View viewContent = inflater.inflate(R.layout.fragment_note, container,
                false);
        ButterKnife.bind(this, viewContent);
        initDatabase();
        if (note != null) {
            Util.setTitleForActionBar(
                    ((AppCompatActivity) getContext()).getSupportActionBar(),
                    Util.getStringDatetime(note.getDatetime()));
            editTextMessage.setText(note.getMessage());
        } else {
            datetime = Calendar.getInstance().getTimeInMillis();
            Util.setTitleForActionBar(
                    ((AppCompatActivity) getContext()).getSupportActionBar(),
                    Util.getStringDatetime(datetime));
        }
        editTextMessage.requestFocus();
        Util.showSoftKeyboard(getContext().getApplicationContext());
        return viewContent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.itemSaveNote:
                String message = editTextMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    Util.reportAnError(getView(),
                            getString(R.string.error_note_is_empty));
                    return true;
                }
                saveNote(message);
                Util.hideSoftKeyboard(getContext().getApplicationContext(), getView());
                return true;
            case R.id.itemDeleteNote:
                Util.hideSoftKeyboard(getContext().getApplicationContext(), getView());
                showDeleteNoteDialogFragment();
                return true;
            case android.R.id.home:
                Util.hideSoftKeyboard(getContext().getApplicationContext(), getView());
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult()");
        if (requestCode == Constants.REQUEST_CODE_FOR_DIALOG_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                deleteNote();
                return;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Util.showSoftKeyboard(getContext().getApplicationContext());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        inflater.inflate(R.menu.fragment_note_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onPrepareOptionsMenu()");
        super.onPrepareOptionsMenu(menu);
        if (note == null) {
            menu.findItem(R.id.itemDeleteNote).setVisible(false);
        }
    }

    private void showDeleteNoteDialogFragment() {
        Log.d(LOG_TAG, "showDeleteNoteDialogFragment()");
        DeleteNoteDialogFragment deleteNoteDialogFragmentFrag =
                new DeleteNoteDialogFragment();
        deleteNoteDialogFragmentFrag.setTargetFragment(
                this, Constants.REQUEST_CODE_FOR_DIALOG_FRAGMENT);
        deleteNoteDialogFragmentFrag.show(
                getFragmentManager(), Constants.TAG_OF_Delete_Note_Dialog_Fragment);
    }

    private void deleteNote() {
        Log.d(LOG_TAG, "deleteNote()");
        if (note != null) {
            onEventNoteListener.onDeleteNote(notePosition);
            databaseManager.deleteNote(note);
        }
    }

    private void saveNote(String message) {
        Log.d(LOG_TAG, "saveNote()");
        Util.checkForNull(message);
        if (note == null) {
            String userLogin = SharedPreferencesManager.getSharedPreferencesManager()
                    .getUser(getContext().getApplicationContext()).getLogin();
            Note newNote = createNote(message, userLogin);
            databaseManager.addNote(newNote);
            onEventNoteListener.onAddNote(newNote);
        } else {
            note.setMessage(message);
            databaseManager.updateNote(note);
        }
    }

    @NonNull
    private Note createNote(String message, String userLogin) {
        Log.d(LOG_TAG, "createNote()");
        Util.checkForNull(message, userLogin);
        if (SharedPreferencesManager.getSharedPreferencesManager()
                .isUseFirebase(getContext().getApplicationContext())) {
            return new NoteFirebaseModel(userLogin, datetime, message);
        } else {
            return new NoteSQLiteModel(userLogin, datetime, message);
        }
    }

    private void initDatabase() {
        Log.d(LOG_TAG, "initDatabase()");
        if (SharedPreferencesManager.getSharedPreferencesManager().isUseFirebase(
                getContext().getApplicationContext())) {
            databaseManager = new FirebaseManager(getDataListener());
        } else {
            databaseManager = new SQLiteManager(
                    getContext().getApplicationContext(), getDataListener()
            );
        }
    }

    @NonNull
    private OnGetDataListener getDataListener() {
        Log.d(LOG_TAG, "getDataListener()");
        return new OnGetDataListener() {
            @Override
            public void onStart() {
                Log.d(LOG_TAG, "onStart()");
                progressBarActionsWithNote.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess() {
                Log.d(LOG_TAG, "onSuccess()");
                progressBarActionsWithNote.setVisibility(View.GONE);
                getFragmentManager().popBackStack();
            }

            @Override
            public void onFailed() {
                Log.d(LOG_TAG, "onFailed()");
            }

            @Override
            public void onSuccess(List<Note> notes) {
                Log.d(LOG_TAG, "onSuccess()");
            }
        };
    }
}