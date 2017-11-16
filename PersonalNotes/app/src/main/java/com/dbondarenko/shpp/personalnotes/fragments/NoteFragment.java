package com.dbondarenko.shpp.personalnotes.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dbondarenko.shpp.personalnotes.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteFragment extends Fragment {

    private static final String LOG_TAG = NoteFragment.class.getSimpleName();

    @BindView(R.id.textViewDatetime)
    TextView textViewDatetime;
    @BindView(R.id.editTextMessage)
    EditText editTextMessage;

    private long messageDatetime;

    public NoteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageDatetime = Calendar.getInstance().getTimeInMillis();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewContent = inflater.inflate(R.layout.fragment_note, container,
                false);
        ButterKnife.bind(this, viewContent);
        textViewDatetime.setText(getTime(messageDatetime));
        return viewContent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.itemSaveNote:
                showNotesListFragment();
                return true;
            case R.id.itemDeleteNote:
                showNotesListFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        inflater.inflate(R.menu.fragment_note_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private String getTime(long datetime) {
        Log.d(LOG_TAG, "getTime()");
        DateFormat dateFormat = new SimpleDateFormat(
                "MMMM d, yyyy h:mm a", Locale.US);
        return dateFormat.format(new Date(datetime));
    }

    /**
     * Show registration screen.
     */
    private void showNotesListFragment() {
        Log.d(LOG_TAG, "showRegisterFragment()");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frameLayoutContainerForContent, new NotesListFragment())
                .commit();
    }

}