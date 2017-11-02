package com.dbondarenko.shpp.cookislands.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dbondarenko.shpp.cookislands.Constants;
import com.dbondarenko.shpp.cookislands.R;
import com.dbondarenko.shpp.cookislands.activities.ContentActivity;
import com.dbondarenko.shpp.cookislands.database.CookIslandsSQLiteManager;
import com.dbondarenko.shpp.cookislands.models.IslandModel;
import com.dbondarenko.shpp.cookislands.models.UserModel;
import com.dbondarenko.shpp.cookislands.utils.SharedPreferencesManager;
import com.dbondarenko.shpp.cookislands.utils.Util;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

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
    @BindView(R.id.spinnerIslandsNames)
    Spinner spinnerIslandsNames;
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
        spinnerIslandsNames.setAdapter(createSpinnerAdapter());
        return viewContent;
    }

    @OnClick({R.id.buttonRegister, R.id.imageViewLoginInfo, R.id.imageViewPasswordInfo})
    public void onViewClicked(View view) {
        Log.d(LOG_TAG, "onViewClicked()");
        switch (view.getId()) {
            case R.id.buttonRegister:
                if (validateLoginAndPassword()) {
                    int userIslandId = spinnerIslandsNames.getSelectedItemPosition() + 1;
                    UserModel newUser = new UserModel(editTextLogin.getText().toString(),
                            editTextPassword.getText().toString(), userIslandId);
                    CookIslandsSQLiteManager.addUser(getContext(), newUser);
                    SharedPreferencesManager.getSharedPreferencesManager()
                            .saveInformationAboutLogin(getContext(), userIslandId);
                    startActivity(ContentActivity.newInstance(getContext()));
                    getActivity().finish();
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

    /**
     * Validate the login and password.
     *
     * @return true if the validation was successful, otherwise false.
     */
    private boolean validateLoginAndPassword() {
        Log.d(LOG_TAG, "validateLoginAndPassword()");
        return makeGeneralValidate(editTextLogin, Constants.MIN_LENGTH_LOGIN,
                getString(R.string.error_short_login)) &&
                validateLoginAvailability() &&
                makeGeneralValidate(editTextPassword, Constants.MIN_LENGTH_PASSWORD,
                        getString(R.string.error_short_password)) &&
                makeGeneralValidate(editTextRepeatedPassword, Constants.MIN_LENGTH_PASSWORD,
                        getString(R.string.error_short_password)) &&
                validatePasswordsMatch();
    }

    /**
     * Make general validate the editText.
     *
     * @param editText The editText for validate.
     * @param minLength The minimum length of a string to be obtained from editText.
     * @param errorMessage The error message if the string has not passed
     *                     the minimum length validation.
     * @return true if the validation was successful, otherwise false.
     */
    private boolean makeGeneralValidate(EditText editText, int minLength, String errorMessage) {
        Log.d(LOG_TAG, "makeGeneralValidate()");
        Util.checkForNull(editText);
        Util.checkStringToNull(errorMessage);
        return validateEmpty(editText) &&
                validateLength(editText, minLength, errorMessage) &&
                validateMatchingCharacters(editText);
    }

    /**
     * Check login for availability. Show error if the verification was not successful.
     *
     * @return true if the login for availability, otherwise false.
     */
    private boolean validateLoginAvailability() {
        Log.d(LOG_TAG, "validateLoginAvailability()");
        if (!CookIslandsSQLiteManager.isUserLoginAvailable(getContext(),
                editTextLogin.getText().toString())) {
            editTextLogin.setError(getString(R.string.error_login_is_busy));
            return false;
        }
        return true;
    }

    /**
     * Check match passwords. Show error if the verification was not successful.
     *
     * @return true if the passwords match, otherwise false.
     */
    private boolean validatePasswordsMatch() {
        Log.d(LOG_TAG, "validatePasswordsMatch()");
        if (!Objects.equals(editTextPassword.getText().toString(),
                editTextRepeatedPassword.getText().toString())) {
            editTextRepeatedPassword.setError(getString(R.string.error_passwords_do_not_match));
            return false;
        }
        return true;
    }

    /**
     * Check string from editText for matching characters.
     * Show error if the verification was not successful.
     *
     * @param editText The editText for validate.
     * @return true if the validation was successful, otherwise false.
     */
    private boolean validateMatchingCharacters(EditText editText) {
        Log.d(LOG_TAG, "validateMatchingCharacters()");
        Util.checkForNull(editText);
        if (!Pattern.compile(Constants.LOGIN_AND_PASSWORD_PATTERN)
                .matcher(editText.getText().toString()).matches()) {
            editText.setError(getString(R.string.error_invalid_character_in_login_or_password));
            return false;
        }
        return true;
    }

    /**
     * Check string for length. Show error if the verification was not successful.
     *
     * @param editText The editText for validate.
     * @param minLength The minimum length of a string to be obtained from editText.
     * @param errorMessage The error message if the string has not passed
     *                     the minimum length validation.
     * @return true if the validation was successful, otherwise false.
     */
    private boolean validateLength(EditText editText, int minLength, String errorMessage) {
        Log.d(LOG_TAG, "validateLength()");
        Util.checkForNull(editText);
        Util.checkStringToNull(errorMessage);
        int textLength = editText.getText().length();
        if (textLength < minLength || textLength > Constants.MAX_LENGTH_LOGIN_OR_PASSWORD) {
            editText.setError(errorMessage);
            return false;
        }
        return true;
    }

    /**
     * Check string for emptiness.
     *
     * @param editText The editTexts for validate.
     * @return true if the validation was successful, otherwise false.
     */
    private boolean validateEmpty(EditText editText) {
        Log.d(LOG_TAG, "validateEmpty()");
        Util.checkForNull(editText);
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(getString(R.string.error_empty_field));
            return false;
        }
        return true;
    }

    /**
     * Create an adapter for the spinner.
     *
     * @return the adapter for the spinner.
     */
    private ArrayAdapter<String> createSpinnerAdapter() {
        Log.d(LOG_TAG, "createSpinnerAdapter()");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, getIslandsName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    /**
     * Get the array of islands names.
     *
     * @return the array of islands names.
     */
    private String[] getIslandsName() {
        Log.d(LOG_TAG, "getIslandsName()");
        ArrayList<IslandModel> arrayListOfIslands = CookIslandsSQLiteManager
                .getIslands(getContext());
        String[] islandsNames = new String[arrayListOfIslands.size()];
        for (int i = 0; i < islandsNames.length; i++) {
            islandsNames[i] = arrayListOfIslands.get(i).getName();
        }
        return islandsNames;
    }

    /**
     * Show dialog with prompt.
     *
     * @param  dialogMessage The message for the dialog box.
     * @param  fragmentTag   The teg of fragment.
     */
    private void showDialogFragment(String dialogMessage, String fragmentTag) {
        Log.d(LOG_TAG, "showDialogFragment()");
        Util.checkStringToNull(dialogMessage, fragmentTag);
        InfoDialogFragment infoDialogFragment = InfoDialogFragment
                .newInstance(dialogMessage);
        infoDialogFragment.show(getActivity().getSupportFragmentManager(), fragmentTag);
    }
}