package de.cicero_interactive.practiceofprogrammingexamapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ChatMessage implements Parcelable {
    String sender;
    String message;
    Date date;

    public ChatMessage(String sender, String message, Date date) {
        this.sender = sender;
        this.message = message;
        this.date = date;
    }



    // Parcelable functions

    protected ChatMessage(Parcel in) {
        sender = in.readString();
        message = in.readString();
        date = (java.util.Date)in.readSerializable();
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sender);
        parcel.writeString(message);
        parcel.writeSerializable(date);
    }



    // Getters & Setters

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }



    // Override toString
    @Override
    public String toString() {
        String result = getMessage();
        if (result == null) {
            result = "N/A";
        }
        return result;
    }
}
