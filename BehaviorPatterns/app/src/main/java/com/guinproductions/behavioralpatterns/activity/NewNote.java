package com.guinproductions.behavioralpatterns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guinproductions.behavioralpatterns.R;
import com.guinproductions.behavioralpatterns.model.Note;
import com.guinproductions.behavioralpatterns.model.Profile;
import com.guinproductions.behavioralpatterns.recyclerview.NoteListRV;

import java.util.HashMap;
import java.util.Map;

import static com.guinproductions.behavioralpatterns.R.id.edit_note;
import static com.guinproductions.behavioralpatterns.R.id.view_date;


public class NewNote extends AppCompatActivity {


    public static final String EXTRA_NOTE = "NOTE";
    public static final String NOTE_ID = "com.guinproductions.behavioralpatterns.noteid";
    public static final String MAIN_CONTENT = "com.guinproductions.behavioralpatterns.maincontent";
    public static final String DATE_CREATED = "com.guinproductions.behavioralpatterns.datecreated";
    public static final String TIME_STAMP = "com.guinproductions.behavioralpatterns.timestamp";


    private static final String TAG = "NewNoteActivity";
    private static final String REQUIRED = "Required";
    private FloatingActionButton submitButton;

    NoteListRV noteAdapter;

    EditText mainContent;
    TextView dateStamp;
    TextView timeStamp;
    Profile profile;
    Note note;

    String profileId;

    DatabaseReference dbNote;
    DatabaseReference mDB;
    DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

   String profileID = intent.getStringExtra(ProfileViewActivity.PROFILE_ID2);


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("profiles");


        mDatabase = FirebaseDatabase.getInstance().getReference("notes");
        mDB = FirebaseDatabase.getInstance().getReference("profiles").child("notes");
        dbNote = FirebaseDatabase.getInstance().getReference("notes").child(profileID);

//        noteAdapter = new NoteListRV(Collections.<Note>emptyList());

        mainContent = (EditText) findViewById(edit_note);
        dateStamp = (TextView) findViewById(view_date);
        timeStamp = (TextView) findViewById(R.id.view_time);
        submitButton = (FloatingActionButton) findViewById(R.id.fab_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

//        View noteDate = findViewById(R.id.view_date);
//        View noteTime = findViewById(R.id.view_time);
//
//        TextView note_date = (TextView) noteDate.findViewById(R.id.view_date);
//        TextView note_time = (TextView) noteTime.findViewById(R.id.view_time);
//
//        note_date.setText(intent.getStringExtra(NoteListRV.DATE_CREATED));
//        note_time.setText(intent.getStringExtra(NoteListRV.TIME_STAMP));


//        FloatingActionButton submitButton = (FloatingActionButton) findViewById(R.id.fab_submit);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                saveNote();
//
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }

    private void saveNote(){

//        final String noteContent = note.setMainContent(mainContent.getText().toString());
//        final String noteDate = note.setDateCreated(dateStamp.getText().toString());
//        final String noteTime =note.setTimeStamp(timeStamp.getText().toString()) ;



        final String noteContent = mainContent.getText().toString();
        final String noteDate = dateStamp.getText().toString();
        final String noteTime = timeStamp.getText().toString();


//        note.setNoteId(mDatabase.child("profiles").getKey());
//        note.setMainContent(mainContent.getText().toString());
//        note.setDateCreated(dateStamp.getText().toString());
//        note.setTimeStamp(timeStamp.getText().toString());

        if(TextUtils.isEmpty(noteContent)){
            mainContent.setError(REQUIRED);
            return;
        }

        setEditingEnabled(false);
        Toast.makeText(this, "Submitting...", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();

        final DatabaseReference profileId = FirebaseDatabase.getInstance().getReference("profiles").child(intent.getStringExtra(ProfileViewActivity.PROFILE_ID2));

        mDatabase.child("profiles").child(String.valueOf(profileId)).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Note note = dataSnapshot.getValue(Note.class);

                        if(note == null){

                            Log.e(TAG, "Profile " + profileId + "is unexpectly null");
                            Toast.makeText(NewNote.this,
                                    "Error: could not fetch profile.",
                                    Toast.LENGTH_SHORT).show();
                        }else {

                            writeNewNote(noteContent, noteDate, noteTime );

                        }

                        setEditingEnabled(true);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Log.w(TAG, "getProfile:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]

                    }
                }
        );



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("profiles/profileId");
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                List<Note> notes = new ArrayList<>();
//
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//
//                    Note note = postSnapshot.getValue(Note.class);
//
//                    notes.add(note);
//
//                }
//
//                noteAdapter.updateList(notes);
//
//
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//
//        });
//
//    }

    private void setEditingEnabled(boolean enabled) {
        mainContent.setEnabled(enabled);

        if (enabled) {
            submitButton.setVisibility(View.VISIBLE);
        } else {
            submitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewNote(String mainContent, String dateCreated, String timeStamp) {

        //        note.setNoteId(mDB.child("profiles").push().getKey());
//        note.setMainContent(mainContent.getText().toString());
//        note.setDateCreated(dateStamp.getText().toString());
//        note.setTimeStamp(timeStamp.getText().toString());
//        mDB.child(note.getNoteId()).setValue(note);
//
//        finish();
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String noteId = mDatabase.child("notes").push().getKey();
        Note note = new Note(noteId, mainContent, dateCreated, timeStamp);
        Map<String, Object> noteValues = note.noteMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/notes/" + noteId, noteValues);
        childUpdates.put("/profiles/" + noteId + "/" + noteId, noteValues);

        mDatabase.updateChildren(childUpdates);
    }
}
