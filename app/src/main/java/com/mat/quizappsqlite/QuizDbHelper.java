package com.mat.quizappsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mat.quizappsqlite.QuizContract.QuestionTable;


import androidx.annotation.Nullable;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GoQuiz";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = db;

        final  String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        fillQuestionsTabele();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }

    private void addQuestions(Questions questions){

        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION,questions.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1,questions.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2,questions.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3,questions.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4,questions.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR,questions.getAnswerNr());

        db.insert(QuestionTable.TABLE_NAME, null,cv);
    }

    private void fillQuestionsTabele(){

        Questions q1 = new Questions("Which physical feature do you notice first in a guy?", "eyes", "teeth", "abs", "smile", 1);
        addQuestions(q1);

        Questions q2 = new Questions("Your perfect man is going to have to learn to accept your...?", "sds", "fdfd", "fddfd", "dfdf", 1);
        addQuestions(q2);

        Questions q3 = new Questions("What's your type??", "dfdfvc", "vcvc", "re", "smjkjkile", 1);
        addQuestions(q3);

        Questions q4 = new Questions("You can't have a celebrity boyfriend without thinking about your future celebrity wedding! What would yours be like??", "kjkj", "yuyu", "hghg", "bbbmile", 1);
        addQuestions(q4);

        Questions q5 = new Questions("Your man's friends are mostly:", "eyegfgfgs", "ffff", "abgdfgfds", "smigfdgdle", 1);
        addQuestions(q5);

    }
}
