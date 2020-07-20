package de.cicero_interactive.practiceofprogrammingexamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MedicalTrainingArticleViewerActivity extends AppCompatActivity {
    ImageView article_icon;
    TextView article_title,
            article_date,
            article_author,
            article_content,
            article_quiz_question,
            article_quiz_solution;
    RadioGroup radioGroup;
    RadioButton radio_1,
                radio_2,
                radio_3,
                radio_4;
    Button button_ok;
    Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_training_article_viewer);
        Intent intent = getIntent();

        article = intent.getParcelableExtra("article");

        article_icon = findViewById(R.id.article_icon);
        article_title = findViewById(R.id.article_title);
        article_date = findViewById(R.id.article_date);
        article_author = findViewById(R.id.article_author);
        article_content = findViewById(R.id.article_content);
        article_quiz_question = findViewById(R.id.article_quiz_question);
        article_quiz_solution = findViewById(R.id.article_quiz_solution);
        radioGroup = findViewById(R.id.radioGroup);
        radio_1 = findViewById(R.id.radio_1);
        radio_2 = findViewById(R.id.radio_2);
        radio_3 = findViewById(R.id.radio_3);
        radio_4 = findViewById(R.id.radio_4);
        button_ok = findViewById(R.id.button_ok);

        if (article.getQuiz() != null) {
            article_quiz_question.setText(article.getQuiz().getQuestion());
            radio_1.setText(article.getQuiz().getAnswers().get(0).getContent());
            radio_2.setText(article.getQuiz().getAnswers().get(1).getContent());
            radio_3.setText(article.getQuiz().getAnswers().get(2).getContent());
            radio_4.setText(article.getQuiz().getAnswers().get(3).getContent());
            article_quiz_solution.setText(article.getQuiz().getSolutionText());
        } else {
            findViewById(R.id.quiz_card).setVisibility(View.GONE);
        }


        article_icon.setImageResource(R.drawable.ic_outline_highlight_24);
        article_title.setText(article.getTitle());
        article_date.setText(new SimpleDateFormat("dd MMMM yyyy").format(article.getDate()));
        article_author.setText(article.getAuthor());
        article_content.setText(article.getContent());


        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(radioId);
                RadioButton correctRadioButton = null;

                for (int i = 0; i < article.getQuiz().getAnswers().size(); i++) {
                    if (article.getQuiz().getAnswers().get(i).isSolution() == true) {
                        // A switch statement would be better, but Java doesn't allow it with radio buttons
                        if (i == 0) {
                            correctRadioButton = radio_1;
                        } else if (i == 1) {
                            correctRadioButton = radio_2;
                        } else if (i == 2) {
                            correctRadioButton = radio_3;
                        } else if (i == 3) {
                            correctRadioButton = radio_4;
                        }
                    }
                }

                String message = "";
                MyDatabase myDatabase = new MyDatabase(getApplicationContext());
                if (selectedRadioButton.equals(correctRadioButton)) {
                    message = "Well done!";
                    myDatabase.incrementCorrectQuizNumber();
                } else {
                    message = "Wrong answer.";
                    myDatabase.incrementWrongQuizNumber();
                }
                button_ok.setEnabled(false);
                article_quiz_solution.setVisibility(View.VISIBLE);
                article_quiz_solution.setText(message + " " + article_quiz_solution.getText());
                selectedRadioButton.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorWrong, null));
                correctRadioButton.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorCorrect, null));
                radio_1.setEnabled(false);
                radio_2.setEnabled(false);
                radio_3.setEnabled(false);
                radio_4.setEnabled(false);
            }
        });
    }

    public void checkButton(View view) {
        button_ok.setEnabled(true);
    }




    // Makes sure that you get back to the correct fragment in MainActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return true;
    }
}