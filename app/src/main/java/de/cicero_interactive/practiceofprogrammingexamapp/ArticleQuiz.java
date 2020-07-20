package de.cicero_interactive.practiceofprogrammingexamapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class ArticleQuiz implements Parcelable {
    private String question,
                    solution_text;
    private ArrayList<ArticleQuizAnswer> answers;

    public ArticleQuiz(String question, String solution_text, ArrayList<ArticleQuizAnswer> answers) {
        this.question = question;
        this.solution_text = solution_text;
        this.answers = answers;
    }



    // Parcelable functions

    protected ArticleQuiz(Parcel in) {
        question = in.readString();
        solution_text = in.readString();
        answers = in.createTypedArrayList(ArticleQuizAnswer.CREATOR);
    }

    public static final Creator<ArticleQuiz> CREATOR = new Creator<ArticleQuiz>() {
        @Override
        public ArticleQuiz createFromParcel(Parcel in) {
            return new ArticleQuiz(in);
        }

        @Override
        public ArticleQuiz[] newArray(int size) {
            return new ArticleQuiz[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeString(solution_text);
        parcel.writeTypedList(answers);
    }



    // Getters & Setters

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getSolutionText() {
        return solution_text;
    }
    public void setSolutionText(String solution_text) {
        this.solution_text = solution_text;
    }
    public ArrayList<ArticleQuizAnswer> getAnswers() {
        return answers;
    }
    public void setAnswers(ArrayList<ArticleQuizAnswer> answers) {
        this.answers = answers;
    }
}
