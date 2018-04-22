package com.example.jana.motivlearn;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.model.fillBlankImp;
import com.example.jana.motivlearn.presenter.fillBlankPresenter;
import com.example.jana.motivlearn.view.fillBlankView;

public class fillBlank extends AppCompatActivity implements fillBlankView {
    private fillBlankPresenter pres;
    ProgressDialog progressDialog;
String pagetitle ;
Bundle bundle;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blank);
        pres = new fillBlankImp(fillBlank.this);
        Button myButton = (Button)findViewById(R.id.button3);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "{{write the answer here}}";
                EditText myInput = (EditText)findViewById(R.id.editText);
                myInput.append(s);
                myInput.setSelection(myInput.getText().toString().indexOf("write the answer here"),myInput.getText().toString().indexOf("write the answer here")+21);
            }
        });
        bundle=getIntent().getExtras();
        final String pathtype =bundle.getString("pathType");
        if(pathtype.equals("p")) {
            pagetitle = "Create Challenge";
        }
        else {
            pagetitle="Suggest Challenge";
        }
        Button submit = (Button)findViewById(R.id.button2);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(fillBlank.this, "", "Please wait...");

                EditText myInput = (EditText)findViewById(R.id.editText);
                String question = myInput.getText().toString();
                String title =bundle.getString("ChallengeTitle");
                String field =bundle.getString("challengeField");
                int time=bundle.getInt("challengeTime");
                int coins=bundle.getInt("challengeCoins");
                SharedPreferences sp1= fillBlank.this.getSharedPreferences("Login", MODE_PRIVATE);
                int uid =sp1.getInt("user_id", 0);

                pres = new fillBlankImp(fillBlank.this);
                if(pathtype.equals("p")) {
                    pres.addPublicChallenge(uid, question, title, field, time, coins);
                }
                else {
                    pres.suggestFillBlank(uid, question, title, field, time, coins);
                }

            }
        });

    /*    try {
            String ss = "[{  'GENERATED_KEY': 2 }]";
            ss = ss.substring(1, ss.length()-1);
            JSONObject obj = new JSONObject(ss);
            int index = obj.getInt("GENERATED_KEY");
        }
        catch (Exception e) {}*/
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(pagetitle);
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
    public void fillBlankSuccess(String message) {
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = fillBlank.this.getResources().getString(resourceId);

        new TTFancyGifDialog.Builder(fillBlank.this)
                .setTitle("Congratulations")
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
                    }
                })
                .build();

    }

    @Override
    public void fillBlankFail(String message) {
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = fillBlank.this.getResources().getString(resourceId);

        Noty.init(fillBlank.this, mmm, rl,
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
