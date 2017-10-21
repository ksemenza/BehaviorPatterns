package com.guinproductions.behavioralpatterns.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guinproductions.behavioralpatterns.R;
import com.guinproductions.behavioralpatterns.model.Note;
import com.guinproductions.behavioralpatterns.model.Profile;

import java.util.List;

import static com.guinproductions.behavioralpatterns.recyclerview.ProfileListRV.PROFILE_FNAME;
import static com.guinproductions.behavioralpatterns.recyclerview.ProfileListRV.PROFILE_ID;
import static com.guinproductions.behavioralpatterns.recyclerview.ProfileListRV.PROFILE_LNAME;
import static com.guinproductions.behavioralpatterns.recyclerview.ProfileListRV.PROFILE_THUMBNAIL;


public class ProfileViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Intent newInstance(Context context, Profile profile){
        Intent intent = new Intent(context, ProfileViewActivity.class);
        if (profile !=null){
            intent.putExtra(PROFILE_ID, profile);
            intent.putExtra(PROFILE_FNAME,  profile);
            intent.putExtra(PROFILE_LNAME, profile);
            intent.putExtra(PROFILE_THUMBNAIL,  profile);
        }

        return intent;
    }

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton fabBehavior, fabTreatment, fabAntecedent;

    public static String PROFILE_ID2 = "com.guinproductions.behavioralpatterns.activity.profileid";

    DatabaseReference dbProfile, dbNote;
    private Profile profile;
    List<Note> notes;
    private Context mContext;
private NavigationView navigationView;
    public static String newNoteClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Intent intent = getIntent();

        dbProfile = FirebaseDatabase.getInstance().getReference("profiles");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        TextView nav_fname = (TextView)hView.findViewById(R.id.view_fname);
        TextView nav_lname = (TextView)hView.findViewById(R.id.view_lname);

        nav_fname.setText(intent.getStringExtra(PROFILE_FNAME));
        nav_lname.setText(intent.getStringExtra(PROFILE_LNAME));


        if (profile != null){

            nav_fname.setText(profile.getFName());
            nav_lname.setText(profile.getLName());
        }

        navigationView = (NavigationView) findViewById(R.id.nav_new_note);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        fabBehavior = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_behavior);
        fabTreatment = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_treatment);
        fabAntecedent = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_antecedent);

        fabBehavior.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProfileViewActivity.this, NewBehaviors.class);

                intent.putExtra(PROFILE_ID2, profile.getProfileId());

                startActivity(intent);
            }
        });

        fabTreatment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProfileViewActivity.this, NewBehaviors.class);

                startActivity(intent);
            }
        });

        fabAntecedent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked

            }
        });


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_new_note) {


            Intent intent = new Intent(getApplicationContext(), NewNote.class);


intent.putExtra(PROFILE_ID2, ProfileViewActivity.PROFILE_ID2);

//            intent.putExtra(PROFILE_FNAME, profile.getFName());
//            intent.putExtra(PROFILE_LNAME, profile.getLName());
//            intent.putExtra(DATE_CREATED, profile.getDateCreated());
//            intent.putExtra(TIME_STAMP, profile.getTimeStamp());


        getApplicationContext().startActivity(intent);

            return true;


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
