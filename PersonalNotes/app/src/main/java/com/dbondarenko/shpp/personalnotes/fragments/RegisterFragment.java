package com.dbondarenko.shpp.personalnotes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.activities.ContentActivity;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.database.firebase.FirebaseManager;
import com.dbondarenko.shpp.personalnotes.database.sqlitebase.SQLiteManager;
import com.dbondarenko.shpp.personalnotes.listeners.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.models.Note;
import com.dbondarenko.shpp.personalnotes.utils.SharedPreferencesManager;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * File: RegisterFragment.java
 * The fragment that displays a register screen.
 * Created by Dmitro Bondarenko on 08.11.2017.
 */
public class RegisterFragment extends Fragment {

    private static final String LOG_TAG = RegisterFragment.class.getSimpleName();

    @BindView(R.id.editTextLogin)
    EditText editTextLogin;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editTextConfirmedPassword)
    EditText editTextConfirmedPassword;
    @BindView(R.id.imageViewLoginInfo)
    ImageView imageViewLoginInfo;
    @BindView(R.id.imageViewPasswordInfo)
    ImageView imageViewPasswordInfo;
    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.progressBarRegisterUser)
    ProgressBar progressBarRegisterUser;

    DatabaseManager databaseManager;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View viewContent = inflater.inflate(
                R.layout.fragment_register, container, false);
        ButterKnife.bind(this, viewContent);
        initDatabase();
        return viewContent;
    }

    @OnClick({R.id.buttonRegister, R.id.imageViewLoginInfo,
            R.id.imageViewPasswordInfo})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonRegister:
                if (validateCredentials()) {
                    databaseManager.addUser(editTextLogin.getText().toString(),
                            editTextPassword.getText().toString());
                    Util.hideSoftKeyboard(getContext().getApplicationContext(), getView());
                }
                break;
            case R.id.imageViewLoginInfo:
                showDialogFragment(getString(R.string.login_information),
                        Constants.TAG_OF_INFO_DIALOG_FRAGMENT_FOR_LOGIN);
                break;
            case R.id.imageViewPasswordInfo:
                showDialogFragment(getString(R.string.password_information),
                        Constants.TAG_OF_INFO_DIALOG_FRAGMENT_FOR_PASSWORD);
                break;
        }
    }

    private void initDatabase() {
        Log.d(LOG_TAG, "initDatabase()");
        if (SharedPreferencesManager.getSharedPreferencesManager().isUseFirebase(
                getContext().getApplicationContext())) {
            databaseManager = new FirebaseManager(getDataListener());
        } else {
            databaseManager = new SQLiteManager(
                    getContext().getApplicationContext(), getDataListener()
            );
        }
    }

    @NonNull
    private OnGetDataListener getDataListener() {
        Log.d(LOG_TAG, "getDataListener()");
        return new OnGetDataListener() {
            @Override
            public void onStart() {
                progressBarRegisterUser.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess() {
                Log.d(LOG_TAG, "onSuccess()");
                progressBarRegisterUser.setVisibility(View.GONE);
                SharedPreferencesManager
                        .getSharedPreferencesManager()
                        .saveInformationAboutUser(
                                getContext().getApplicationContext(),
                                editTextLogin.getText().toString(),
                                editTextPassword.getText().toString());
                startActivity(ContentActivity.newInstance(getContext()));
                getActivity().finish();
            }

            @Override
            public void onSuccess(List<Note> notes) {
                Log.d(LOG_TAG, "onSuccess()");
            }

            @Override
            public void onFailed() {
                Log.d(LOG_TAG, "onFailed()");
                progressBarRegisterUser.setVisibility(View.GONE);
                editTextLogin.setError(getString(R.string.error_login_is_busy));
                editTextLogin.requestFocus();
            }
        };
    }

    private boolean validateCredentials() {
        Log.d(LOG_TAG, "validateCredentials()");
        return validateLogin() &&
                validatePassword() &&
                validateConfirmedPassword();
    }

    private boolean validateLogin() {
        Log.d(LOG_TAG, "validateLogin()");
        String login = editTextLogin.getText().toString();
        if (validateEmpty(login)) {
            editTextLogin.setError(getString(R.string.error_empty_field));
            editTextLogin.requestFocus();
            return false;
        }
        if (validateMatchingCharacters(login)) {
            editTextLogin.setError(getString(
                    R.string.error_invalid_character_in_login_or_password));
            editTextLogin.requestFocus();
            return false;
        }
        if (validateLength(login, Constants.MIN_LENGTH_LOGIN)) {
            editTextLogin.setError(getString(R.string.error_short_login));
            editTextLogin.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        Log.d(LOG_TAG, "validatePassword()");
        String password = editTextPassword.getText().toString();
        if (validateEmpty(password)) {
            editTextPassword.setError(getString(R.string.error_empty_field));
            editTextPassword.requestFocus();
            return false;
        }
        if (validateMatchingCharacters(password)) {
            editTextPassword.setError(getString(
                    R.string.error_invalid_character_in_login_or_password));
            editTextPassword.requestFocus();
            return false;
        }
        if (validateLength(password, Constants.MIN_LENGTH_PASSWORD)) {
            editTextPassword.setError(getString(R.string.error_short_password));
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateConfirmedPassword() {
        Log.d(LOG_TAG, "validateConfirmedPassword()");
        String confirmedPassword = editTextConfirmedPassword.getText().toString();
        if (validateEmpty(confirmedPassword)) {
            editTextConfirmedPassword.setError(getString(R.string.error_empty_field));
            editTextConfirmedPassword.requestFocus();
            return false;
        }
        if (validateMatchingCharacters(confirmedPassword)) {
            editTextConfirmedPassword.setError(getString(
                    R.string.error_invalid_character_in_login_or_password));
            editTextConfirmedPassword.requestFocus();
            return false;
        }
        if (validateLength(confirmedPassword, Constants.MIN_LENGTH_PASSWORD)) {
            editTextConfirmedPassword.setError(getString(R.string.error_short_password));
            editTextConfirmedPassword.requestFocus();
            return false;
        }
        if (validatePasswordsMatch()) {
            editTextConfirmedPassword.setError(getString(R.string.error_passwords_do_not_match));
            editTextConfirmedPassword.requestFocus();
            return false;
        }
        return true;
    }


    private boolean validateEmpty(String text) {
        Log.d(LOG_TAG, "validateEmpty()");
        return TextUtils.isEmpty(text);
    }

    private boolean validateMatchingCharacters(String text) {
        Log.d(LOG_TAG, "validateMatchingCharacters()");
        return !Pattern.compile(Constants.PATTERN_LOGIN_AND_PASSWORD)
                .matcher(text).matches();
    }

    private boolean validateLength(String text, int minLength) {
        Log.d(LOG_TAG, "validateLength()");
        int textLength = text.length();
        return textLength < minLength || textLength > Constants.MAX_LENGTH_LOGIN_OR_PASSWORD;
    }

    private boolean validatePasswordsMatch() {
        Log.d(LOG_TAG, "validatePasswordsMatch()");
        return !Objects.equals(editTextPassword.getText().toString(),
                editTextConfirmedPassword.getText().toString());
    }

    private void showDialogFragment(String dialogMessage, String fragmentTag) {
        Log.d(LOG_TAG, "showDialogFragment()");
        InfoDialogFragment infoDialogFragment = InfoDialogFragment
                .newInstance(dialogMessage);
        infoDialogFragment.show(getActivity().getSupportFragmentManager(), fragmentTag);
    }
}