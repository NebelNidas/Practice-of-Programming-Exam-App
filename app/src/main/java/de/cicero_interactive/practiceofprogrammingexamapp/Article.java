package de.cicero_interactive.practiceofprogrammingexamapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.util.Date;

public class Article implements Parcelable {
    private String title,
                    author,
                    description,
                    content;
    private Date date;
    private ArticleQuiz quiz;

    public Article(String title, Date date, String author, String description, String content) {
        this.title = title;
        this.date = date;
        this.author = author;
        this.description = description;
        this.content = content;
    }



    // Parcelable functions

    protected Article(Parcel in) {
        title = in.readString();
        author = in.readString();
        description = in.readString();
        content = in.readString();
        date = (java.util.Date)in.readSerializable();
        quiz = in.readParcelable(ArticleQuiz.class.getClassLoader());
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(description);
        parcel.writeString(content);
        parcel.writeSerializable(date);
        parcel.writeParcelable(quiz, i);
    }



    // Getters & Setters

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public ArticleQuiz getQuiz() {
        return quiz;
    }
    public void setQuiz(ArticleQuiz quiz) {
        this.quiz = quiz;
    }
}
