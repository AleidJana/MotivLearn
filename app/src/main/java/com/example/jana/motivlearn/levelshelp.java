package com.example.jana.motivlearn;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class levelshelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelshelp);

        ViewPager viewPager = findViewById(R.id.levelshelp);
        levelsAdapter adapter = new levelsAdapter(this );
        viewPager.setAdapter(adapter);
    }
}
