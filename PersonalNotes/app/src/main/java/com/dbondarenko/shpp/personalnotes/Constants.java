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
    public static final String TABLE_USERS = "users";
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_USER_LOGIN = "login";
    public static final String COLUMN_NOTES_USERLOGIN = "userLogin";
    public static final String COLUMN_NOTES_MESSAGE = "message";

    // Information for validate of login and password.
    public static final String PATTERN_LOGIN_AND_PASSWORD = "[a-zA-Z0-9]{1,256}";
    public static final int MIN_LENGTH_LOGIN = 6;
    public static final int MIN_LENGTH_PASSWORD = 8;
    public static final int MAX_LENGTH_LOGIN_OR_PASSWORD = 30;

    // Key for saving and retrieving information about the use database.
    public static final String KEY_USE_DATABASE = "UseDatabase";

    // Keys for saving and retrieving information about the user who logged in.
    public static final String KEY_USER_LOGIN = "UserLogin";
    public static final String KEY_USER_PASSWORD = "UserPassword";

    // The identifiers of messages for the handler.
    public static final int ID_OF_BOOLEAN_RESULT = 1;
    public static final int ID_OF_RESULT_WITH_LIST = 2;

    // Keys for obtaining results in the handler.
    public static final String KEY_FOR_RESULT_WITH_LIST = "KeyForResultWithList";
    public static final String KEY_FOR_BOOLEAN_RESULT = "KeyForBooleanResult";

    // The keys for getting a note and note position from the arguments of the fragment.
    public static final String KEY_NOTE = "KeyNote";
    public static final String KEY_NOTE_POSITION = "KeyNotePosition";

    // The patterns for displaying the date and time on the screen.
    public static final String PATTERN_DATETIME = "MMM dd yyyy, HH:mm";
    public static final String PATTERN_DATE = "dd-MM-yyyy";
    public static final String PATTERN_TIME = "HH:mm";

    // The tags of fragments.
    public static final String TAG_OF_NOTES_LIST_FRAGMENT = "TagNotesListFragment";

    // Key and tags for creating information dialog fragments.
    public static final String KEY_DIALOG_MESSAGE = "KeyDialogMessage";
    public static final String TAG_OF_INFO_DIALOG_FRAGMENT_FOR_LOGIN =
            "TagOfInfoDialogFragmentForLogin";
    public static final String TAG_OF_INFO_DIALOG_FRAGMENT_FOR_PASSWORD =
            "TagOfInfoDialogFragmentForPassword";

    // Request code and tag for creating dialog fragment for confirmation to delete note.
    public static final String TAG_OF_Delete_Note_Dialog_Fragment = "DeleteNoteDialogFragment";
    public static final int REQUEST_CODE_FOR_DIALOG_FRAGMENT = 1;

    // The minimum number of items to have below your current scroll position
    // before loading more.
    public static final int VISIBLE_THRESHOLD = 10;
    public static final int MAXIMUM_COUNT_OF_NOTES_TO_LOAD = 20;

    // The identifiers of holders type.
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
}