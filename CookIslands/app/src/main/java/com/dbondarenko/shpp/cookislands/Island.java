package com.dbondarenko.shpp.cookislands;

/**
 * File: Island.java
 * Created by Dmitro Bondarenko on 24.10.2017.
 */
class Island {
    private int id;
    private String name;

    Island(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}