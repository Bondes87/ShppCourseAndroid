package com.dbondarenko.shpp.cookislands;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.dbondarenko.shpp.cookislands.CookIslandsSQLiteManager.COLUMN_ISLAND_ID;
import static com.dbondarenko.shpp.cookislands.CookIslandsSQLiteManager.COLUMN_ISLAND_NAME;
import static com.dbondarenko.shpp.cookislands.CookIslandsSQLiteManager.COLUMN_USER_ID;
import static com.dbondarenko.shpp.cookislands.CookIslandsSQLiteManager.COLUMN_USER_ISLAND_ID;
import static com.dbondarenko.shpp.cookislands.CookIslandsSQLiteManager.COLUMN_USER_LOGIN;
import static com.dbondarenko.shpp.cookislands.CookIslandsSQLiteManager.COLUMN_USER_PASSWORD;
import static com.dbondarenko.shpp.cookislands.CookIslandsSQLiteManager.TABLE_ISLANDS;
import static com.dbondarenko.shpp.cookislands.CookIslandsSQLiteManager.TABLE_USERS;

/**
 * File: CookIslandsSQLiteOpenHelper.java
 * Created by Dmitro Bondarenko on 24.10.2017.
 */
class CookIslandsSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cookislands.db";
    // Database Info
    private static final int DATABASE_VERSION = 1;
    private static final String LOG_TAG = "login_activity";
    private static final String KEYS_TABLE_ISLANDS_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_ISLANDS + " (" +
            COLUMN_ISLAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ISLAND_NAME + " TEXT NOT NULL, " + ");";
    private static final String KEYS_TABLE_USERS_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_LOGIN + " TEXT NOT NULL, " +
            COLUMN_USER_PASSWORD + " TEXT NOT NULL, " +
            COLUMN_USER_ISLAND_ID + " INTEGER NOT NULL, " + ");";

    private static final String FILE_NAME = "CookIslands.txt";
    private static final String UTF_8_ENCODING = "UTF-8";

    private Context context;

    CookIslandsSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "onCreate()");
        db.execSQL(KEYS_TABLE_ISLANDS_CREATE);
        db.execSQL(KEYS_TABLE_USERS_CREATE);
        fillIslandsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "onUpgrade()");
    }

    private void fillIslandsTable() {
        Log.d(LOG_TAG, "fillIslandsTable()");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            ArrayList<String> arrayListOfIslands = getIslandsNames();
            for (String islandName : arrayListOfIslands) {
                values.put(COLUMN_ISLAND_NAME, islandName);
            }
            db.insertOrThrow(TABLE_ISLANDS, null, values);
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
                        .open(FILE_NAME), UTF_8_ENCODING))) {
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
