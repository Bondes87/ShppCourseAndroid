package com.dbondarenko.shpp.personalnotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.fragments.InfoDialogFragment;
import com.dbondarenko.shpp.personalnotes.fragments.LoginFragment;
import com.dbondarenko.shpp.personalnotes.fragments.RegisterFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        Fragment loginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutContainer, loginFragment)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        Log.d(LOG_TAG, "onStop()");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onUserInterfaceEvent(String userInterfaceCommand) {
        Log.d(LOG_TAG, "onUserInterfaceEvent()");
        switch (userInterfaceCommand) {
            case Constants.COMMAND_FOR_RUN_CONTENT_ACTIVITY:
                runContentActivity();
                finish();
                break;
            case Constants.COMMAND_FOR_RUN_REGISTER_FRAGMENT:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayoutContainer, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case Constants.COMMAND_FOR_RUN_INFO_DIALOG_FRAGMENT_FOR_LOGIN:
                showDialogFragment(getString(R.string.login_information),
                        Constants.TAG_OF_INFO_DIALOG_FRAGMENT_FOR_LOGIN);
                break;
            case Constants.COMMAND_FOR_RUN_INFO_DIALOG_FRAGMENT_FOR_PASSWORD:
                showDialogFragment(getString(R.string.password_information),
                        Constants.TAG_OF_INFO_DIALOG_FRAGMENT_FOR_PASSWORD);
                break;
        }
    }

    /**
     * Show dialog with prompt.
     *
     * @param dialogMessage The message for the dialog box.
     * @param fragmentTag   The teg of fragment.
     */
    private void showDialogFragment(String dialogMessage, String fragmentTag) {
        Log.d(LOG_TAG, "showDialogFragment()");
        InfoDialogFragment infoDialogFragment = InfoDialogFragment
                .newInstance(dialogMessage);
        infoDialogFragment.show(getSupportFragmentManager(), fragmentTag);
    }

    /**
     * Run the MainActivity.
     */
    private void runContentActivity() {
        Log.d(LOG_TAG, "runContentActivity()");
        Intent intentToStartContentActivity = new Intent(
                getApplicationContext(), ContentActivity.class);
        intentToStartContentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentToStartContentActivity);
    }
}