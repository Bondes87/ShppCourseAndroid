package com.dbondarenko.shpp.personalnotes.database.sqlitebase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.listeners.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.models.Note;
import com.dbondarenko.shpp.personalnotes.models.NoteSQLiteModel;
import com.dbondarenko.shpp.personalnotes.models.UserSQLiteModel;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import java.util.ArrayList;

/**
 * File: SQLiteManager.java
 * Created by Dmitro Bondarenko on 11.11.2017.
 */
public class SQLiteManager implements DatabaseManager {

    private static final String LOG_TAG = SQLiteManager.class.getSimpleName();

    static private OnGetDataListener onGetDataListener;
    private SQLiteRoomDatabase sQLiteRoomDatabase;
    private Handler handler;

    public SQLiteManager(Context context, OnGetDataListener listener) {
        Util.checkForNull(context, listener);
        sQLiteRoomDatabase = Room.databaseBuilder(context,
                SQLiteRoomDatabase.class, Constants.DATABASE_NAME).build();
        onGetDataListener = listener;
        handler = new DataHandler();
    }

    @Override
    public void addUser(String login, String password) {
        Log.d(LOG_TAG, "addUser()");
        Util.checkForNull(login, password);
        onGetDataListener.onStart();
        runJobInNewThread(() -> {
            Message message = handler.obtainMessage(Constants.ID_OF_BOOLEAN_RESULT);
            Bundle bundle = new Bundle();
            UserSQLiteModel user = new UserSQLiteModel(login, password);
            if (sQLiteRoomDatabase.getUserDao().isLoginAvailable(login) == null) {
                sQLiteRoomDatabase.getUserDao().insertUser(user);
                bundle.putBoolean(Constants.KEY_FOR_BOOLEAN_RESULT, true);
            } else {
                bundle.putBoolean(Constants.KEY_FOR_BOOLEAN_RESULT, false);
            }
            message.setData(bundle);
            handler.sendMessage(message);
        });
    }

    @Override
    public void checkIsUserExists(String login, String password) {
        Log.d(LOG_TAG, "checkIsUserExists()");
        Util.checkForNull(login, password);
        onGetDataListener.onStart();
        runJobInNewThread(() -> {
            Message message = handler.obtainMessage(Constants.ID_OF_BOOLEAN_RESULT);
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.KEY_FOR_BOOLEAN_RESULT,
                    sQLiteRoomDatabase.getUserDao().getUser(login, password) != null);
            message.setData(bundle);
            handler.sendMessage(message);
        });
    }

    @Override
    public void addNote(Note note) {
        Log.d(LOG_TAG, "addNote()");
        Util.checkForNull(note);
        runJobInNewThread(() -> {
            Message message = handler.obtainMessage(Constants.ID_OF_BOOLEAN_RESULT);
            Bundle bundle = new Bundle();
            sQLiteRoomDatabase.getNoteDao().insertNote((NoteSQLiteModel) note);
            bundle.putBoolean(Constants.KEY_FOR_BOOLEAN_RESULT, true);
            message.setData(bundle);
            handler.sendMessage(message);
        });
    }

    @Override
    public void updateNote(Note note) {
        Log.d(LOG_TAG, "updateNote()");
        Util.checkForNull(note);
        runJobInNewThread(() -> {
            Message message = handler.obtainMessage(Constants.ID_OF_BOOLEAN_RESULT);
            Bundle bundle = new Bundle();
            sQLiteRoomDatabase.getNoteDao().updateNote((NoteSQLiteModel) note);
            bundle.putBoolean(Constants.KEY_FOR_BOOLEAN_RESULT, true);
            message.setData(bundle);
            handler.sendMessage(message);
        });
    }

    @Override
    public void deleteNote(Note note) {
        Log.d(LOG_TAG, "deleteNote()");
        Util.checkForNull(note);
        runJobInNewThread(() -> {
            Message message = handler.obtainMessage(Constants.ID_OF_BOOLEAN_RESULT);
            Bundle bundle = new Bundle();
            sQLiteRoomDatabase.getNoteDao().deleteNote((NoteSQLiteModel) note);
            bundle.putBoolean(Constants.KEY_FOR_BOOLEAN_RESULT, true);
            message.setData(bundle);
            handler.sendMessage(message);
        });
    }

    @Override
    public void requestNotes(String userLogin, int startNotesPosition,
                             Note lastNoteFromTheLastDownload) {
        Log.d(LOG_TAG, "requestNotes()");
        Util.checkForNull(userLogin);
        if (startNotesPosition != 0 &&
                startNotesPosition % Constants.MAXIMUM_COUNT_OF_NOTES_TO_LOAD == 0) {
            onGetDataListener.onStart();
        }
        runJobInNewThread(() -> {
            Message message = handler.obtainMessage(Constants.ID_OF_RESULT_WITH_LIST);
            Bundle bundle = new Bundle();
            ArrayList<NoteSQLiteModel> notes =
                    (ArrayList<NoteSQLiteModel>) sQLiteRoomDatabase.getNoteDao()
                            .getNotes(userLogin, startNotesPosition);
            bundle.putParcelableArrayList(Constants.KEY_FOR_RESULT_WITH_LIST, notes);
            message.setData(bundle);
            handler.sendMessage(message);
        });
    }

    private void runJobInNewThread(Runnable runnable) {
        Log.d(LOG_TAG, "runJobInNewThread()");
        Util.checkForNull(runnable);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static class DataHandler extends Handler {

        private static final String LOG_TAG = DataHandler.class.getSimpleName();

        @Override
        public void handleMessage(Message message) {
            Log.d(LOG_TAG, "handleMessage()");
            Util.checkForNull(message);
            Bundle bundle = message.getData();
            switch (message.what) {
                case Constants.ID_OF_BOOLEAN_RESULT:
                    boolean data = bundle.getBoolean(
                            Constants.KEY_FOR_BOOLEAN_RESULT);
                    if (data) {
                        onGetDataListener.onSuccess();
                    } else {
                        onGetDataListener.onFailed();
                    }
                    break;
                case Constants.ID_OF_RESULT_WITH_LIST:
                    ArrayList<NoteSQLiteModel> notesArrayList = bundle
                            .getParcelableArrayList(Constants.KEY_FOR_RESULT_WITH_LIST);
                    if (notesArrayList != null && notesArrayList.size() != 0) {
                        onGetDataListener.onSuccess(new ArrayList<>(notesArrayList));
                    } else {
                        onGetDataListener.onFailed();
                    }
                    break;
            }
        }
    }
}
