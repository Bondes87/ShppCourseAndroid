package com.dbondarenko.shpp.personalnotes.listeners;

import com.dbondarenko.shpp.personalnotes.models.NoteModel;

/**
 * File: OnEventNoteListener.java
 * Created by Dmitro Bondarenko on 21.11.2017.
 */
public interface OnEventNoteListener {

    void onAddNote(NoteModel note);

    void onDeleteNote(NoteModel note);
}