package com.dbondarenko.shpp.personalnotes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * File: LoginFragment.java
 * The fragment that displays a login screen.
 * Created by Dmitro Bondarenko on 08.11.2017.
 */
public class LoginFragment extends Fragment {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.editTextLogin)
    EditText editTextLogin;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.buttonLogIn)
    Button buttonLogIn;
    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.progressBarLoginUser)
    ProgressBar progressBarLoginUser;

    DatabaseManager databaseManager;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View viewContent = inflater.inflate(R.layout.fragment_login, container,
                false);
        ButterKnife.bind(this, viewContent);
        initActionBar();
        return viewContent;
    }

    @OnClick({R.id.buttonLogIn, R.id.buttonRegister})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonLogIn:
                initDatabase();
                if (databaseManager instanceof FirebaseManager &&
                        !Util.isInternetConnectionAvailable(
                                getContext().getApplicationContext())) {
                    Util.reportAnError(getContext().getApplicationContext(),
                            getView(), getString(R.string.error_no_internet_connection));
                    return;
                }
                if (validateCredentials()) {
                    databaseManager.checkIsUserExists(
                            editTextLogin.getText().toString(),
                            editTextPassword.getText().toString());
                }
                break;
            case R.id.buttonRegister:
                showRegisterFragment();
                break;
        }
    }

    private void initActionBar() {
        ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
        Util.enableBackStackButton(actionBar, false);
        Util.setTitleForActionBar(actionBar, getString(R.string.app_name));
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
                progressBarLoginUser.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess() {
                Log.d(LOG_TAG, "onSuccess()");
                progressBarLoginUser.setVisibility(View.GONE);
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
                progressBarLoginUser.setVisibility(View.GONE);
                Util.hideSoftKeyboard(getContext().getApplicationContext(), getView());
                Util.reportAnError(getContext().getApplicationContext(),
                        getView(), getString(R.string.error_invalid_login_or_password));
                //reportAnError();
            }
        };
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
        Log.d(LOG_TAG, "reportAnError()");
        Snackbar snackbar = Snackbar.make(buttonLogIn,
                getString(R.string.error_invalid_login_or_password),
                Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
        snackbar.show();
    }

    private void showRegisterFragment() {
        Log.d(LOG_TAG, "showRegisterFragment()");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutContainer, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }
}