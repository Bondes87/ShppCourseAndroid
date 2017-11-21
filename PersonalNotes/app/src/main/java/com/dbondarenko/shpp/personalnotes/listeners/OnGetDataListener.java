package com.dbondarenko.shpp.personalnotes.listeners;

import com.dbondarenko.shpp.personalnotes.models.NoteModel;

import java.util.List;

/**
 * File: OnGetDataListener.java
 * Created by Dmitro Bondarenko on 14.11.2017.
 */
public interface OnGetDataListener {

    void onSuccess();

    void onSuccess(List<NoteModel> notes);

    void onFailed();
}