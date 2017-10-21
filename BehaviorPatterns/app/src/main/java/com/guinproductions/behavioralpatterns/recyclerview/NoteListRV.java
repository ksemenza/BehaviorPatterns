package com.guinproductions.behavioralpatterns.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guinproductions.behavioralpatterns.R;
import com.guinproductions.behavioralpatterns.activity.NoteView;
import com.guinproductions.behavioralpatterns.model.Note;

import java.util.List;

/**
 * Created by guinp on 10/15/2017.
 */

public class NoteListRV extends RecyclerView.Adapter <NoteListRV.ViewHolder> {

    public static final String EXTRA_NOTE = "NOTE";
    public static final String NOTE_ID = "com.guinproductions.behavioralpatterns.noteid";
    public static final String MAIN_CONTENT = "com.guinproductions.behavioralpatterns.maincontent";
    public static final String DATE_CREATED = "com.guinproductions.behavioralpatterns.datecreated";
    public static final String TIME_STAMP = "com.guinproductions.behavioralpatterns.timestamp";

    private List<Note> notes;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private Note note;
        private Context mContext;
        TextView viewDate;
        TextView viewTime;

        public ViewHolder(View itemView){
            super(itemView);

            mContext = itemView.getContext();
            viewDate = (TextView) itemView.findViewById(R.id.view_date);
            viewTime = (TextView) itemView.findViewById(R.id.view_time);

            itemView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void bind (Note note){
            this.note = note;
            viewDate.setText(note.getDateCreated());
            viewTime.setText(note.getTimeStamp());
        }

        @Override
        public void onClick (View view){

            final Intent i;
            switch (getAdapterPosition()) {
                default:

                    i = new Intent(mContext, NoteView.class);
                    break;
            }

            i.putExtra(NOTE_ID, note.getNoteId());
            i.putExtra(MAIN_CONTENT, note.getMainContent());
            i.putExtra(DATE_CREATED, note.getDateCreated());
            i.putExtra(TIME_STAMP, note.getTimeStamp());

            mContext.startActivity(i);
        }

        @Override
        public boolean onLongClick(View view) {

            Context context = view.getContext();
            context.startActivity(NoteView.newInstance(context, note));

            return true;

        }

    }

    public NoteListRV(List<Note> notes){
        this.notes = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_note, parent, false);

        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(notes.get(position));

    }

    @Override
    public int getItemCount() {
       return  notes.size();
    }

    public void updateList(List<Note> notes){

        if(notes.size() != this.notes.size() || !this.notes.containsAll(notes)){
            this.notes = notes;
            notifyDataSetChanged();
        }
    }

    public void removedItem(int position){
        notes.remove(position);
        notifyItemRemoved(position);
    }

    public Note getItem(int position){
        return  notes.get(position);
    }

}
