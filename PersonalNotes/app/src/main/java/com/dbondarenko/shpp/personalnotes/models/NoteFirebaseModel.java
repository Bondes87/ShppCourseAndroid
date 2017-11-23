package com.dbondarenko.shpp.personalnotes.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * File: NoteFirebaseModel.java
 * Created by Dmitro Bondarenko on 22.11.2017.
 */
public class NoteFirebaseModel implements Note, Parcelable {

    public static final Parcelable.Creator<NoteFirebaseModel> CREATOR =
            new Parcelable.Creator<NoteFirebaseModel>() {
                @Override
                public NoteFirebaseModel createFromParcel(Parcel source) {
                    return new NoteFirebaseModel(source);
                }

                @Override
                public NoteFirebaseModel[] newArray(int size) {
                    return new NoteFirebaseModel[size];
                }
            };

    private String userLogin;
    private long datetime;
    private String message;

    public NoteFirebaseModel() {
    }

    public NoteFirebaseModel(String userLogin, Long datetime, String message) {
        this.userLogin = userLogin;
        this.datetime = datetime;
        this.message = message;
    }

    protected NoteFirebaseModel(Parcel in) {
        this.userLogin = in.readString();
        this.datetime = in.readLong();
        this.message = in.readString();
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
    public long getDatetime() {
        return datetime;
    }

    @Override
    public void setDatetime(long datetime) {
        this.datetime = datetime;
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
        dest.writeString(this.userLogin);
        dest.writeLong(this.datetime);
        dest.writeString(this.message);
    }
}
