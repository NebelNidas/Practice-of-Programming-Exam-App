package de.cicero_interactive.practiceofprogrammingexamapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.util.Date;

public class Sleep implements Parcelable {
    private int minutes;
    private Date date;

    public Sleep(int minutes, Date date) {
        this.minutes = minutes;
        this.date = date;
    }



    // Parcelable functions

    protected Sleep(Parcel in) {
        minutes = in.readInt();
        date = (java.util.Date)in.readSerializable();
    }

    public static final Creator<Sleep> CREATOR = new Creator<Sleep>() {
        @Override
        public Sleep createFromParcel(Parcel in) {
            return new Sleep(in);
        }

        @Override
        public Sleep[] newArray(int size) {
            return new Sleep[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(minutes);
        parcel.writeSerializable(date);
    }



    // Getters & Setters

    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
