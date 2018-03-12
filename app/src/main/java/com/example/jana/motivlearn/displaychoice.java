package com.example.jana.motivlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
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

        final int challNum = getIntent().getIntExtra("id", 0);

        SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
        final int uid =sp1.getInt("user_id", 0);
        ll=new displaymultiChoicesImp(displaychoice.this);
        ll.peformDisplayChoice(challNum);


        // check the answer ...
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radio.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                if (answer.equals(radioButton.getText())){
                    ll.selectRank(uid,challNum,"pass","gg",3);


                }
                else{
               /*     Toast.makeText(displaychoice.this, "foooolseee",
                            Toast.LENGTH_LONG).show();*/
                    ll.crrectAnswer(uid, challNum, "fail", "gg", 0 , 0);
                }

            }
        });



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
    public void correct(int coinns, String status) {

        String msg1, msg2;
        int dr1 = 0;
        if(status.equals("pass"))
        {
            msg1 = "Congratulations";
            msg2= "You Have got "+coinns+" Coins";
            dr1 = getResources().getIdentifier("hgif1", "drawable", getPackageName());


        }
        else
        {
            msg1 = "Unfortunately";
            msg2= "you didn't get any coins";
            dr1 = getResources().getIdentifier("losse", "drawable", getPackageName());
        }

        new TTFancyGifDialog.Builder(displaychoice.this)
                .setTitle(msg1)
                .setMessage(msg2)
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc")
                .setGifResource(dr1)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("nextFrag", "cha");
                        startActivity(intent);
                    }
                })
                .build();

    }






}
