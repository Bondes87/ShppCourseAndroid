package com.dbondarenko.shpp.personalnotes.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

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

    @BindView(R.id.textViewDatetime)
    TextView textViewDatetime;
    @BindView(R.id.editTextMessage)
    EditText editTextMessage;
    @BindView(R.id.progressBarActionsWithNote)
    ProgressBar progressBarActionsWithNote;

    private OnEventNoteListener onEventNoteListener;
    private DatabaseManager databaseManager;
    private Note note;
    private long datetime;

    public NoteFragment() {
    }

    @Override
    public void onAttach(Context context) {
        Log.d(LOG_TAG, "onAttach()");
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
            note = getArguments().getParcelable(Constants.KEY_NOTE);
        }
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
            textViewDatetime.setText(Util.getStringDatetime(note.getDatetime()));
            editTextMessage.setText(note.getMessage());
        } else {
            datetime = Calendar.getInstance().getTimeInMillis();
            textViewDatetime.setText(Util.getStringDatetime(datetime));
        }
        editTextMessage.requestFocus();
        if (editTextMessage.isFocused()) {
            Util.showSoftKeyboard(getContext().getApplicationContext());
        }
        return viewContent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.itemSaveNote:
                String message = editTextMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    reportNoteIsEmpty();
                    return true;
                }
                saveNote(message);
                Util.hideSoftKeyboard(getContext().getApplicationContext(), getView());
                return true;
            case R.id.itemDeleteNote:
                deleteNote();
                Util.hideSoftKeyboard(getContext().getApplicationContext(), getView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    private void deleteNote() {
        Log.d(LOG_TAG, "deleteNote()");
        if (note != null) {
            onEventNoteListener.onDeleteNote(note);
            databaseManager.deleteNote(note);
        }
    }

    private void saveNote(String message) {
        Log.d(LOG_TAG, "saveNote()");
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
                progressBarActionsWithNote.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess() {
                progressBarActionsWithNote.setVisibility(View.GONE);
                Log.d(LOG_TAG, "onSuccess()");
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

    private void reportNoteIsEmpty() {
        Log.d(LOG_TAG, "reportAnError()");
        View layoutView = getView();
        Snackbar snackbar;
        if (layoutView != null) {
            snackbar = Snackbar.make(layoutView,
                    getString(R.string.error_note_is_empty),
                    Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
            snackbar.show();
        }
    }
}