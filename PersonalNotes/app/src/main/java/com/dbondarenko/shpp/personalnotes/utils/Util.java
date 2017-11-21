package com.dbondarenko.shpp.personalnotes.utils;

import com.dbondarenko.shpp.personalnotes.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * File: Util.java
 * Created by Dmitro Bondarenko on 21.11.2017.
 */
public class Util {

    public static String getStringDatetime(long datetime) {
        DateFormat dateFormat = new SimpleDateFormat(
                Constants.PATTERN_DATETIME, Locale.US);
        return dateFormat.format(new Date(datetime));
    }

    public static String getStringDate(long datetime) {
        DateFormat dateFormat = new SimpleDateFormat(
                Constants.PATTERN_DATE, Locale.US);
        return dateFormat.format(new Date(datetime));
    }

    public static String getStringTime(long datetime) {
        DateFormat dateFormat = new SimpleDateFormat(
                Constants.PATTERN_TIME, Locale.US);
        return dateFormat.format(new Date(datetime));
    }
}