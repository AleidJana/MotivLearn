package com.example.jana.motivlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

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
                t.setText(arr[i].substring(4));

        }

        Button btn = findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
