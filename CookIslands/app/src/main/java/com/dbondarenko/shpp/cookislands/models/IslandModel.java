package com.dbondarenko.shpp.cookislands.models;

/**
 * File: IslandModel.java
 * Created by Dmitro Bondarenko on 24.10.2017.
 */
public class IslandModel {
    private int id;
    private String name;
    private String url;

    public IslandModel(String name, String url) {
        this(0, name, url);
    }

    public IslandModel(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return "IslandModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
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