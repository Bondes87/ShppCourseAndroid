package com.dbondarenko.shpp.personalnotes;

/**
 * File: ConstantsTest.java
 * Created by Dmitro Bondarenko on 21.12.2017.
 */
class ConstantsTest {
    static final String INCORRECT_LOGIN = "linux";
    static final String INCORRECT_PASSWORD = "123456";

    static final String INCORRECT_LOGIN_WRONG_FORMAT = "a.n,droid";
    static final String INCORRECT_PASSWORD_WRONG_FORMAT = "1.2,3456789";

    static final String INCORRECT_LOGIN_INSUFFICIENT_CHARACTERS = "mvp";
    static final String INCORRECT_PASSWORD_INSUFFICIENT_CHARACTERS = "mvvm";

    static final String CORRECT_LOGIN = "android1";
    static final String CORRECT_PASSWORD = "1234567890";

    static final String USED_LOGIN = "bondes";
    static final String USED_PASSWORD = "12345678";
    static final String NEW_TEXT_NOTE = "new";

    static final int MINIMUM_LENGTH_OF_CORRECT_LOGIN = 6;
    static final int MINIMUM_LENGTH_OF_CORRECT_PASSWORD = 8;

    static final String CHARACTERS_FOR_CREATING_LOGIN_OR_PASSWORD =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890";
}
