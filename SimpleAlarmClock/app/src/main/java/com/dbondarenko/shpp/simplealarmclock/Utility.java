package com.dbondarenko.shpp.simplealarmclock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * File: Utility.java
 * Created by Dmitro Bondarenko on 06.09.2017.
 */
class Utility {
    static String getTime(long datetime){
        DateFormat dateFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        return dateFormat.format(new Date(datetime));
    }
}
