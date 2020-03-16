package com.mat.quizappsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView txtSplashText;
    ImageView imgViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        txtSplashText = findViewById(R.id.textviewLogoText);
        imgViewLogo = findViewById(R.id.imgviewLogo);
    }
}
