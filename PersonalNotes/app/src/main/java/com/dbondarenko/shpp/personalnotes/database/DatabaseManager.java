package com.dbondarenko.shpp.personalnotes.database;

import com.dbondarenko.shpp.personalnotes.models.NoteModel;

/**
 * File: DatabaseManager.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
public interface DatabaseManager {

    void addUser(String login, String password);

    void checkIsUserExists(String login, String password);

    void requestNotes(String userLogin, int startNotesPosition);

    void addNote(NoteModel note);

    void updateNote(NoteModel note);

    void deleteNote(NoteModel note);
}