package com.guinproductions.behavioralpatterns.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guinp on 10/15/2017.
 */

public class Note implements Parcelable{

    private String noteId;
    private String mainContent;
    private String dateCreated;
    private String dateModified;
    private String timeStamp;

    public Note(String noteId, String mainContent, String dateCreated, String dateModified){
        this();
        this.noteId = noteId;
        this.mainContent = mainContent;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.timeStamp = timeStamp;
    }

    @Exclude
    public Map<String, Object> noteMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("noteId", noteId);
        result.put("mainContent", mainContent);
        result.put("dateCreated", dateCreated);
        result.put("dateModified", dateModified);
        result.put("timeStamp", timeStamp);

        return result;
    }

    public Note(Parcel in){
        this.noteId = in.readString();
        this.mainContent = in.readString();
        this.dateCreated = in.readString();
        this.dateModified = in.readString();
        this.timeStamp = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>(){

        @Override
        public Note createFromParcel(Parcel source) {

            Note note = new Note();
            note.mainContent = source.readString();
            note.dateCreated = source.readString();
            note.dateModified = source.readString();
            note.timeStamp = source.readString();

            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Note(){

    }

    public String getNoteId(){return noteId;}
    public void setNoteId(String noteId){this.noteId = noteId;}

    public String getMainContent(){return mainContent;}
    public String setMainContent(String mainContent){this.mainContent = mainContent;
        return mainContent;
    }

    public String getDateCreated(){
        final Calendar c = Calendar.getInstance();

        return (new StringBuilder()
                .append(c.get(Calendar.MONTH) + 1).append("/")
                .append(c.get(Calendar.DAY_OF_MONTH)).append("/")
                .append(c.get(Calendar.YEAR)).append(" ")).toString();}

    public String setDateCreated(String dateCreated){this.dateCreated = dateCreated;
        return dateCreated;
    }

    public String getDateModified(){   final Calendar c = Calendar.getInstance();

        return (new StringBuilder()
                .append(c.get(Calendar.MONTH) + 1).append("/")
                .append(c.get(Calendar.DAY_OF_MONTH)).append("/")
                .append(c.get(Calendar.YEAR)).append(" ")).toString();}

    public void setDateModified(String dateModified){this.dateModified = dateModified;}

    public String getTimeStamp(){ final Calendar c = Calendar.getInstance();

        return(new StringBuilder()
                .append(c.get(Calendar.HOUR_OF_DAY)).append(":")
                .append(c.get(Calendar.MINUTE)).append(":")
                .append(c.get(Calendar.SECOND)).append(" ")).toString();}

    public String setTimeStamp(String timeStamp){this.timeStamp = timeStamp;
        return timeStamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.noteId);
        dest.writeString(this.mainContent);
        dest.writeString(this.dateCreated);
        dest.writeString(this.dateModified);
        dest.writeString(this.timeStamp);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (noteId != null ? !noteId.equals(note.noteId) : note.noteId != null)
            return false;
        if (mainContent != null ? !mainContent.equals(note.mainContent) : note.mainContent != null) return false;


        return false;
    }
}
