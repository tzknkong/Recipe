package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        progressBar = findViewById(R.id.progressBar);

        new CountDownTimer(3000, 1000) {
            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();

    }
}
