package com.dbondarenko.shpp.personalnotes.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.listeners.OnListItemClickListener;
import com.dbondarenko.shpp.personalnotes.models.Note;
import com.dbondarenko.shpp.personalnotes.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File: NoteAdapter.java
 * Created by Dmitro Bondarenko on 16.11.2017.
 */
public class NoteAdapter extends
        RecyclerView.Adapter<NoteAdapter.BaseHolder> {

    private static final String LOG_TAG = NoteAdapter.class.getSimpleName();

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private OnListItemClickListener onListItemClickListener;
    private List<Note> notesList;
    private boolean isEnabledFooter;

    public NoteAdapter(List<Note> notesList,
                       OnListItemClickListener onListItemClickListener) {
        if (notesList == null) {
            this.notesList = new ArrayList<>();
        } else {
            this.notesList = notesList;
        }
        this.onListItemClickListener = onListItemClickListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder()");
        View itemView;
        switch (viewType) {
            case TYPE_ITEM:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.notes_list_item, parent, false);
                return new NoteHolder(itemView, onListItemClickListener);
            case TYPE_FOOTER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.notes_list_footer, parent, false);
                return new FooterHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        Log.d(LOG_TAG, "onBindViewHolder()");
        if (holder.getItemViewType() == TYPE_ITEM) {
            Note note = notesList.get(position);
            ((NoteHolder) holder).textViewNoteMessage.setText(note.getMessage());
            ((NoteHolder) holder).textViewNoteDate.setText(Util.getStringDate(note.getDatetime()));
            ((NoteHolder) holder).textViewNoteTime.setText(Util.getStringTime(note.getDatetime()));
        }
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "getItemCount()");
        if (isEnabledFooter) {
            return notesList.size() + 1;
        } else {
            return notesList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public void setEnabledFooter(boolean enabled) {
        isEnabledFooter = enabled;
        notifyDataSetChanged();
    }

    public void addNotes(List<Note> notes) {
        Log.d(LOG_TAG, "addNotes()");
        notesList.addAll(notes);
        notifyDataSetChanged();
    }

    public void addNote(Note note) {
        Log.d(LOG_TAG, "addNote()");
        notesList.add(0, note);
        notifyDataSetChanged();
    }

    public void deleteNote(Note note) {
        Log.d(LOG_TAG, "deleteNote()");
        notesList.remove(note);
        notifyDataSetChanged();
    }

    public Note getNote(int position) {
        Log.d(LOG_TAG, "getNote()");
        if (isEnabledFooter){
            return notesList.get(position-1);
        }
        return notesList.get(position);
    }

    private boolean isPositionFooter(int position) {
        return position == notesList.size();
    }

    static class BaseHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        BaseHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }

    static class NoteHolder extends BaseHolder {

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

    static class FooterHolder extends BaseHolder {

        private static final String LOG_TAG = FooterHolder.class.getSimpleName();

        @BindView(R.id.progressBarNotesListFooter)
        ProgressBar progressBarNotesListFooter;

        FooterHolder(View itemView) {
            super(itemView);
            Log.d(LOG_TAG, "FooterHolder()");
            ButterKnife.bind(this, itemView);
        }
    }
}