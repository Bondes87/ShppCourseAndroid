package com.dbondarenko.shpp.animationviews;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "main_activity";

    private static final int ONE_SECOND = 1000;
    // Settings for creating fall animation and return animation.
    private static final int ROTATION_X_FOR_FALL = 275;
    private static final int ROTATION_Y_FOR_FALL = 360;
    private static final int ROTATION_X_FOR_RETURN = 0;
    private static final int ROTATION_Y_FOR_RETURN = -360;
    private static final int TRANSLATION_X_FOR_RETURN = 0;
    private static final int TRANSLATION_Y_FOR_RETURN = 0;
    // Parameter names in the settings for creating fall animation and return animation.
    private static final String PROPERTY_NAME_MOVE_BY_X = "x";
    private static final String PROPERTY_NAME_MOVE_BY_Y = "y";
    private static final String PROPERTY_NAME_ROTATION_BY_X = "rotationX";
    private static final String PROPERTY_NAME_ROTATION_BY_Y = "rotationY";
    private static final String PROPERTY_NAME_TRANSLATION_BY_X = "translationX";
    private static final String PROPERTY_NAME_TRANSLATION_BY_Y = "translationY";
    private static final int DURATION_OF_RETURN_ANIMATION = ONE_SECOND;
    // The parameter name that is used to save in SharedPreferences and
    // get the result from SharedPreferences about using ShowcaseView.
    private static final String KEY_PREFERENCES_SHOWCASE_VIEW = "ShowcaseView";

    @BindView(R.id.constraintLayoutBoard)
    ConstraintLayout constraintLayoutBoard;
    @BindView(R.id.textViewStickerA)
    TextView textViewStickerA;

    // Stores the value true, if you can start the animation of the return;
    // if it stores the value false - then you can start the fall animation
    private boolean isStickersReturn;
    // Used to prohibit starting a new animation if the current
    // one did not reach the end.
    private AnimatorSet slowestAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        slowestAnimation = new AnimatorSet();
        presentShowcaseView();
    }

    @OnClick(R.id.constraintLayoutBoard)
    public void onViewClicked() {
        Log.d(LOG_TAG, "onViewClicked()");
        if (!isStickersReturn && !slowestAnimation.isRunning()) {
            isStickersReturn = true;
            runOfFallAnimation();
        }
    }

    @OnLongClick(R.id.constraintLayoutBoard)
    public boolean onViewLongClicked() {
        Log.d(LOG_TAG, "onViewLongClicked()");
        if (isStickersReturn && !slowestAnimation.isRunning()) {
            isStickersReturn = false;
            runOfReturnAnimation();
        }
        return true;
    }

    /**
     * Create and run fall animation for all views.
     */
    private void runOfFallAnimation() {
        long maxValueOfDuration = 0;
        for (int i = 0; i < constraintLayoutBoard.getChildCount(); i++) {
            long currentValueOfDuration = getValueOfDuration();
            Utility.checkForNotPositiveNumber(currentValueOfDuration);
            View sticker = constraintLayoutBoard.getChildAt(i);
            Utility.checkForNull(sticker);
            AnimatorSet fallAnimation = new AnimatorSet();
            fallAnimation.setInterpolator(new LinearInterpolator());
            fallAnimation.setDuration(currentValueOfDuration);
            fallAnimation.playTogether(ObjectAnimator.ofFloat(sticker, PROPERTY_NAME_MOVE_BY_X, getValueOfX()),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_NAME_MOVE_BY_Y, getValueOfY()),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_NAME_ROTATION_BY_X, ROTATION_X_FOR_FALL),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_NAME_ROTATION_BY_Y, ROTATION_Y_FOR_FALL));
            fallAnimation.start();
            // The slowest animation is an animation that has the longest duration.
            if (currentValueOfDuration > maxValueOfDuration) {
                maxValueOfDuration = currentValueOfDuration;
                slowestAnimation = fallAnimation;
            }
        }
    }

    /**
     * Create and run return animation for all views.
     */
    private void runOfReturnAnimation() {
        for (int i = 0; i < constraintLayoutBoard.getChildCount(); i++) {
            View sticker = constraintLayoutBoard.getChildAt(i);
            Utility.checkForNull(sticker);
            AnimatorSet returnAnimation = new AnimatorSet();
            returnAnimation.setInterpolator(new LinearInterpolator());
            returnAnimation.setDuration(DURATION_OF_RETURN_ANIMATION);
            returnAnimation.playTogether(
                    ObjectAnimator.ofFloat(sticker, PROPERTY_NAME_TRANSLATION_BY_X, TRANSLATION_X_FOR_RETURN),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_NAME_TRANSLATION_BY_Y, TRANSLATION_Y_FOR_RETURN),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_NAME_ROTATION_BY_X, ROTATION_X_FOR_RETURN),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_NAME_ROTATION_BY_Y, ROTATION_Y_FOR_RETURN));
            returnAnimation.start();
            // The slowest animation is the last one.
            if (i == constraintLayoutBoard.getChildCount() - 1) {
                slowestAnimation = returnAnimation;
            }
        }
    }

    /**
     * Get the x coordinate to move the view. The coordinate is a random number
     * between the maximum and minimum X coordinates.
     *
     * @return The x coordinate.
     */
    private float getValueOfX() {
        int maxValueOfX = constraintLayoutBoard.getWidth() - constraintLayoutBoard.getPaddingEnd()
                - textViewStickerA.getWidth();
        int minValueOfX = constraintLayoutBoard.getPaddingStart();
        return minValueOfX + Utility.getRandomNumber(maxValueOfX);
    }

    /**
     * Get the y coordinate to move the view.
     *
     * @return The y coordinate.
     */
    private float getValueOfY() {
        return constraintLayoutBoard.getHeight()
                - constraintLayoutBoard.getPaddingBottom()
                - textViewStickerA.getHeight();
    }

    /**
     * Get the duration of the animation. Time is a milliseconds,
     * which is obtained randomly between one and a few seconds.
     *
     * @return The duration of the animation in milliseconds.
     */
    private long getValueOfDuration() {
        return ONE_SECOND + Utility.getRandomNumber(4 * ONE_SECOND);
    }

    /**
     * Create and start the presentation of ShowcaseView.
     */
    private void presentShowcaseView() {
        if (!isWasUsedShowcaseView()) {
            saveShowcaseViewSettings();
            new ShowcaseView.Builder(this)
                    .setStyle(R.style.ShowcaseViewTheme)
                    .setTarget(ActionViewTarget.NONE)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.showcase_view_content_text))
                    .build();
        }
    }

    /**
     * Returns true if ShowcaseView has already been was used, otherwise it returns false.
     *
     * @return The true if ShowcaseView has already been was used, otherwise it returns false.
     */
    private boolean isWasUsedShowcaseView() {
        Log.d(LOG_TAG, "isWasUsedShowcaseView()");
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean(KEY_PREFERENCES_SHOWCASE_VIEW, false);
    }

    /**
     * Save the value to true in SharedPreferences if ShowcaseView is used.
     */
    private void saveShowcaseViewSettings() {
        Log.d(LOG_TAG, "saveShowcaseViewSettings()");
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .edit()
                .putBoolean(KEY_PREFERENCES_SHOWCASE_VIEW, true)
                .apply();
    }
}