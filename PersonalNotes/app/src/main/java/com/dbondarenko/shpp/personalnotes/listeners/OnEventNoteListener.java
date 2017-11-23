package com.dbondarenko.shpp.personalnotes.listeners;

import com.dbondarenko.shpp.personalnotes.models.Note;

/**
 * File: OnEventNoteListener.java
 * Created by Dmitro Bondarenko on 21.11.2017.
 */
public interface OnEventNoteListener {

    void onAddNote(Note note);

    void onDeleteNote(Note note);
}