package com.example.jana.motivlearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.model.PresenterImp;
import com.example.jana.motivlearn.presenter.RegisterPresenter;
import com.example.jana.motivlearn.view.RegisterView;
import org.json.JSONObject;

import email.GMailSender;

public class Register extends AppCompatActivity {

    EditText userNameF , emailF,passwordF,conPasswordF ;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button create ,login ;
    ProgressDialog progressDialog;
    String RQ;
    String[] userinfo;
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

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameF.getText().toString();
                String email = emailF.getText().toString();
                String password = passwordF.getText().toString();
                String conPassword = conPasswordF.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                if(userName.equals("")||email.equals("")||password.equals("")||conPassword.equals(""))
                {
                    errormessage("All fields is required");

                }
                else{

                    if (email.contains("@")&&((email.substring(email.indexOf("@"))).toLowerCase().equals("@student.ksu.edu.sa") ||(email.substring(email.indexOf("@"))).toLowerCase().equals("@ksu.edu.sa")))
                    {
                        if (password.equals(conPassword))
                        {
                            userinfo = new String[]{userName, email, password, conPassword, String.valueOf(radioButton.getText())};
                            progressDialog = ProgressDialog.show(Register.this, "", "Please wait...");
                            RQ = "" + ((int) (Math.random() * 9000) + 1000);
                            SharedPreferences sp = getSharedPreferences("RegisterCode", MODE_PRIVATE);
                            SharedPreferences.Editor Ed = sp.edit();
                            Ed.putString("RQ", RQ);
                            Ed.commit();
                            sendEmail(email, "Registration code", "Dear, " + userName + " Welcome to MotivLearn Community your registration code is:  " + RQ, progressDialog, userinfo);
                        }
                        else {errormessage("The passwords not matches");}
                        }
                        else {errormessage("Please enter KSU email");}
                    }



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

    private void sendEmail(String email,String title,String message,ProgressDialog p,String[] userinfo)
    {

        GMailSender sm = new GMailSender(this,email,title,message,p,userinfo);
        sm.execute();

    }
    private void errormessage (String message)
    {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        Noty.init(Register.this, message, rl,
                Noty.WarningStyle.ACTION)
                .setActionText("OK")
                .setWarningBoxBgColor("#d9534f")
                .setWarningTappedColor("#d9534f")
                .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
                .setWarningBoxRadius(80,80,80,80)
                .setWarningBoxMargins(2,2,2,10)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                .show();
    }
}



























