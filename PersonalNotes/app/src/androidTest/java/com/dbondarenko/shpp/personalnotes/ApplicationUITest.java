package com.dbondarenko.shpp.personalnotes;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import com.dbondarenko.shpp.personalnotes.activities.MainActivity;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import org.junit.Before;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
    private static boolean setUpIsDone = false;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void prepareForTesting() {
        if (setUpIsDone) {
            return;
        }
        runToMainActivity();
        getActivityInstance().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setUpIsDone = true;
    }

    @Test
    public void testIncorrectCredentialsForLoginUsingSQL() {
        openActionBarOverflowOrOptionsMenu(getActivityInstance());
        selectMenuItem(R.string.text_use_local_database);
        makeDelay();
        checkIncorrectCredentialsForLogin(false);
    }

    @Test
    public void testIncorrectCredentialsForLoginUsingFirebase() {
        openActionBarOverflowOrOptionsMenu(getActivityInstance());
        selectMenuItem(R.string.text_use_server_database);
        makeDelay();
        checkIncorrectCredentialsForLogin(true);
    }

    @Test
    public void testSavingCredentialsWhenChangedScreenOrientationForLogin() {
        openActionBarOverflowOrOptionsMenu(getActivityInstance());
        selectMenuItem(R.string.text_use_server_database);
        makeDelay();
        checkIncorrectCredentialsForLogin(true);
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

    private void runToMainActivity() {
        if (!(getActivityInstance() instanceof MainActivity)) {
            ViewInteraction actionMenuItemLogOut = onView(withId(R.id.itemLogOut));
            actionMenuItemLogOut.check(matches(isDisplayed()));
            actionMenuItemLogOut.perform(click());
        }
    }

    private void checkIncorrectCredentialsForLogin(boolean isUsedFirebase) {
        ViewInteraction editTextLogin = getViewInteraction(R.id.editTextLogin);
        ViewInteraction editTextPassword = getViewInteraction(R.id.editTextPassword);
        ViewInteraction buttonLogIn = getViewInteraction(R.id.buttonLogIn);

        checkDisplayViewInteraction(editTextLogin, editTextPassword, buttonLogIn);

        editTextLogin.check(matches(isFocusable()));
        checkEmptyCredentials(editTextLogin, buttonLogIn);
        makeDelay();
        checkEmptyPassword(editTextLogin, editTextPassword, buttonLogIn);
        makeDelay();
        checkEmptyLogin(editTextLogin, editTextPassword, buttonLogIn);
        makeDelay();
        if (isUsedFirebase &&
                !(Util.isInternetConnectionAvailable(getActivityInstance()))) {
            onView(allOf(withId(android.support.design.R.id.snackbar_text),
                    withText(getStringForResources(R.string.error_no_internet_connection))))
                    .check(matches(isDisplayed()));
        } else {
            checkIncorrectCredentials(editTextLogin, editTextPassword, buttonLogIn);
        }
        makeDelay();
    }

    private void selectMenuItem(int stringId) {
        ViewInteraction itemOptionMenuLocalDatabase = onView(withText(stringId));
        itemOptionMenuLocalDatabase.check(matches(isDisplayed()));
        itemOptionMenuLocalDatabase.perform(click());
    }

    private void checkIncorrectCredentials(ViewInteraction editTextLogin,
                                           ViewInteraction editTextPassword,
                                           ViewInteraction buttonLogIn) {
        editTextLogin.perform(clearText());
        editTextPassword.perform(clearText());
        editTextLogin.perform(typeText(INCORRECT_LOGIN));
        editTextPassword.perform(typeText(INCORRECT_PASSWORD));
        closeSoftKeyboard();
        buttonLogIn.perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(getStringForResources(R.string.error_invalid_login_or_password))))
                .check(matches(isDisplayed()));
    }

    private void checkEmptyLogin(ViewInteraction editTextLogin,
                                 ViewInteraction editTextPassword,
                                 ViewInteraction buttonLogIn) {
        editTextLogin.perform(clearText());
        editTextPassword.perform(typeText(INCORRECT_PASSWORD));
        closeSoftKeyboard();
        buttonLogIn.perform(click());
        editTextLogin.check(matches(isFocusable()));
        editTextLogin.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private void checkEmptyPassword(ViewInteraction editTextLogin,
                                    ViewInteraction editTextPassword,
                                    ViewInteraction buttonLogIn) {
        editTextLogin.perform(typeText(INCORRECT_LOGIN));
        closeSoftKeyboard();
        buttonLogIn.perform(click());
        editTextPassword.check(matches(isFocusable()));
        editTextPassword.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private void checkEmptyCredentials(ViewInteraction editTextLogin,
                                       ViewInteraction buttonLogIn) {
        buttonLogIn.perform(click());
        editTextLogin.check(matches(isFocusable()));
        editTextLogin.check(matches(hasErrorText(getStringForResources(
                R.string.error_empty_field))));
    }

    private String getStringForResources(int stringId) {
        return getActivityInstance().getString(stringId);
    }

    private void makeDelay() {
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

    private ViewInteraction getViewInteraction(int viewId) {
        return onView(withId(viewId));
    }
}
