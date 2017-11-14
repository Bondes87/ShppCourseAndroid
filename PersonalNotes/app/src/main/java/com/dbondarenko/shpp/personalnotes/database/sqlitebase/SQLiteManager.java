package com.dbondarenko.shpp.personalnotes.database.sqlitebase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.database.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.models.UserModel;

/**
 * File: SQLiteManager.java
 * Created by Dmitro Bondarenko on 11.11.2017.
 */
public class SQLiteManager implements DatabaseManager {

    static private OnGetDataListener onGetDataListener;
    private SQLiteRoomDatabase sQLiteRoomDatabase;

    public SQLiteManager(Context context, OnGetDataListener listener) {
        sQLiteRoomDatabase = Room.databaseBuilder(context,
                SQLiteRoomDatabase.class, Constants.DATABASE_NAME).build();
        onGetDataListener = listener;
    }

    @Override
    public void addUser(UserModel user) {
        Handler handler = new UserHandler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                if (sQLiteRoomDatabase.getUserDao().isLoginAvailable(
                        user.getLogin()) == null) {
                    sQLiteRoomDatabase.getUserDao().insertUser(user);
                    bundle.putBoolean("Key", true);
                } else {
                    bundle.putBoolean("Key", false);
                }
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
        thread.start();
    }

    @Override
    public void isUserExists(UserModel user) {
        Handler handler = new UserHandler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putBoolean("Key", sQLiteRoomDatabase.getUserDao()
                        .getUser(user.getLogin(), user.getPassword()) != null);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
        thread.start();
    }

    private static class UserHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Bundle bundle = message.getData();
            boolean data = bundle.getBoolean("Key");
            if (data) {
                onGetDataListener.onSuccess();
            } else {
                onGetDataListener.onFailed();
            }
        }
    }
}
