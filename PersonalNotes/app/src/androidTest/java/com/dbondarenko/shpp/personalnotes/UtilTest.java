package com.dbondarenko.shpp.personalnotes;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Matcher;

import java.util.Collection;
import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * File: UtilTest.java
 * Created by Dmitro Bondarenko on 21.12.2017.
 */
public class UtilTest {

    public static Activity getActivityInstance() {
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

    public static void setScreenOrientation(int screenOrientation) {
        getActivityInstance().setRequestedOrientation(screenOrientation);
    }

    public static void makePause() {
        try {
            Thread.sleep(ConstantsTest.ONE_SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getStringForResources(int stringId) {
        return getActivityInstance().getString(stringId);
    }

    public static String getRandomString(int length) {
        StringBuilder result = new StringBuilder();
        while (length > 0) {
            Random rand = new Random();
            result.append(ConstantsTest.CHARACTERS_FOR_CREATING_LOGIN_OR_PASSWORD
                    .charAt(rand.nextInt(
                            ConstantsTest.CHARACTERS_FOR_CREATING_LOGIN_OR_PASSWORD.length())));
            length--;
        }
        return result.toString();
    }

    public static int getRandomNumber(int maxNumber) {
        Random rand = new Random();
        return rand.nextInt(maxNumber);
    }

    public static String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(EditText.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                EditText tv = (EditText) view;
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }
}