package com.mat.quizappsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;


    private TextView textViewQuestions;
    private TextView textViewScore;
    private TextView textViewQuestionsCount;
    private TextView textViewCountDown;
    private TextView textViewCorrect, textViewWrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupUI();

    }

    private void setupUI() {

        textViewCorrect = findViewById(R.id.txtCorrect);
        textViewWrong = findViewById(R.id.txtWrong);
        textViewCountDown = findViewById(R.id.txtViewTimer);
        textViewQuestionsCount = findViewById(R.id.txtTotalQuestion);
        textViewScore = findViewById(R.id.txtScore);


        buttonConfirmNext = findViewById(R.id.button);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);

    }
}
