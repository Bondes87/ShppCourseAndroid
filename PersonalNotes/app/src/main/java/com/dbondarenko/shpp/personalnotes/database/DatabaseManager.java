package com.dbondarenko.shpp.personalnotes.database;

import com.dbondarenko.shpp.personalnotes.models.UserModel;

/**
 * File: DatabaseManager.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
public interface DatabaseManager {

    public boolean addUser(UserModel user);

    public boolean isUserExists(UserModel user);
}

