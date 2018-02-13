package com.example.jana.motivlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.model.multiChoicesImp;
import com.example.jana.motivlearn.presenter.multiChoicesPresenter;
import com.example.jana.motivlearn.view.multiChoicesView;

public class multipleChoices extends AppCompatActivity implements multiChoicesView {
    private multiChoicesPresenter pres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choices);


        Button submit = (Button)findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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

                Bundle bundle=getIntent().getExtras();
                String title =bundle.getString("ChallengeTitle");
                String field =bundle.getString("challengeField");
                int time=bundle.getInt("challengeTime");
                int coins=bundle.getInt("challengeCoins");

                pres = new multiChoicesImp(multipleChoices.this);
                pres.addPublicChallenge(2,question, choice11, choice22, choice33, choice44,
                        indx, title,field,time,coins);

            }
        });

    }


    @Override
    public void multiChoiceSuccess(String message) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = multipleChoices.this.getResources().getString(resourceId);
        Noty.init(multipleChoices.this, mmm, rl,
                Noty.WarningStyle.ACTION)
                .setActionText("OK")
                .setWarningBoxBgColor("#5cb85c")
                .setWarningTappedColor("#5cb85c")
                .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
                .setWarningBoxRadius(80,80,80,80)
                .setWarningBoxMargins(15,15,15,10)
                .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
                .show();

    }

    @Override
    public void multiChoiceFail(String message) {
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
