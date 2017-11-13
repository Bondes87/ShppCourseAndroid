package com.dbondarenko.shpp.personalnotes.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dbondarenko.shpp.personalnotes.R;

public class ContentActivity extends AppCompatActivity {

    private static final String LOG_TAG = ContentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        getMenuInflater().inflate(R.menu.activity_content_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        if (item.getItemId() == R.id.itemLogOut) {
            runMainActivity();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Run the MainActivity.
     */
    private void runMainActivity() {
        Log.d(LOG_TAG, "runMainActivity()");
        Intent intentToStartNewActivity = new Intent(
                getApplicationContext(), MainActivity.class);
        intentToStartNewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentToStartNewActivity);
    }

    /**
     * Get the intent to run ContentActivity.
     *
     * @param context  The Context of the application package implementing this class.
     * @return the intent to run ContentActivity.
     */
    public static Intent newInstance(Context context) {
        Log.d(LOG_TAG, "runContentActivity()");
        Intent intentToStartContentActivity = new Intent(context, ContentActivity.class);
        intentToStartContentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intentToStartContentActivity;
    }

}
