package com.dbondarenko.shpp.cookislands.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.Constants;
import com.dbondarenko.shpp.cookislands.models.IslandModel;
import com.dbondarenko.shpp.cookislands.models.UserModel;
import com.dbondarenko.shpp.cookislands.utils.Util;

import java.util.ArrayList;

/**
 * File: CookIslandsSQLiteManager.java
 * The class in which information is got from the database
 * and information is saved in the database
 * Created by Dmitro Bondarenko on 24.10.2017.
 */
public class CookIslandsSQLiteManager {

    private static final String LOG_TAG = CookIslandsSQLiteManager.class.getSimpleName();

    /**
     * Add user information to the database.
     *
     * @param context The Context of the application package implementing this class.
     * @param user    The object information about which you want to save in the database.
     */
    public static void addUser(Context context, UserModel user) {
        Log.d(LOG_TAG, "addUser()");
        Util.checkForNull(context,user);
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

    /**
     * Get islands information from the database.
     *
     * @param context The Context of the application package implementing this class.
     * @return the list of islands.
     */
    public static ArrayList<IslandModel> getIslands(Context context) {
        Log.d(LOG_TAG, "getIslands()");
        Util.checkForNull(context);
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
                        cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ISLAND_NAME)),
                        cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ISLAND_URL)));
                arrayListOfIslands.add(island);
            } while (cursor.moveToNext());
        }
        cursor.close();
        cookIslandsSQLiteOpenHelper.close();
        return arrayListOfIslands;
    }

    /**
     * Get user from the database.
     *
     * @param context  The Context of the application package implementing this class.
     * @param login    The user login.
     * @param password The user password.
     * @return the user.
     */
    public static UserModel getUser(Context context, String login, String password) {
        Log.d(LOG_TAG, "getUser()");
        Util.checkForNull(context);
        Util.checkStringToNull(login,password);
        UserModel user = null;
        CookIslandsSQLiteOpenHelper cookIslandsSQLiteOpenHelper =
                new CookIslandsSQLiteOpenHelper(context);
        SQLiteDatabase db = cookIslandsSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_USERS, null,
                Constants.COLUMN_USER_LOGIN + " = ? AND " +
                        Constants.COLUMN_USER_PASSWORD + " = ?",
                new String[]{login, password},
                null, null, null);
        if (cursor.moveToFirst()) {
            user = new UserModel(
                    cursor.getString(cursor.getColumnIndex(Constants.COLUMN_USER_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(Constants.COLUMN_USER_PASSWORD)),
                    cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_USER_ISLAND_ID))
            );
        }
        cursor.close();
        cookIslandsSQLiteOpenHelper.close();
        return user;
    }

    /**
     * Checks the availability of the login..
     *
     * @param context   The Context of the application package implementing this class.
     * @param userLogin The user login.
     * @return if the login is available it will return true, otherwise false.
     */
    public static boolean isUserLoginAvailable(Context context, String userLogin) {
        Log.d(LOG_TAG, "isUserLoginAvailable()");
        Util.checkForNull(context);
        Util.checkStringToNull(userLogin);
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