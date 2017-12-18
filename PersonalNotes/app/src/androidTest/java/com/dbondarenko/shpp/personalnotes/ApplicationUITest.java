package com.dbondarenko.shpp.personalnotes;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.dbondarenko.shpp.personalnotes.activities.ContentActivity;
import com.dbondarenko.shpp.personalnotes.activities.MainActivity;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;

/**
 * File: ApplicationUITest.java
 * Created by Dmitro Bondarenko on 15.12.2017.
 */

@RunWith(AndroidJUnit4.class)
public class ApplicationUITest {
    private static final String INCORRECT_LOGIN = "linux";
    private static final String INCORRECT_PASSWORD = "123456";

    private static final String INCORRECT_LOGIN_WRONG_FORMAT = "a.n,droid";
    private static final String INCORRECT_PASSWORD_WRONG_FORMAT = "1.2,3456789";

    private static final String INCORRECT_LOGIN_INSUFFICIENT_CHARACTERS = "mvp";
    private static final String INCORRECT_PASSWORD_INSUFFICIENT_CHARACTERS = "mvvm";

    private static final String CORRECT_LOGIN = "android";
    private static final String CORRECT_PASSWORD = "123456789";

    private static final String USED_LOGIN = "bondes";
    private static final String USED_PASSWORD = "12345678";
    private static String[] notesTexts;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void initData() {
        notesTexts = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
    }

