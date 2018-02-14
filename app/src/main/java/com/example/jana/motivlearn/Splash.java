package com.example.jana.motivlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sp1= Splash.this.getSharedPreferences("Login", MODE_PRIVATE);

                int uid =sp1.getInt("user_id", 0);
                if(uid == 0)
                    startActivity(new Intent(getApplicationContext(), IntroActivity.class));
                else
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        },3000);
    }
}
