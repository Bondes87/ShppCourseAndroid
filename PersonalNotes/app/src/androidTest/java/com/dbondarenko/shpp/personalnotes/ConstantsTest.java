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
}
