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
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * File: LoginUserUITest.java
 * Created by Dmitro Bondarenko on 20.12.2017.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginUserUITest extends BaseUITest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

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
        useSQLite();
        UtilTest.makePause();
        checkIncorrectCredentials();
        UtilTest.makePause();
    }

    @Test
    public void test02SavingCredentialsWhenChangedScreenOrientationUsingSQLite() {
        runToMainActivity();
        useSQLite();
        UtilTest.makePause();
        checkSavingCredentialsWhenChangedScreenOrientation();
        UtilTest.makePause();
    }

    @Test
    public void test03RunToRegisterWindowUsedSQLite() {
        runToMainActivity();
        useSQLite();
        UtilTest.makePause();
        runToRegisterWindow();
        UtilTest.makePause();
    }

    @Test
    public void test04LoginUserWithCorrectCredentialsUsedSQLite() {
        runToMainActivity();
        useSQLite();
        UtilTest.makePause();
        checkLoginUserWithCorrectCredentials();
        UtilTest.makePause();
    }

    @Test
    public void test05IncorrectCredentialsUsingFirebase() {
        runToMainActivity();
        useFirebase();
        UtilTest.makePause();
        checkIncorrectCredentials();
        UtilTest.makePause();
    }

    @Test
    public void test06SavingCredentialsWhenChangedScreenOrientationUsingFirebase() {
        runToMainActivity();
        useFirebase();
        UtilTest.makePause();
        checkSavingCredentialsWhenChangedScreenOrientation();
        UtilTest.makePause();
    }

    @Test
    public void test07RunToRegisterWindowUsedFirebase() {
        runToMainActivity();
        useFirebase();
        UtilTest.makePause();
        runToRegisterWindow();
        UtilTest.makePause();
    }

    @Test
    public void test08LoginUserWithCorrectCredentialsUsedFirebase() {
        runToMainActivity();
        useFirebase();
        UtilTest.makePause();
        checkLoginUserWithCorrectCredentials();
        UtilTest.makePause();
    }

    private void checkSavingCredentialsWhenChangedScreenOrientation() {
        ViewInteraction editTextLogin = onView(ViewMatchers.withId((R.id.editTextLogin)));
        editTextLogin.check(matches(isDisplayed()))
                .perform(typeText(ConstantsTest.INCORRECT_LOGIN));

        closeSoftKeyboard();
        UtilTest.makePause();
        UtilTest.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        editTextLogin.check(matches(withText(ConstantsTest.INCORRECT_LOGIN)));
    }

    private void checkIncorrectCredentials() {
        ViewInteraction editTextLogin = onView(withId((R.id.editTextLogin)));
        ViewInteraction editTextPassword = onView(withId((R.id.editTextPassword)));
        ViewInteraction buttonLogIn = onView(withId((R.id.buttonLogIn)));

        editTextLogin.check(matches(isDisplayed()))
                .check(matches(isFocusable()));
        editTextPassword.check(matches(isDisplayed()));
        buttonLogIn.check(matches(isDisplayed()));

        checkEmptyCredentials(editTextLogin, buttonLogIn);
        UtilTest.makePause();

        checkEmptyPassword(editTextLogin, editTextPassword, buttonLogIn);
        UtilTest.makePause();

        checkEmptyLogin(editTextLogin, editTextPassword, buttonLogIn);
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

    private void checkEmptyLogin(ViewInteraction editTextLogin,
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

    private void checkEmptyPassword(ViewInteraction editTextLogin,
                                    ViewInteraction editTextPassword,
                                    ViewInteraction buttonLogIn) {
        editTextLogin.perform(typeText(ConstantsTest.INCORRECT_LOGIN));
        closeSoftKeyboard();

        buttonLogIn.perform(click());

        editTextPassword.check(matches(isFocusable()))
                .check(matches(hasErrorText(UtilTest.getStringForResources(
                        R.string.error_empty_field))));
    }

    private void checkEmptyCredentials(ViewInteraction editTextLogin,
                                       ViewInteraction buttonLogIn) {
        buttonLogIn.perform(click());

        editTextLogin.check(matches(isFocusable()))
                .check(matches(hasErrorText(UtilTest.getStringForResources(
                        R.string.error_empty_field))));
    }
}