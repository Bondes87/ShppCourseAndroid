package com.dbondarenko.shpp.personalnotes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.activities.MainActivity;
import com.dbondarenko.shpp.personalnotes.utils.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesListFragment extends Fragment {

    private static final String LOG_TAG = NotesListFragment.class.getSimpleName();

    @BindView(R.id.floatingActionButtonAddNote)
    FloatingActionButton floatingActionButtonAddNote;
    @BindView(R.id.recyclerViewNotesList)
    RecyclerView recyclerViewNotesList;

    public NotesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewContent = inflater.inflate(R.layout.fragment_notes_list, container,
                false);
        ButterKnife.bind(this, viewContent);
        recyclerViewNotesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return viewContent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        if (item.getItemId() == R.id.itemLogOut) {
            SharedPreferencesManager.getSharedPreferencesManager()
                    .deleteInformationAboutUser(getContext().getApplicationContext());
            runMainActivity();
            getActivity().finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        inflater.inflate(R.menu.fragment_list_notes_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @OnClick(R.id.floatingActionButtonAddNote)
    public void onViewClicked() {
        Log.d(LOG_TAG, "onViewClicked()");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutContainerForContent, new NoteFragment())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Run the MainActivity.
     */
    private void runMainActivity() {
        Log.d(LOG_TAG, "runMainActivity()");
        Intent intentToStartNewActivity = new Intent(
                getContext().getApplicationContext(), MainActivity.class);
        intentToStartNewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentToStartNewActivity);
    }
}