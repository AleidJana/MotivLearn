package com.example.jana.motivlearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    int uid;
    int time;
    int challNum;
    int millisecondsleft;
    String field;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        TitleD =findViewById(R.id.textView);
        qustionD= findViewById(R.id.textViewTitle);
        radio=findViewById(R.id.radioG);
        submit=findViewById(R.id.button);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // finish();
                if(countDownTimer!=null)
                {
                    countDownTimer.cancel();

                }else {
                    countDownTimer=null;
                }               new TTFancyGifDialog.Builder(displaychoice.this)
                        .setTitle("Are you sure you want close this question?")
                        .setMessage("Note that you will not be able to resolve this question\n and you will not gain any coins")
                        .setPositiveBtnText("Yes")
                        .setPositiveBtnBackground("#9577bc")
                        .setNegativeBtnText("No")
                        .setNegativeBtnBackground("#c6c9ce")
                        .setGifResource(R.drawable.closequ)      //pass your gif, png or jpg
                        .isCancellable(true)
                        .OnPositiveClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                //Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                                ll.crrectAnswer(uid, challNum, "fialBack", "gg", 0 , 0,0);
                                finish();
                            }
                        })
                        .OnNegativeClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                if(countDownTimer!=null) {
                                    countDownTimer = new CountDownTimer(millisecondsleft, 1000) {

                                        public void onTick(long millisUntilFinished) {
                                            progressBar.setProgress((int) (millisUntilFinished / 1000));
                                        }

                                        public void onFinish() {
                                            progressBar.setProgress(0);
                                            ll.crrectAnswer(uid, challNum, "timeout", "gg", 0, 0,0);
                                        }
                                    }.start();
                                }
                            }
                        })
                        .build();
            }
        });
        progressBar = (ProgressBar)findViewById(R.id.progressBar2) ;

        // check the answer ...
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                progressDialog = ProgressDialog.show(displaychoice.this, "", "Please wait...");

                int selectedId = radio.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                if (answer.equals(radioButton.getText())){
                    ll.selectRank(uid,challNum,"pass","gg",3, field);
                }
                else{

                    ll.crrectAnswer(uid, challNum, "fail", "gg", 0 , 0, 0);
                }
            }
        });

        challNum = getIntent().getIntExtra("id", 0);

        SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
        uid =sp1.getInt("user_id", 0);
        ll=new displaymultiChoicesImp(displaychoice.this);
        ll.peformDisplayChoice(challNum);
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
            time= obj.getInt("time");
            field = obj.getString("field");
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
            progressBar.setMax(10);
            progressBar.setProgress(10);
            time=time*1000;
            countDownTimer=new CountDownTimer(time, 1000) {

                public void onTick(long millisUntilFinished) {
                    millisecondsleft=(int)millisUntilFinished;
                    progressBar.setProgress((int)(millisUntilFinished / 1000));
                }
                public void onFinish() {
                    progressBar.setProgress(0);
                    ll.crrectAnswer(uid, challNum, "timeout", "gg", 0 , 0, 0);
                }
            }.start();

            answer = obj2.getString("answer");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void correct(int coinns, String status, int badge) {
        if(progressDialog!=null&&progressDialog.isShowing()) {
            progressDialog.dismiss();
        }        String msg1 = "", msg2="";
        int dr1 = 0;
        if(status.equals("pass"))
        {
            msg1 = "Congratulations";
            msg2= "You Have got "+coinns+" Coins";
            dr1 = getResources().getIdentifier("hgif1", "drawable", getPackageName());
            errormessage(msg1,msg2,dr1,badge);


        }
        if(status.equals("fail"))
        {
            msg1 = "Unfortunately";
            msg2= "you didn't get any coins";
            dr1 = getResources().getIdentifier("losse", "drawable", getPackageName());
            errormessage(msg1,msg2,dr1,badge);

        }
        if(status.equals("timeout"))
        {
            msg1 = "OOPS!";
            msg2= "Question time is finished";
            dr1 = getResources().getIdentifier("time", "drawable", getPackageName());
            errormessage(msg1,msg2,dr1,badge);
        }
        if(status.equals("fialBack"))
        {
        }



    }
    public void errormessage (String msg1,String msg2,int dr1, final int badge)
    {
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
                        if(badge!=0)
                        {
                            int badgeid = getResources().getIdentifier("badgec"+badge, "drawable", getPackageName());
                            new TTFancyGifDialog.Builder(displaychoice.this)
                                    .setTitle("Good Job")
                                    .setMessage("Congratulations, \n you have got a new badge")
                                    .setPositiveBtnText("Ok")
                                    .setPositiveBtnBackground("#9577bc")
                                    .setGifResource(badgeid)      //pass your gif, png or jpg
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
                        else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nextFrag", "cha");
                            startActivity(intent);
                        }
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(this,"can't go back", Toast.LENGTH_SHORT).show();
    }



}
