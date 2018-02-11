package com.example.jana.motivlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class fillBlank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myButton = (Button)findViewById(R.id.button234);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = "()";
                final EditText myInput = (EditText)findViewById(R.id.editText);
                String full = myInput.getText()+s;
                myInput.setText(full);
            }
        });

    }
}
