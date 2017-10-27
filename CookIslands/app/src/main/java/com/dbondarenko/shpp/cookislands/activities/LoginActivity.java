package com.dbondarenko.shpp.cookislands.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dbondarenko.shpp.cookislands.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The login screen that offers login via login / password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.editTextLogin)
    EditText editTextLogin;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.buttonLogIn)
    Button buttonLogIn;
    @BindView(R.id.buttonRegister)
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.buttonLogIn, R.id.buttonRegister})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonLogIn:
                runActivity(ContentActivity.class);
                break;
            case R.id.buttonRegister:
                runActivity(RegisterActivity.class);
                break;
        }
    }

    private void runActivity(Class<?> activityClass) {
        Log.d(LOG_TAG, "runActivity()");
        Intent intentToStartAlarmActivity = new Intent(getApplicationContext(),
                activityClass);
        // Set this action as the beginning of a new task in this history stack.
        intentToStartAlarmActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentToStartAlarmActivity);
    }
}