package com.dbondarenko.shpp.personalnotes.models;

/**
 * File: UserModel.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
public class UserModel {
    private String userLogin;
    private String userPassword;

    public UserModel(String userLogin, String userPassword) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}