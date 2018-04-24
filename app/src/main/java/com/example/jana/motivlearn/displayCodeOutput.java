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
    TextView Ttitle, Tqustion;
    EditText Uanswer;
    Button submit;
    int count, coins;
    String answer;
    String field;
    ProgressBar progressBar;
    int time;
    ProgressDialog progressDialog;
    CountDownTimer countDownTimer;
    displayCodeOutputPresenter displayCodeOutputP;
    int millisecondsleft;
    int challNum;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeoutput);
        Ttitle = findViewById(R.id.textView);
        Tqustion = findViewById(R.id.textViewTitle);
        challNum = getIntent().getIntExtra("id", 0);
        SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);
        uid = sp1.getInt("user_id", 0);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        displayCodeOutputP = new displayCodeOutputImp(displayCodeOutput.this);
        displayCodeOutputP.peformdisplayfillBlanck(challNum);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // finish();
                if (countDownTimer != null) {
                    countDownTimer.cancel();

                } else {
                    countDownTimer = null;
                    closeQuestion();
                }
            }
        });
    }

    @Override
    public void fileView() {
        Toast.makeText(getApplicationContext(), "View fail", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setR(String responseString) {
        try {
            final int challNum = getIntent().getIntExtra("id", 0);
            JSONObject obj = new JSONObject(responseString);
            String title = obj.getString("challenge_title");
            String question = obj.getString("question");
            coins = obj.getInt("coins");
            time = obj.getInt("time");
            Ttitle.setText(title);
            Tqustion.setText(question);
            answer = obj.getString("answer");
            field = obj.getString("field");
            //Set The Counter And Start When The Question Display
            progressBar.setMax(time);
            progressBar.setProgress(time);
            time = time * 1000;
            countDownTimer = new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    millisecondsleft = (int) millisUntilFinished;
                    progressBar.setProgress((int) (millisUntilFinished / 1000));
                }
                public void onFinish() {
                    progressBar.setProgress(0);
                    //Send The Result Status("timeout") To Register In The Database
                    displayCodeOutputP.crrectAnswer(uid, challNum, "timeout", "gg", 0, 0, 0);
                }
            }.start();
            submit = findViewById(R.id.button);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Stop the Timer
                    countDownTimer.cancel();
                    progressDialog = ProgressDialog.show(displayCodeOutput.this, "", "Please wait...");
                    Uanswer = findViewById(R.id.editText);
                    String userAnswer = Uanswer.getText().toString().trim();
                    //Chick The User Answer If Correct Or Not
                    if (userAnswer.equals(answer.trim())) {
                        //If The Answer Was Correct  Send The Result Status("pass") To Register In The Database
                        displayCodeOutputP.selectRank(uid, challNum, "pass", "gg", 3, field);
                    } else {
                        //If The Answer Was Incorrect Send The Result Status("pass") To Register In The Database
                        displayCodeOutputP.crrectAnswer(uid, challNum, "fail", "gg", 0, 0, 0);
                    }
                }
            });
        } catch (JSONException e) {e.printStackTrace();}}

    @Override
    public void correct(int coinns, String status, final int badge) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        //title : Alert Title Used In TTFancyGifDialog
        // message : Message Will Displayed In TTFancyGifDialog
        // drawableResources : GIF Drawable Will Used In TTFancyGifDialog
        String title = "", message = "";
        int drawableResources = 0;
        if (status.equals("pass")) {
            title = "Congratulations";
            message = "You Have got " + coinns + " Coins";
            drawableResources = getResources().getIdentifier("hgif1", "drawable", getPackageName());
        }
        if (status.equals("fail")) {
            title = "Unfortunately";
            message = "you didn't get any coins";
            drawableResources = getResources().getIdentifier("losse", "drawable", getPackageName());
        }
        if (status.equals("timeout")) {
            title = "OOPS!";
            message = "Question time is finished";
            drawableResources = getResources().getIdentifier("time", "drawable", getPackageName());
        }
        new TTFancyGifDialog.Builder(displayCodeOutput.this)
                .setTitle(title).setMessage(message).setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc").setGifResource(drawableResources).isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        setBadge(badge);
                    }
                }).build();
    }

    @Override
    public void onBackPressed() {
    }

    public void closeQuestion()
    {
        new TTFancyGifDialog.Builder(displayCodeOutput.this)
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

    public  void setBadge(int badge)
    {
        if (badge != 0) {
            int badgeid = getResources().getIdentifier("badgec" + badge, "drawable", getPackageName());
            new TTFancyGifDialog.Builder(displayCodeOutput.this)
                    .setTitle("Good Job").setMessage("Congratulations, \n you have got a new badge")
                    .setPositiveBtnText("Ok").setPositiveBtnBackground("#9577bc")
                    .setGifResource(badgeid).isCancellable(true)
                    .OnPositiveClicked(new TTFancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nextFrag", "cha");
                            startActivity(intent);
                        }}).build();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("nextFrag", "cha");
            startActivity(intent);
        }
    }
}






