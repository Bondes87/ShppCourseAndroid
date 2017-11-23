package com.dbondarenko.shpp.personalnotes.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.fragments.NotesListFragment;

/**
 * File: OnEndlessRecyclerScrollListener.java
 * Created by Dmitro Bondarenko on 22.11.2017.
 */
public abstract class OnEndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {

    private static final String LOG_TAG = NotesListFragment.class.getSimpleName();

    private int totalItemCountAfterLastLoad;
    private boolean isWaitForLoading = true;

    public abstract void onLoadMore();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.d(LOG_TAG, "onScrolled()");

        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();

        if (isWaitForLoading && totalItemCount > totalItemCountAfterLastLoad) {
            isWaitForLoading = false;
            totalItemCountAfterLastLoad = totalItemCount;

        }
        if (!isWaitForLoading &&
                (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + Constants.VISIBLE_THRESHOLD) &&
                totalItemCountAfterLastLoad
                        > Constants.MAXIMUM_COUNT_OF_NOTES_TO_LOAD - 1) {
            onLoadMore();
            isWaitForLoading = true;
        }
    }
}
