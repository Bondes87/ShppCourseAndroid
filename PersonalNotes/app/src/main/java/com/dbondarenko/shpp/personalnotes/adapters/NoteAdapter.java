package com.dbondarenko.shpp.personalnotes.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.listeners.OnListItemClickListener;
import com.dbondarenko.shpp.personalnotes.models.NoteModel;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File: NoteAdapter.java
 * Created by Dmitro Bondarenko on 16.11.2017.
 */
public class NoteAdapter extends
        RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private static final String LOG_TAG = NoteAdapter.class.getSimpleName();

    private OnListItemClickListener onListItemClickListener;
    private List<NoteModel> notesList;

    public NoteAdapter(List<NoteModel> notesList,
                       OnListItemClickListener onListItemClickListener) {
        this.notesList = notesList;
        this.onListItemClickListener = onListItemClickListener;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder()");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_item, parent, false);
        return new NoteHolder(itemView, onListItemClickListener);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        Log.d(LOG_TAG, "onBindViewHolder()");
        NoteModel note = notesList.get(position);
        holder.textViewNoteMessage.setText(note.getMessage());
        holder.textViewNoteDate.setText(Util.getStringDate(note.getDatetime()));
        holder.textViewNoteTime.setText(Util.getStringTime(note.getDatetime()));
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "getItemCount()");
        return notesList.size();
    }

    public void addNotes(List<NoteModel> notes) {
        Log.d(LOG_TAG, "addNotes()");
        notesList.addAll(notes);
    }

    public void addNote(NoteModel note) {
        Log.d(LOG_TAG, "addNote()");
        notesList.add(0, note);
    }

    public NoteModel getNote(int position) {
        Log.d(LOG_TAG, "getNote()");
        return notesList.get(position);
    }

    static class NoteHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private static final String LOG_TAG = NoteHolder.class.getSimpleName();

        @BindView(R.id.textViewNoteMessage)
        TextView textViewNoteMessage;
        @BindView(R.id.textViewNoteDate)
        TextView textViewNoteDate;
        @BindView(R.id.textViewNoteTime)
        TextView textViewNoteTime;
        private OnListItemClickListener onListItemClickListener;

        NoteHolder(View itemView, OnListItemClickListener onListItemClickListener) {
            super(itemView);
            Log.d(LOG_TAG, "NoteHolder()");
            this.onListItemClickListener = onListItemClickListener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "onClick()");
            if (onListItemClickListener != null) {
                onListItemClickListener.onClickListItem(getAdapterPosition());
            }
        }
    }
}