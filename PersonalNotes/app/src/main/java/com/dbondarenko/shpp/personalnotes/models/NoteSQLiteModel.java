package com.dbondarenko.shpp.personalnotes.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.dbondarenko.shpp.personalnotes.Constants;

/**
 * File: NoteSQLiteModel.java
 * Created by Dmitro Bondarenko on 17.11.2017.
 */

@Entity(tableName = Constants.TABLE_NOTES,
        foreignKeys = @ForeignKey(
                entity = UserSQLiteModel.class,
                parentColumns = Constants.COLUMN_USER_LOGIN,
                childColumns = Constants.COLUMN_NOTES_USERLOGIN),
        indices = @Index(Constants.COLUMN_NOTES_USERLOGIN))

public class NoteSQLiteModel implements Note, Parcelable {
    public static final Creator<NoteSQLiteModel> CREATOR =
            new Creator<NoteSQLiteModel>() {
                @Override
                public NoteSQLiteModel createFromParcel(Parcel source) {
                    return new NoteSQLiteModel(source);
                }

                @Override
                public NoteSQLiteModel[] newArray(int size) {
                    return new NoteSQLiteModel[size];
                }
            };

    @PrimaryKey
    private long datetime;
    private String userLogin;
    private String message;

    public NoteSQLiteModel(String userLogin, long datetime, String message) {
        this.datetime = datetime;
        this.userLogin = userLogin;
        this.message = message;
    }

    protected NoteSQLiteModel(Parcel in) {
        this.datetime = in.readLong();
        this.userLogin = in.readString();
        this.message = in.readString();
    }

    @Override
    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    @Override
    public String getUserLogin() {
        return userLogin;
    }

    @Override
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.datetime);
        dest.writeString(this.userLogin);
        dest.writeString(this.message);
    }
}
