package com.dbondarenko.shpp.personalnotes.database.firebase;

import android.util.Log;

import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.listeners.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.models.NoteModel;
import com.dbondarenko.shpp.personalnotes.models.UserFirebaseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        firebaseDatabase = FirebaseDatabase.getInstance();
        this.onGetDataListener = onGetDataListener;
    }

    @Override
    public void addUser(String login, String password) {
        Log.d(LOG_TAG, "addUser()");
        Query queryForIsUserExists = firebaseDatabase
                .getReference()
                .child("users")
                .orderByKey()
                .equalTo(login);
        queryForIsUserExists.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    onGetDataListener.onFailed();
                } else {
                    DatabaseReference databaseReferenceForAddUser = firebaseDatabase
                            .getReference().child("users").child(login);
                    UserFirebaseModel user = new UserFirebaseModel(login, password);
                    databaseReferenceForAddUser.setValue(user);
                    onGetDataListener.onSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void checkIsUserExists(String login, String password) {
        Log.d(LOG_TAG, "checkIsUserExists()");
        DatabaseReference referenceForIsUserExists = firebaseDatabase
                .getReference()
                .child("users")
                .child(login);
        referenceForIsUserExists.addListenerForSingleValueEvent(new ValueEventListener() {
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
        });
    }

    @Override
    public void addNote(NoteModel note) {
        Log.d(LOG_TAG, "addNote()");
    }

    @Override
    public void updateNote(NoteModel note) {
        Log.d(LOG_TAG, "updateNote()");
    }

    @Override
    public void deleteNote(NoteModel note) {
        Log.d(LOG_TAG, "deleteNote()");
    }

    @Override
    public void requestNotes(String userLogin, int startNotesPosition) {
        Log.d(LOG_TAG, "requestNotes()");
    }
}