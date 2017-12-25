package com.dbondarenko.shpp.personalnotes.adapters;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.R;
import com.dbondarenko.shpp.personalnotes.listeners.OnEmptyListListener;
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

    private OnListItemClickListener onListItemClickListener;
    private OnEmptyListListener onEmptyListListener;
    private List<Note> notesList;
    private boolean isEnabledFooter;

    public NoteAdapter(OnListItemClickListener onListItemClickListener,
                       OnEmptyListListener onEmptyListListener) {
        Util.checkForNull(onListItemClickListener, onEmptyListListener);
        notesList = new ArrayList<>();
        this.onListItemClickListener = onListItemClickListener;
        this.onEmptyListListener = onEmptyListListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder()");
        View itemView;
        switch (viewType) {
            case Constants.TYPE_ITEM:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.notes_list_item, parent, false);
                return new NoteHolder(itemView, onListItemClickListener);
            case Constants.TYPE_FOOTER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.notes_list_footer, parent, false);
                return new FooterHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        Log.d(LOG_TAG, "onBindViewHolder()");
        if (holder.getItemViewType() == Constants.TYPE_ITEM) {
            Note note = notesList.get(position);
            ((NoteHolder) holder).textViewNoteMessage.setText(note.getMessage());
            ((NoteHolder) holder).textViewNoteDate.setText(
                    Util.getStringDate(note.getDatetime()));
            ((NoteHolder) holder).textViewNoteTime.setText(
                    Util.getStringTime(note.getDatetime()));
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
            return Constants.TYPE_FOOTER;
        }
        return Constants.TYPE_ITEM;
    }

    public void setEnabledFooter(boolean enabled) {
        isEnabledFooter = enabled;
        //Todo: see this if()
        if (enabled) {
            notifyItemInserted(notesList.size());
        } else {
            notifyItemRemoved(notesList.size() - 1);
        }
    }

    public void addNotes(List<Note> notes) {
        Log.d(LOG_TAG, "addNotes()");
        Util.checkForNull(notes);
        notesList.addAll(notes);
        notifyItemRangeInserted(notesList.size(), notes.size());
        checkListForEmptiness();
    }

    public void addNote(Note note) {
        Log.d(LOG_TAG, "addNote()");
        Util.checkForNull(note);
        addNote(note, 0);
    }

    public void addNote(Note note, int position) {
        Log.d(LOG_TAG, "addNote()");
        Util.checkForNull(note);
        notesList.add(position, note);
        notifyItemInserted(position);
        checkListForEmptiness();
    }

    public void deleteNote(int notePosition) {
        Log.d(LOG_TAG, "deleteNote()");
        notesList.remove(notePosition);
        notifyItemRemoved(notePosition);
        checkListForEmptiness();
    }

    public Note getNote(int position) {
        Log.d(LOG_TAG, "getNote()");
        if (isEnabledFooter) {
            return notesList.get(position - 1);
        }
        return notesList.get(position);
    }

    public void checkListForEmptiness() {
        onEmptyListListener.onEmptyList(notesList.size() == 0);
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

    public static class NoteHolder extends BaseHolder {

        private static final String LOG_TAG = NoteHolder.class.getSimpleName();

        @BindView(R.id.constraintLayoutForeground)
        public ConstraintLayout constraintLayoutForeground;
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