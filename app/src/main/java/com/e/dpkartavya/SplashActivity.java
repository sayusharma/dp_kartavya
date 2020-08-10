package com.e.dpkartavya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SaveSharedPreference.getUserName(SplashActivity.this).length()!=0){
                    startActivity(new Intent(SplashActivity.this,DashActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this,FirstActivity.class));
                    finish();
                }

            }
        },1500);
    }
}