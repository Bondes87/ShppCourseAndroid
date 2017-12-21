package com.dbondarenko.shpp.personalnotes;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dbondarenko.shpp.personalnotes.activities.MainActivity;
import com.dbondarenko.shpp.personalnotes.utils.SharedPreferencesManager;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * File: LoginUserUITest.java
 * Created by Dmitro Bondarenko on 20.12.2017.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginUserUITest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private boolean isSelectedFirebase;

    @Before
    public void prepareForTesting() {
        UtilTest.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        isSelectedFirebase = SharedPreferencesManager
                .getSharedPreferencesManager()
                .isUseFirebase(UtilTest.getActivityInstance());
    }

    @Test
    public void test1IncorrectCredentialsUsingSQLite() {
        runToMainActivity();
        useSQLite();
        UtilTest.makePause();
        checkIncorrectCredentials();
        UtilTest.makePause();
    }

    @Test
    public void test2SavingCredentialsWhenChangedScreenOrientationUsingSQLite() {
        runToMainActivity();
        useSQLite();
        UtilTest.makePause();
        checkSavingCredentialsWhenChangedScreenOrientation();
        UtilTest.makePause();
    }

    @Test
    public void test3RunToRegisterWindowUsedSQLite() {
        runToMainActivity();
        useSQLite();
        UtilTest.makePause();
        runToRegisterWindow();
        UtilTest.makePause();
    }

    @Test
    public void test4LoginUserWithCorrectCredentialsUsedSQLite() {
        runToMainActivity();
        useSQLite();
        UtilTest.makePause();
        checkLoginUserWithCorrectCredentials();
        UtilTest.makePause();
    }

    @Test
    public void test5IncorrectCredentialsUsingFirebase() {
        runToMainActivity();
        useFirebase();
        UtilTest.makePause();
        checkIncorrectCredentials();
    }

    @Test
    public void test6SavingCredentialsWhenChangedScreenOrientationUsingFirebase() {
        runToMainActivity();
        useFirebase();
        UtilTest.makePause();
        checkSavingCredentialsWhenChangedScreenOrientation();
    }

    @Test
    public void test7RunToRegisterWindowUsedFirebase() {
        runToMainActivity();
        useFirebase();
        UtilTest.makePause();
        runToRegisterWindow();
    }

    @Test
    public void test8LoginUserWithCorrectCredentialsUsedFirebase() {
        runToMainActivity();
        useFirebase();
        UtilTest.makePause();
        checkLoginUserWithCorrectCredentials();
    }

    private void runToMainActivity() {
        if (!(UtilTest.getActivityInstance() instanceof MainActivity)) {
            ViewInteraction actionMenuItemLogOut = onView(withId(R.id.itemLogOut));
            actionMenuItemLogOut.check(matches(isDisplayed()));
            actionMenuItemLogOut.perform(click());
        }
    }

    private void useSQLite() {
        if (isSelectedFirebase) {
            openActionBarOverflowOrOptionsMenu(UtilTest.getActivityInstance());
            selectMenuItem(R.string.text_use_local_database);
        }
    }

    private void useFirebase() {
        if (!isSelectedFirebase) {
            openActionBarOverflowOrOptionsMenu(UtilTest.getActivityInstance());
            selectMenuItem(R.string.text_use_server_database);
        }
    }

    private void selectMenuItem(int stringId) {
        onView(withText(UtilTest.getStringForResources(stringId)))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    private void checkSavingCredentialsWhenChangedScreenOrientation() {
        ViewInteraction editTextLogin = onView(withId((R.id.editTextLogin)));
        editTextLogin.check(matches(isDisplayed()))
                .perform(typeText(ConstantsTest.INCORRECT_LOGIN));

        closeSoftKeyboard();
        UtilTest.makePause();
        UtilTest.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        editTextLogin.check(matches(withText(ConstantsTest.INCORRECT_LOGIN)));
    }

    private void checkLoginUserWithCorrectCredentials() {
        onView(withId((R.id.editTextLogin)))
                .check(matches(isDisplayed()))
                .perform(typeText(ConstantsTest.CORRECT_LOGIN));

        onView(withId((R.id.editTextPassword)))
                .check(matches(isDisplayed()))
                .perform(typeText(ConstantsTest.CORRECT_PASSWORD));

        closeSoftKeyboard();
        UtilTest.makePause();

        onView(withId((R.id.buttonLogIn)))
                .check(matches(isDisplayed()))
                .perform(click());

        if (isSelectedFirebase &&
                !(Util.isInternetConnectionAvailable(UtilTest.getActivityInstance()))) {
            checkSnackbarWithMessageNoInternetConection();
        } else {
            UtilTest.makePause();
            onView(withId((R.id.floatingActionButtonAddNote)))
                    .check(matches(isDisplayed()));
        }
    }

    private void checkIncorrectCredentials() {
        ViewInteraction editTextLogin = onView(withId((R.id.editTextLogin)));
        ViewInteraction editTextPassword = onView(withId((R.id.editTextPassword)));
        ViewInteraction buttonLogIn = onView(withId((R.id.buttonLogIn)));

        editTextLogin.check(matches(isDisplayed()))
                .check(matches(isFocusable()));
        editTextPassword.check(matches(isDisplayed()));
        buttonLogIn.check(matches(isDisplayed()));

        checkEmptyCredentialsWhenLogin(editTextLogin, buttonLogIn);
        UtilTest.makePause();

        checkEmptyPasswordWhenLogin(editTextLogin, editTextPassword, buttonLogIn);
        UtilTest.makePause();

        checkEmptyLoginWhenLogin(editTextLogin, editTextPassword, buttonLogIn);
        UtilTest.makePause();

        if (isSelectedFirebase &&
                !(Util.isInternetConnectionAvailable(UtilTest.getActivityInstance()))) {
            checkSnackbarWithMessageNoInternetConection();
        } else {
            checkIncorrectCredentials(editTextLogin,
                    editTextPassword, buttonLogIn);
        }
    }

    private void checkIncorrectCredentials(ViewInteraction editTextLogin,
                                           ViewInteraction editTextPassword,
                                           ViewInteraction buttonLogIn) {
        editTextLogin.perform(clearText(), typeText(ConstantsTest.INCORRECT_LOGIN));
        editTextPassword.perform(clearText(), typeText(ConstantsTest.INCORRECT_PASSWORD));
        closeSoftKeyboard();

        buttonLogIn.perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(UtilTest.getStringForResources(
                        R.string.error_invalid_login_or_password))))
                .check(matches(isDisplayed()));
    }

    private void checkSnackbarWithMessageNoInternetConection() {
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(UtilTest.getStringForResources(
                        R.string.error_no_internet_connection))))
                .check(matches(isDisplayed()));
    }

    private void checkEmptyLoginWhenLogin(ViewInteraction editTextLogin,
                                          ViewInteraction editTextPassword,
                                          ViewInteraction buttonLogIn) {
        editTextLogin.perform(clearText());
        editTextPassword.perform(typeText(ConstantsTest.INCORRECT_PASSWORD));
        closeSoftKeyboard();

        buttonLogIn.perform(click());

        editTextLogin.check(matches(isFocusable()))
                .check(matches(hasErrorText(UtilTest.getStringForResources(
                        R.string.error_empty_field))));
    }

    private void checkEmptyPasswordWhenLogin(ViewInteraction editTextLogin,
                                             ViewInteraction editTextPassword,
                                             ViewInteraction buttonLogIn) {
        editTextLogin.perform(typeText(ConstantsTest.INCORRECT_LOGIN));
        closeSoftKeyboard();

        buttonLogIn.perform(click());

        editTextPassword.check(matches(isFocusable()))
                .check(matches(hasErrorText(UtilTest.getStringForResources(
                        R.string.error_empty_field))));
    }

    private void checkEmptyCredentialsWhenLogin(ViewInteraction editTextLogin,
                                                ViewInteraction buttonLogIn) {
        buttonLogIn.perform(click());

        editTextLogin.check(matches(isFocusable()))
                .check(matches(hasErrorText(UtilTest.getStringForResources(
                        R.string.error_empty_field))));
    }

    private void runToRegisterWindow() {
        onView(withId((R.id.buttonRegister)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId((R.id.editTextConfirmedPassword)))
                .check(matches(isDisplayed()));

        onView(allOf(isAssignableFrom(TextView.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(UtilTest.getStringForResources(
                        R.string.text_register))));
    }
}