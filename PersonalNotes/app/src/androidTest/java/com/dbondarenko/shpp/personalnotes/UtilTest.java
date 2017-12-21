package com.dbondarenko.shpp.personalnotes;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import java.util.Collection;
import java.util.Random;

import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * File: UtilTest.java
 * Created by Dmitro Bondarenko on 21.12.2017.
 */
class UtilTest {

    static Activity getActivityInstance() {
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

    static void setScreenOrientation(int screenOrientation) {
        getActivityInstance().setRequestedOrientation(screenOrientation);
    }

    static void makePause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static String getStringForResources(int stringId) {
        return getActivityInstance().getString(stringId);
    }

    static String getRandomString(int length) {
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
}