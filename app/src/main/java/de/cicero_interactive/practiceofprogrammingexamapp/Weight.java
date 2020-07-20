package de.cicero_interactive.practiceofprogrammingexamapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.util.Date;

public class Weight implements Parcelable {
    private int weight;
    private Date date;

    public Weight(int weight, Date date) {
        this.weight = weight;
        this.date = date;
    }



    // Parcelable functions

    protected Weight(Parcel in) {
        weight = in.readInt();
        date = (java.util.Date)in.readSerializable();
    }

    public static final Creator<Weight> CREATOR = new Creator<Weight>() {
        @Override
        public Weight createFromParcel(Parcel in) {
            return new Weight(in);
        }

        @Override
        public Weight[] newArray(int size) {
            return new Weight[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(weight);
        parcel.writeSerializable(date);
    }



    // Getters & Setters

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
