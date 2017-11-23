package com.dbondarenko.shpp.personalnotes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.activities.MainActivity;
import com.dbondarenko.shpp.personalnotes.adapters.NoteAdapter;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.database.firebase.FirebaseManager;
import com.dbondarenko.shpp.personalnotes.database.sqlitebase.SQLiteManager;
import com.dbondarenko.shpp.personalnotes.listeners.OnEndlessRecyclerScrollListener;
import com.dbondarenko.shpp.personalnotes.listeners.OnGetDataListener;
import com.dbondarenko.shpp.personalnotes.listeners.OnListItemClickListener;
import com.dbondarenko.shpp.personalnotes.models.Note;
import com.dbondarenko.shpp.personalnotes.utils.SharedPreferencesManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesListFragment extends Fragment implements OnListItemClickListener {

    private static final String LOG_TAG = NotesListFragment.class.getSimpleName();

    @BindView(R.id.floatingActionButtonAddNote)
    FloatingActionButton floatingActionButtonAddNote;
    @BindView(R.id.recyclerViewNotesList)
    RecyclerView recyclerViewNotesList;

    NoteAdapter noteAdapter;
    DatabaseManager databaseManager;

    public NotesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View viewContent = inflater.inflate(R.layout.fragment_notes_list, container,
                false);
        ButterKnife.bind(this, viewContent);
        initDatabase();
        if (noteAdapter == null) {
            downloadNotes(0);
        }
        initRecyclerView();
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

    @Override
    public void onClickListItem(int position) {
        Log.d(LOG_TAG, "onClickListItem()");
        NoteFragment noteFragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_NOTE,
                (Parcelable) noteAdapter.getNote(position));
        noteFragment.setArguments(bundle);
        showNoteFragment(noteFragment);
    }

    @OnClick(R.id.floatingActionButtonAddNote)
    public void onViewClicked() {
        Log.d(LOG_TAG, "onViewClicked()");
        showNoteFragment(new NoteFragment());
    }

    public void addNoteToAdapter(Note note) {
        Log.d(LOG_TAG, "addNoteToAdapter()");
        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(null,
                    NotesListFragment.this);
        }
        noteAdapter.addNote(note);
        noteAdapter.notifyDataSetChanged();
    }

    public void deleteNoteFromAdapter(Note note) {
        Log.d(LOG_TAG, "deleteNoteFromAdapter()");
        noteAdapter.deleteNote(note);
        noteAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNotesList.setLayoutManager(linearLayoutManager);
        recyclerViewNotesList.setAdapter(noteAdapter);
        recyclerViewNotesList.addOnScrollListener(
                new OnEndlessRecyclerScrollListener() {
                    @Override
                    public void onLoadMore() {
                        Log.d(LOG_TAG, "onLoadMore()");
                        downloadNotes(noteAdapter.getItemCount());
                    }
                });
    }

    private void downloadNotes(int startNotesPosition) {
        Note note = null;
        if (startNotesPosition != 0) {
            note = noteAdapter.getNote(startNotesPosition - 1);
        }
        databaseManager.requestNotes(
                SharedPreferencesManager
                        .getSharedPreferencesManager()
                        .getUser(getContext().getApplicationContext())
                        .getLogin(),
                startNotesPosition,
                note);
    }

    private void showNoteFragment(NoteFragment noteFragment) {
        Log.d(LOG_TAG, "showNoteFragment()");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutContainerForContent, noteFragment)
                .addToBackStack(null)
                .commit();
    }

    private void initDatabase() {
        Log.d(LOG_TAG, "initDatabase()");
        if (SharedPreferencesManager.getSharedPreferencesManager().isUseFirebase(
                getContext().getApplicationContext())) {
            databaseManager = new FirebaseManager(getDataListener());
        } else {
            databaseManager = new SQLiteManager(
                    getContext().getApplicationContext(), getDataListener()
            );
        }
    }

    @NonNull
    private OnGetDataListener getDataListener() {
        Log.d(LOG_TAG, "getDataListener()");
        return new OnGetDataListener() {
            @Override
            public void onSuccess() {
                Log.d(LOG_TAG, "onSuccess()");
            }

            @Override
            public void onSuccess(List<Note> notes) {
                Log.d(LOG_TAG, "onSuccess()");
                if (noteAdapter == null) {
                    noteAdapter = new NoteAdapter(notes,
                            NotesListFragment.this);
                    if (recyclerViewNotesList != null) {
                        recyclerViewNotesList.setAdapter(noteAdapter);
                    }
                } else {
                    noteAdapter.addNotes(notes);
                    noteAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed() {
                Log.d(LOG_TAG, "onFailed()");
            }
        };
    }

    private void runMainActivity() {
        Log.d(LOG_TAG, "runMainActivity()");
        Intent intentToStartNewActivity = new Intent(
                getContext().getApplicationContext(), MainActivity.class);
        intentToStartNewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentToStartNewActivity);
    }
}