package com.dbondarenko.shpp.personalnotes.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * File: UserFirebaseModel.java
 * Created by Dmitro Bondarenko on 14.11.2017.
 */
public class UserFirebaseModel implements User, Parcelable {

    public static final Parcelable.Creator<UserFirebaseModel> CREATOR =
            new Parcelable.Creator<UserFirebaseModel>() {
                @Override
                public UserFirebaseModel createFromParcel(Parcel source) {
                    return new UserFirebaseModel(source);
                }

                @Override
                public UserFirebaseModel[] newArray(int size) {
                    return new UserFirebaseModel[size];
                }
            };

    private String login;
    private String password;

    public UserFirebaseModel() {
    }

    public UserFirebaseModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    protected UserFirebaseModel(Parcel in) {
        this.login = in.readString();
        this.password = in.readString();
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.password);
    }
}