    @Before
    public void prepareForTesting() {
        setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Test
    public void testIncorrectCredentialsWhenLoginUsingSQL() {
        runToMainActivity();
        useDatabase(R.string.text_use_local_database);
        makePause();
        checkIncorrectCredentialsWhenLogin(false);
    }

    @Test
    public void testIncorrectCredentialsWhenLoginUsingFirebase() {
        runToMainActivity();
        useDatabase(R.string.text_use_server_database);
        makePause();
        checkIncorrectCredentialsWhenLogin(true);
    }

    @Test
    public void testSavingCredentialsWhenChangedScreenOrientationWhenLogin() {
        runToMainActivity();
        ViewInteraction editTextLogin = getViewInteraction(R.id.editTextLogin);
        editTextLogin.check(matches(isDisplayed()))
                .perform(typeText(INCORRECT_LOGIN));
        makePause();
        setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        editTextLogin.check(matches(withText(INCORRECT_LOGIN)));
        makePause();
    }

    @Test
    public void testRunToRegisterWindow() {
        runToMainActivity();
        makePause();
        runToRegisterWindow();
    }

    @Test
    public void testIncorrectCredentialsWhenRegisterUsingSQL() {
        runToMainActivity();
        runToRegisterWindow();
        useDatabase(R.string.text_use_local_database);
        makePause();
        checkIncorrectCredentialsWhenRegister(false);
    }

    @Test
    public void testIncorrectCredentialsWhenRegisterUsingFirebase() {
        runToMainActivity();
        runToRegisterWindow();
        useDatabase(R.string.text_use_server_database);
        makePause();
        checkIncorrectCredentialsWhenRegister(true);
    }

    @Test
    public void testSavingCredentialsWhenChangedScreenOrientationWhenRegister() {
        runToMainActivity();
        runToRegisterWindow();
        ViewInteraction editTextLogin = getViewInteraction(R.id.editTextLogin);
        editTextLogin.check(matches(isDisplayed()))
                .perform(typeText(CORRECT_LOGIN));
        ViewInteraction editTextPassword = getViewInteraction(R.id.editTextPassword);
        editTextPassword.check(matches(isDisplayed()))
                .perform(typeText(CORRECT_PASSWORD));
        makePause();
        setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        makePause();
        editTextLogin.check(matches(withText(CORRECT_LOGIN)));
        editTextPassword.check(matches(withText(CORRECT_PASSWORD)));
        makePause();
    }

    @Test
    public void testRunInfoDialogWindowWhenRegister() {
        runToMainActivity();
        runToRegisterWindow();
        checkInfoDialogWindow(getViewInteraction(R.id.imageViewLoginInfo));
        makePause();
        checkInfoDialogWindow(getViewInteraction(R.id.imageViewPasswordInfo));
    }

    @Test
    public void testPressingBackButtonToLoginFromRegisterWindow() {
        runToMainActivity();
        runToRegisterWindow();
        onView(allOf(isAssignableFrom(ImageView.class),
                withParent(isAssignableFrom(Toolbar.class)),
                withContentDescription(R.string.abc_action_bar_up_description)))
                .check(matches(isDisplayed()))
                .perform(click());
        makePause();
        getViewInteraction(R.id.buttonLogIn).check(matches(isDisplayed()));
    }

    @Test
    public void testRegisterUserWithCorrectCredentialsUsedSQL() {
        runToMainActivity();
        runToRegisterWindow();
        useDatabase(R.string.text_use_local_database);
        checkRegisterUserWithCorrectCredentials(false);
    }

    @Test
    public void testRegisterUserWithCorrectCredentialsUsedFirebase() {
        runToMainActivity();
        runToRegisterWindow();
        useDatabase(R.string.text_use_server_database);
        checkRegisterUserWithCorrectCredentials(true);
    }

    @Test
    public void testLoginUserWithCorrectCredentialsUsedSQL() {
        runToMainActivity();
        useDatabase(R.string.text_use_local_database);
        checkLoginUserWithCorrectCredentials(false);
    }

    @Test
    public void testLoginUserWithCorrectCredentialsUsedFirebase() {
        runToMainActivity();
        useDatabase(R.string.text_use_server_database);
        checkLoginUserWithCorrectCredentials(true);
    }

    private static Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Activity currentActivity;
            Collection resumedActivities = ActivityLifecycleMonitorRegistry
                    .getInstance().getActivitiesInStage(RESUMED);
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = (Activity) resumedActivities.iterator().next();
                activity[0] = currentActivity;
            }
        });
        return activity[0];
    }

    private void checkLoginUserWithCorrectCredentials(boolean isUsedFirebase) {
        ViewInteraction editTextLogin = getViewInteraction(R.id.editTextLogin);
        ViewInteraction editTextPassword = getViewInteraction(R.id.editTextPassword);
        ViewInteraction buttonLogin = getViewInteraction(R.id.buttonLogIn);

        checkDisplayViewInteraction(editTextLogin, editTextPassword, buttonLogin);

        editTextLogin.perform(typeText(CORRECT_LOGIN));
        editTextPassword.perform(typeText(CORRECT_PASSWORD));
        closeSoftKeyboard();
        makePause();
        buttonLogin.perform(click());
        if (isUsedFirebase &&
                !(Util.isInternetConnectionAvailable(getActivityInstance()))) {
            checkSnackbarWithMessageNoInternetConection();
        } else {
            makePause();
            getViewInteraction(R.id.floatingActionButtonAddNote)
                    .check(matches(isDisplayed()));
        }
    }

    private void useDatabase(int databaseNameId) {
        openActionBarOverflowOrOptionsMenu(getActivityInstance());
        selectMenuItem(databaseNameId);
    }

    private void checkRegisterUserWithCorrectCredentials(boolean isUsedFirebase) {
        ViewInteraction editTextLogin = getViewInteraction(R.id.editTextLogin);
        ViewInteraction editTextPassword = getViewInteraction(R.id.editTextPassword);
        ViewInteraction editTextConfirmedPassword = getViewInteraction(
                R.id.editTextConfirmedPassword);
        ViewInteraction buttonRegister = getViewInteraction(R.id.buttonRegister);

        checkDisplayViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);

        editTextLogin.perform(typeText(CORRECT_LOGIN));
        editTextPassword.perform(typeText(CORRECT_PASSWORD));
        editTextConfirmedPassword.perform(typeText(CORRECT_PASSWORD));
        closeSoftKeyboard();
        makePause();
        buttonRegister.perform(click());
        if (isUsedFirebase &&
                !(Util.isInternetConnectionAvailable(getActivityInstance()))) {
            checkSnackbarWithMessageNoInternetConection();
        } else {
            makePause();
            getViewInteraction(R.id.floatingActionButtonAddNote)
                    .check(matches(isDisplayed()));
        }
    }

    private void checkInfoDialogWindow(ViewInteraction viewInteraction) {
        viewInteraction.check(matches(isDisplayed())).perform(click());
        getViewInteraction(getStringForResources(R.string.text_hint))
                .check(matches(isDisplayed()));
        makePause();
        getViewInteraction(android.R.id.button1).check(matches(isDisplayed()))
                .perform(click());
        makePause();
        getViewInteraction(R.id.editTextLogin).check(matches(isDisplayed()));
    }

    private void runToRegisterWindow() {
        ViewInteraction buttonRegister = getViewInteraction(R.id.buttonRegister);
        buttonRegister.check(matches(isDisplayed()));
        buttonRegister.perform(click());
        getViewInteraction(R.id.imageViewLoginInfo).check(matches(isDisplayed()));
        onView(allOf(isAssignableFrom(TextView.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(getStringForResources(
                        R.string.text_register))));
        makePause();
    }

    private void setScreenOrientation(int screenOrientation) {
        getActivityInstance().setRequestedOrientation(screenOrientation);
    }

    private void runToMainActivity() {
        if (!(getActivityInstance() instanceof MainActivity)) {
            ViewInteraction actionMenuItemLogOut = onView(withId(R.id.itemLogOut));
            actionMenuItemLogOut.check(matches(isDisplayed()));
            actionMenuItemLogOut.perform(click());
        }
    }

    private void checkIncorrectCredentialsWhenLogin(boolean isUsedFirebase) {
        ViewInteraction editTextLogin = getViewInteraction(R.id.editTextLogin);
        ViewInteraction editTextPassword = getViewInteraction(R.id.editTextPassword);
        ViewInteraction buttonLogIn = getViewInteraction(R.id.buttonLogIn);

        checkDisplayViewInteraction(editTextLogin, editTextPassword, buttonLogIn);

        editTextLogin.check(matches(isFocusable()));
        checkEmptyCredentialsWhenLogin(editTextLogin, buttonLogIn);
        makePause();
        checkEmptyPasswordWhenLogin(editTextLogin, editTextPassword, buttonLogIn);
        makePause();
        checkEmptyLoginWhenLogin(editTextLogin, editTextPassword, buttonLogIn);
        makePause();
        if (isUsedFirebase &&
                !(Util.isInternetConnectionAvailable(getActivityInstance()))) {
            checkSnackbarWithMessageNoInternetConection();
        } else {
            checkIncorrectCredentialsWhenLogin(editTextLogin,
                    editTextPassword, buttonLogIn);
        }
        makePause();
    }

    private void checkSnackbarWithMessageNoInternetConection() {
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(getStringForResources(R.string.error_no_internet_connection))))
                .check(matches(isDisplayed()));
    }

    private void checkIncorrectCredentialsWhenRegister(boolean isUsedFirebase) {
        ViewInteraction editTextLogin = getViewInteraction(R.id.editTextLogin);
        ViewInteraction editTextPassword = getViewInteraction(R.id.editTextPassword);
        ViewInteraction editTextConfirmedPassword = getViewInteraction(
                R.id.editTextConfirmedPassword);
        ViewInteraction buttonRegister = getViewInteraction(R.id.buttonRegister);

        checkDisplayViewInteraction(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);

        checkEmptyFieldInCredentialsWhenRegister(
                editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();

        checkWrongFormatInCredentialsWhenRegister(
                editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();

        checkInsufficientCharactersInCredentialsWhenRegister(
                editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();

        checkPasswordMatchingInCredentialsWhenRegister(
                editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();

        if (isUsedFirebase &&
                !(Util.isInternetConnectionAvailable(getActivityInstance()))) {
            checkSnackbarWithMessageNoInternetConection();
        } else {
            checkUsedLoginWhenRegister(editTextLogin, editTextPassword,
                    editTextConfirmedPassword, buttonRegister);
        }
        makePause();
    }

    private void checkUsedLoginWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(USED_LOGIN));
        editTextPassword.perform(typeText(CORRECT_PASSWORD));
        editTextConfirmedPassword.perform(
                typeText(CORRECT_PASSWORD));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextLogin.check(matches(withText(USED_LOGIN)));
    }

    private void checkPasswordMatchingInCredentialsWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(USED_LOGIN));
        editTextPassword.perform(typeText(USED_PASSWORD));
        editTextConfirmedPassword.perform(
                typeText(CORRECT_PASSWORD));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextConfirmedPassword.check(matches(hasErrorText(getStringForResources(
                R.string.error_passwords_do_not_match))));
    }

    private void checkInsufficientCharactersInCredentialsWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        checkInsufficientCharactersLoginWhenRegister(editTextLogin,
                editTextPassword, editTextConfirmedPassword, buttonRegister);
        makePause();

        checkInsufficientCharactersPasswordWhenRegister(editTextLogin,
                editTextPassword, editTextConfirmedPassword, buttonRegister);
        makePause();

        checkInsufficientCharactersConfirmedPasswordWhenRegister(editTextLogin,
                editTextPassword, editTextConfirmedPassword, buttonRegister);
        makePause();
    }

    private void checkInsufficientCharactersConfirmedPasswordWhenRegister
            (ViewInteraction editTextLogin, ViewInteraction editTextPassword,
             ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(USED_LOGIN));
        editTextPassword.perform(typeText(USED_PASSWORD));
        editTextConfirmedPassword.perform(
                typeText(INCORRECT_PASSWORD_INSUFFICIENT_CHARACTERS));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextConfirmedPassword.check(matches(hasErrorText(getStringForResources(
                R.string.error_short_password))));
    }

    private void checkInsufficientCharactersPasswordWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(USED_LOGIN));
        editTextPassword.perform(typeText(INCORRECT_PASSWORD_INSUFFICIENT_CHARACTERS));
        editTextConfirmedPassword.perform(typeText(USED_PASSWORD));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextPassword.check(matches(hasErrorText(getStringForResources(
                R.string.error_short_password))));

    }

    private void checkInsufficientCharactersLoginWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(INCORRECT_LOGIN_INSUFFICIENT_CHARACTERS));
        editTextPassword.perform(typeText(USED_PASSWORD));
        editTextConfirmedPassword.perform(typeText(USED_PASSWORD));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextLogin.check(matches(hasErrorText(getStringForResources(
                R.string.error_short_login))));
    }

    private void checkWrongFormatInCredentialsWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        checkWrongFormatLoginWhenRegister(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();

        checkWrongFormatPasswordWhenRegister(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();

        checkWrongFormatConfirmedPasswordWhenRegister(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();
    }

    private void checkWrongFormatConfirmedPasswordWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(USED_LOGIN));
        editTextPassword.perform(typeText(USED_PASSWORD));
        editTextConfirmedPassword.perform(typeText(INCORRECT_PASSWORD_WRONG_FORMAT));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextConfirmedPassword.check(matches(hasErrorText(getStringForResources(
                R.string.error_invalid_character_in_login_or_password))));
    }

    private void checkWrongFormatPasswordWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(USED_LOGIN));
        editTextPassword.perform(typeText(INCORRECT_PASSWORD_WRONG_FORMAT));
        editTextConfirmedPassword.perform(typeText(USED_PASSWORD));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextPassword.check(matches(hasErrorText(getStringForResources(
                R.string.error_invalid_character_in_login_or_password))));

    }

    private void checkWrongFormatLoginWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(INCORRECT_LOGIN_WRONG_FORMAT));
        editTextPassword.perform(typeText(USED_PASSWORD));
        editTextConfirmedPassword.perform(typeText(USED_PASSWORD));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextLogin.check(matches(hasErrorText(getStringForResources(
                R.string.error_invalid_character_in_login_or_password))));
    }

    private void checkEmptyFieldInCredentialsWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {

        checkEmptyAllFieldWhenRegister(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();

        checkEmptyLoginWhenRegister(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();

        checkEmptyPasswordWhenRegister(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();

        checkEmptyConfirmedPasswordWhenRegister(editTextLogin, editTextPassword,
                editTextConfirmedPassword, buttonRegister);
        makePause();
    }

    private void checkEmptyConfirmedPasswordWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(USED_LOGIN));
        editTextPassword.perform(typeText(USED_PASSWORD));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextConfirmedPassword.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private void checkEmptyPasswordWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextLogin.perform(typeText(USED_LOGIN));
        editTextConfirmedPassword.perform(typeText(USED_PASSWORD));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextPassword.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private void checkEmptyLoginWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        editTextPassword.perform(typeText(USED_PASSWORD));
        editTextConfirmedPassword.perform(typeText(USED_PASSWORD));
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextLogin.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private void checkEmptyAllFieldWhenRegister(
            ViewInteraction editTextLogin, ViewInteraction editTextPassword,
            ViewInteraction editTextConfirmedPassword, ViewInteraction buttonRegister) {
        clearTextViewInteraction(editTextLogin, editTextPassword, editTextConfirmedPassword);
        closeSoftKeyboard();
        buttonRegister.perform(click());
        editTextLogin.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private void selectMenuItem(int stringId) {
        ViewInteraction itemOptionMenuLocalDatabase =
                getViewInteraction(getStringForResources(stringId));
        itemOptionMenuLocalDatabase.check(matches(isDisplayed()));
        itemOptionMenuLocalDatabase.perform(click());
    }

    private void checkIncorrectCredentialsWhenLogin(ViewInteraction editTextLogin,
                                                    ViewInteraction editTextPassword,
                                                    ViewInteraction buttonLogIn) {
        clearTextViewInteraction(editTextLogin, editTextPassword);
        editTextLogin.perform(typeText(INCORRECT_LOGIN));
        editTextPassword.perform(typeText(INCORRECT_PASSWORD));
        closeSoftKeyboard();
        buttonLogIn.perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(getStringForResources(R.string.error_invalid_login_or_password))))
                .check(matches(isDisplayed()));
    }

    private void checkEmptyLoginWhenLogin(ViewInteraction editTextLogin,
                                          ViewInteraction editTextPassword,
                                          ViewInteraction buttonLogIn) {
        clearTextViewInteraction(editTextLogin);
        editTextPassword.perform(typeText(INCORRECT_PASSWORD));
        closeSoftKeyboard();
        buttonLogIn.perform(click());
        editTextLogin.check(matches(isFocusable()));
        editTextLogin.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private void checkEmptyPasswordWhenLogin(ViewInteraction editTextLogin,
                                             ViewInteraction editTextPassword,
                                             ViewInteraction buttonLogIn) {
        editTextLogin.perform(typeText(INCORRECT_LOGIN));
        closeSoftKeyboard();
        buttonLogIn.perform(click());
        editTextPassword.check(matches(isFocusable()));
        editTextPassword.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private void checkEmptyCredentialsWhenLogin(ViewInteraction editTextLogin,
                                                ViewInteraction buttonLogIn) {
        buttonLogIn.perform(click());
        editTextLogin.check(matches(isFocusable()));
        editTextLogin.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private String getStringForResources(int stringId) {
        return getActivityInstance().getString(stringId);
    }

    private void makePause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkDisplayViewInteraction(ViewInteraction... viewInteractions) {
        for (ViewInteraction viewInteraction : viewInteractions) {
            viewInteraction.check(matches(isDisplayed()));
        }
    }

    private void clearTextViewInteraction(ViewInteraction... viewInteractions) {
        for (ViewInteraction viewInteraction : viewInteractions) {
            viewInteraction.perform(clearText());
        }
    }

    private ViewInteraction getViewInteraction(int viewId) {
        return onView(withId(viewId));
    }

    private ViewInteraction getViewInteraction(String text) {
        return onView(withText(text));
    }
}