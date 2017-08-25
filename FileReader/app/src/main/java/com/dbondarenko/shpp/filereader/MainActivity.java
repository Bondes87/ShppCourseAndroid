package com.dbondarenko.shpp.filereader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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
    private TextView textViewFileContents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewFileContents = (TextView) findViewById(R.id.textViewFileContents);
        textViewFileContents.setMovementMethod(new ScrollingMovementMethod());
        getSupportLoaderManager().initLoader(LOADER_FILE_ID, null, this);
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        return new FileAsyncLoader(this, getString(R.string.file_name));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d(LOG_TAG, "onFinishLoader");
        textViewFileContents.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.d(LOG_TAG, "onLoaderReset");
    }
}