package com.guinproductions.behavioralpatterns.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.guinproductions.behavioralpatterns.R;
import com.guinproductions.behavioralpatterns.model.Profile;

import java.util.List;

import static com.guinproductions.behavioralpatterns.activity.NewProfile.PROFILE_FNAME;
import static com.guinproductions.behavioralpatterns.activity.NewProfile.PROFILE_ID;
import static com.guinproductions.behavioralpatterns.activity.NewProfile.PROFILE_LNAME;
import static com.guinproductions.behavioralpatterns.activity.NewProfile.PROFILE_THUMBNAIL;

public class EditProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    List<Profile> profiles;

    public static Intent newInstance(Context context, Profile profile){
        Intent intent = new Intent(context, NewProfile.class);
        if (profile !=null){
            intent.putExtra(PROFILE_ID, profile);
            intent.putExtra(PROFILE_FNAME,  profile);
            intent.putExtra(PROFILE_LNAME, profile);
            intent.putExtra(PROFILE_THUMBNAIL,  profile);
        }

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
