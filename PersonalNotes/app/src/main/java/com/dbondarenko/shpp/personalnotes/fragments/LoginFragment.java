package com.dbondarenko.shpp.personalnotes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * File: LoginFragment.java
 * The fragment that displays a login screen.
 * Created by Dmitro Bondarenko on 01.11.2017.
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

    @OnClick({R.id.buttonLogIn, R.id.buttonRegister})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonLogIn:
                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();
                EventBus.getDefault().post(
                        Constants.COMMAND_FOR_RUN_CONTENT_ACTIVITY);
                break;
            case R.id.buttonRegister:
                EventBus.getDefault().post(
                        Constants.COMMAND_FOR_RUN_REGISTER_FRAGMENT);
                break;
        }
    }
}