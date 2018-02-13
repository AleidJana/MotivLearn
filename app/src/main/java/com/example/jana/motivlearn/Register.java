package com.example.jana.motivlearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.Login;
import com.example.jana.motivlearn.R;
import com.example.jana.motivlearn.model.PresenterImp;
import com.example.jana.motivlearn.presenter.RegisterPresenter;
import com.example.jana.motivlearn.view.RegisterView;

public class Register extends AppCompatActivity implements RegisterView {

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
    public void registerSuccess(String message) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = Register.this.getResources().getString(resourceId);
        Noty.init(Register.this, mmm, rl,
                Noty.WarningStyle.ACTION)
                .setActionText("OK")
                .setWarningBoxBgColor("#5cb85c")
                .setWarningTappedColor("#5cb85c")
                .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
                .setWarningBoxRadius(80,80,80,80)
                .setWarningBoxMargins(15,15,15,10)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                .show();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);

    }

    public void registerFail(String message){
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = Register.this.getResources().getString(resourceId);

        Noty.init(Register.this, mmm, rl,
                Noty.WarningStyle.ACTION)
                .setActionText("OK")
                .setWarningBoxBgColor("#d9534f")
                .setWarningTappedColor("#d9534f")
                .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
                .setWarningBoxRadius(80,80,80,80)
                .setWarningBoxMargins(15,15,15,10)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                .show();

    }
}



























