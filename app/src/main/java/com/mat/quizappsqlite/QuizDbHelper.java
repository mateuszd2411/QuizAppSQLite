package com.mat.quizappsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mat.quizappsqlite.QuizContract.QuestionTable;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GoQuiz.db";
    private static final int DATBASE_VERSION = 2;

    private SQLiteDatabase db;


    QuizDbHelper(Context context) {
        super(context, DATABASE_NAME,null, DATBASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER," +
                QuestionTable.COLUMN_CATEGORY + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);

    }


    private void fillQuestionsTable()
    {

        Questions q1 = new Questions("Android is what ?","OS","Drivers","Software","Hardware",1, Questions.CATEGORY_COMPUTERS);
        addQuestions(q1);


        Questions q2 = new Questions("Full form of PC is ?","OS","Personal Computer","Pocket Computer","Hardware",2, Questions.CATEGORY_COMPUTERS);
        addQuestions(q2);


        Questions q3 = new Questions("Windows is what ?","Easy Software","Hardware Device","Operating System","Hardware",3, Questions.CATEGORY_COMPUTERS);
        addQuestions(q3);


        Questions q4 = new Questions("Unity is used for what ?","Game Development","Movie Making","Firmware","Hardware",1, Questions.CATEGORY_COMPUTERS);
        addQuestions(q4);


        Questions q5 = new Questions("RAM stands for ","Windows","Drivers","GUI","Random Access Memory",4, Questions.CATEGORY_COMPUTERS);
        addQuestions(q5);


        Questions q6 = new Questions("Chrome is what ?","OS","Browser","Tool","New Browser",2, Questions.CATEGORY_COMPUTERS);
        addQuestions(q6);

        Questions q7 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_MATHS);
        addQuestions(q7);

        Questions q8 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_MATHS);
        addQuestions(q8);

        Questions q9 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_MATHS);
        addQuestions(q9);

        Questions q10 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_MATHS);
        addQuestions(q10);

        Questions q11 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_MATHS);
        addQuestions(q11);

        Questions q12 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_HISTORY);
        addQuestions(q12);

        Questions q13 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_HISTORY);
        addQuestions(q13);

        Questions q14 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_ENGLISH);
        addQuestions(q14);

        Questions q15 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_ENGLISH);
        addQuestions(q15);

        Questions q16 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_ENGLISH);
        addQuestions(q16);

        Questions q17 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_ENGLISH);
        addQuestions(q17);

        Questions q18 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_GRAPHICS);
        addQuestions(q18);

        Questions q19 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_GRAPHICS);
        addQuestions(q19);

        Questions q20 = new Questions("5 + 5","0","10","23","None ofthese",2, Questions.CATEGORY_GRAPHICS);
        addQuestions(q20);



    }

    private void addQuestions(Questions question){

        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4,question.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR,question.getAnswerNr());
        cv.put(QuestionTable.COLUMN_CATEGORY,question.getCategory());
        db.insert(QuestionTable.TABLE_NAME,null,cv);

    }

    public ArrayList<Questions> getAllQuestions() {

        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();



        String Projection[] = {

                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR
        };



        Cursor c = db.query(QuestionTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null);


        if (c.moveToFirst()) {
            do {

                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));

                questionList.add(question);

            } while (c.moveToNext());

        }
        c.close();
        return questionList;

    }

    public ArrayList<Questions> getAllQuestionsWithCategory(String category) {

        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();



        String Projection[] = {

                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR,
                QuestionTable.COLUMN_CATEGORY
        };


        String selection = QuestionTable.COLUMN_CATEGORY + " = ? ";
        String selectionArgs[] = {category};

        Cursor c = db.query(QuestionTable.TABLE_NAME,
                Projection,
                selection,
                selectionArgs,
                null,
                null,
                null);


        if (c.moveToFirst()) {
            do {

                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionTable.COLUMN_CATEGORY)));

                questionList.add(question);

            } while (c.moveToNext());

        }
        c.close();
        return questionList;

    }

}