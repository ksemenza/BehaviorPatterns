package com.guinproductions.behavioralpatterns.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.guinproductions.behavioralpatterns.R;
import com.guinproductions.behavioralpatterns.model.Profile;

import java.io.IOException;

public class NewProfile extends AppCompatActivity implements View.OnClickListener{

    public static final String PROFILE_FNAME = "com.guinproductions.behavioralpatterns.fname";
    public static final String PROFILE_LNAME = "com.guinproductions.behavioralpatterns.lname";
    public static final String PROFILE_ID = "com.guinproductions.behavioralpatterns.profileid";
    public static final String DATE_CREATED = "com.guinproductions.behavioralpatterns.datecreated";
    public static final String PROFILE_THUMBNAIL = "com.guinproductions.behavioralpatterns.thumbnail";

    private Profile profile;
    private EditText editFname;
    private EditText editLname;
    private TextView dateCreated;
    private StorageReference mStorage, thumbnailStorage;
    private DatabaseReference mDatabase, mDB;

    ImageView imageThumbnail;
    private static final int PICK_IMAGE_REQUEST = 234;

    Uri FilePathUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        editFname = (EditText) findViewById(R.id.edit_fname);
        editLname = (EditText) findViewById(R.id.edit_lname);
        dateCreated = (TextView) findViewById(R.id.view_date_created) ;
        imageThumbnail = (ImageView) findViewById(R.id.view_thumbnail);

        mDB = FirebaseDatabase.getInstance().getReference();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();

        mStorage = storage.getReference();

        StorageReference photoRef = mStorage.child("profile_photo.jpg");
        StorageReference imagesRef = mStorage.child("images/profile_photo.jpg");

        photoRef.getName().equals(imagesRef.getName());
        photoRef.getPath().equals(imagesRef.getPath());

      mStorage = storage.getReferenceFromUrl("gs://behavioralpatterns-adf60.appspot.com/");

        profile = getIntent().getParcelableExtra("profiles");
        if(profile !=null){
            editFname.setText(profile.getFName());
            editLname.setText(profile.getLName());
            dateCreated.setText(profile.getDateCreated());
            Glide.with(this).load(profile.getThumbnail()).into(imageThumbnail);
        }




imageThumbnail.setOnClickListener(this);






    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imageThumbnail.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_menu) {

//            Intent i = new Intent(NewProfile.this, ProfileListRV.class);


            addProfile();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void addProfile() {

//        String profileId, String fName, String lName, String dateCreated, String thumbnail
//        profileId, fName, lName, dateCreated, thumbnail
        profile = new Profile();

        DatabaseReference profileRef = mDatabase.child("profiles");
        DatabaseReference newProfileRef = profileRef.push();

        DatabaseReference pushedProfile = profileRef.push();
        String profileID = pushedProfile.getKey();


        profile.setProfileId(mDatabase.child("profiles").getKey());
        profile.setFName(editFname.getText().toString());
        profile.setLName(editLname.getText().toString());
        Glide.with(this).load(profile.getThumbnail()).into(imageThumbnail);


        mDatabase.child(profileID).setValue(profile);

        newProfileRef.setValue(profile);

        finish();

        editFname.setText("");
        editLname.setText("");

        Toast.makeText(this, "Profile added", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
        if (view == imageThumbnail) {
            showFileChooser();
        } if (view == imageThumbnail) {

        } else if (view == imageThumbnail) {

        }
    }


}


