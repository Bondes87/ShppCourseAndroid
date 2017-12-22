package com.dbondarenko.shpp.personalnotes.tests;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dbondarenko.shpp.personalnotes.BaseUITest;
import com.dbondarenko.shpp.personalnotes.ConstantsTest;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.UtilTest;
import com.dbondarenko.shpp.personalnotes.activities.MainActivity;
import com.dbondarenko.shpp.personalnotes.utils.SharedPreferencesManager;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * File: RegisterUserUITest.java
 * Created by Dmitro Bondarenko on 21.12.2017.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterUserUITest extends BaseUITest {

    private static String correctLogin;
    private static String correctPassword;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void init() {
        correctLogin = UtilTest.getRandomString(
                ConstantsTest.MINIMUM_LENGTH_OF_CORRECT_LOGIN);
        correctPassword = UtilTest.getRandomString(
                ConstantsTest.MINIMUM_LENGTH_OF_CORRECT_PASSWORD);
    }

    @Before
    public void prepareForTesting() {
        UtilTest.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        isSelectedFirebase = SharedPreferencesManager
                .getSharedPreferencesManager()
                .isUseFirebase(UtilTest.getActivityInstance());
    }

    @Test
    public void test01IncorrectCredentialsUsingSQLite() {
        runToMainActivity();
        runToRegisterWindow();
        useSQLite();
        UtilTest.makePause();
        checkIncorrectCredentials();
    }

    @Test
    public void test02SavingCredentialsWhenChangedScreenOrientationUsingSQLite() {
        runToMainActivity();
        runToRegisterWindow();
        useSQLite();
        UtilTest.makePause();
        checkSavingCredentialsWhenChangedScreenOrientation();
    }

    @Test
    public void test03RunInfoDialogWindowUsingSQLite() {
        runToMainActivity();
        runToRegisterWindow();
        useSQLite();
        UtilTest.makePause();
        checkInfoDialogWindows();
    }

    @Test
    public void test04PressingBackButtonToLoginFromRegisterWindowUsingSQLite() {
        runToMainActivity();
        runToRegisterWindow();
        useSQLite();
        UtilTest.makePause();
        checkPressingBackButtonToLoginFromRegisterWindow();
    }

    @Test
    public void test05RegisterUserWithCorrectCredentialsUsedSQLite() {
        runToMainActivity();
        runToRegisterWindow();
        useSQLite();
        UtilTest.makePause();
        checkRegisterUserWithCorrectCredentials();
    }

    @Test
    public void test06IncorrectCredentialsUsingFirebase() {
        runToMainActivity();
        runToRegisterWindow();
        useFirebase();
        UtilTest.makePause();
        checkIncorrectCredentials();
    }

    @Test
    public void test07SavingCredentialsWhenChangedScreenOrientationUsingFirebase() {
        runToMainActivity();
        runToRegisterWindow();
        useFirebase();
        UtilTest.makePause();
        checkSavingCredentialsWhenChangedScreenOrientation();
    }

    @Test
    public void test08RunInfoDialogWindowUsingFirebase() {
        runToMainActivity();
        runToRegisterWindow();
        useFirebase();
        UtilTest.makePause();
        checkInfoDialogWindows();
    }

    @Test
    public void test09PressingBackButtonToLoginFromRegisterWindowUsingFirebase() {
        runToMainActivity();
        runToRegisterWindow();
        useFirebase();
        UtilTest.makePause();
        checkPressingBackButtonToLoginFromRegisterWindow();
    }

    @Test
    public void test10RegisterUserWithCorrectCredentialsUsedFirebase() {
        runToMainActivity();
        runToRegisterWindow();
        useFirebase();
        UtilTest.makePause();
        checkRegisterUserWithCorrectCredentials();
    }

    private void checkPressingBackButtonToLoginFromRegisterWindow() {
        checkPressingBackButton();
        UtilTest.makePause();
        onView(ViewMatchers.withId(R.id.buttonLogIn)).check(matches(isDisplayed()));
    }

    private void checkInfoDialogWindows() {
        checkInfoDialogWindow(onView(withId(R.id.imageViewLoginInfo)));
        UtilTest.makePause();
        checkInfoDialogWindow(onView(withId(R.id.imageViewPasswordInfo)));
    }

    private void checkSavingCredentialsWhenChangedScreenOrientation() {
        ViewInteraction editTextLogin = onView(withId(R.id.editTextLogin));
        editTextLogin.check(matches(isDisplayed()))
                .perform(typeText(ConstantsTest.CORRECT_LOGIN));

        ViewInteraction editTextPassword = onView(withId(R.id.editTextPassword));
        editTextPassword.check(matches(isDisplayed()))
                .perform(typeText(ConstantsTest.CORRECT_PASSWORD));

        UtilTest.makePause();
        UtilTest.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        UtilTest.makePause();

        editTextLogin.check(matches(withText(ConstantsTest.CORRECT_LOGIN)));
        editTextPassword.check(matches(withText(ConstantsTest.CORRECT_PASSWORD)));
        UtilTest.makePause();
    }

    private void checkInfoDialogWindow(ViewInteraction viewInteraction) {
        viewInteraction.check(matches(isDisplayed()))
                .perform(click());
        onView(withText(UtilTest.getStringForResources(R.string.text_hint)))
                .check(matches(isDisplayed()));

        UtilTest.makePause();
        onView(withId(android.R.id.button1)).check(matches(isDisplayed()))
                .perform(click());

        UtilTest.makePause();
        onView(withId(R.id.editTextLogin)).check(matches(isDisplayed()));
    }

    private void checkIncorrectCredentials() {
        ViewInteraction editTextLogin = onView(withId(R.id.editTextLogin));
        ViewInteraction editTextPassword = onView(withId(R.id.editTextPassword));
        ViewInteraction editTextConfirmedPassword = onView(withId(
                R.id.editTextConfirmedPassword));
        ViewInteraction buttonRegister = onView(withId(R.id.buttonRegister));

        editTextLogin.check(matches(isDisplayed()));
        editTextPassword.check(matches(isDisplayed()));
        editTextConfirmedPassword.check(matches(isDisplayed()));
        buttonRegister.check(matches(isDisplayed()));

        checkEmptyFieldInCredentials(
                editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkWrongFormatInCredentials(
                editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkInsufficientCharactersInCredentials(
                editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkPasswordMatchingInCredentials(
                editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        if (isSelectedFirebase &&
                !(Util.isInternetConnectionAvailable(UtilTest.getActivityInstance()))) {
            checkSnackbarWithMessageNoInternetConection();
        } else {
            checkUsedLogin(editTextLogin, editTextPassword,
                    editTextConfirmedPassword, buttonRegister);
        }
        UtilTest.makePause();
    }

    private void checkEmptyFieldInCredentials(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        checkEmptyAllField(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkEmptyLogin(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkEmptyPassword(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkEmptyConfirmedPassword(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();
    }

    private void checkUsedLogin(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(ConstantsTest.USED_LOGIN));
        editTextPassword.perform(typeText(ConstantsTest.CORRECT_PASSWORD));
        editTextConfirmedPassword.perform(
                typeText(ConstantsTest.CORRECT_PASSWORD));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextLogin.check(matches(withText(ConstantsTest.USED_LOGIN)));
    }

    private void checkPasswordMatchingInCredentials(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(ConstantsTest.USED_LOGIN));
        editTextPassword.perform(typeText(ConstantsTest.USED_PASSWORD));
        editTextConfirmedPassword.perform(
                typeText(ConstantsTest.CORRECT_PASSWORD));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextConfirmedPassword.check(matches(hasErrorText(UtilTest
                .getStringForResources(R.string.error_passwords_do_not_match))));
    }

    private void checkInsufficientCharactersInCredentials(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        checkInsufficientCharactersLogin(editTextLogin,
                editTextPassword, editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkInsufficientCharactersPassword(editTextLogin,
                editTextPassword, editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkInsufficientCharactersConfirmedPassword(editTextLogin,
                editTextPassword, editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();
    }

    private void checkInsufficientCharactersConfirmedPassword
            (ViewInteraction editTextLogin, ViewInteraction editTextPassword,
             ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(ConstantsTest.USED_LOGIN));
        editTextPassword.perform(typeText(ConstantsTest.USED_PASSWORD));
        editTextConfirmedPassword.perform(
                typeText(ConstantsTest.INCORRECT_PASSWORD_INSUFFICIENT_CHARACTERS));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextConfirmedPassword.check(matches(hasErrorText(UtilTest
                .getStringForResources(R.string.error_short_password))));
    }

    private void checkInsufficientCharactersPassword(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(ConstantsTest.USED_LOGIN));
        editTextPassword.perform(typeText(
                ConstantsTest.INCORRECT_PASSWORD_INSUFFICIENT_CHARACTERS));
        editTextConfirmedPassword.perform(typeText(ConstantsTest.USED_PASSWORD));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextPassword.check(matches(hasErrorText(UtilTest
                .getStringForResources(R.string.error_short_password))));

    }

    private void checkInsufficientCharactersLogin(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(
                ConstantsTest.INCORRECT_LOGIN_INSUFFICIENT_CHARACTERS));
        editTextPassword.perform(typeText(ConstantsTest.USED_PASSWORD));
        editTextConfirmedPassword.perform(typeText(ConstantsTest.USED_PASSWORD));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextLogin.check(matches(hasErrorText(UtilTest
                .getStringForResources(R.string.error_short_login))));
    }

    private void checkWrongFormatInCredentials(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        checkWrongFormatLogin(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkWrongFormatPassword(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();

        checkWrongFormatConfirmedPassword(editTextLogin,
                editTextPassword, editTextConfirmedPassword, buttonRegister);
        UtilTest.makePause();
    }

    private void checkWrongFormatConfirmedPassword(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(ConstantsTest.USED_LOGIN));
        editTextPassword.perform(typeText(ConstantsTest.USED_PASSWORD));
        editTextConfirmedPassword.perform(typeText(
                ConstantsTest.INCORRECT_PASSWORD_WRONG_FORMAT));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextConfirmedPassword.check(matches(hasErrorText(
                UtilTest.getStringForResources(
                        R.string.error_invalid_character_in_login_or_password))));
    }

    private void checkWrongFormatPassword(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(ConstantsTest.USED_LOGIN));
        editTextPassword.perform(typeText(
                ConstantsTest.INCORRECT_PASSWORD_WRONG_FORMAT));
        editTextConfirmedPassword.perform(typeText(ConstantsTest.USED_PASSWORD));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextPassword.check(matches(hasErrorText(
                UtilTest.getStringForResources(
                        R.string.error_invalid_character_in_login_or_password))));

    }

    private void checkWrongFormatLogin(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(ConstantsTest.INCORRECT_LOGIN_WRONG_FORMAT));
        editTextPassword.perform(typeText(ConstantsTest.USED_PASSWORD));
        editTextConfirmedPassword.perform(typeText(ConstantsTest.USED_PASSWORD));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextLogin.check(matches(hasErrorText(
                UtilTest.getStringForResources(
                        R.string.error_invalid_character_in_login_or_password))));
    }


    private void checkEmptyConfirmedPassword(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(ConstantsTest.USED_LOGIN));
        editTextPassword.perform(typeText(ConstantsTest.USED_PASSWORD));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextConfirmedPassword.check(matches(hasErrorText(UtilTest
                .getStringForResources(R.string.error_empty_field))));
    }

    private void checkEmptyPassword(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        editTextLogin.perform(typeText(ConstantsTest.USED_LOGIN));
        editTextConfirmedPassword.perform(typeText(ConstantsTest.USED_PASSWORD));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextPassword.check(matches(hasErrorText(UtilTest
                .getStringForResources(R.string.error_empty_field))));
    }

    private void checkEmptyLogin(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);
        editTextPassword.perform(typeText(ConstantsTest.USED_PASSWORD));
        editTextConfirmedPassword.perform(typeText(ConstantsTest.USED_PASSWORD));

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextLogin.check(matches(hasErrorText(UtilTest
                .getStringForResources(R.string.error_empty_field))));
    }

    private void checkEmptyAllField(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        clearTextViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword);

        closeSoftKeyboard();
        buttonRegister.perform(click());

        editTextLogin.check(matches(hasErrorText(UtilTest
                .getStringForResources(R.string.error_empty_field))));
    }

    private void clearTextViewInteraction(ViewInteraction... viewInteractions) {
        for (ViewInteraction viewInteraction : viewInteractions) {
            viewInteraction.perform(clearText());
        }
    }

    private void checkRegisterUserWithCorrectCredentials() {
        onView(withId(R.id.editTextLogin))
                .check(matches(isDisplayed()))
                .perform(typeText(correctLogin));
        onView(withId(R.id.editTextPassword))
                .check(matches(isDisplayed()))
                .perform(typeText(correctPassword));
        onView(withId(R.id.editTextConfirmedPassword))
                .check(matches(isDisplayed()))
                .perform(typeText(correctPassword));

        closeSoftKeyboard();
        UtilTest.makePause();
        onView(withId(R.id.buttonRegister))
                .check(matches(isDisplayed()))
                .perform(click());

        if (isSelectedFirebase &&
                !(Util.isInternetConnectionAvailable(UtilTest.getActivityInstance()))) {
            checkSnackbarWithMessageNoInternetConection();
        } else {
            UtilTest.makePause();
            onView(withId(R.id.floatingActionButtonAddNote))
                    .check(matches(isDisplayed()));
        }
    }
}