package de.cicero_interactive.practiceofprogrammingexamapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.util.Date;

public class Allergy implements Parcelable {
    private String name;
    private Date date;

    public Allergy(String name, Date date) {
        this.name = name;
        this.date = date;
    }



    // Parcelable functions

    protected Allergy(Parcel in) {
        name = in.readString();
        date = (java.util.Date)in.readSerializable();
    }

    public static final Creator<Allergy> CREATOR = new Creator<Allergy>() {
        @Override
        public Allergy createFromParcel(Parcel in) {
            return new Allergy(in);
        }

        @Override
        public Allergy[] newArray(int size) {
            return new Allergy[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeSerializable(date);
    }



    // Getters & Setters

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
