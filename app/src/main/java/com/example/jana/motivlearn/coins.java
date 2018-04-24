package com.example.jana.motivlearn;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class coins extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);

        ViewPager viewPager = findViewById(R.id.coinshelp);
        coinsAdapter adapter = new coinsAdapter(this );
        viewPager.setAdapter(adapter);
    }
}
