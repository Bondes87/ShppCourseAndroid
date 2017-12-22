package com.dbondarenko.shpp.personalnotes;

/**
 * File: ConstantsTest.java
 * Created by Dmitro Bondarenko on 21.12.2017.
 */
public class ConstantsTest {
    public static final String INCORRECT_LOGIN = "linux";
    public static final String INCORRECT_PASSWORD = "123456";

    public static final String INCORRECT_LOGIN_WRONG_FORMAT = "a.n,droid";
    public static final String INCORRECT_PASSWORD_WRONG_FORMAT = "1.2,3456789";

    public static final String INCORRECT_LOGIN_INSUFFICIENT_CHARACTERS = "mvp";
    public static final String INCORRECT_PASSWORD_INSUFFICIENT_CHARACTERS = "mvvm";

    public static final String CORRECT_LOGIN = "android";
    public static final String CORRECT_PASSWORD = "123456789";

    public static final String USED_LOGIN = "bondes";
    public static final String USED_PASSWORD = "12345678";

    public static final String NEW_TEXT_NOTE = "new";

    public static final int MINIMUM_LENGTH_OF_CORRECT_LOGIN = 6;
    public static final int MINIMUM_LENGTH_OF_CORRECT_PASSWORD = 8;

    public static final int COUNT_OF_NOTES_TO_CREATE = 20;

    public static final int FIRST_POSITION_OF_LIST_ITEM_TO_CHANGE = 0;
    public static final int SECOND_POSITION_OF_LIST_ITEM_TO_CHANGE = 4;
    public static final int THIRD_POSITION_OF_LIST_ITEM_TO_CHANGE = 12;
    public static final int FOURTH_POSITION_OF_LIST_ITEM_TO_CHANGE = 16;

    public static final int ONE_SECOND = 1000;

    public static final String CHARACTERS_FOR_CREATING_LOGIN_OR_PASSWORD =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890";
}
