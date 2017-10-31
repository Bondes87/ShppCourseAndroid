package com.dbondarenko.shpp.cookislands.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dbondarenko.shpp.cookislands.Constants;
import com.dbondarenko.shpp.cookislands.R;

public class PageFragment extends Fragment {

    private static final String LOG_TAG = PageFragment.class.getSimpleName();

    private int pageNumber;

    public static PageFragment newInstance(int page) {
        Log.d(LOG_TAG, "newInstance()");
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.KEY_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        pageNumber = getArguments().getInt(Constants.KEY_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView tvPage = view.findViewById(R.id.textViewPageNumber);
        tvPage.setText("" + pageNumber);
        return view;
    }
}