package com.dbondarenko.shpp.personalnotes.models;

/**
 * File: Note.java
 * Created by Dmitro Bondarenko on 17.11.2017.
 */
public interface Note {

    String getUserLogin();

    void setUserLogin(String userLogin);

    long getDatetime();

    void setDatetime(long datetime);

    String getMessage();

    void setMessage(String message);
}
