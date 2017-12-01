package com.dbondarenko.shpp.personalnotes.listeners;

import android.support.v7.widget.RecyclerView;

/**
 * File: RecyclerItemTouchHelperListener.java
 * Created by Dmitro Bondarenko on 01.12.2017.
 */
public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}