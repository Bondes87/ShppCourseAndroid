package com.dbondarenko.shpp.cookislands.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dbondarenko.shpp.cookislands.R;
import com.dbondarenko.shpp.cookislands.database.CookIslandsSQLiteManager;
import com.dbondarenko.shpp.cookislands.models.UserModel;
import com.dbondarenko.shpp.cookislands.utils.SharedPreferencesManager;

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
        if (SharedPreferencesManager.getSharedPreferencesManager().
                getInformationAboutLogin(getApplicationContext())) {
            runContentActivity();
            finish();
        }
    }

    @OnClick({R.id.buttonLogIn, R.id.buttonRegister})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonLogIn:
                UserModel user = CookIslandsSQLiteManager.getUser(getApplicationContext(),
                        editTextLogin.getText().toString(), editTextPassword.getText().toString());
                if (user != null) {
                    SharedPreferencesManager.getSharedPreferencesManager()
                            .saveInformationAboutLogin(getApplicationContext(), user.getIslandId());
                    runContentActivity();
                    finish();
                } else {
                    reportIncorrectLoginOrPassword(view);
                }
                break;
            case R.id.buttonRegister:
                runRegisterActivity();
                break;
        }
    }

    private void reportIncorrectLoginOrPassword(View view) {
        Snackbar snackbar = Snackbar.make(view,
                getString(R.string.error_invalid_login_or_password),
                Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
        snackbar.show();
    }

    private void runRegisterActivity() {
        runActivity(RegisterActivity.class);
    }

    private void runContentActivity() {
        runActivity(ContentActivity.class);
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