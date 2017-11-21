package com.dbondarenko.shpp.personalnotes.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.fragments.NotesListFragment;
import com.dbondarenko.shpp.personalnotes.listeners.OnEventNoteListener;
import com.dbondarenko.shpp.personalnotes.models.NoteModel;

public class ContentActivity extends AppCompatActivity implements OnEventNoteListener {

    private static final String LOG_TAG = ContentActivity.class.getSimpleName();

    public static Intent newInstance(Context context) {
        Log.d(LOG_TAG, "runContentActivity()");
        Intent intentToStartContentActivity = new Intent(context, ContentActivity.class);
        intentToStartContentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intentToStartContentActivity;
    }

    @Override
    public void onAddNote(NoteModel note) {
        NotesListFragment notesListFragment = (NotesListFragment)
                getSupportFragmentManager()
                        .findFragmentByTag(Constants.TAG_OF_NOTES_LIST_FRAGMENT);
        notesListFragment.addNoteToAdapter(note);
    }

    @Override
    public void onDeleteNote(NoteModel note) {
        NotesListFragment notesListFragment = (NotesListFragment)
                getSupportFragmentManager()
                        .findFragmentByTag(Constants.TAG_OF_NOTES_LIST_FRAGMENT);
        notesListFragment.deleteNoteFromAdapter(note);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_content);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayoutContainerForContent, new NotesListFragment(),
                            Constants.TAG_OF_NOTES_LIST_FRAGMENT)
                    .commit();
        }
    }
}
