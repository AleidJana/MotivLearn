package com.example.jana.motivlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jana.motivlearn.model.loginImp;
import com.example.jana.motivlearn.presenter.loginPresenter;
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
}

