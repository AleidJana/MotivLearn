package com.example.jana.motivlearn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        Button submit = (Button)findViewById(R.id.button2);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                EditText myInput = (EditText)findViewById(R.id.editText);
                String question = myInput.getText().toString();
                Bundle bundle=getIntent().getExtras();
                String title =bundle.getString("ChallengeTitle");
                String field =bundle.getString("challengeField");
                int time=bundle.getInt("challengeTime");
                int coins=bundle.getInt("challengeCoins");
                SharedPreferences sp1= fillBlank.this.getSharedPreferences("Login", MODE_PRIVATE);
                int uid =sp1.getInt("user_id", 0);

                pres = new fillBlankImp(fillBlank.this);
                pres.addPublicChallenge(uid,question, title,field,time,coins);

            }
        });

    /*    try {
            String ss = "[{  'GENERATED_KEY': 2 }]";
            ss = ss.substring(1, ss.length()-1);
            JSONObject obj = new JSONObject(ss);
            int index = obj.getInt("GENERATED_KEY");
        }
        catch (Exception e) {}*/

    }

    @Override
    public void fillBlankSuccess(String message) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = fillBlank.this.getResources().getString(resourceId);
        Noty.init(fillBlank.this, mmm, rl,
                Noty.WarningStyle.ACTION)
                .setActionText("OK")
                .setWarningBoxBgColor("#5cb85c")
                .setWarningTappedColor("#5cb85c")
                .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
                .setWarningBoxRadius(80,80,80,80)
                .setWarningBoxMargins(15,15,15,10)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                .show();

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
                        finish();
                    }
                })
                .build();

    }

    @Override
    public void fillBlankFail(String message) {
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
