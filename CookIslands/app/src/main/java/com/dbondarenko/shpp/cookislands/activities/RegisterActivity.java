package com.dbondarenko.shpp.cookislands.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dbondarenko.shpp.cookislands.Constants;
import com.dbondarenko.shpp.cookislands.R;
import com.dbondarenko.shpp.cookislands.database.CookIslandsSQLiteManager;
import com.dbondarenko.shpp.cookislands.fragments.InfoDialogFragment;
import com.dbondarenko.shpp.cookislands.models.IslandModel;
import com.dbondarenko.shpp.cookislands.models.UserModel;
import com.dbondarenko.shpp.cookislands.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.editTextLogin)
    EditText editTextLogin;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editTextRepeatedPassword)
    EditText editTextRepeatedPassword;
    @BindView(R.id.imageViewLoginInfo)
    ImageView imageViewLoginInfo;
    @BindView(R.id.imageViewPasswordInfo)
    ImageView imageViewPasswordInfo;
    @BindView(R.id.spinnerIslandsNames)
    Spinner spinnerIslandsNames;
    @BindView(R.id.buttonRegister)
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        spinnerIslandsNames.setAdapter(createSpinnerAdapter());
    }

    @OnClick({R.id.buttonRegister, R.id.imageViewLoginInfo, R.id.imageViewPasswordInfo})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonRegister:
                Log.d(LOG_TAG, "buttonRegister");
                if (validateLoginAndPassword()) {
                    int userIslandId = spinnerIslandsNames.getSelectedItemPosition() + 1;
                    UserModel newUser = new UserModel(editTextLogin.getText().toString(),
                            editTextPassword.getText().toString(), userIslandId);
                    CookIslandsSQLiteManager.addUser(getApplicationContext(), newUser);
                    SharedPreferencesManager.getSharedPreferencesManager()
                            .saveInformationAboutLogin(getApplicationContext(), userIslandId);
                    runContentActivity();
                    finish();
                }
                break;
            case R.id.imageViewLoginInfo:
                Log.d(LOG_TAG, "imageViewLoginInfo");
                showDialogFragment(getString(R.string.login_information),
                        Constants.TAG_OF_INFO_DIALOG_FRAGMENT_FOR_LOGIN);
                break;
            case R.id.imageViewPasswordInfo:
                Log.d(LOG_TAG, "imageViewPasswordInfo");
                showDialogFragment(getString(R.string.password_information),
                        Constants.TAG_OF_INFO_DIALOG_FRAGMENT_FOR_PASSWORD);
                break;
        }
    }

    private boolean validateLoginAndPassword() {
        Log.d(LOG_TAG, "validateLoginAndPassword()");
        String login = editTextLogin.getText().toString();
        String password = editTextPassword.getText().toString();
        String repeatedPassword = editTextRepeatedPassword.getText().toString();
        return makeGeneralValidate(editTextLogin, login,
                Constants.MIN_LENGTH_LOGIN, getString(R.string.error_short_login)) &&
                validateLoginAvailability(editTextLogin, login) &&
                makeGeneralValidate(editTextPassword, password,
                        Constants.MIN_LENGTH_PASSWORD,
                        getString(R.string.error_short_password)) &&
                makeGeneralValidate(editTextRepeatedPassword, repeatedPassword,
                        Constants.MIN_LENGTH_PASSWORD,
                        getString(R.string.error_short_password)) &&
                validatePasswordsMatch(editTextRepeatedPassword, password, repeatedPassword);
    }

    private boolean makeGeneralValidate(EditText editText, String string,
                                        int minLength, String errorMessage) {
        Log.d(LOG_TAG, "makeGeneralValidate()");
        return validateEmpty(editText, string) &&
                validateLength(editText, string, minLength, errorMessage) &&
                validateMatchingCharacters(editText, string);
    }

    private boolean validateLoginAvailability(EditText editText, String login) {
        Log.d(LOG_TAG, "validateLoginAvailability()");
        if (!CookIslandsSQLiteManager.isUserLoginAvailable(getApplicationContext(), login)) {
            editText.setError(getString(R.string.error_login_is_busy));
            return false;
        }
        return true;
    }

    private boolean validatePasswordsMatch(EditText editText, String password,
                                           String repeatedPassword) {
        Log.d(LOG_TAG, "validatePasswordsMatch()");
        if (!Objects.equals(password, repeatedPassword)) {
            editText.setError(getString(R.string.error_passwords_do_not_match));
            return false;
        }
        return true;
    }

    private boolean validateMatchingCharacters(EditText editText, String string) {
        Log.d(LOG_TAG, "validateMatchingCharacters()");
        if (!Pattern.compile(Constants.LOGIN_AND_PASSWORD_PATTERN)
                .matcher(string).matches()) {
            editText.setError(getString(R.string.error_invalid_character_in_login_or_password));
            return false;
        }
        return true;
    }

    private boolean validateLength(EditText editText, String string,
                                   int minLength, String errorMessage) {
        Log.d(LOG_TAG, "validateLength()");
        if (string.length() < minLength ||
                string.length() > Constants.MAX_LENGTH_LOGIN_OR_PASSWORD) {
            editText.setError(errorMessage);
            return false;
        }
        return true;
    }

    private boolean validateEmpty(EditText editText, String string) {
        Log.d(LOG_TAG, "validateEmpty()");
        if (TextUtils.isEmpty(string)) {
            editText.setError(getString(R.string.error_empty_field));
            return false;
        }
        return true;
    }

    private ArrayAdapter<String> createSpinnerAdapter() {
        Log.d(LOG_TAG, "createSpinnerAdapter()");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, getIslandsName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private String[] getIslandsName() {
        Log.d(LOG_TAG, "getIslandsName()");
        ArrayList<IslandModel> arrayListOfIslands = CookIslandsSQLiteManager
                .getIslands(getApplicationContext());
        String[] islandsNames = new String[arrayListOfIslands.size()];
        for (int i = 0; i < islandsNames.length; i++) {
            islandsNames[i] = arrayListOfIslands.get(i).getName();
        }
        return islandsNames;
    }

    private void showDialogFragment(String dialogMessage, String fragmentTag) {
        Log.d(LOG_TAG, "showDialogFragment()");
        InfoDialogFragment infoDialogFragment = InfoDialogFragment
                .newInstance(dialogMessage);
        infoDialogFragment.show(getSupportFragmentManager(), fragmentTag);
    }

    private void runContentActivity() {
        Log.d(LOG_TAG, "runContentActivity()");
        Intent intentToStartAlarmActivity = new Intent(getApplicationContext(),
                ContentActivity.class);
        // Set this action as the beginning of a new task in this history stack.
        intentToStartAlarmActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentToStartAlarmActivity);
    }
}