package com.example.jana.motivlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jana.motivlearn.model.displaymultiChoicesImp;
import com.example.jana.motivlearn.presenter.displayChoicePresenter;
import com.example.jana.motivlearn.view.displayChoiceView;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.PendingIntent.getActivity;

public class displaychoice extends AppCompatActivity implements displayChoiceView {
TextView TitleD , qustionD ;
Button submit;
RadioGroup radio ;
RadioButton radioButton;
displayChoicePresenter ll;
String answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        TitleD =findViewById(R.id.textView);
        qustionD= findViewById(R.id.textViewTitle);
        radio=findViewById(R.id.radioG);
        submit=findViewById(R.id.button);
        ll=new displaymultiChoicesImp(displaychoice.this);
        ll.peformDisplayChoice(52);


        // check the answer ...
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radio.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                if (answer.equals(radioButton.getText())){
                    ll.selectRank(4,52,"pass","gg",22);


                }
                else{
                    Toast.makeText(displaychoice.this, "foooolseee",
                            Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    @Override
    //will be delated
    public void displaySuccess() {

        Toast.makeText(displaychoice.this, "wowww",
                Toast.LENGTH_LONG).show();

    }

    @Override
    //will be delated
    public void displayFailed() {

    }

    // display ...
    public void setR(String res){

        try {

            JSONObject obj = new JSONObject(res);
            String qustion=obj.getString("question");
            String challenge_title=obj.getString("challenge_title");
            String allChoise = obj.getString("answer");
            JSONObject obj2 = new JSONObject(allChoise);
            int length= obj2.length();
            TitleD.setText(challenge_title);
            qustionD.setText(qustion);

            for (int i = 1; i <= length-1 ; i++) {
                RadioButton rbn= new RadioButton(this);
                rbn.setId(i);
                String c=obj2.getString("choice"+i);
                rbn.setText(c);
                radio.addView(rbn);

            }
             answer = obj2.getString("answer");




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void correct() {
        Toast.makeText(displaychoice.this, "good joooobbb",
                Toast.LENGTH_LONG).show();
    }






}
