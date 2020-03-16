package com.mat.quizappsqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

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

    private ColorStateList defaultTextColor;

    private int correctAns = 0, wrongAns = 0;

    private FinalScoreDialog finalScoreDialog;
    private CorrectDialog correctDialog;
    private WrongDialog wrongDialog;
    private PlayAudioForAnswers playAudioForAnswers;

    int FLAG = 0;


    int score = 0;

    private int totalSzieofQuiz = 0;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeleftinMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupUI();

        fetchDB();

        defaultTextColor = rb1.getTextColors();

        finalScoreDialog = new FinalScoreDialog(this);
        correctDialog = new CorrectDialog(this);
        wrongDialog = new WrongDialog(this);
        playAudioForAnswers = new PlayAudioForAnswers(this);

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

    public void showQuestions(){

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


            timeleftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();

        } else {

            Toast.makeText(this, "Finish!", Toast.LENGTH_SHORT).show();

            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonConfirmNext.setClickable(false);


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

               finalResult();

                }
            }, 2000);

        }

    }

    private void quizOperations() {

        answered = true;

        countDownTimer.cancel();

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

                    score += 10;
                    textViewScore.setText("Score: " + String.valueOf(score));
                    correctDialog.correctDialog(score, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswer(FLAG);




                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    String correctAnswer = (String) rb1.getText();
                    wrongDialog.wrongDialog(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswer(FLAG);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);
                }
                break;

            case 2:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb2.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score += 10;
                    textViewScore.setText("Score: " + String.valueOf(score));
                    correctDialog.correctDialog(score, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswer(FLAG);


                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    String correctAnswer = (String) rb2.getText();
                    wrongDialog.wrongDialog(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswer(FLAG);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);
                }
                break;

            case 3:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb3.setBackground(ContextCompat.getDrawable(this,R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score += 10;
                    textViewScore.setText("Score: " + String.valueOf(score));
                    correctDialog.correctDialog(score, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswer(FLAG);



                } else {

                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    String correctAnswer = (String) rb3.getText();
                    wrongDialog.wrongDialog(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswer(FLAG);

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

                    score += 10;
                    textViewScore.setText("Score: " + String.valueOf(score));
                    correctDialog.correctDialog(score, this);

                    FLAG = 1;
                    playAudioForAnswers.setAudioforAnswer(FLAG);


                } else {
                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    String correctAnswer = (String) rb4.getText();
                    wrongDialog.wrongDialog(correctAnswer, this);

                    FLAG = 2;
                    playAudioForAnswers.setAudioforAnswer(FLAG);

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


    private void startCountDown(){

        countDownTimer = new CountDownTimer(timeleftinMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeleftinMillis = l;

                updateCountDownText();
            }

            @Override
            public void onFinish() {

                timeleftinMillis = 0;
                updateCountDownText();

            }
        }.start();

    }


    private void updateCountDownText(){

        int minutes = (int) (timeleftinMillis/1000) / 60;
        int seconds = (int) (timeleftinMillis/1000) & 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeFormatted);

        if (timeleftinMillis < 10000){

            textViewCountDown.setTextColor(Color.RED);

            FLAG = 3;
            playAudioForAnswers.setAudioforAnswer(FLAG);

        }else {

            textViewCountDown.setTextColor(defaultTextColor);
        }


        if (timeleftinMillis == 0){

            Toast.makeText(this, "Times Up!", Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);
                }
            }, 2000);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (countDownTimer!= null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer!= null){
            countDownTimer.cancel();
        }
    }


    private void finalResult(){

        Intent resultData = new Intent(QuizActivity.this, ResultActivity.class);

        resultData.putExtra("UserScore", score);
        resultData.putExtra("TotalQuestion", questionTotalCount);
        resultData.putExtra("CorrectQues", correctAns);
        resultData.putExtra("WrongQues", wrongAns);
        startActivity(resultData);

    }


}
