package com.dbondarenko.shpp.personalnotes.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.loader.UsersManagementAsyncTaskLoader;
import com.dbondarenko.shpp.personalnotes.models.UserModel;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * File: LoginFragment.java
 * The fragment that displays a login screen.
 * Created by Dmitro Bondarenko on 08.11.2017.
 */
public class LoginFragment extends Fragment implements LoaderManager.LoaderCallbacks<Boolean> {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.editTextLogin)
    EditText editTextLogin;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.buttonLogIn)
    Button buttonLogIn;
    @BindView(R.id.buttonRegister)
    Button buttonRegister;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View viewContent = inflater.inflate(R.layout.fragment_login, container,
                false);
        ButterKnife.bind(this, viewContent);
        return viewContent;
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        UserModel user = new UserModel(
                editTextLogin.getText().toString(),
                editTextPassword.getText().toString());
        return new UsersManagementAsyncTaskLoader(
                getContext().getApplicationContext(),
                user, Constants.COMMAND_IS_SER_EXIST);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        Log.d(LOG_TAG, "onFinishLoader");
        if (data == null) {
            return;
        }
        if (data) {
            EventBus.getDefault().post(
                    Constants.COMMAND_FOR_RUN_CONTENT_ACTIVITY);
        } else {
            hideSoftKeyboard();
            reportIncorrectLoginOrPassword();
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {
        Log.d(LOG_TAG, "onLoaderReset");
    }

    @OnClick({R.id.buttonLogIn, R.id.buttonRegister})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonLogIn:
                if (validateCredentials()) {
                    getLoaderManager().restartLoader(
                            Constants.ID_USERS_MANAGEMENT_ASYNC_TASK_LOADER,
                            null, this);
                }
                break;
            case R.id.buttonRegister:
                EventBus.getDefault().post(
                        Constants.COMMAND_FOR_RUN_REGISTER_FRAGMENT);
                break;
        }
    }

    private void hideSoftKeyboard() {
        Log.d(LOG_TAG, "hideSoftKeyboard()");
        InputMethodManager inputMethodManager =
                (InputMethodManager) getContext().getApplicationContext()
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (buttonLogIn != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    buttonLogIn.getWindowToken(), 0);
        }
    }

    private boolean validateCredentials() {
        Log.d(LOG_TAG, "validateCredentials()");
        String login = editTextLogin.getText().toString();
        String password = editTextPassword.getText().toString();
        if (validateEmpty(login)) {
            editTextLogin.setError(getString(R.string.error_empty_field));
            editTextLogin.requestFocus();
            return false;
        }
        if (validateEmpty(password)) {
            editTextPassword.setError(getString(R.string.error_empty_field));
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateEmpty(String text) {
        Log.d(LOG_TAG, "validateEmpty()");
        return TextUtils.isEmpty(text);
    }

    private void reportIncorrectLoginOrPassword() {
        Log.d(LOG_TAG, "reportIncorrectLoginOrPassword()");
        Snackbar snackbar = Snackbar.make(buttonLogIn,
                getString(R.string.error_invalid_login_or_password),
                Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
        snackbar.show();
    }
}