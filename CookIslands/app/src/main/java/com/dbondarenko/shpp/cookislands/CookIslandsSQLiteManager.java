package com.dbondarenko.shpp.cookislands;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * File: CookIslandsSQLiteManager.java
 * Created by Dmitro Bondarenko on 24.10.2017.
 */
class CookIslandsSQLiteManager {

    private static final String LOG_TAG = "sqlite_manager";

    // Table Names
    static final String TABLE_ISLANDS = "islands";
    static final String TABLE_USERS = "users";
    // Island Table Columns
    static final String COLUMN_ISLAND_ID = "id";
    static final String COLUMN_ISLAND_NAME = "islandName";
    // Users Table Columns
    static final String COLUMN_USER_ID = "id";
    static final String COLUMN_USER_LOGIN = "userLogin";
    static final String COLUMN_USER_PASSWORD = "userPassword";
    static final String COLUMN_USER_ISLAND_ID = "islandId";

    public static void addUser(Context context, User user) {
        Log.d(LOG_TAG, "addUser()");
        CookIslandsSQLiteOpenHelper cookIslandsSQLiteOpenHelper =
                new CookIslandsSQLiteOpenHelper(context);
        SQLiteDatabase db = cookIslandsSQLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_LOGIN, user.getLogin());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_ISLAND_ID, user.getIslandId());
        db.insertOrThrow(TABLE_USERS, null, values);
        cookIslandsSQLiteOpenHelper.close();
    }

    public static ArrayList<Island> getIslands(Context context) {
        Log.d(LOG_TAG, "getIslands()");
        ArrayList<Island> arrayListOfIslands = new ArrayList<>();
        CookIslandsSQLiteOpenHelper cookIslandsSQLiteOpenHelper =
                new CookIslandsSQLiteOpenHelper(context);
        SQLiteDatabase db = cookIslandsSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ISLANDS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Island island = new Island(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ISLAND_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ISLAND_NAME)));
                arrayListOfIslands.add(island);
            } while (cursor.moveToNext());
        }
        Log.d(LOG_TAG, "getIslands(): list: " + arrayListOfIslands);
        cursor.close();
        cookIslandsSQLiteOpenHelper.close();
        return arrayListOfIslands;
    }

    public static boolean isUserExist(Context context, User user) {
        Log.d(LOG_TAG, "isUserExist()");
        boolean isUserExist;
        CookIslandsSQLiteOpenHelper cookIslandsSQLiteOpenHelper =
                new CookIslandsSQLiteOpenHelper(context);
        SQLiteDatabase db = cookIslandsSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ISLANDS,
                new String[]{COLUMN_USER_LOGIN, COLUMN_USER_PASSWORD},
                COLUMN_USER_LOGIN + " = ? AND " + COLUMN_USER_PASSWORD + " = ?",
                new String[]{user.getLogin(), user.getPassword()},
                null, null, null);
        isUserExist = cursor.moveToFirst();
        cursor.close();
        cookIslandsSQLiteOpenHelper.close();
        return isUserExist;
    }


}
