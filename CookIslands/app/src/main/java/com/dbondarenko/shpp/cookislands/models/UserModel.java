package com.dbondarenko.shpp.cookislands.models;

/**
 * File: UserModel.java
 * Created by Dmitro Bondarenko on 24.10.2017.
 */
public class UserModel {
    private String login;
    private String password;
    private int islandId;

    public UserModel(String login, String password, int islandId) {
        this.login = login;
        this.password = password;
        this.islandId = islandId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIslandId() {
        return islandId;
    }

    public void setIslandId(int islandId) {
        this.islandId = islandId;
    }
}