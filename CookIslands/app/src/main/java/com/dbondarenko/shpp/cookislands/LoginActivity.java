package com.dbondarenko.shpp.cookislands;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The login screen that offers login via login / password.
 */
public class LoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.editTextLogin, R.id.editTextPassword, R.id.buttonLogIn, R.id.buttonRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editTextLogin:
                break;
            case R.id.editTextPassword:
                break;
            case R.id.buttonLogIn:
                break;
            case R.id.buttonRegister:
                break;
        }
    }
}