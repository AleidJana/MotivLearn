package com.example.jana.motivlearn.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jana.motivlearn.Login;
import com.example.jana.motivlearn.R;
import com.example.jana.motivlearn.model.PresenterImp;
import com.example.jana.motivlearn.presenter.RegisterPresenter;
import com.example.jana.motivlearn.view.RegisterView;

public class Register extends Activity implements RegisterView {

    EditText userNameF , emailF,passwordF,conPasswordF ;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button create ,login ;

RegisterPresenter mRegisterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userNameF = findViewById(R.id.editText8);
        emailF = findViewById(R.id.editText7);
        passwordF = findViewById(R.id.editText5);
        conPasswordF = findViewById(R.id.editText2);
        radioGroup=findViewById(R.id.RadioG);
        create = findViewById(R.id.button2);
        login = findViewById(R.id.button3);
        mRegisterView = new PresenterImp(Register.this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameF.getText().toString();
                String email = emailF.getText().toString();
                String password = passwordF.getText().toString();
                String conPassword = conPasswordF.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                mRegisterView.performRegister(userName, email, password, conPassword,radioButton);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginInt = new Intent(getBaseContext(),Login.class);
                startActivity(LoginInt);
            }
        });




    }



    @Override
    public void registerValidations() {
        Toast.makeText(getApplicationContext(),"Enter all the fields",Toast.LENGTH_LONG).show();

    }

    @Override
    public void registerSuccess() {
        Toast.makeText(getApplicationContext(),"Register is success",Toast.LENGTH_LONG).show();


    }

    @Override
    public void registerError() {
        Toast.makeText(getApplicationContext(),"Register is failed",Toast.LENGTH_LONG).show();


    }
    public void registerIn(){
        //coecion

        Toast.makeText(getApplicationContext(),"Register  In",Toast.LENGTH_LONG).show();


    }
    public void passwordMatch(){

        Toast.makeText(getApplicationContext()," password dosent match",Toast.LENGTH_LONG).show();
    }
    public void emailError(){

        Toast.makeText(getApplicationContext()," reEnterEmail",Toast.LENGTH_LONG).show();
    }
}



























