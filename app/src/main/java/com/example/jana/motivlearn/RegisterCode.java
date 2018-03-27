package com.example.jana.motivlearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.model.PresenterImp;
import com.example.jana.motivlearn.presenter.RegisterPresenter;
import com.example.jana.motivlearn.view.RegisterView;

import org.json.JSONObject;

public class RegisterCode extends AppCompatActivity implements RegisterView {
Button submit;
    RegisterPresenter mRegisterView;
    ProgressDialog progressDialog;
    EditText RQtext;
    String RQ;
    String[] userinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_code);
        mRegisterView = new PresenterImp(RegisterCode.this);
        SharedPreferences sp1= this.getSharedPreferences("RegisterCode", MODE_PRIVATE);
        RQ =sp1.getString("RQ",null);
        RQtext=(EditText)findViewById(R.id.editText);

        Intent intent = getIntent();
        userinfo = intent.getStringArrayExtra("userinfo");
       // Toast.makeText(RegisterCode.this,userinfo[5]+"",Toast.LENGTH_LONG).show();

        submit=(Button)findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!String.valueOf(RQtext.getText()).equals("")&&String.valueOf(RQtext.getText())!=null&&Integer.parseInt(RQ)==Integer.parseInt(String.valueOf(RQtext.getText()))) {
                    progressDialog = ProgressDialog.show(RegisterCode.this, "", "Please wait...");
                    mRegisterView.performRegister(userinfo[0], userinfo[1],userinfo[2], userinfo[3],userinfo[4]);
                }else
                {
                    RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
                    Noty.init(RegisterCode.this, "Please enter the received registration code correctly ", rl,
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
            }
        });


    }
    @Override
    public void registerSuccess(String responsee, String type) {
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier("register_success", "string", this.getPackageName());
        String mmm = RegisterCode.this.getResources().getString(resourceId);
        Noty.init(RegisterCode.this, mmm, rl,
                Noty.WarningStyle.ACTION)
                .setActionText("OK")
                .setWarningBoxBgColor("#5cb85c")
                .setWarningTappedColor("#5cb85c")
                .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
                .setWarningBoxRadius(80,80,80,80)
                .setWarningBoxMargins(15,15,15,10)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                .show();
        try{
            responsee = responsee.substring(1, responsee.length()-1);
            JSONObject obj = new JSONObject(responsee);
            int uid = obj.getInt("GENERATED_KEY");
            SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putInt("user_id",uid );
            Ed.putString("user_type",type);
            Ed.commit();
        }
        catch (Exception e) {}

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);

    }

    public void registerFail(String message){
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = RegisterCode.this.getResources().getString(resourceId);

        Noty.init(RegisterCode.this, mmm, rl,
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
