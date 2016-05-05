package com.tevinjeffrey.tictactoe;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class GameTest extends ActivityInstrumentationTestCase2<GameActivity> {

    public GameTest(String pkg, Class<GameActivity> activityClass) {
        super(pkg, activityClass);
    }

    public GameTest() {
        super(GameActivity.class.getCanonicalName(), GameActivity.class);
    }

    public void testActivityLaunch() {
        // Launch the activity
        Activity activity = getActivity();

        activity.finish();

        setActivity(null);
    }

    public void testActivityRecreation() {
        Activity activity = getActivity();

        getInstrumentation().callActivityOnRestart(activity);

        activity.finish();

        setActivity(null);
    }
}