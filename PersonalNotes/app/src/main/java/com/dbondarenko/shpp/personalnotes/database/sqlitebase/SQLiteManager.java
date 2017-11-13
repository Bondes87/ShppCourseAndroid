package com.dbondarenko.shpp.personalnotes.database.sqlitebase;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.models.UserModel;

/**
 * File: SQLiteManager.java
 * Created by Dmitro Bondarenko on 11.11.2017.
 */
public class SQLiteManager implements DatabaseManager {

    private SQLiteRoomDatabase sQLiteRoomDatabase;

    public SQLiteManager(Context context) {
        sQLiteRoomDatabase = Room.databaseBuilder(context,
                SQLiteRoomDatabase.class, Constants.DATABASE_NAME).build();
    }

    @Override
    public boolean addUser(UserModel user) {
        if (isUserExists(user)) {
            return false;
        }
        sQLiteRoomDatabase.getUserDao().insertUser(user);
        return true;
    }

    @Override
    public boolean isUserExists(UserModel user) {
        return sQLiteRoomDatabase.getUserDao()
                .getUser(user.getLogin(), user.getPassword()) != null;
    }
}
