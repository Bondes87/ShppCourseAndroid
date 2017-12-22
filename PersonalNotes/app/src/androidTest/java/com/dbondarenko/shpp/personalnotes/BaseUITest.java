package com.dbondarenko.shpp.personalnotes;

import android.support.test.espresso.ViewInteraction;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.dbondarenko.shpp.personalnotes.activities.MainActivity;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * File: BaseUITest.java
 * Created by Dmitro Bondarenko on 21.12.2017.
 */
public class BaseUITest {

    protected boolean isSelectedFirebase;

    private void selectMenuItem(int stringId) {
        onView(withText(UtilTest.getStringForResources(stringId)))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    protected void checkPressingBackButton() {
        onView(allOf(isAssignableFrom(ImageView.class),
                withParent(isAssignableFrom(Toolbar.class)),
                withContentDescription(R.string.abc_action_bar_up_description)))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    protected void runToMainActivity() {
        if (!(UtilTest.getActivityInstance() instanceof MainActivity)) {
            checkPressingLogOutButton();
        }
    }

    protected void checkPressingLogOutButton() {
        ViewInteraction actionMenuItemLogOut = onView(withId(R.id.itemLogOut));
        actionMenuItemLogOut.check(matches(isDisplayed()));
        actionMenuItemLogOut.perform(click());
        onView(withId(R.id.buttonLogIn))
                .check(matches(isDisplayed()));
    }

    protected void useSQLite() {
        if (isSelectedFirebase) {
            openActionBarOverflowOrOptionsMenu(UtilTest.getActivityInstance());
            selectMenuItem(R.string.text_use_local_database);
        }
    }

    protected void useFirebase() {
        if (!isSelectedFirebase) {
            openActionBarOverflowOrOptionsMenu(UtilTest.getActivityInstance());
            selectMenuItem(R.string.text_use_server_database);
        }
    }

    protected void runToRegisterWindow() {
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

    protected void checkSnackbarWithMessageNoInternetConection() {
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(UtilTest.getStringForResources(
                        R.string.error_no_internet_connection))))
                .check(matches(isDisplayed()));
    }

    protected void checkLoginUserWithCorrectCredentials() {
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
}