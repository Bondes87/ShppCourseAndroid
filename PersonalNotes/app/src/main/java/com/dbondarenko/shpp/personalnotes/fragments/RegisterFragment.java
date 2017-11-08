package com.dbondarenko.shpp.personalnotes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * File: RegisterFragment.java
 * The fragment that displays a register screen.
 * Created by Dmitro Bondarenko on 01.11.2017.
 */
public class RegisterFragment extends Fragment {

    private static final String LOG_TAG = RegisterFragment.class.getSimpleName();

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
    @BindView(R.id.buttonRegister)
    Button buttonRegister;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View viewContent = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, viewContent);
        return viewContent;
    }

    @OnClick({R.id.buttonRegister, R.id.imageViewLoginInfo, R.id.imageViewPasswordInfo})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonRegister:
                EventBus.getDefault().post(
                        Constants.COMMAND_FOR_RUN_CONTENT_ACTIVITY);
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
}