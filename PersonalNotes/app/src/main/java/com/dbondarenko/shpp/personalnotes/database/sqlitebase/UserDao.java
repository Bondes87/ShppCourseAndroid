package com.dbondarenko.shpp.personalnotes.database.sqlitebase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.dbondarenko.shpp.personalnotes.models.UserModel;

/**
 * File: UserDao.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
@Dao
public interface UserDao {

    @Insert
    void insertUser(UserModel user);

    @Query("SELECT * FROM users WHERE login = :login AND password = :password")
    UserModel getUser(String login, String password);
}
