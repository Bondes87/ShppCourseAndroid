package com.dbondarenko.shpp.personalnotes.database.sqlitebase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dbondarenko.shpp.personalnotes.models.NoteSQLiteModel;

import java.util.List;

/**
 * File: NoteDao.java
 * Created by Dmitro Bondarenko on 17.11.2017.
 */
@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes " +
            "WHERE userLogin = :userLogin " +
            "ORDER BY datetime DESC " +
            "LIMIT 20 " +
            "OFFSET :startNotesPosition")
    List<NoteSQLiteModel> getNotes(String userLogin, int startNotesPosition);

    @Insert
    void insertNote(NoteSQLiteModel note);

    @Update
    void updateNote(NoteSQLiteModel note);

    @Delete
    void deleteNote(NoteSQLiteModel note);
}
