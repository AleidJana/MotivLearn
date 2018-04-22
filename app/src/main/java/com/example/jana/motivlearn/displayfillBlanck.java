package com.example.jana.motivlearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.example.jana.motivlearn.model.displayfillBlanckImp;
import com.example.jana.motivlearn.presenter.displayfillBlanckPresenter;
import com.example.jana.motivlearn.view.displayfillBlanckView;
import com.google.zxing.common.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class displayfillBlanck extends AppCompatActivity  implements displayfillBlanckView {
TextView Ttitle , Tqustion ;
EditText Uanswer;
Button submit ;
int count ,  coins;
String [] answer1;
ProgressBar progressBar;
    ProgressDialog progressDialog;
    int millisecondsleft;
    CountDownTimer countDownTimer;
int time;
    int challNum;
    String field;
    int uid;
displayfillBlanckPresenter displayfillBlanckP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillblank);
         challNum = getIntent().getIntExtra("id", 0);

        SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
        uid =sp1.getInt("user_id", 0);

        Ttitle = findViewById(R.id.textView);
        Tqustion = findViewById(R.id.textViewTitle);
        Uanswer = findViewById(R.id.editText);
        submit = findViewById(R.id.button);

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
                }               new TTFancyGifDialog.Builder(displayfillBlanck.this)
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
                                displayfillBlanckP.crrectAnswer(uid, challNum, "fialBack", "gg", 0 , 0,0);
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
                                            displayfillBlanckP.crrectAnswer(uid, challNum, "timeout", "gg", 0, 0,0);
                                        }
                                    }.start();
                                }
                            }
                        })
                        .build();
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.progressBar2) ;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                progressDialog = ProgressDialog.show(displayfillBlanck.this, "", "Please wait...");
                String userAnswer = Uanswer.getText().toString();
                String[] answer = userAnswer.split(",");
             //   Toast.makeText(displayfillBlanck.this, answer.length+"--"+answer1.length,
               //         Toast.LENGTH_LONG).show();

                if (answer.length == answer1.length) {
                    boolean flag = true;
                    for (int l = 0; l < answer.length; l++) {
                        if ((answer[l]).trim().toLowerCase().equals(answer1[l])){
                            flag = true;
                        }
                        else{
                            flag=false;
                            break;
                        }
                    }
                    if (flag) {
                        //Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_LONG).show();
                        displayfillBlanckP.selectRank(uid,challNum,"pass","gg",3, field);
                    } else {
                        // Toast.makeText(getApplicationContext(), "not correct", Toast.LENGTH_LONG).show();
                        displayfillBlanckP.crrectAnswer(uid, challNum, "fail", "gg", 0 , 0,0);
                    }

                }else {
                  //  Toast.makeText(displayfillBlanck.this, "small or long",
                    //        Toast.LENGTH_LONG).show();
                }
            }
        });

        displayfillBlanckP = new displayfillBlanckImp(displayfillBlanck.this);
        displayfillBlanckP.peformdisplayfillBlanck(challNum);
    }

    @Override
    public void succesView() {
      //  Toast.makeText(getApplicationContext(), "View success",
             //   Toast.LENGTH_LONG).show();
    }

    @Override
    public void fileView() {
        if(progressDialog!=null&&progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    Toast.makeText(getApplicationContext(), "View fail",Toast.LENGTH_LONG).show();
    }

    @Override
    public void setR(String responseString) {
        try {
            JSONObject obj = new JSONObject(responseString);
            String title = obj.getString("challenge_title");
            String question = obj.getString("question");
            time= obj.getInt("time");
            coins = obj.getInt("coins");
            Ttitle.setText(title);
            field = obj.getString("field");
            Tqustion.setText(question);
            String Allanswer = obj.getString("answer");
            JSONObject obj2 = new JSONObject(Allanswer);
            count = obj2.length();
            String hint = "answer";
            answer1 = new String[obj2.length()];
           for (int i = 1; i <= obj2.length() ; i++) {
               int j=i-1;
             answer1[j]=(obj2.getString(""+j)).trim().toLowerCase();
               hint=hint+i+",";
           }
            Uanswer.setHint(hint);
            progressBar.setMax(time);
            progressBar.setProgress(time);
            time=time*1000;
            countDownTimer= new CountDownTimer(time, 1000) {

                public void onTick(long millisUntilFinished) {
                   // Toast.makeText(displayfillBlanck.this,"seconds remaining: " + millisUntilFinished / 1000,Toast.LENGTH_SHORT).show();
                    millisecondsleft=(int)millisUntilFinished;
                    progressBar.setProgress((int) (millisUntilFinished / 1000));
                }
                public void onFinish() {
                    progressBar.setProgress(0);
                    displayfillBlanckP.crrectAnswer(uid, challNum, "timeout", "gg", 0 , 0,0);
                }
            }.start();

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
        new TTFancyGifDialog.Builder(displayfillBlanck.this)
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
                            new TTFancyGifDialog.Builder(displayfillBlanck.this)
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

