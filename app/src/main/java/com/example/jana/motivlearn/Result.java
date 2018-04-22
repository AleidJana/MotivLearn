package com.example.jana.motivlearn;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.emredavarci.circleprogressbar.CircleProgressBar;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    private CircleProgressBar progressBar;
    private  Handler updateHandler;
    private int soon = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView t = findViewById(R.id.textView13);
        String s=getIntent().getStringExtra("winners");
        int n = getIntent().getIntExtra("num", 0);
        s=s.substring(1, s.length()-1);
        String[] arr = s.split(", ");
        for(int i=0; i<arr.length; i++)
        {
            if(arr[i].startsWith("ch"+n))
                t.setText("The winner is : "+arr[i].substring(4));
            else
                t.setText("Hard Luck to you all");
        }

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(soon);
        progressBar.setMaxValue(soon); 			// set progress max value
        progressBar.setStrokeWidth(8); 		// set stroke width
        progressBar.setBackgroundWidth(8); 		// set progress background width
        progressBar.setText(String.valueOf(soon)); 	// set progress text
        progressBar.setTextColor("#FFFFFF"); 		// set text color

        updateHandler = new Handler();
        Runnable RecurringTask = new Runnable() {

            public void run() {
                // Do whatever you want
                    progressBar.setText(String.valueOf(--soon));
                    progressBar.setProgress(soon);
                    // Call this method again every 30 seconds
                    updateHandler.postDelayed(this, 1000);
                if(soon == 0)
                    finish();
                }
            }

            ;
            // Do this first after 1 second
        updateHandler.postDelayed(RecurringTask,1000);

        Button btn = findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void finishActivity()
    {
        finish();
    }
}
