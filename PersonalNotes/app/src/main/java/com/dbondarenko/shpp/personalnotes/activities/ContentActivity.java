package com.dbondarenko.shpp.personalnotes.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.fragments.NotesListFragment;

import butterknife.OnClick;

public class ContentActivity extends AppCompatActivity {

    private static final String LOG_TAG = ContentActivity.class.getSimpleName();

    /**
     * Get the intent to run ContentActivity.
     *
     * @param context The Context of the application package implementing this class.
     * @return the intent to run ContentActivity.
     */
    public static Intent newInstance(Context context) {
        Log.d(LOG_TAG, "runContentActivity()");
        Intent intentToStartContentActivity = new Intent(context, ContentActivity.class);
        intentToStartContentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intentToStartContentActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_content);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayoutContainerForContent, new NotesListFragment())
                    .commit();
        }
    }
}
