package com.example.jana.motivlearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.model.puzzleImp;
import com.example.jana.motivlearn.presenter.puzzlePresenter;
import com.example.jana.motivlearn.view.puzzleView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class puzzel extends AppCompatActivity implements puzzleView {

    puzzlePresenter pre;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_puzzle);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
       // mToolbar.setTitle("Create Challenge");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pre = new puzzleImp(this);

        Button submit = findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(puzzel.this, "", "Please wait...");
                Bundle bundle=getIntent().getExtras();
                String challengTitle =bundle.getString("ChallengeTitle");
                String pathtype =bundle.getString("pathType");
                String challengType =bundle.getString("challengeType");
                String challengField =bundle.getString("challengeField");
                int challengeTime=bundle.getInt("challengeTime");
                int challengeCoins=bundle.getInt("challengeCoins");

                List<String> lines0 = new ArrayList<String>();// = getLines((EditText)findViewById(R.id.editText));
              //  JSONArray jsArray = new JSONArray(lines0);
             //   jsArray.toString();
               // Toast.makeText(puzzel.this,question, Toast.LENGTH_SHORT).show();

                String[] arr = ((EditText)findViewById(R.id.editText)).getText().toString().split("\n");
                //String full="";
                for(int i=0 ; i<arr.length ; i++)
                    lines0.add(arr[i]);

                JSONArray question = new JSONArray(lines0);

               // Toast.makeText(puzzel.this,question.toString(), Toast.LENGTH_SHORT).show();

                SharedPreferences sp1= puzzel.this.getSharedPreferences("Login", MODE_PRIVATE);
                int uid =sp1.getInt("user_id", 0);

                if(pathtype.equals("p"))
                    pre.performPuzzle(uid, question.toString(),"",challengTitle,challengType,challengField,challengeTime,challengeCoins);
                else
                    pre.suggestPuzzle(uid, question.toString(),"",challengTitle,challengType,challengField,challengeTime,challengeCoins);

                //  Intent intent = new Intent(getBaseContext(),MainActivity.class);
                //  startActivity(intent);

            }
        });
    }

    @Override
    public void puzzleSuccess(String message) {

        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = puzzel.this.getResources().getString(resourceId);
        new TTFancyGifDialog.Builder(puzzel.this)
                .setTitle("The challenge have been created successfully")
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
                        //finish();
                    }
                })
                .build();

    }

    @Override
    public void puzzleFail(String message) {

        progressDialog.dismiss();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        int resourceId = this.getResources().getIdentifier(message, "string", this.getPackageName());
        String mmm = puzzel.this.getResources().getString(resourceId);

        Noty.init(puzzel.this, mmm, rl,
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
