package com.mat.quizappsqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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

    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;
    private Questions currentQuestions;
    private boolean answered;


    private final Handler handler = new Handler();

    private ColorStateList buttonLabelColor;

    private int correctAns = 0, wrongAns = 0;

    private FinalScoreDialog finalScoreDialog;

    private int totalSzieofQuiz = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupUI();

        fetchDB();

        buttonLabelColor = rb1.getTextColors();

        finalScoreDialog = new FinalScoreDialog(this);

    }

    private void setupUI() {
        textViewQuestions = findViewById(R.id.txtQuestionContainer);

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

    private void fetchDB(){

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        startQuiz();

    }

    private void startQuiz() {

        questionTotalCount = questionList.size();
        Collections.shuffle(questionList);

        showQuestions();


        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.radioButton1:

                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_options_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));

                    break;

                    case R.id.radioButton2:

                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_options_selected));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));

                        break;

                    case R.id.radioButton3:

                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_options_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));

                        break;

                    case R.id.radioButton4:

                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_options_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));

                        break;

                }

            }
        });

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!answered){
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        quizOperations();

                    } else {

                        Toast.makeText(QuizActivity.this, "Please Select Option", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });



    }

    private void showQuestions(){

        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);



        if (questionCounter < questionTotalCount){
            currentQuestions = questionList.get(questionCounter);
            textViewQuestions.setText(currentQuestions.getQuestion());
            rb1.setText(currentQuestions.getOption1());
            rb2.setText(currentQuestions.getOption2());
            rb3.setText(currentQuestions.getOption3());
            rb4.setText(currentQuestions.getOption4());

            questionCounter++;
            answered = false;

            buttonConfirmNext.setText("Confirm");

            textViewQuestionsCount.setText("Questions: " + questionCounter + "/" + questionTotalCount);
        } else {

            totalSzieofQuiz  =questionList.size();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                finalScoreDialog.finalScoreDialog(correctAns,wrongAns,totalSzieofQuiz);

                }
            }, 1000);

        }

    }

    private void quizOperations() {

        answered = true;
        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected) + 1;

        checkSolution(answerNr, rbselected);

    }


    private void checkSolution(int answerNr, RadioButton rbselected) {

        switch (currentQuestions.getAnswerNr()){

            case 1:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb1.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },1000);



                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },1000);
                }
                break;

            case 2:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb2.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },1000);

                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },1000);
                }
                break;

            case 3:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb3.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);

                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);
                }
                break;

            case 4:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb4.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);

                } else {
                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);
                }
                break;

        } /// ent of the switch

        if (questionCounter == questionTotalCount){

            buttonConfirmNext.setText("Confirm and Finish");
        }


    }

    private void changetoIncorrectColor(RadioButton rbselected) {

        rbselected.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_wrong));
        rbselected.setTextColor(Color.WHITE);

    }




}
