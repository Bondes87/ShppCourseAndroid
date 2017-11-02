package com.dbondarenko.shpp.cookislands;

/**
 * File: Constants.java
 * The class that contains all application constants.
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
    public static final String COLUMN_ISLAND_URL = "islandUrl";

    // Name of columns of the user table.
    private static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_LOGIN = "userLogin";
    public static final String COLUMN_USER_PASSWORD = "userPassword";
    public static final String COLUMN_USER_ISLAND_ID = "islandId";

    // Commands for creating database tables.
    public static final String KEYS_TABLE_ISLANDS_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_ISLANDS + " (" +
            COLUMN_ISLAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ISLAND_NAME + " TEXT NOT NULL, " +
            COLUMN_ISLAND_URL + " TEXT NOT NULL" + ");";
    public static final String KEYS_TABLE_USERS_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_LOGIN + " TEXT NOT NULL, " +
            COLUMN_USER_PASSWORD + " TEXT NOT NULL, " +
            COLUMN_USER_ISLAND_ID + " INTEGER NOT NULL" + ");";

    // Information about the file from which the list of islands is read.
    public static final String FILE_NAME = "CookIslands.txt";
    public static final String UTF_8_ENCODING = "UTF-8";

    // Key and tags for creating information dialog fragments.
    public static final String KEY_DIALOG_MESSAGE = "KeyDialogMessage";
    public static final String TAG_OF_INFO_DIALOG_FRAGMENT_FOR_LOGIN =
            "TagOfInfoDialogFragmentForLogin";
    public static final String TAG_OF_INFO_DIALOG_FRAGMENT_FOR_PASSWORD =
            "TagOfInfoDialogFragmentForPassword";

    // Information for validate of login and password.
    public static final String LOGIN_AND_PASSWORD_PATTERN = "[a-zA-Z0-9]{1,256}";
    public static final int MIN_LENGTH_LOGIN = 6;
    public static final int MIN_LENGTH_PASSWORD = 8;
    public static final int MAX_LENGTH_LOGIN_OR_PASSWORD = 30;

    // Key for saving and retrieving information about the user's login to the system.
    public static final String KEY_USER_LOGGED_IN = "UserLoggedIn";
    public static final String KEY_USER_ISLAND_ID = "UserIslandId";

    // Key for obtaining the page number for displaying the required information in pageView.
    public static final String KEY_PAGE_URL = "pageUrl";

    // User agent string for setting up an WebView.
    public static final String USER_AGENT_STRING = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
}