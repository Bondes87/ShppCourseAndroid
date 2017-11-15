package com.dbondarenko.shpp.personalnotes.database;

/**
 * File: DatabaseManager.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
public interface DatabaseManager {

    void addUser(String login, String password);

    void isUserExists(String login, String password);
}