package com.dbondarenko.shpp.personalnotes.tests;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dbondarenko.shpp.personalnotes.BaseUITest;
import com.dbondarenko.shpp.personalnotes.ConstantsTest;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.UtilTest;
import com.dbondarenko.shpp.personalnotes.activities.ContentActivity;
import com.dbondarenko.shpp.personalnotes.activities.MainActivity;
import com.dbondarenko.shpp.personalnotes.utils.SharedPreferencesManager;

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
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbondarenko.shpp.personalnotes.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

/**
 * File: ActionsWithNotesUITest.java
 * Created by Dmitro Bondarenko on 21.12.2017.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActionsWithNotesUITest extends BaseUITest {

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
    public void test01AddNoteUsedSQLite() {
        runToContentActivityUsedSQLite();

        checkAddNotes(ConstantsTest.COUNT_OF_NOTES_TO_CREATE);
    }

    @Test
    public void test02CorrectDisplayingNotesInListUsedSQLite() {
        runToContentActivityUsedSQLite();

        setUp();

        checkCorrectDisplayingNotesInList();
    }

    @Test
    public void test03PressingBackButtonToNotesListFromNoteWindowUsedSQLite() {
        runToContentActivityUsedSQLite();

        setUp();

        checkPressingBackButtonToNotesListFromNoteWindow();
    }

    @Test
    public void test04PressingLogOutButtonUsedSQLite() {
        runToContentActivityUsedSQLite();

        checkPressingLogOutButton();
    }

    @Test
    public void test05FloatingActionButtonAnimationsUsedSQLite() {
        runToContentActivityUsedSQLite();

        setUp();

        checkFloatingActionButtonAnimations();
    }

    @Test
    public void test06ChangeNoteUsedSQLite() {
        runToContentActivityUsedSQLite();

        setUp();

        checkChangeNote(ConstantsTest.FIRST_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkChangeNote(ConstantsTest.FOURTH_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkChangeNote(ConstantsTest.SECOND_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkChangeNote(ConstantsTest.THIRD_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();
    }

    @Test
    public void test07UndoDeleteNoteUsedSQLite() {
        runToContentActivityUsedSQLite();

        setUp();

        checkUndoDeleteNoteUsedSwipe(ConstantsTest.FIRST_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkUndoDeleteNote(ConstantsTest.FOURTH_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkUndoDeleteNoteUsedSwipe(ConstantsTest.SECOND_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkUndoDeleteNote(ConstantsTest.THIRD_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();
    }

    @Test
    public void test08DeleteNoteUsedSQLite() {
        runToContentActivityUsedSQLite();

        setUp();

        checkDeleteNote(ConstantsTest.FOURTH_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkDeleteNote(ConstantsTest.THIRD_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkDeleteNoteUsedSwipe(ConstantsTest.SECOND_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkDeleteNoteUsedSwipe(ConstantsTest.FIRST_POSITION_OF_LIST_ITEM_TO_CHANGE);
    }

    @Test
    public void test09AddEmptyNoteUsedSQLite() {
        runToContentActivityUsedSQLite();

        checkAddNote("");

        onView(withText(UtilTest.getStringForResources(R.string.error_note_is_empty)))
                .check(matches(isDisplayed()));

        UtilTest.makePause();
    }

    @Test
    public void test10SavingNoteTextWhenChangedScreenOrientationUsedSQLite() {
        runToContentActivityUsedSQLite();

        checkAddNote(ConstantsTest.CORRECT_LOGIN);

        ViewInteraction recyclerViewNotesList =
                onView(withId(R.id.recyclerViewNotesList));
        recyclerViewNotesList.check(matches(isDisplayed()));
        clickOnRecyclerViewItem(recyclerViewNotesList, 0);
        UtilTest.makePause();

        ViewInteraction editTextMessage = onView(withId(R.id.editTextMessage));
        editTextMessage.check(matches(isDisplayed()));
        UtilTest.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        UtilTest.makePause();

        editTextMessage.check(matches(withText(ConstantsTest.CORRECT_LOGIN)));
        UtilTest.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        UtilTest.makePause();

        editTextMessage.check(matches(withText(ConstantsTest.CORRECT_LOGIN)));
    }

    @Test
    public void test11AddNoteUsedFirebase() {
        runToContentActivityUsedFirebase();

        checkAddNotes(ConstantsTest.COUNT_OF_NOTES_TO_CREATE);
    }

    @Test
    public void test12CorrectDisplayingNotesInListUsedFirebase() {
        runToContentActivityUsedFirebase();

        setUp();

        checkCorrectDisplayingNotesInList();
    }

    @Test
    public void test13PressingBackButtonToNotesListFromNoteWindowUsedFirebase() {
        runToContentActivityUsedFirebase();

        setUp();

        checkPressingBackButtonToNotesListFromNoteWindow();
    }

    @Test
    public void test14PressingLogOutButtonUsedFirebase() {
        runToContentActivityUsedFirebase();
        checkPressingLogOutButton();
    }

    @Test
    public void test15FloatingActionButtonAnimationsUsedFirebase() {
        runToContentActivityUsedFirebase();

        setUp();

        checkFloatingActionButtonAnimations();
    }

    @Test
    public void test16ChangeNoteUsedFirebase() {
        runToContentActivityUsedFirebase();

        setUp();

        checkChangeNote(ConstantsTest.FIRST_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkChangeNote(ConstantsTest.FOURTH_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkChangeNote(ConstantsTest.SECOND_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkChangeNote(ConstantsTest.THIRD_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();
    }

    @Test
    public void test17UndoDeleteNoteUsedFirebase() {
        runToContentActivityUsedFirebase();

        setUp();

        checkUndoDeleteNoteUsedSwipe(ConstantsTest.FIRST_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkUndoDeleteNote(ConstantsTest.FOURTH_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkUndoDeleteNoteUsedSwipe(ConstantsTest.SECOND_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkUndoDeleteNote(ConstantsTest.THIRD_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();
    }

    @Test
    public void test18DeleteNoteUsedFirebase() {
        runToContentActivityUsedFirebase();

        setUp();

        checkDeleteNote(ConstantsTest.FOURTH_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkDeleteNote(ConstantsTest.THIRD_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkDeleteNoteUsedSwipe(ConstantsTest.SECOND_POSITION_OF_LIST_ITEM_TO_CHANGE);
        UtilTest.makePause();

        checkDeleteNoteUsedSwipe(ConstantsTest.FIRST_POSITION_OF_LIST_ITEM_TO_CHANGE);
    }

    @Test
    public void test19AddEmptyNoteUsedFirebase() {
        runToContentActivityUsedFirebase();

        checkAddNote("");

        onView(withText(UtilTest.getStringForResources(R.string.error_note_is_empty)))
                .check(matches(isDisplayed()));

        UtilTest.makePause();
    }

    @Test
    public void test20SavingNoteTextWhenChangedScreenOrientationUsedFirebase() {
        runToContentActivityUsedFirebase();

        checkAddNote(ConstantsTest.CORRECT_LOGIN);

        ViewInteraction recyclerViewNotesList =
                onView(withId(R.id.recyclerViewNotesList));
        recyclerViewNotesList.check(matches(isDisplayed()));
        clickOnRecyclerViewItem(recyclerViewNotesList, 0);
        UtilTest.makePause();

        ViewInteraction editTextMessage = onView(withId(R.id.editTextMessage));
        editTextMessage.check(matches(isDisplayed()));
        UtilTest.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        UtilTest.makePause();

        editTextMessage.check(matches(withText(ConstantsTest.CORRECT_LOGIN)));
        UtilTest.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        UtilTest.makePause();

        editTextMessage.check(matches(withText(ConstantsTest.CORRECT_LOGIN)));
    }

    private void setUp() {
        try {
            onView(withId(R.id.recyclerViewNotesList))
                    .check(matches(isDisplayed()))
                    .check(withItemCount(greaterThan(
                            ConstantsTest.COUNT_OF_NOTES_TO_CREATE - 1)));
        } catch (Exception e) {
            checkAddNotes(ConstantsTest.COUNT_OF_NOTES_TO_CREATE);
        }
    }

    private void checkPressingBackButtonToNotesListFromNoteWindow() {
        ViewInteraction recyclerViewNotesList =
                onView(withId(R.id.recyclerViewNotesList));
        recyclerViewNotesList.check(matches(isDisplayed()));

        clickOnRecyclerViewItem(recyclerViewNotesList, 0);

        onView(withText(String.valueOf(ConstantsTest.COUNT_OF_NOTES_TO_CREATE - 1)))
                .check(matches(isDisplayed()));

        checkPressingBackButton();
        UtilTest.makePause();

        ViewInteraction floatingActionButtonAddNote =
                onView(withId(R.id.floatingActionButtonAddNote));
        floatingActionButtonAddNote.check(matches(isDisplayed()))
                .perform(click());

        checkPressingBackButton();

        floatingActionButtonAddNote.check(matches(isDisplayed()));
    }

    private void checkCorrectDisplayingNotesInList() {
        ViewInteraction recyclerViewNotesList = onView(withId(R.id.recyclerViewNotesList));
        recyclerViewNotesList.check(matches(isDisplayed()));

        clickOnRecyclerViewItem(recyclerViewNotesList,
                UtilTest.getRandomNumber(
                        ConstantsTest.COUNT_OF_NOTES_TO_CREATE - 1));
        UtilTest.makePause();

        String text = UtilTest.getText(withId(R.id.editTextMessage));

        checkPressingBackButton();

        onView(withText(text))
                .check(matches(isDisplayed()));
    }


    private void clickOnRecyclerViewItem(ViewInteraction recyclerViewNotesList,
                                         int itemPosition) {
        recyclerViewNotesList.perform(RecyclerViewActions
                .actionOnItemAtPosition(itemPosition, click()));
    }

    private void checkAddNotes(int countNotes) {
        for (int i = 0; i < countNotes; i++) {
            checkAddNote(String.valueOf(i));
            onView(withId(R.id.floatingActionButtonAddNote))
                    .check(matches(isDisplayed()));
        }
    }

    private void runToContentActivityUsedSQLite() {
        if (UtilTest.getActivityInstance() instanceof ContentActivity) {
            if (!isSelectedFirebase) {
                return;
            } else {
                runToMainActivity();
            }
        }
        useSQLite();
        checkLoginUserWithCorrectCredentials();
    }

    private void runToContentActivityUsedFirebase() {
        if (UtilTest.getActivityInstance() instanceof ContentActivity) {
            if (isSelectedFirebase) {
                return;
            } else {
                runToMainActivity();
            }
        }
        useFirebase();
        checkLoginUserWithCorrectCredentials();
    }

    private void checkAddNote(String noteText) {
        onView(withId(R.id.floatingActionButtonAddNote))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.editTextMessage))
                .check(matches(isDisplayed()))
                .perform(typeText(noteText));

        onView(withId(R.id.itemSaveNote))
                .check(matches(isDisplayed()))
                .perform(click());

        closeSoftKeyboard();
    }

    private void checkFloatingActionButtonAnimations() {
        ViewInteraction recyclerViewNotesList =
                onView(withId(R.id.recyclerViewNotesList));
        recyclerViewNotesList.check(matches(isDisplayed()));
        ViewInteraction floatingActionButtonAddNote =
                onView(withId(R.id.floatingActionButtonAddNote));

        recyclerViewNotesList.check(matches(isDisplayed())).perform(swipeUp());
        floatingActionButtonAddNote.check(matches(not(isDisplayed())));
        UtilTest.makePause();

        recyclerViewNotesList.perform(swipeDown());
        floatingActionButtonAddNote.check(matches(not(isDisplayed())));

        UtilTest.makePause();
        floatingActionButtonAddNote.check(matches(isDisplayed()));
    }

    private void checkChangeNote(int notePosition) {
        ViewInteraction recyclerViewNotesList =
                onView(withId(R.id.recyclerViewNotesList));
        recyclerViewNotesList.check(matches(isDisplayed()));
        clickOnRecyclerViewItem(recyclerViewNotesList, notePosition);

        ViewInteraction editTextMessage = onView(withId(R.id.editTextMessage));
        editTextMessage.check(matches(isDisplayed()))
                .perform(clearText(), typeText(ConstantsTest.NEW_TEXT_NOTE));

        onView(withId(R.id.itemSaveNote))
                .check(matches(isDisplayed()))
                .perform(click());
        closeSoftKeyboard();
        UtilTest.makePause();

        recyclerViewNotesList.check(matches(isDisplayed()));
        clickOnRecyclerViewItem(recyclerViewNotesList, notePosition);

        editTextMessage.check(matches(isDisplayed()))
                .check(matches(withText(ConstantsTest.NEW_TEXT_NOTE)));

        checkPressingBackButton();
    }

    private void checkDeleteNote(int notePosition) {
        ViewInteraction recyclerViewNotesList =
                onView(withId(R.id.recyclerViewNotesList));
        recyclerViewNotesList.check(matches(isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(notePosition));
        clickOnRecyclerViewItem(recyclerViewNotesList, notePosition);

        clickDeleteNoteButton();

        clickDeleteButtonFromDeleteNoteDialog();

        recyclerViewNotesList.check(matches(isDisplayed()));
    }

    private void clickDeleteButtonFromDeleteNoteDialog() {
        onView(withText(UtilTest.getStringForResources(R.string.text_delete_note)))
                .check(matches(isDisplayed()));
        UtilTest.makePause();

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());
        UtilTest.makePause();
    }

    private void clickDeleteNoteButton() {
        onView(withId(R.id.itemDeleteNote))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    private void checkDeleteNoteUsedSwipe(int notePosition) {
        ViewInteraction recyclerViewNotesList =
                onView(withId(R.id.recyclerViewNotesList));
        recyclerViewNotesList.check(matches(isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(notePosition - 1));

        UtilTest.makePause();
        swipeOnRecyclerViewItem(recyclerViewNotesList, notePosition);

        clickOnRecyclerViewItem(recyclerViewNotesList, notePosition);

        onView(withId(R.id.editTextMessage))
                .check(matches(not(withText(ConstantsTest.NEW_TEXT_NOTE))));
        checkPressingBackButton();
    }

    private void swipeOnRecyclerViewItem(ViewInteraction recyclerViewNotesList,
                                         int itemPosition) {
        recyclerViewNotesList.perform(RecyclerViewActions
                .actionOnItemAtPosition(itemPosition, swipeLeft()));
    }

    private void checkUndoDeleteNoteUsedSwipe(int notePosition) {
        checkDeleteNoteUsedSwipe(notePosition);
        UtilTest.makePause();

        onView(withId(android.support.design.R.id.snackbar_action))
                .check(matches(isDisplayed())).perform(click());
    }

    private void checkUndoDeleteNote(int notePosition) {
        ViewInteraction recyclerViewNotesList =
                onView(withId(R.id.recyclerViewNotesList));
        recyclerViewNotesList.check(matches(isDisplayed()));

        clickOnRecyclerViewItem(recyclerViewNotesList, notePosition);

        clickDeleteNoteButton();

        clickСancelButtonFromDeleteNoteDialog();

        checkPressingBackButton();
        recyclerViewNotesList.check(matches(isDisplayed()));
    }

    private void clickСancelButtonFromDeleteNoteDialog() {
        onView(withText(UtilTest.getStringForResources(R.string.text_delete_note)))
                .check(matches(isDisplayed()));
        UtilTest.makePause();

        onView(withId(android.R.id.button2)).check(matches(isDisplayed()))
                .perform(click());
        UtilTest.makePause();
    }
}