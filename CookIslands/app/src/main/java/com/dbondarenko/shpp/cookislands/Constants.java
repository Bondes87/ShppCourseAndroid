package com.dbondarenko.shpp.cookislands;

/**
 * File: Constants.java
 * Created by Dmitro Bondarenko on 26.10.2017.
 */
public class Constants {
    // Information about the database.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cookislands.db";

    // The names of database tables.
    public static final String TABLE_ISLANDS = "islands";
    public static final String TABLE_USERS = "users";

    // Name of columns of the island table.
    public static final String COLUMN_ISLAND_ID = "id";
    public static final String COLUMN_ISLAND_NAME = "islandName";

    // Name of columns of the user table.
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_LOGIN = "userLogin";
    public static final String COLUMN_USER_PASSWORD = "userPassword";
    public static final String COLUMN_USER_ISLAND_ID = "islandId";

    // Commands for creating database tables.
    public static final String KEYS_TABLE_ISLANDS_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_ISLANDS + " (" +
            COLUMN_ISLAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ISLAND_NAME + " TEXT NOT NULL" + ");";
    public static final String KEYS_TABLE_USERS_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_LOGIN + " TEXT NOT NULL, " +
            COLUMN_USER_PASSWORD + " TEXT NOT NULL, " +
            COLUMN_USER_ISLAND_ID + " INTEGER NOT NULL" + ");";

    // Information about the file from which the list of islands is read
    public static final String FILE_NAME = "CookIslands.txt";
    public static final String UTF_8_ENCODING = "UTF-8";

    public static final String KEY_DIALOG_MESSAGE = "KeyDialogMessage";
    public static final String TAG_OF_INFO_DIALOG_FRAGMENT_FOR_LOGIN =
            "TagOfInfoDialogFragmentForLogin";
    public static final String TAG_OF_INFO_DIALOG_FRAGMENT_FOR_PASSWORD =
            "TagOfInfoDialogFragmentForPassword";
}