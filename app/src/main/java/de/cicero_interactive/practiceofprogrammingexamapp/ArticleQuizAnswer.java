package de.cicero_interactive.practiceofprogrammingexamapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ArticleQuizAnswer implements Parcelable {
    private String content;
    private boolean solution;

    public ArticleQuizAnswer(String content, boolean solution) {
        this.content = content;
        this.solution = solution;
    }



    // Parcelable functions

    protected ArticleQuizAnswer(Parcel in) {
        content = in.readString();
        solution = in.readByte() != 0;
    }

    public static final Creator<ArticleQuizAnswer> CREATOR = new Creator<ArticleQuizAnswer>() {
        @Override
        public ArticleQuizAnswer createFromParcel(Parcel in) {
            return new ArticleQuizAnswer(in);
        }

        @Override
        public ArticleQuizAnswer[] newArray(int size) {
            return new ArticleQuizAnswer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(content);
        parcel.writeByte((byte) (solution ? 1 : 0));
    }



    // Getters & Setters

    public String getContent() {
        return content;
    }
    public void setTitle(String content) {
        this.content = content;
    }
    public boolean isSolution() {
        return solution;
    }
    public void setSolution(boolean solution) {
        this.solution = solution;
    }
}
