package com.example.jana.motivlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.model.CreateChallengeImp;
import com.example.jana.motivlearn.presenter.CreateChallengePresenter;
import com.example.jana.motivlearn.view.CreateChallengeView;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

public class suggestQuestion extends AppCompatActivity implements CreateChallengeView {
    EditText ChallengeTitleF;
    Spinner challengeTypeF , challengeFieldF;
    ScrollableNumberPicker ScrollableNumberPickerTime ,ScrollableNumberPickerCoins;
    Button Next ;
    CreateChallengePresenter  CreateChallengeView = new CreateChallengeImp(suggestQuestion.this);
    String challengeType = "";
    String ChallengeTitle="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        ChallengeTitleF = findViewById(R.id.editText3);
        challengeTypeF =findViewById(R.id.spinner);
        challengeFieldF=findViewById(R.id.spinner2);
        ScrollableNumberPickerTime=findViewById(R.id.snp_vertical);
        ScrollableNumberPickerCoins=findViewById(R.id.snp_vertical2);
        Next=findViewById(R.id.buttonCreate);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChallengeTitle = ChallengeTitleF.getText().toString();

                CreateChallengeView.pformvalidet(ChallengeTitle);

            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Spinner grade1 = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> grade = ArrayAdapter.createFromResource(
                this,
                R.array.planets_array,
                android.R.layout.simple_spinner_item);
//         Specify the layout to use when the list of choices appears
        grade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        challengeTypeF.setAdapter(grade);



        //  Spinner adapter1 = (Spinner) findViewById(R.id.spinner2);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.planets_array2,
                android.R.layout.simple_spinner_item);
//         Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        challengeFieldF.setAdapter(adapter);


    }

    @Override
    public void CreateChallengeValidations(String message) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = suggestQuestion.this.getResources().getString(resourceId);

        Noty.init(suggestQuestion.this, mmm, rl,
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
    public void CreateChallengeSuccess()
    {
        challengeType = challengeTypeF.getSelectedItem().toString();
        String challengeField = challengeFieldF.getSelectedItem().toString();
        int challengeTime=ScrollableNumberPickerTime.getValue();
        int challengeCoins=ScrollableNumberPickerCoins.getValue();

        Intent intent=null;

        if(challengeType.equals("Multiple choices"))
            intent= new Intent(getBaseContext(),multipleChoices.class);
        else if(challengeType.equals("Code output"))
            intent= new Intent(getBaseContext(),CodeOutput.class);
        else if(challengeType.equals("Puzzle"))
            intent= new Intent(getBaseContext(),puzzel.class);
        else if(challengeType.equals("Fill blanks"))
            intent= new Intent(getBaseContext(),fillBlank.class);

        intent.putExtra("pathType", "s");
        intent.putExtra("ChallengeTitle",ChallengeTitle);
        intent.putExtra("challengeType",challengeType);
        intent.putExtra("challengeField",challengeField);
        intent.putExtra("challengeTime",challengeTime);
        intent.putExtra("challengeCoins",challengeCoins);
        startActivity(intent);

        // finish();
    }

}
