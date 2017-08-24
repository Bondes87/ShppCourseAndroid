package com.dbondarenko.shpp.filereader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<String> {

    public static final int LOADER_FILE_ID = 1;
    private static final String LOG_TAG = "my_tag";
    private TextView textViewFileContents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonReadFile) {
            getSupportLoaderManager().initLoader(LOADER_FILE_ID, null, this);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> loader = new FileAsyncLoader(this, getString(R.string.file_name));
        Log.d(LOG_TAG, "onCreateLoader");
        return loader;
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

    private void initViews() {
        setContentView(R.layout.activity_main);
        Button buttonReadFile = (Button) findViewById(R.id.buttonReadFile);
        buttonReadFile.setOnClickListener(this);
        textViewFileContents = (TextView) findViewById(R.id.textViewFileContents);
    }
}