package com.dbondarenko.shpp.cookislands;

/**
 * File: User.java
 * Created by Dmitro Bondarenko on 24.10.2017.
 */
class User {
    private String login;
    private String password;
    private int islandId;

    public User(String login, String password, int islandId) {
        this.login = login;
        this.password = password;
        this.islandId = islandId;
    }

    String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    int getIslandId() {
        return islandId;
    }

    public void setIslandId(int islandId) {
        this.islandId = islandId;
    }
}