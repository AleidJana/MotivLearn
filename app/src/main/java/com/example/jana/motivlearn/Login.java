package com.example.jana.motivlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jana.motivlearn.activities.Register;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void Action (View v)
    {
        Intent i =new Intent(Login.this,Register.class);
        startActivity(i);

    }

}
