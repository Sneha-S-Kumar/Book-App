package com.example.bookapp;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;
    public class SplashActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent;
                    intent=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    Log.e("Splash","end");
                    finish();
                }
            },1000);
        }
    }
