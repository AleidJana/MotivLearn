package com.example.jana.motivlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jana.motivlearn.model.CreateChallengeImp;
import com.example.jana.motivlearn.presenter.CreateChallengePresenter;
import com.example.jana.motivlearn.view.CreateChallengeView;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

public class createChallenge extends AppCompatActivity implements CreateChallengeView {
    EditText ChallengeTitleF;
    Spinner challengeTypeF , challengeFieldF;
    ScrollableNumberPicker ScrollableNumberPickerTime ,ScrollableNumberPickerCoins;
    Button Next ;
    CreateChallengePresenter  CreateChallengeView = new CreateChallengeImp(createChallenge.class);


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
                String ChallengeTitle = ChallengeTitleF.getText().toString();
                String challengeType = challengeTypeF.getSelectedItem().toString();
                String challengeField = challengeFieldF.getSelectedItem().toString();
                int challengeTime=ScrollableNumberPickerTime.getValue();
                int challengeCoins=ScrollableNumberPickerCoins.getValue();
                CreateChallengeView.pformvalidet(ChallengeTitle);
                Intent intent= new Intent(getBaseContext(),CodeOutput.class);
                intent.putExtra("ChallengeTitle",ChallengeTitle);
                intent.putExtra("challengeType",challengeType);
                intent.putExtra("challengeField",challengeField);
                intent.putExtra("challengeTime",challengeTime);
                intent.putExtra("challengeCoins",challengeCoins);
                startActivity(intent);

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
    public void CreateChallengeValidations() {
        Toast.makeText(getApplicationContext(),"Enter all the field",Toast.LENGTH_LONG).show();

    }

    @Override
    public void CreateChallengeError() {
        Toast.makeText(getApplicationContext()," Error found",Toast.LENGTH_LONG).show();

    }

    @Override
    public void CreateChallengeSuccess() {
        Toast.makeText(getApplicationContext()," success :))",Toast.LENGTH_LONG).show();

    }
}
