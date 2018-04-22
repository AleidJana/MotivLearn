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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.model.multiChoicesImp;
import com.example.jana.motivlearn.presenter.multiChoicesPresenter;
import com.example.jana.motivlearn.view.multiChoicesView;

public class multipleChoices extends AppCompatActivity implements multiChoicesView {
    private multiChoicesPresenter pres;
    ProgressDialog progressDialog;
    Bundle bundle;
    String title;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choices);

        bundle=getIntent().getExtras();
        final String pathtype =bundle.getString("pathType");
        if(pathtype.equals("p")) {
            title = "Create Challenge";
        }
        else {
            title="Suggest Challenge";
        }

        Button submit = (Button)findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(multipleChoices.this, "", "Please wait...");

                EditText myInput = (EditText)findViewById(R.id.editText);
                String question = myInput.getText().toString();

                EditText choice1 = (EditText)findViewById(R.id.editText2);
                String choice11 = choice1.getText().toString();
                EditText choice2 = (EditText)findViewById(R.id.editText3);
                String choice22 = choice2.getText().toString();
                EditText choice3 = (EditText)findViewById(R.id.editText4);
                String choice33 = choice3.getText().toString();
                EditText choice4 = (EditText)findViewById(R.id.editText5);
                String choice44 = choice4.getText().toString();

                RadioGroup radGroup=findViewById(R.id.radioGroup);
                int answerId = radGroup.getCheckedRadioButtonId();
                RadioButton schoice = findViewById(answerId);
                int indx = radGroup.indexOfChild(schoice);
                String title =bundle.getString("ChallengeTitle");
                String field =bundle.getString("challengeField");
                int time=bundle.getInt("challengeTime");
                int coins=bundle.getInt("challengeCoins");
                SharedPreferences sp1= multipleChoices.this.getSharedPreferences("Login", MODE_PRIVATE);
                int userId =sp1.getInt("user_id", 0);
                pres = new multiChoicesImp(multipleChoices.this);

                if(pathtype.equals("p"))
                pres.addPublicChallenge(userId,question, choice11, choice22, choice33, choice44,
                        indx, title,field,time,coins);
                else
                    pres.suggestMultiChoices(userId,question, choice11, choice22, choice33, choice44,
                            indx, title,field,time,coins);

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
    public void multiChoiceSuccess(String message) {
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = multipleChoices.this.getResources().getString(resourceId);
    /*    Noty.init(multipleChoices.this, mmm, rl,
                Noty.WarningStyle.ACTION)
                .setActionText("OK")
                .setWarningBoxBgColor("#5cb85c")
                .setWarningTappedColor("#5cb85c")
                .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
                .setWarningBoxRadius(80,80,80,80)
                .setWarningBoxMargins(15,15,15,10)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                .show();*/
     //   finish();
        new TTFancyGifDialog.Builder(multipleChoices.this)
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
    public void multiChoiceFail(String message) {
        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = multipleChoices.this.getResources().getString(resourceId);

        Noty.init(multipleChoices.this, mmm, rl,
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
