package com.dbondarenko.shpp.personalnotes.database;

import com.dbondarenko.shpp.personalnotes.models.UserModel;

/**
 * File: DatabaseManager.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
public interface DatabaseManager {

    void addUser(UserModel user);

    void isUserExists(UserModel user);
}

