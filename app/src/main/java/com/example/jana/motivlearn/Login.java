package com.example.jana.motivlearn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.emredavarci.noty.Noty;

import com.example.jana.motivlearn.model.loginImp;
import com.example.jana.motivlearn.presenter.loginPresenter;
import com.example.jana.motivlearn.view.loginView;

import org.json.JSONObject;

public class Login extends AppCompatActivity implements loginView {
    private loginPresenter logPres;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button toReg = findViewById(R.id.button3);
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
                progressDialog = ProgressDialog.show(Login.this, "", "Please wait...");
                EditText uemail = findViewById(R.id.editText7);
                EditText upassword = findViewById(R.id.editText5);
                logPres = new loginImp(Login.this);
                logPres.login(uemail.getText()+"", upassword.getText()+"");
            }
        });
    }

    @Override
    public void loginFail(String message) {
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = Login.this.getResources().getString(resourceId);

        Noty.init(Login.this, mmm, rl,
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

    @Override
    public void loginSuccess(String result) {
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier("login_success", "string", this.getPackageName());
        String mmm = Login.this.getResources().getString(resourceId);

        Noty.init(Login.this, mmm, rl,
                Noty.WarningStyle.ACTION)
                .setActionText("OK")
                .setWarningBoxBgColor("#5cb85c")
                .setWarningTappedColor("#5cb85c")
                .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
                .setWarningBoxRadius(80,80,80,80)
                .setWarningBoxMargins(15,15,15,10)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                .show();
        try {
            result = result.substring(1, result.length() - 1);
            JSONObject obj = new JSONObject(result);
            int user_id = obj.getInt("user_id");
            String user_type = obj.getString("u_type");
            String user_name=obj.getString("u_name");

            SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putInt("user_id",user_id );
            Ed.putString("user_type",user_type);
            Ed.putString("user_name",user_name);
            Ed.commit();
        }
        catch (Exception e) {}

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

}

