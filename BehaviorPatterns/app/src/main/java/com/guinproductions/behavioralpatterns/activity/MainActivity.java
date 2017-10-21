package com.guinproductions.behavioralpatterns.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guinproductions.behavioralpatterns.R;
import com.guinproductions.behavioralpatterns.interfaces.DeletionListener;
import com.guinproductions.behavioralpatterns.interfaces.SwipeController;
import com.guinproductions.behavioralpatterns.model.Profile;
import com.guinproductions.behavioralpatterns.recyclerview.ProfileListRV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements DeletionListener {

    public static final String EXTRA_PROFILE = "profiles";

    private DatabaseReference mDatabase, profileDB;
    private ArrayList<Profile> profiles;
    public static String profileSelected;

    private RecyclerView profileList;

    ProfileListRV rvAdapter;

    private TextView viewFname;
    private TextView viewLname;
    private TextView viewDate;
    private ImageView viewThumbnail;
    private Profile profile;
//
////    public static Intent newInstance(Context context, Profile profile) {
////        Intent intent = new Intent(context, NewProfile.class);
////        if (profile != null) {
////            intent.putExtra(EXTRA_PROFILE, profile);
////        }
//
//        return intent;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        profileDB = FirebaseDatabase.getInstance().getReference("profiles");
        profileDB = mDatabase.child("profiles");

//        profileList = (RecyclerView)findViewById(R.id.rvProfile);
//        profileList.setHasFixedSize(true);
//

        rvAdapter = new ProfileListRV(Collections.<Profile>emptyList());

        viewFname = (TextView) findViewById(R.id.view_fname);
        viewLname = (TextView) findViewById(R.id.view_lname);
        viewDate = (TextView) findViewById(R.id.view_date_created);
        viewThumbnail = (ImageView) findViewById(R.id.view_thumbnail);



        if (profile !=null){

            viewFname.setText(profile.getFName());
            viewLname.setText(profile.getLName());
            viewDate.setText(profile.getDateCreated());

            Glide.with(this).load(profile.getThumbnail()).into(viewThumbnail);        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, NewProfile.class);

                startActivity(i);

               
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvProfile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        final int offset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = offset;
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new SwipeController(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, MainActivity.this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(rvAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();

        mDatabase.child("profiles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Profile> profiles = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    Profile profile = postSnapshot.getValue(Profile.class);

                    profiles.add(profile);

                }

                rvAdapter.updateList(profiles);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void itemRemoved(int position){
        Profile profile = rvAdapter.getItem(position);
        rvAdapter.removeItem(position);
        mDatabase.child("profiles").child(profile.getProfileId()).removeValue();
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    /**
     * RecyclerView: Implementing single item click and long press (Part-II)
     *
     * - creating an innerclass implementing RevyvlerView.OnItemTouchListener
     * - Pass clickListener interface as parameter
     * */

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {


                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));

                        Intent intent = new Intent(MainActivity.this, ProfileViewActivity.class);
                        startActivity(intent);


                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
