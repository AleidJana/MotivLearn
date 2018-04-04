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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.jana.motivlearn.model.displayCodeOutputImp;
import com.example.jana.motivlearn.presenter.displayCodeOutputPresenter;
import com.example.jana.motivlearn.view.displayCodeOutputView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by reemaibrahim on 18/02/2018 AD.
 */

public class displayCodeOutput extends AppCompatActivity implements displayCodeOutputView {
    TextView Ttitle , Tqustion ;
    EditText Uanswer;
    Button submit ;
    int count ,  coins;
    String  answer;
    String field;
    ProgressBar progressBar;
    int time;
    ProgressDialog progressDialog;
    CountDownTimer countDownTimer;
    displayCodeOutputPresenter displayCodeOutputP;
    int millisecondsleft;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeoutput);
        Ttitle = findViewById(R.id.textView);
        Tqustion = findViewById(R.id.textViewTitle);

        final int challNum = getIntent().getIntExtra("id", 0);

        SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
        final int uid =sp1.getInt("user_id", 0);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2) ;

       // Toast.makeText(this,getIntent().getIntExtra("id", 0)+"", Toast.LENGTH_SHORT).show();
        displayCodeOutputP = new displayCodeOutputImp(displayCodeOutput.this);
        displayCodeOutputP.peformdisplayfillBlanck(challNum);
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
                }               new TTFancyGifDialog.Builder(displayCodeOutput.this)
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
                                displayCodeOutputP.crrectAnswer(uid, challNum, "fialBack", "gg", 0 , 0, 0);
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
                                            displayCodeOutputP.crrectAnswer(uid, challNum, "timeout", "gg", 0, 0, 0);
                                        }
                                    }.start();
                                }
                            }
                        })
                        .build();
            }
        });
    }


    @Override
    public void fileView() {
        Toast.makeText(getApplicationContext(), "View fail",
                Toast.LENGTH_LONG).show();

    }
    @Override
    public void setR(String responseString) {
        try {
          //  Toast.makeText(this,"SetR", Toast.LENGTH_SHORT).show();
            final int challNum = getIntent().getIntExtra("id", 0);

            SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
            final int uid =sp1.getInt("user_id", 0);

            JSONObject obj = new JSONObject(responseString);
            String title = obj.getString("challenge_title");
            String question = obj.getString("question");
            coins = obj.getInt("coins");
            time= obj.getInt("time");
            Ttitle.setText(title);
            Tqustion.setText(question);
             answer = obj.getString("answer");
            field = obj.getString("field");
            progressBar.setMax(time);
            progressBar.setProgress(time);
            time=time*1000;
            countDownTimer = new CountDownTimer(time, 1000) {

                public void onTick(long millisUntilFinished) {
                    millisecondsleft=(int)millisUntilFinished;
                    progressBar.setProgress((int) (millisUntilFinished / 1000));
                }
                public void onFinish() {
                    progressBar.setProgress(0);
                    //progressDialog.dismiss();
                    displayCodeOutputP.crrectAnswer(uid, challNum, "timeout", "gg", 0 , 0, 0);
                }
            }.start();

            submit = findViewById(R.id.button);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    countDownTimer.cancel();
                    progressDialog = ProgressDialog.show(displayCodeOutput.this, "", "Please wait...");

                    Uanswer = findViewById(R.id.editText);
                    String userAnswer = Uanswer.getText().toString().trim();


                        if (userAnswer.equals(answer.trim())) {
                         /*   Toast.makeText(getApplicationContext(), "correct",
                                    Toast.LENGTH_LONG).show();*/
                         displayCodeOutputP.selectRank(uid,challNum,"pass","gg",3, field);

                        } else {
                           // Toast.makeText(getApplicationContext(), "not correct", Toast.LENGTH_LONG).show();
                            displayCodeOutputP.crrectAnswer(uid, challNum, "fail", "gg", 0 , 0, 0);
                        }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void correct(int coinns, String status, final int badge) {
        if(progressDialog!=null&&progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        String msg1="", msg2="";
        int dr1 = 0;
        if(status.equals("pass"))
        {
            msg1 = "Congratulations";
            msg2= "You Have got "+coinns+" Coins";
            dr1 = getResources().getIdentifier("hgif1", "drawable", getPackageName());
        }
        if(status.equals("fail"))
        {
            msg1 = "Unfortunately";
            msg2= "you didn't get any coins";
            dr1 = getResources().getIdentifier("losse", "drawable", getPackageName());
        }
        if(status.equals("timeout"))
        {
            msg1 = "OOPS!";
            msg2= "Question time is finished";
            dr1 = getResources().getIdentifier("time", "drawable", getPackageName());
        }

        new TTFancyGifDialog.Builder(displayCodeOutput.this)
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
                            new TTFancyGifDialog.Builder(displayCodeOutput.this)
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






