package com.dbondarenko.shpp.personalnotes.database;

/**
 * File: OnGetDataListener.java
 * Created by Dmitro Bondarenko on 14.11.2017.
 */
public interface OnGetDataListener {

    void onSuccess();

    void onSuccess(Object data);

    void onFailed();

    void onFailed(Object data);
}
