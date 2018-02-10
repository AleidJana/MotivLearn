package com.example.jana.motivlearn;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jana.motivlearn.model.loginImp;
import com.example.jana.motivlearn.presenter.loginPresenter;
import com.example.jana.motivlearn.model.PresenterImp;
import com.example.jana.motivlearn.presenter.RegisterPresenter;
import com.example.jana.motivlearn.view.RegisterView;
import com.example.jana.motivlearn.view.loginView;

public class Login extends AppCompatActivity
        implements loginView {
    private loginPresenter logPres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button toReg = findViewById(R.id.button2);
        toReg.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Register.class);
                startActivity(intent);
            }
        });

        Button logg = findViewById(R.id.button2);
        logg.setOnClickListener(new Button.OnClickListener(

        ) {
            @Override
            public void onClick(View view) {
                EditText uemail = findViewById(R.id.editText7);
                EditText upassword = findViewById(R.id.editText5);
                logPres = new loginImp(Login.this);
                logPres.login(uemail.getText()+"", upassword.getText()+"");
            }
        });
    }

    @Override
    public void loginValidation() {
        // Toast toast=Toast.makeText(MainActivity.this,res,Toast.LENGTH_SHORT);
        //  toast.show();
        LinearLayout linlay = findViewById(R.id.errorlay);
        linlay.setVisibility(View.VISIBLE);

        TextView eror = findViewById(R.id.textView7);
        eror.setVisibility(View.GONE);

        TextView eror2 = findViewById(R.id.textView8);
        eror2.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginFail() {
        LinearLayout linlay = findViewById(R.id.errorlay);
        linlay.setVisibility(View.VISIBLE);

        TextView eror2 = findViewById(R.id.textView8);
        eror2.setVisibility(View.GONE);

        TextView eror = findViewById(R.id.textView7);
        eror.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginSuccess(String res) {
        Toast toast=Toast.makeText(Login.this,res,Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public static class Register extends Activity implements RegisterView {

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
}

