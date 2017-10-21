package com.guinproductions.behavioralpatterns.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guinproductions.behavioralpatterns.R;
import com.guinproductions.behavioralpatterns.activity.EditProfile;
import com.guinproductions.behavioralpatterns.activity.ProfileViewActivity;
import com.guinproductions.behavioralpatterns.model.Profile;

import java.util.List;

/**
 * Created by guinp on 10/13/2017.
 */

public class ProfileListRV extends RecyclerView.Adapter <ProfileListRV.MyViewHolder>{


    public static final String PROFILE_FNAME = "com.guinproductions.behavioralpatterns.recyclerview.fname";
    public static final String PROFILE_LNAME = "com.guinproductions.behavioralpatterns.recyclerview.lname";
    public static String PROFILE_ID = "com.guinproductions.behavioralpatterns.recyclerview.profileid";
    public static final String DATE_CREATED = "com.guinproductions.behavioralpatterns.recyclerview.datecreated";
    public static final String TIME_STAMP = "com.guinproductions.behavioralpatterns.recyclerview.timestamp";
    public static final String PROFILE_THUMBNAIL = "com.guinproductions.behavioralpatterns.thumbnail";

    private List<Profile> profiles;

//    private ArrayList<Profile> profiles = new ArrayList<>();
    public static String profileSelected;
    CardView profileCardView;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private Profile profile;
        private Context mContext;
        TextView viewFname;
        TextView viewLname;
        TextView viewDate;
        ImageView viewThumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);

            mContext = itemView.getContext();

            profileCardView= (CardView) itemView.findViewById(R.id.profileList);

            viewFname = (TextView) itemView.findViewById(R.id.view_fname);
            viewLname = (TextView) itemView.findViewById(R.id.view_lname);
            viewDate = (TextView) itemView.findViewById(R.id.view_date_created);
            viewThumbnail = (ImageView) itemView.findViewById(R.id.view_thumbnail);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void bind(Profile profile) {
            this.profile = profile;
            viewFname.setText(profile.getFName());
            viewLname.setText(profile.getLName());
            viewDate.setText(profile.getDateCreated());
            Glide.with(mContext).load(profile.getThumbnail()).into(viewThumbnail);
        }


        @Override
        public void onClick(View view) {

            Context context = view.getContext();

            final Intent i;
            switch (getAdapterPosition()) {
                default:

                    i = new Intent(ProfileViewActivity.newInstance(context, profile));

                i.putExtra(PROFILE_ID, profile.getProfileId());
                i.putExtra(PROFILE_FNAME, profile.getFName());
                i.putExtra(PROFILE_LNAME, profile.getLName());
                i.putExtra(DATE_CREATED, profile.getDateCreated());
                    i.putExtra(TIME_STAMP, profile.getTimeStamp());
                i.putExtra(PROFILE_THUMBNAIL, profile.getThumbnail());



                context.startActivity(i);

                    break;
            }
        }

        @Override
        public boolean onLongClick(View view) {

            Context context = view.getContext();
            context.startActivity(EditProfile.newInstance(context, profile));

            return true;

        }



        }






    public ProfileListRV(List<Profile> profiles) {
        this.profiles = profiles;}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_card, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override

    public void onBindViewHolder(final MyViewHolder holder, int position){
      holder.bind(

              profiles.get(position));


    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public void updateList(List<Profile> profiles){

        if(profiles.size() != this.profiles.size() || !this.profiles.containsAll(profiles)){
            this.profiles = profiles;
            notifyDataSetChanged();
        }
    }

    public  void removeItem(int position){
        profiles.remove(position);
        notifyItemRemoved(position);
    }

    public Profile getItem(int position){



        return profiles.get(position);
    }

}