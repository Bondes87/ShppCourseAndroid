package com.dbondarenko.shpp.personalnotes;

/**
 * File: Constants.java
 * The class that contains all application constants.
 * Created by Dmitro Bondarenko on 26.10.2017.
 */
public class Constants {

    // Information about the database.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "personalnotes.db";

    // Key and tags for creating information dialog fragments.
    public static final String KEY_DIALOG_MESSAGE = "KeyDialogMessage";
    public static final String TAG_OF_INFO_DIALOG_FRAGMENT_FOR_LOGIN =
            "TagOfInfoDialogFragmentForLogin";
    public static final String TAG_OF_INFO_DIALOG_FRAGMENT_FOR_PASSWORD =
            "TagOfInfoDialogFragmentForPassword";

    // Commands to change the user interface.
    public static final String COMMAND_FOR_RUN_INFO_DIALOG_FRAGMENT_FOR_LOGIN =
            "RunInfoDialogFragmentForLogin";
    public static final String COMMAND_FOR_RUN_INFO_DIALOG_FRAGMENT_FOR_PASSWORD =
            "RunInfoDialogFragmentForPassword";
    public static final String COMMAND_FOR_RUN_REGISTER_FRAGMENT = "RunRegisterFragment";
    public static final String COMMAND_FOR_RUN_CONTENT_ACTIVITY = "RunContentActivity";

    // Information for validate of login and password.
    public static final String LOGIN_AND_PASSWORD_PATTERN = "[a-zA-Z0-9]{1,256}";
    public static final int MIN_LENGTH_LOGIN = 6;
    public static final int MIN_LENGTH_PASSWORD = 8;
    public static final int MAX_LENGTH_LOGIN_OR_PASSWORD = 30;

    // Commands for the asynchronous task loaders.
    public static final String COMMAND_ADD_USER = "AddUser";
    public static final String COMMAND_IS_USER_EXIST = "IsUserExists";

    // Key for saving and retrieving information about the use database.
    public static final String KEY_USE_DATABASE = "UseDatabase";

    // Keys for saving and retrieving information about the user who logged in.
    public static final String KEY_USER_LOGIN = "UserLogin";
    public static final String KEY_USER_PASSWORD = "UserPassword";
}