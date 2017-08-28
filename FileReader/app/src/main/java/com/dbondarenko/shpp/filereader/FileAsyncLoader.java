package com.dbondarenko.shpp.filereader;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * File: FileAsyncLoader.java
 * The class in which the content is downloaded from the file.
 * The file is located in the Assets folder.
 * Created by Dmitro Bondarenko on 24.08.2017.
 */
class FileAsyncLoader extends AsyncTaskLoader<String> {

    private static final String LOG_TAG = "file_async_loader";

    private String fileName;

    FileAsyncLoader(Context context, String fileName) {
        super(context);
        this.fileName = fileName;
        onContentChanged();
    }

    @Override
    public void onStartLoading() {
        Log.d(LOG_TAG, "onStartLoading");
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        Log.d(LOG_TAG, "loadInBackground");
        AssetManager assetManager = getContext().getAssets();
        String result = null;
        if (!TextUtils.isEmpty(fileName)) {
            try (InputStream inputStream = assetManager.open(fileName)) {
                result = convertStreamToString(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onStopLoading() {
        Log.d(LOG_TAG, "onStopLoading");
        cancelLoad();
    }

    private static String convertStreamToString(InputStream inputStream) throws IOException {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int charactersNumber;
            while ((charactersNumber = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, charactersNumber);
            }
        } finally {
            inputStream.close();
        }
        return writer.toString();
    }
}
