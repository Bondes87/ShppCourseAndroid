package com.dbondarenko.shpp.personalnotes.database;

import com.dbondarenko.shpp.personalnotes.models.Note;

/**
 * File: DatabaseManager.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
public interface DatabaseManager {

    void addUser(String login, String password);

    void checkIsUserExists(String login, String password);

    void requestNotes(String userLogin, int startNotesPosition,
                      Note lastNoteFromTheLastDownload);

    void addNote(Note note);

    void updateNote(Note note);

    void deleteNote(Note note);
}