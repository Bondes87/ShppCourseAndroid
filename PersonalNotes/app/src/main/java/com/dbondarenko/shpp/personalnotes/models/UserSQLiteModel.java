package com.dbondarenko.shpp.personalnotes.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.dbondarenko.shpp.personalnotes.Constants;

/**
 * File: UserSQLiteModel.java
 * Created by Dmitro Bondarenko on 14.11.2017.
 */
@Entity(tableName = Constants.TABLE_USERS)
public class UserSQLiteModel implements User, Parcelable {

    public static final Parcelable.Creator<UserSQLiteModel> CREATOR =
            new Parcelable.Creator<UserSQLiteModel>() {
                @Override
                public UserSQLiteModel createFromParcel(Parcel source) {
                    return new UserSQLiteModel(source);
                }

                @Override
                public UserSQLiteModel[] newArray(int size) {
                    return new UserSQLiteModel[size];
                }
            };

    @PrimaryKey
    @NonNull
    private String login;
    private String password;

    public UserSQLiteModel(@NonNull String login, String password) {
        this.login = login;
        this.password = password;
    }

    protected UserSQLiteModel(Parcel in) {
        this.login = in.readString();
        this.password = in.readString();
    }

    @Override
    @NonNull
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
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
