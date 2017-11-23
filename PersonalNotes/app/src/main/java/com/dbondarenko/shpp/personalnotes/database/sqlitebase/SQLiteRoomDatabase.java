package com.dbondarenko.shpp.personalnotes.database.sqlitebase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.models.NoteSQLiteModel;
import com.dbondarenko.shpp.personalnotes.models.UserSQLiteModel;

/**
 * File: SQLiteRoomDatabase.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
@Database(entities = {UserSQLiteModel.class, NoteSQLiteModel.class},
        version = Constants.DATABASE_VERSION,
        exportSchema = false)

public abstract class SQLiteRoomDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();

    public abstract NoteDao getNoteDao();
}