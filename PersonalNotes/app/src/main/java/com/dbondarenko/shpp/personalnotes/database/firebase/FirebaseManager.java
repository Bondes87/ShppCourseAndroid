package com.dbondarenko.shpp.personalnotes.database.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.listeners.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.models.Note;
import com.dbondarenko.shpp.personalnotes.models.NoteFirebaseModel;
import com.dbondarenko.shpp.personalnotes.models.UserFirebaseModel;
import com.dbondarenko.shpp.personalnotes.utils.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * File: FirebaseManager.java
 * Created by Dmitro Bondarenko on 13.11.2017.
 */
public class FirebaseManager implements DatabaseManager {

    private static final String LOG_TAG = FirebaseManager.class.getSimpleName();

    private FirebaseDatabase firebaseDatabase;
    private OnGetDataListener onGetDataListener;

    public FirebaseManager(OnGetDataListener onGetDataListener) {
        Util.checkForNull(onGetDataListener);
        firebaseDatabase = FirebaseDatabase.getInstance();
        this.onGetDataListener = onGetDataListener;
    }

    @Override
    public void addUser(String login, String password) {
        Log.d(LOG_TAG, "addUser()");
        Util.checkForNull(login, password);
        onGetDataListener.onStart();
        Query queryForIsUserExists = firebaseDatabase
                .getReference()
                .child(Constants.TABLE_USERS)
                .orderByKey()
                .equalTo(login);
        queryForIsUserExists.addListenerForSingleValueEvent(
                getValueEventListenerToAddUser(login, password));
    }

    @Override
    public void checkIsUserExists(String login, String password) {
        Log.d(LOG_TAG, "checkIsUserExists()");
        Util.checkForNull(login, password);
        onGetDataListener.onStart();
        DatabaseReference referenceForIsUserExists = firebaseDatabase
                .getReference()
                .child(Constants.TABLE_USERS)
                .child(login);
        referenceForIsUserExists.addListenerForSingleValueEvent(
                getValueEventListenerToCheckIsUserExists(password));
    }

    @Override
    public void addNote(Note note) {
        Log.d(LOG_TAG, "addNote()");
        Util.checkForNull(note);
        firebaseDatabase.getReference()
                .child(Constants.TABLE_NOTES)
                .child(note.getUserLogin())
                .child(String.valueOf(note.getDatetime()))
                .setValue(note);
        onGetDataListener.onSuccess();
    }

    @Override
    public void updateNote(Note note) {
        Log.d(LOG_TAG, "updateNote()");
        Util.checkForNull(note);
        firebaseDatabase.getReference()
                .child(Constants.TABLE_NOTES)
                .child(note.getUserLogin())
                .child(String.valueOf(note.getDatetime()))
                .child(Constants.COLUMN_NOTES_MESSAGE)
                .setValue(note.getMessage());
        onGetDataListener.onSuccess();
    }

    @Override
    public void deleteNote(Note note) {
        Log.d(LOG_TAG, "deleteNote()");
        Util.checkForNull(note);
        firebaseDatabase.getReference()
                .child(Constants.TABLE_NOTES)
                .child(note.getUserLogin())
                .child(String.valueOf(note.getDatetime()))
                .removeValue();
        onGetDataListener.onSuccess();
    }

    @Override
    public void requestNotes(String userLogin, int startNotesPosition,
                             Note lastNoteFromTheLastDownload) {
        Log.d(LOG_TAG, "requestNotes()");
        Util.checkForNull(userLogin);
        if (startNotesPosition != 0 &&
                startNotesPosition % Constants.MAXIMUM_COUNT_OF_NOTES_TO_LOAD == 0) {
            onGetDataListener.onStart();
        }
        Query queryToDownloadNotes = getQueryToDownloadNotes(
                userLogin,
                startNotesPosition,
                lastNoteFromTheLastDownload);
        queryToDownloadNotes.keepSynced(true);
        queryToDownloadNotes.addListenerForSingleValueEvent(
                getValueEventListenerToDownloadNotes(startNotesPosition));
    }

    @NonNull
    private ValueEventListener getValueEventListenerToCheckIsUserExists(String password) {
        Log.d(LOG_TAG, "getValueEventListenerToCheckIsUserExists()");
        Util.checkForNull(password);
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists() || dataSnapshot.getValue() == null) {
                    onGetDataListener.onFailed();
                    return;
                }
                UserFirebaseModel user = dataSnapshot.getValue(UserFirebaseModel.class);
                if (user != null && Objects.equals(user.getPassword(), password)) {
                    onGetDataListener.onSuccess();
                    return;
                }
                onGetDataListener.onFailed();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    @NonNull
    private ValueEventListener getValueEventListenerToAddUser(String login, String password) {
        Log.d(LOG_TAG, "getValueEventListenerToAddUser()");
        Util.checkForNull(login, password);
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    onGetDataListener.onFailed();
                } else {
                    DatabaseReference databaseReferenceForAddUser = firebaseDatabase
                            .getReference().child(Constants.TABLE_USERS).child(login);
                    UserFirebaseModel user = new UserFirebaseModel(login, password);
                    databaseReferenceForAddUser.setValue(user);
                    onGetDataListener.onSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    @NonNull
    private ValueEventListener getValueEventListenerToDownloadNotes(int startNotesPosition) {
        Log.d(LOG_TAG, "getValueEventListenerToDownloadNotes()");
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists() || dataSnapshot.getValue() == null) {
                    onGetDataListener.onFailed();
                    return;
                }
                GenericTypeIndicator<HashMap<String, NoteFirebaseModel>> mapGenericTypeIndicator =
                        new GenericTypeIndicator<HashMap<String, NoteFirebaseModel>>() {
                        };
                Map<String, NoteFirebaseModel> notesMap =
                        dataSnapshot.getValue(mapGenericTypeIndicator);
                if (notesMap != null) {
                    List<Note> notesList = new ArrayList<>(notesMap.values());
                    sortNotes(notesList);
                    if (startNotesPosition != 0) {
                        notesList.remove(0);
                    }
                    onGetDataListener.onSuccess(notesList);
                    return;
                }
                onGetDataListener.onFailed();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    private Query getQueryToDownloadNotes(String userLogin, int startNotesPosition,
                                          Note lastNoteFromTheLastDownload) {
        Log.d(LOG_TAG, "getQueryToDownloadNotes()");
        Util.checkForNull(userLogin);
        if (startNotesPosition == 0) {
            return firebaseDatabase
                    .getReference()
                    .child(Constants.TABLE_NOTES)
                    .child(userLogin)
                    .orderByKey()
                    .limitToLast(Constants.MAXIMUM_COUNT_OF_NOTES_TO_LOAD);
        } else {
            return firebaseDatabase
                    .getReference()
                    .child(Constants.TABLE_NOTES)
                    .child(userLogin)
                    .orderByKey()
                    .endAt(String.valueOf(lastNoteFromTheLastDownload.getDatetime()))
                    .limitToLast(Constants.MAXIMUM_COUNT_OF_NOTES_TO_LOAD + 1);
        }
    }

    private void sortNotes(List<Note> notesList) {
        Log.d(LOG_TAG, "sortNotes()");
        Util.checkForNull(notesList);
        Collections.sort(notesList, (note1, note2) -> {
            if (note1.getDatetime() == note2.getDatetime()) {
                return 0;
            } else if (note1.getDatetime() < note2.getDatetime()) {
                return 1;
            }
            return -1;
        });
    }
}
