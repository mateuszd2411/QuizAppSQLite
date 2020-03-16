package com.mat.quizappsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textHightScore;
    TextView txtTotalQuizQues, txtCorrectQuest, txtWrongQuest;

    Button btnStartQuiz;
    Button btnMainMenu;

    private int highScore;
    public static final String SHARED_PREFERENCE = "shread_preference";
    public static final String SHARED_PREFERENCE_HIGH_SCORE = "shread_preference_high_score";

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

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ResultActivity.this, PlayActivity.class);
                startActivity(intent);

            }
        });


        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                startActivity(intent);

            }
        });

        loadHighScore();

        Intent intent = getIntent();

        int score = intent.getIntExtra("UserScore",0);
        int totalQuestion = intent.getIntExtra("TotalQuestion",0);
        int correctQues = intent.getIntExtra("CorrectQues",0);
        int wrongQues = intent.getIntExtra("WrongQues",0);

        txtTotalQuizQues.setText("Total Ques: " + String.valueOf(totalQuestion));
        txtCorrectQuest.setText("Correct: " + String.valueOf(correctQues));
        txtWrongQuest.setText("Wrong: " + String.valueOf(wrongQues));

        if (score > highScore){

            updateHighScore(score);

        }



    }

    private void updateHighScore(int newHighScore) {

        highScore = newHighScore;
        textHightScore.setText("High Score: " + String.valueOf(highScore));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREFERENCE_HIGH_SCORE,highScore);
        editor.apply();
    }

    private void loadHighScore() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SHARED_PREFERENCE_HIGH_SCORE,0);
        textHightScore.setText("High Score: " + String.valueOf(highScore));


    }
}
