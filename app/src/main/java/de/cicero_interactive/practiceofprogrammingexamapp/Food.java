package de.cicero_interactive.practiceofprogrammingexamapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Food implements Parcelable {
    private String name;
    private int calories;
    private Date date;

    public Food(String name, int calories, Date date) {
        this.name = name;
        this.calories = calories;
        this.date = date;
    }



    // Parcelable functions

    protected Food(Parcel in) {
        name = in.readString();
        calories = in.readInt();
        date = (Date)in.readSerializable();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(calories);
        parcel.writeSerializable(date);
    }



    // Getters & Setters

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
