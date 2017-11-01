package com.dbondarenko.shpp.cookislands.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

import com.dbondarenko.shpp.cookislands.R;
import com.dbondarenko.shpp.cookislands.activities.ContentActivity;
import com.dbondarenko.shpp.cookislands.database.CookIslandsSQLiteManager;
import com.dbondarenko.shpp.cookislands.models.UserModel;
import com.dbondarenko.shpp.cookislands.utils.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View viewContent = inflater.inflate(R.layout.fragment_login, container,
                false);
        ButterKnife.bind(this, viewContent);
        if (SharedPreferencesManager.getSharedPreferencesManager().
                getInformationAboutLogin(getContext())) {
            startActivity(ContentActivity.newInstance(getContext()));
            getActivity().finish();
        }
        return viewContent;
    }

    @OnClick({R.id.buttonLogIn, R.id.buttonRegister})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonLogIn:
                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();
                if (validateEmpty(editTextLogin, editTextPassword)) {
                    hideSoftKeyboard(getContext(), buttonLogIn);
                    UserModel user = CookIslandsSQLiteManager.getUser(
                            getContext(), login, password);
                    if (user != null) {
                        SharedPreferencesManager.getSharedPreferencesManager()
                                .saveInformationAboutLogin(getContext(), user.getIslandId());
                        startActivity(ContentActivity.newInstance(getContext()));
                        getActivity().finish();
                    } else {
                        reportIncorrectLoginOrPassword(view);
                    }
                }
                break;
            case R.id.buttonRegister:
                showRegisterFragment();
                break;
        }
    }

    public void hideSoftKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void reportIncorrectLoginOrPassword(View view) {
        Log.d(LOG_TAG, "reportIncorrectLoginOrPassword()");
        Snackbar snackbar = Snackbar.make(view,
                getString(R.string.error_invalid_login_or_password),
                Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
        snackbar.show();
    }

    private boolean validateEmpty(EditText... editTexts) {
        Log.d(LOG_TAG, "validateEmpty()");
        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError(getString(R.string.error_empty_field));
                return false;
            }
        }
        return true;
    }

    private void showRegisterFragment() {
        Log.d(LOG_TAG, "showRegisterFragment()");
        Fragment registerFragment = new RegisterFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutContainer, registerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}