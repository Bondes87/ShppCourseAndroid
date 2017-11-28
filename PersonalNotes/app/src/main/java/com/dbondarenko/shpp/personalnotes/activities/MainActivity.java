package com.dbondarenko.shpp.personalnotes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.fragments.LoginFragment;
import com.dbondarenko.shpp.personalnotes.utils.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        if (SharedPreferencesManager.getSharedPreferencesManager()
                .getUser(getApplicationContext()) != null) {
            startActivity(ContentActivity.newInstance(getApplicationContext()));
            finish();
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayoutContainer, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        if (SharedPreferencesManager.getSharedPreferencesManager()
                .isUseFirebase(getApplicationContext())) {
            menu.getItem(1).setChecked(true);
        } else {
            menu.getItem(0).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.localDatabase:
                item.setChecked(!item.isChecked());
                SharedPreferencesManager.getSharedPreferencesManager()
                        .saveInformationAboutDatabase(getApplicationContext(),
                                false);
                return true;
            case R.id.serverDatabase:
                item.setChecked(!item.isChecked());
                SharedPreferencesManager.getSharedPreferencesManager()
                        .saveInformationAboutDatabase(getApplicationContext(),
                                true);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}