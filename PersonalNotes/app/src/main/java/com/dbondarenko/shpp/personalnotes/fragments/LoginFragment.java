package com.dbondarenko.shpp.personalnotes.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.activities.ContentActivity;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.database.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.database.firebase.FirebaseManager;
import com.dbondarenko.shpp.personalnotes.database.sqlitebase.SQLiteManager;
import com.dbondarenko.shpp.personalnotes.models.UserModel;
import com.dbondarenko.shpp.personalnotes.utils.SharedPreferencesManager;

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
        initDatabase();
        return viewContent;
    }

    @OnClick({R.id.buttonLogIn, R.id.buttonRegister})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonLogIn:
                if (validateCredentials()) {
                    UserModel user = new UserModel(
                            editTextLogin.getText().toString(),
                            editTextPassword.getText().toString());
                    databaseManager.isUserExists(user);
                }
                break;
            case R.id.buttonRegister:
                showRegisterFragment();
                break;
        }
    }

    private void initDatabase() {
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
        return new OnGetDataListener() {
            @Override
            public void onSuccess() {
                startActivity(ContentActivity.newInstance(getContext()));
                getActivity().finish();
            }

            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onFailed() {
                hideSoftKeyboard();
                reportIncorrectLoginOrPassword();
            }

            @Override
            public void onFailed(Object data) {

            }
        };
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

    /**
     * Show registration screen.
     */
    private void showRegisterFragment() {
        Log.d(LOG_TAG, "showRegisterFragment()");
        Fragment registerFragment = new RegisterFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutContainer, registerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}