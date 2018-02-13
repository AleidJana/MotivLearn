package com.example.jana.motivlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jana.motivlearn.model.CodeOutputImp;
import com.example.jana.motivlearn.presenter.CodeOutputPresenter;
import com.example.jana.motivlearn.view.CodeOutputView;

public class CodeOutput extends AppCompatActivity implements CodeOutputView{

    EditText codeF ,outputF;
    Button Submit;
    CodeOutputPresenter codeOutpuView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_output);
        codeF = findViewById(R.id.editText);
        outputF = findViewById(R.id.editText2);
        codeOutpuView = new CodeOutputImp(CodeOutput.this);
        Submit=findViewById(R.id.button);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=getIntent().getExtras();
                String challengTitle =bundle.getString("ChallengeTitle");
                String challengType =bundle.getString("challengeType");
                String challengField =bundle.getString("challengeField");
                int challengeTime=bundle.getInt("challengeTime");
                int challengeCoins=bundle.getInt("challengeCoins");
                String code =codeF.getText().toString();
                String output =outputF.getText().toString();
                codeOutpuView.performCodeOutput(code,output,challengTitle,challengType,challengField,challengeTime,challengeCoins);
              //  Intent intent = new Intent(getBaseContext(),MainActivity.class);
              //  startActivity(intent);

            }
        });

    }

    @Override
    public void codeOutputValidations() {
        Toast.makeText(getApplicationContext(),"all the field",Toast.LENGTH_LONG).show();

    }

    @Override
    public void codeOutputSuccess() {
        Toast.makeText(getApplicationContext()," is success",Toast.LENGTH_LONG).show();


    }

    @Override
    public void codeOutputError() {
        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

    }
}
