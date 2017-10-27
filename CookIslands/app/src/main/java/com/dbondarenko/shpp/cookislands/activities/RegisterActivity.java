package com.dbondarenko.shpp.cookislands.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

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

    ArrayList<IslandModel> arrayListOfIslands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        arrayListOfIslands = CookIslandsSQLiteManager.getIslands(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, getIslandsName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIslandsNames.setAdapter(adapter);
    }

    @OnClick({R.id.buttonRegister, R.id.imageViewLoginInfo, R.id.imageViewPasswordInfo})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonRegister:
                UserModel newUser = new UserModel(editTextLogin.getText().toString(),
                        editTextPassword.getText().toString(),
                        spinnerIslandsNames.getSelectedItemPosition());
                Log.d(LOG_TAG, editTextLogin.getText().toString() + "\n" +
                        editTextPassword.getText().toString() + "\n" +
                        spinnerIslandsNames.getSelectedItemPosition());
                if (CookIslandsSQLiteManager.addUser(getApplicationContext(), newUser)) {
                    runContentActivity();
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

    private String[] getIslandsName() {
        String[] islandsNames = new String[arrayListOfIslands.size()];
        for (int i = 0; i < islandsNames.length; i++) {
            islandsNames[i] = arrayListOfIslands.get(i).getName();
        }
        return islandsNames;
    }

    private void showDialogFragment(String dialogMessage, String fragmentTag) {
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