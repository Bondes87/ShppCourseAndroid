package com.dbondarenko.shpp.filereader;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * File: FileAsyncLoader.java
 * Created by Dmitro Bondarenko on 24.08.2017.
 */
public class FileAsyncLoader extends AsyncTaskLoader<String> {
    private String fileName;

    public FileAsyncLoader(Context context, String fileName) {
        super(context);
        this.fileName = fileName;
        onContentChanged();
    }

    @Override
    public void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        AssetManager assetManager = getContext().getAssets();
        String result = null;
        if (!TextUtils.isEmpty(fileName)) {
            //InputStream inputStream = null;
            try ( InputStream inputStream=assetManager.open(fileName)){
                //inputStream = ;
                result = convertStreamToString(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }/* finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }
        return result;
    }

    @Override
    public void onStopLoading() {
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
