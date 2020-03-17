package com.mat.quizappsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Button btHistory, btComputers, brEnglish, btMaths, btGraphics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btHistory = findViewById(R.id.button2);
        btComputers = findViewById(R.id.button4);
        brEnglish = findViewById(R.id.button3);
        btMaths = findViewById(R.id.button5);
        btGraphics = findViewById(R.id.button6);

        btGraphics.setOnClickListener(this);
        btMaths.setOnClickListener(this);
        btHistory.setOnClickListener(this);
        brEnglish.setOnClickListener(this);
        btComputers.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button2:   //history

                Intent intentHistory = new Intent(CategoryActivity.this, QuizActivity.class);
                intentHistory.putExtra("Category", Constants.HISTORY);
                startActivity(intentHistory);
                break;

            case R.id.button4:   //btComputers

                Intent intentComputers = new Intent(CategoryActivity.this, QuizActivity.class);
                intentComputers.putExtra("Category", Constants.COMPUTERS);
                startActivity(intentComputers);
                break;

            case R.id.button3:   //brEnglish

                Intent intentEnglish = new Intent(CategoryActivity.this, QuizActivity.class);
                intentEnglish.putExtra("Category", Constants.ENGLISH);
                startActivity(intentEnglish);
                break;

            case R.id.button5:   //btMaths

                Intent intentMaths = new Intent(CategoryActivity.this, QuizActivity.class);
                intentMaths.putExtra("Category", Constants.MATHS);
                startActivity(intentMaths);
                break;

            case R.id.button6:   //btGraphics

                Intent intentGraphics = new Intent(CategoryActivity.this, QuizActivity.class);
                intentGraphics.putExtra("Category", Constants.GRAPHICS);
                startActivity(intentGraphics);
                break;

        }


    }
}
