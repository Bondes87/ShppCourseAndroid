package com.dbondarenko.shpp.personalnotes.models;

/**
 * File: NoteModel.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
public class NoteModel {
    private String userName;
    private Long datetime;
    private String message;

    public NoteModel(String userName, Long datetime, String message) {
        this.userName = userName;
        this.datetime = datetime;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}