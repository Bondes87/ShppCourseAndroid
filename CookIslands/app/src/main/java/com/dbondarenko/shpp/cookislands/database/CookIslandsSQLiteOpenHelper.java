package com.dbondarenko.shpp.cookislands.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * File: CookIslandsSQLiteOpenHelper.java
 * Created by Dmitro Bondarenko on 24.10.2017.
 */
class CookIslandsSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = CookIslandsSQLiteOpenHelper.class.getSimpleName();

    private Context context;

    CookIslandsSQLiteOpenHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "onCreate()");
        db.execSQL(Constants.KEYS_TABLE_ISLANDS_CREATE);
        db.execSQL(Constants.KEYS_TABLE_USERS_CREATE);
        fillIslandsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "onUpgrade()");
    }

    private void fillIslandsTable(SQLiteDatabase db) {
        Log.d(LOG_TAG, "fillIslandsTable()");
        db.beginTransaction();
        try {
            ArrayList<String> arrayListOfIslands = getIslandsNames();
            for (String islandName : arrayListOfIslands) {
                ContentValues values = new ContentValues();
                values.put(Constants.COLUMN_ISLAND_NAME, islandName);
                db.insertOrThrow(Constants.TABLE_ISLANDS, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while trying to add island to database");
        } finally {
            db.endTransaction();
        }
    }

    private ArrayList<String> getIslandsNames() {
        Log.d(LOG_TAG, "getIslandsNames()");
        ArrayList<String> arrayListOfIslands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getAssets()
                        .open(Constants.FILE_NAME), Constants.UTF_8_ENCODING))) {
            String islandName;
            while ((islandName = reader.readLine()) != null) {
                arrayListOfIslands.add(islandName);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while trying to read names of islands from folder of assets");
        }
        return arrayListOfIslands;
    }
}