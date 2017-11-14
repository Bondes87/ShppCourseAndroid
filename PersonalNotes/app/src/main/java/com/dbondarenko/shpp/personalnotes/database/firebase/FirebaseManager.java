package com.dbondarenko.shpp.personalnotes.database.firebase;

import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.database.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.models.UserModel;
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

    private FirebaseDatabase firebaseDatabase;
    private OnGetDataListener onGetDataListener;

    public FirebaseManager(OnGetDataListener onGetDataListener) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        this.onGetDataListener = onGetDataListener;
    }

    @Override
    public void addUser(UserModel user) {
        Query queryForIsUserExists = firebaseDatabase
                .getReference()
                .child("users")
                .orderByKey()
                .equalTo(user.getLogin());
        queryForIsUserExists.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    onGetDataListener.onFailed();
                } else {
                    DatabaseReference databaseReferenceForAddUser = firebaseDatabase
                            .getReference().child("users").child(user.getLogin());
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
    public void isUserExists(UserModel user) {
        Query queryForIsUserExists = firebaseDatabase
                .getReference()
                .child("users")
                .orderByKey()
                .equalTo(user.getLogin());
        queryForIsUserExists.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    onGetDataListener.onFailed();
                    return;
                }
                for (DataSnapshot dataUser : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataPassword : dataUser.getChildren()) {
                        if (Objects.equals(dataPassword.getValue(), user.getPassword())) {
                            onGetDataListener.onSuccess();
                            return;
                        }
                    }
                }
                onGetDataListener.onFailed();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
