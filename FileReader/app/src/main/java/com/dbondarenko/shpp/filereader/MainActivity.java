package com.dbondarenko.shpp.filereader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

/**
 * File: MainActivity.java
 * The program displays the contents of the file. The file is located in the Assets folder.
 * The class that displays the contents of the file on the screen.
 * Created by Dmitro Bondarenko on 24.08.2017.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    public static final int LOADER_FILE_ID = 1;
    private static final String LOG_TAG = "file_reader";

    private TextView tvFileContents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        initFileAsyncLoader();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        return new FileAsyncLoader(getApplicationContext(), getString(R.string.file_name));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d(LOG_TAG, "onFinishLoader");
        if (!TextUtils.isEmpty(data)) {
            tvFileContents.setText(data);
        } else {
            Log.e(LOG_TAG, "File" + getString(R.string.file_name) + " is empty or missing.");
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.d(LOG_TAG, "onLoaderReset");
    }

    private void initFileAsyncLoader() {
        getSupportLoaderManager().initLoader(LOADER_FILE_ID, null, this);
    }

    private void findViews() {
        setContentView(R.layout.activity_main);
        tvFileContents = (TextView) findViewById(R.id.textViewFileContents);
    }
}