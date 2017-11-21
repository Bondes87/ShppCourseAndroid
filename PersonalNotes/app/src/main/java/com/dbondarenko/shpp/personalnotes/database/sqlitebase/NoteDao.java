package com.dbondarenko.shpp.personalnotes.database.sqlitebase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dbondarenko.shpp.personalnotes.models.NoteModel;

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
    List<NoteModel> getNotes(String userLogin, int startNotesPosition);

    @Insert
    void insertNote(NoteModel note);

    @Update
    void updateNote(NoteModel note);

    @Delete
    void deleteNote(NoteModel note);
}
