package com.google.ar.sceneform.fyp.ark;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /* Using a separate thread to run the splash screen animation and then
            switching to the homepage activity after the specified delay of milliseconds
         */

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        }, 0);
    }
}
