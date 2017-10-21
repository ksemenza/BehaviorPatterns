package com.guinproductions.behavioralpatterns.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guinp on 10/13/2017.
 */
public class Profile implements Parcelable {

    private String profileId;
    private String fName;
    private String lName;
    private String dateCreated;
    private String timeStamp;
    private String thumbnail;

    public Profile(){

    }

    public Profile(String profileId, String fName, String lName, String dateCreated, String thumbnail) {

        this.profileId = profileId;
        this.fName = fName;
        this.lName = lName;
        this.dateCreated = dateCreated;
        this.timeStamp = timeStamp;
        this.thumbnail = thumbnail;
    }
    @Exclude
    public Map<String, Object> profileMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("profileId", profileId);
        result.put("fName", fName);
        result.put("lName", lName);
        result.put("dateCreated", dateCreated);
        result.put("timeStamp", timeStamp);
        result.put("thumbnail", thumbnail);

        return result;

    }


    public Profile(Parcel in){
        this.profileId = in.readString();
        this.fName = in.readString();
        this.lName = in.readString();
        this.dateCreated = in.readString();
        this.timeStamp = in.readString();
        this.thumbnail = in.readString();
    }

    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {

            Profile profile = new Profile();
            profile.fName = source.readString();
            profile.lName = source.readString();
            profile.dateCreated = source.readString();
            profile.timeStamp = source.readString();
            profile.thumbnail = source.readString();
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {

            return new Profile[size];
        }
    };

    public String getProfileId() {
        return profileId;
    }

    public String setProfileId(String profileId) {
        this.profileId = profileId;
        return profileId;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDateCreated() {

        final Calendar c = Calendar.getInstance();

        return (new StringBuilder()
                .append(c.get(Calendar.MONTH) + 1).append("/")
                .append(c.get(Calendar.DAY_OF_MONTH)).append("/")
                .append(c.get(Calendar.YEAR)).append(" ")).toString();
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTimeStamp(){ final Calendar c = Calendar.getInstance();

        return(new StringBuilder()
                .append(c.get(Calendar.HOUR)).append(":")
                .append(c.get(Calendar.MINUTE)).append(":")).toString();}

    public void setTimeStamp(String timeStamp){this.timeStamp = timeStamp;}

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public  void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.profileId);
        dest.writeString(this.fName);
        dest.writeString(this.lName);
        dest.writeString(this.dateCreated);
        dest.writeString(this.thumbnail);
    }






}