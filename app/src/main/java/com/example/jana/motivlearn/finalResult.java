package com.example.jana.motivlearn;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class finalResult extends AppCompatActivity {
TextView[] tv = new TextView[4];
    TextView[] tv2 = new TextView[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);

        KonfettiView viewKonfetti = findViewById(R.id.viewKonfetti);
        viewKonfetti.build()
                .addColors(Color.parseColor("#d11141"),
                        Color.parseColor("#00b159"), Color.parseColor("#00aedb"))
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(20000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(10, 5f))
                .setPosition(500f,  600f, -600f, -100f)
                .stream(100, 5000L);

        int[] scores = getIntent().getIntArrayExtra("scores");
        String[] usernames = getIntent().getStringArrayExtra("users");

        tv[0] = findViewById(R.id.textView1);
        tv[1] = findViewById(R.id.textView2);
        tv[2] = findViewById(R.id.textView3);
        tv[3] = findViewById(R.id.textView4);

        tv2[0] = findViewById(R.id.textView11);
        tv2[1] = findViewById(R.id.textView22);
        tv2[2] = findViewById(R.id.textView33);
        tv2[3] = findViewById(R.id.textView44);


        for(int i=0 ; i<scores.length ; i++) {
            if(i==0 && scores[i]!=0)
            {TextView t0 = findViewById(R.id.textView1);
            t0.setText("+ 10");
            }
            tv[i].setText(scores[i]+"");
            tv2[i].setText(usernames[i]);

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
startActivity(new Intent(getBaseContext(),MainActivity.class));
    }
}
