package com.guinproductions.behavioralpatterns.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guinproductions.behavioralpatterns.R;
import com.guinproductions.behavioralpatterns.model.Note;
import com.guinproductions.behavioralpatterns.recyclerview.NoteListRV;

public class NoteView extends AppCompatActivity {

    public static final String EXTRA_NOTE = "NOTE";


    public static Intent newInstance(Context context, Note note) {
        Intent intent = new Intent(context, NoteListRV.class);
        if (note != null) {
            intent.putExtra(EXTRA_NOTE, note);
        }

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
