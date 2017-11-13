package com.dbondarenko.shpp.personalnotes.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * File: NoteModel.java
 * Created by Dmitro Bondarenko on 09.11.2017.
 */
@Entity(tableName = "notes",
        foreignKeys = @ForeignKey(
                entity = UserModel.class,
                parentColumns = "login",
                childColumns = "userLogin"))

public class NoteModel implements Parcelable {

    @PrimaryKey
    private long datetime;
    private String userLogin;
    private String message;

    public NoteModel(String userLogin, Long datetime, String message) {
        this.userLogin = userLogin;
        this.datetime = datetime;
        this.message = message;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

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

    protected NoteModel(Parcel in) {
        this.datetime = in.readLong();
        this.userLogin = in.readString();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<NoteModel> CREATOR = new Parcelable.Creator<NoteModel>() {
        @Override
        public NoteModel createFromParcel(Parcel source) {
            return new NoteModel(source);
        }

        @Override
        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };
}