package com.example.jana.motivlearn;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.model.CodeOutputImp;
import com.example.jana.motivlearn.presenter.CodeOutputPresenter;
import com.example.jana.motivlearn.view.CodeOutputView;

public class CodeOutput extends AppCompatActivity implements CodeOutputView{

    EditText codeF ,outputF;
    Button Submit;
    ProgressDialog progressDialog;
    String title;
    Bundle bundle;
    CodeOutputPresenter codeOutpuView;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_output);
        codeF = findViewById(R.id.editText);
        outputF = findViewById(R.id.editText2);
        codeOutpuView = new CodeOutputImp(CodeOutput.this);
        bundle=getIntent().getExtras();
        final String pathtype =bundle.getString("pathType");
        if(pathtype.equals("p")) {
            title = "Create Challenge";
        }
        else {
            title="Suggest Challenge";
        }
        Submit=findViewById(R.id.button);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(CodeOutput.this, "", "Please wait...");
                String challengTitle =bundle.getString("ChallengeTitle");
                String challengType =bundle.getString("challengeType");
                String challengField =bundle.getString("challengeField");
                int challengeTime=bundle.getInt("challengeTime");
                int challengeCoins=bundle.getInt("challengeCoins");
                String code =codeF.getText().toString();
                String output =outputF.getText().toString();
                SharedPreferences sp1= CodeOutput.this.getSharedPreferences("Login", MODE_PRIVATE);
                int uid =sp1.getInt("user_id", 0);

                if(pathtype.equals("p")) {
                    codeOutpuView.performCodeOutput(uid, code, output, challengTitle, challengType, challengField, challengeTime, challengeCoins);
                }
              else {
                    codeOutpuView.suggestCodeOutput(uid, code, output, challengTitle, challengType, challengField, challengeTime, challengeCoins);
                }
                //  Intent intent = new Intent(getBaseContext(),MainActivity.class);
              //  startActivity(intent);

            }
        });
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(R.color.white);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void codeOutputSuccess(String message) {
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = CodeOutput.this.getResources().getString(resourceId);
        new TTFancyGifDialog.Builder(CodeOutput.this)
                .setTitle("The challenge have been created successfully")
                .setMessage("You Have got 10 Coins")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc")
                .setGifResource(R.drawable.hgif1)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        //Toast.makeText(WatchVideo.this,"Ok",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                     //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("nextFrag", "cha");
                        startActivity(intent);
                        //finish();
                    }
                })
                .build();

    }

    @Override
    public void codeOutputFail(String message) {
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = CodeOutput.this.getResources().getString(resourceId);

        Noty.init(CodeOutput.this, mmm, rl,
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
