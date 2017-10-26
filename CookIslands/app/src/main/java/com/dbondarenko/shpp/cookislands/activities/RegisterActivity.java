package com.dbondarenko.shpp.cookislands.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dbondarenko.shpp.cookislands.database.CookIslandsSQLiteManager;
import com.dbondarenko.shpp.cookislands.models.IslandModel;
import com.dbondarenko.shpp.cookislands.R;
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

    @OnClick(R.id.buttonRegister)
    public void onViewClicked() {
        Log.d(LOG_TAG, "onViewClicked()");
        UserModel newUser = new UserModel(editTextLogin.getText().toString(),
                editTextPassword.getText().toString(),
                spinnerIslandsNames.getSelectedItemPosition());
        Log.d(LOG_TAG, editTextLogin.getText().toString() + "\n" +
                editTextPassword.getText().toString() + "\n" +
                spinnerIslandsNames.getSelectedItemPosition());
        if(CookIslandsSQLiteManager.addUser(getApplicationContext(), newUser)){
            runContentActivity();
        }
    }

    public String[] getIslandsName() {
        String[] islandsNames = new String[arrayListOfIslands.size()];
        for (int i = 0; i < islandsNames.length; i++) {
            islandsNames[i] = arrayListOfIslands.get(i).getName();
        }
        return islandsNames;
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