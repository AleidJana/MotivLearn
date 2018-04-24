package com.example.jana.motivlearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Help extends AppCompatActivity {

    Button Levels;
    Button levels2;
    Button coins;
    Button leader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Levels = findViewById(R.id.Levels);
        Levels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPadges();
            }


        });

        levels2 = findViewById(R.id.levels2);
        levels2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openlevel();
            }

        });


        leader = findViewById(R.id.leaderb);
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openleaderboard();
            }});

        coins = findViewById(R.id.coinsb);
        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencoins();
            }

        });
    }

    public void openPadges() {
        Intent p = new Intent(Help.this, padgeshelp.class);
        startActivity(p);

    }

    public void openlevel() {
        Intent l = new Intent(Help.this, levelshelp.class);
        startActivity(l);

    }


    public void opencoins() {
        Intent l = new Intent(Help.this, coins.class);
        startActivity(l);

    }

    public void openleaderboard() {
        Intent l = new Intent(Help.this, leaderboard.class);
        startActivity(l);

    }
}
