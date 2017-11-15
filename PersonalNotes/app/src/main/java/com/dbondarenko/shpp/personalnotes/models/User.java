package com.dbondarenko.shpp.personalnotes.models;

/**
 * File: User.java
 * Created by Dmitro Bondarenko on 14.11.2017.
 */
public interface User {

    String getLogin();

    void setLogin(String login);

    String getPassword();

    void setPassword(String password);
}
