package com.dbondarenko.shpp.personalnotes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;

import org.greenrobot.eventbus.EventBus;

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

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View viewContent = inflater.inflate(
                R.layout.fragment_register, container, false);
        ButterKnife.bind(this, viewContent);
        return viewContent;
    }

    @OnClick({R.id.buttonRegister, R.id.imageViewLoginInfo, R.id.imageViewPasswordInfo})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonRegister:
                if (validateCredentials()) {
                    EventBus.getDefault().post(
                            Constants.COMMAND_FOR_RUN_CONTENT_ACTIVITY);
                }
                break;
            case R.id.imageViewLoginInfo:
                EventBus.getDefault().post(
                        Constants.COMMAND_FOR_RUN_INFO_DIALOG_FRAGMENT_FOR_LOGIN);
                break;
            case R.id.imageViewPasswordInfo:
                EventBus.getDefault().post(
                        Constants.COMMAND_FOR_RUN_INFO_DIALOG_FRAGMENT_FOR_PASSWORD);
                break;
        }
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
        return !Pattern.compile(Constants.LOGIN_AND_PASSWORD_PATTERN)
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
}