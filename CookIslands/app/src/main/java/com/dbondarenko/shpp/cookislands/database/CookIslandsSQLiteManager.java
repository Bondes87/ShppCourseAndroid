package com.dbondarenko.shpp.cookislands.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.Constants;
import com.dbondarenko.shpp.cookislands.models.IslandModel;
import com.dbondarenko.shpp.cookislands.models.UserModel;

import java.util.ArrayList;

/**
 * File: CookIslandsSQLiteManager.java
 * Created by Dmitro Bondarenko on 24.10.2017.
 */
public class CookIslandsSQLiteManager {

    private static final String LOG_TAG = CookIslandsSQLiteManager.class.getSimpleName();

    public static void addUser(Context context, UserModel user) {
        Log.d(LOG_TAG, "addUser()");
        CookIslandsSQLiteOpenHelper cookIslandsSQLiteOpenHelper =
                new CookIslandsSQLiteOpenHelper(context);
        SQLiteDatabase db = cookIslandsSQLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_USER_LOGIN, user.getLogin());
        values.put(Constants.COLUMN_USER_PASSWORD, user.getPassword());
        values.put(Constants.COLUMN_USER_ISLAND_ID, user.getIslandId());
        db.insertOrThrow(Constants.TABLE_USERS, null, values);
        cookIslandsSQLiteOpenHelper.close();
    }

    public static ArrayList<IslandModel> getIslands(Context context) {
        Log.d(LOG_TAG, "getIslands()");
        ArrayList<IslandModel> arrayListOfIslands = new ArrayList<>();
        CookIslandsSQLiteOpenHelper cookIslandsSQLiteOpenHelper =
                new CookIslandsSQLiteOpenHelper(context);
        SQLiteDatabase db = cookIslandsSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_ISLANDS, null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                IslandModel island = new IslandModel(
                        cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ISLAND_ID)),
                        cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ISLAND_NAME)));
                arrayListOfIslands.add(island);
            } while (cursor.moveToNext());
        }
        cursor.close();
        cookIslandsSQLiteOpenHelper.close();
        return arrayListOfIslands;
    }

    public static boolean isUserExist(Context context, UserModel user) {
        Log.d(LOG_TAG, "isUserExist()");
        boolean isUserExist;
        CookIslandsSQLiteOpenHelper cookIslandsSQLiteOpenHelper =
                new CookIslandsSQLiteOpenHelper(context);
        SQLiteDatabase db = cookIslandsSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_USERS,
                new String[]{Constants.COLUMN_USER_LOGIN, Constants.COLUMN_USER_PASSWORD},
                Constants.COLUMN_USER_LOGIN + " = ? AND " +
                        Constants.COLUMN_USER_PASSWORD + " = ?",
                new String[]{user.getLogin(), user.getPassword()},
                null, null, null);
        isUserExist = cursor.moveToFirst();
        cursor.close();
        cookIslandsSQLiteOpenHelper.close();
        return isUserExist;
    }

    public static boolean isUserLoginAvailable(Context context, String userLogin) {
        Log.d(LOG_TAG, "isUserLoginAvailable()");
        CookIslandsSQLiteOpenHelper cookIslandsSQLiteOpenHelper =
                new CookIslandsSQLiteOpenHelper(context);
        SQLiteDatabase db = cookIslandsSQLiteOpenHelper.getReadableDatabase();
        boolean isUserLoginAvailable;
        Cursor cursor = db.query(Constants.TABLE_USERS,
                new String[]{Constants.COLUMN_USER_LOGIN},
                Constants.COLUMN_USER_LOGIN + " = ?",
                new String[]{userLogin},
                null, null, null);
        isUserLoginAvailable = !cursor.moveToFirst();
        cursor.close();
        cookIslandsSQLiteOpenHelper.close();
        return isUserLoginAvailable;
    }
}