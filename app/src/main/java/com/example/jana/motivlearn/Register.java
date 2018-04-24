package com.example.jana.motivlearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.emredavarci.noty.Noty;

import com.example.jana.motivlearn.email.GMailSender;
import com.example.jana.motivlearn.model.PresenterImp;
import com.example.jana.motivlearn.presenter.RegisterPresenter;
import com.example.jana.motivlearn.view.RegisterView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements RegisterView{

    EditText userNameF , emailF,passwordF,conPasswordF ;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button create ,login ;
    ProgressDialog progressDialog;
    String RQ;
    String[] userinfo;
    RegisterPresenter mRegisterView;
    String userName;
    String email;
    String password;
    String conPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterView = new PresenterImp(Register.this);

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
                userName = userNameF.getText().toString();
                email = emailF.getText().toString();
                password = passwordF.getText().toString();
                conPassword = conPasswordF.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                if(userName.equals("")||email.equals("")||password.equals("")||conPassword.equals(""))
                {
                    errormessage("All fields is required");

                }
                else{

                    if (email.contains("@")&&((email.substring(email.indexOf("@"))).toLowerCase().equals("@student.ksu.edu.sa") ||(email.substring(email.indexOf("@"))).toLowerCase().equals("@ksu.edu.sa"))) {
                        if (validatePassword(password)) {
                            if (password.equals(conPassword)) {
                                progressDialog = ProgressDialog.show(Register.this, "", "Please wait...");
                                mRegisterView.isExist(email);
                            } else {
                                errormessage("The passwords not matches");
                            }
                        }
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

    private void errormessage (String message)
    {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        Noty.init(Register.this, message, rl,
                Noty.WarningStyle.ACTION)
                .setActionText("OK")
                .setWarningBoxBgColor("#d9534f")
                .setWarningTappedColor("#d9534f")
                .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
                .setHeightDp(80)
                .setWarningBoxRadius(80,80,80,80)
                .setWarningBoxMargins(2,2,2,10)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                .show();
    }

    @Override
    public void registerSuccess(String message, String type) {

    }

    @Override
    public void registerFail(String message) {

    }

    private void sendEmail(String email,String title,String message,ProgressDialog p,String[] userinfo)
    {
        // Create An Object Of GMailSender Class
        GMailSender sm = new GMailSender(this,email,title,message,p,userinfo);
        sm.execute();
    }
    @Override
    public void setResult(String message) {
        try {
            if(message.equals("[]"))
            {
                userinfo = new String[]{userName, email, password, conPassword, String.valueOf(radioButton.getText())};
                //progressDialog = ProgressDialog.show(Register.this, "", "Please wait...");
                RQ = "" + ((int) (Math.random() * 9000) + 1000);
                SharedPreferences sp = getSharedPreferences("RegisterCode", MODE_PRIVATE);
                SharedPreferences.Editor Ed = sp.edit();
                Ed.putString("RQ", RQ);
                Ed.commit();
                //Calling sendEmail Method With Subject And Message
                sendEmail(email, "Registration code", "Dear " + userName + ", " +
                        "Welcome to MotivLearn Community your registration code is:  " + RQ, progressDialog, userinfo);
            }
            else {
                progressDialog.dismiss();
                errormessage("This email is already exist");
               }
        }
        catch (Exception e){}
    }


    public boolean validatePassword(String password)
    {
        Pattern hasUppercase = Pattern.compile("[A-Z]");
        Pattern hasLowercase = Pattern.compile("[a-z]");
        Pattern hasNumber = Pattern.compile("\\d");
        Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9 ]");
        String meassage;
        boolean flag=true;
            if (password.length() < 8 || password.contains(" ")) {
                errormessage("The password should be more than 8 characters whiteout spaces");
                flag=false;
            }else
            if ((!hasUppercase.matcher(password).find())||(!hasLowercase.matcher(password).find())||(!hasNumber.matcher(password).find())||(!hasSpecialChar.matcher(password).find())) {
                errormessage("The password should contain at least one uppercase,lowercase,special character and number");
                flag=false;
            }
            return flag;
    }

}



























