package com.dbondarenko.shpp.personalnotes;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import java.util.Collection;

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
}
