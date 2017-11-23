package com.dbondarenko.shpp.personalnotes.models;

/**
 * File: Note.java
 * Created by Dmitro Bondarenko on 17.11.2017.
 */
public interface Note {

    String getUserLogin();

    long getDatetime();

    String getMessage();

    void setMessage(String message);
}
