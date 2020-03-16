package com.mat.quizappsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textHightScore;
    TextView txtTotalQuizQues, txtCorrectQuest, txtWrongQuest;

    Button btnStartQuiz;
    Button btnMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnMainMenu = findViewById(R.id.result_btn_mainmenu);
        btnStartQuiz = findViewById(R.id.result_btn_playagain);
        textHightScore = findViewById(R.id.result_text_High_Score);
        txtTotalQuizQues = findViewById(R.id.result_total_questions);
        txtCorrectQuest = findViewById(R.id.result_correct_quest);
        txtWrongQuest = findViewById(R.id.result_wrong_quest);

    }
}
