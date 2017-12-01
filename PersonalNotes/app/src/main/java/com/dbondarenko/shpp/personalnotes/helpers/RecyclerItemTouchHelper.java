package com.dbondarenko.shpp.personalnotes.helpers;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.dbondarenko.shpp.personalnotes.adapters.NoteAdapter;
import com.dbondarenko.shpp.personalnotes.listeners.RecyclerItemTouchHelperListener;

/**
 * File: RecyclerItemTouchHelper.java
 * Created by Dmitro Bondarenko on 01.12.2017.
 */
public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private static final String LOG_TAG = RecyclerItemTouchHelper.class.getSimpleName();

    private RecyclerItemTouchHelperListener recyclerItemTouchListener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs,
                                   RecyclerItemTouchHelperListener recyclerItemTouchListener) {
        super(dragDirs, swipeDirs);
        this.recyclerItemTouchListener = recyclerItemTouchListener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d(LOG_TAG, "onMove()");
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        Log.d(LOG_TAG, "onSelectedChanged()");
        if (viewHolder != null) {
            final View foregroundView =
                    ((NoteAdapter.NoteHolder) viewHolder).cardViewForeground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState,
                                boolean isCurrentlyActive) {
        Log.d(LOG_TAG, "onChildDrawOver()");
        final View foregroundView =
                ((NoteAdapter.NoteHolder) viewHolder).cardViewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView,
                dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.d(LOG_TAG, "clearView()");
        final View foregroundView =
                ((NoteAdapter.NoteHolder) viewHolder).cardViewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState,
                            boolean isCurrentlyActive) {
        Log.d(LOG_TAG, "onChildDraw()");
        final View foregroundView =
                ((NoteAdapter.NoteHolder) viewHolder).cardViewForeground;
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView,
                dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(LOG_TAG, "onSwiped()");
        recyclerItemTouchListener.onSwiped(viewHolder, direction,
                viewHolder.getAdapterPosition());
    }
}