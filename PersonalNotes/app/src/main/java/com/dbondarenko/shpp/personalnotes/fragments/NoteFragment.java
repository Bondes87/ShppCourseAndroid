package com.dbondarenko.shpp.personalnotes.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.database.firebase.FirebaseManager;
import com.dbondarenko.shpp.personalnotes.database.sqlitebase.SQLiteManager;
import com.dbondarenko.shpp.personalnotes.listeners.OnEventNoteListener;
import com.dbondarenko.shpp.personalnotes.listeners.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.models.NoteModel;
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

    private OnEventNoteListener onEventNoteListener;
    private DatabaseManager databaseManager;
    private NoteModel note;
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
        return viewContent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.itemSaveNote:
                String message = editTextMessage.getText().toString();
                if (note == null) {
                    String userLogin = SharedPreferencesManager.getSharedPreferencesManager()
                            .getUser(getContext().getApplicationContext()).getLogin();
                    NoteModel newNote = new NoteModel(userLogin, datetime, message);
                    databaseManager.addNote(newNote);
                    onEventNoteListener.onAddNote(newNote);
                } else {
                    note.setMessage(message);
                    databaseManager.updateNote(note);
                }
                return true;
            case R.id.itemDeleteNote:
                showNotesListFragment();
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
            public void onSuccess() {
                Log.d(LOG_TAG, "onSuccess()");
                showNotesListFragment();
            }

            @Override
            public void onSuccess(List<NoteModel> notes) {
                Log.d(LOG_TAG, "onSuccess()");
            }

            @Override
            public void onFailed() {
                Log.d(LOG_TAG, "onFailed()");
            }
        };
    }

    private void showNotesListFragment() {
        Log.d(LOG_TAG, "showRegisterFragment()");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }
}