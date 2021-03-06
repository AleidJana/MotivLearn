package com.example.jana.motivlearn;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.example.jana.motivlearn.model.recordAchievementImp;
import com.example.jana.motivlearn.presenter.recordAchievementPresenter;
import com.example.jana.motivlearn.view.recordAchievementView;
import com.google.zxing.Result;

import java.util.Scanner;

public class RecordAchievement extends AppCompatActivity implements recordAchievementView {
    private CodeScanner mCodeScanner;
    private String res;
    private recordAchievementPresenter pres;
    private int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_achievement);
        SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
        uid=sp1.getInt("user_id", 0);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Recode Extra Achievement");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        // Use builder
        mCodeScanner = CodeScanner.builder()
                .formats(CodeScanner.ALL_FORMATS)
                //List<BarcodeFormat>
                .autoFocus(true).autoFocusMode(AutoFocusMode.SAFE).autoFocusInterval(2000L)
                .flash(false)
                .onDecoded(new DecodeCallback() {
                    @Override
                    public void onDecoded(@NonNull final Result result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                res = result.getText().trim();
                                if(res.equals("MotivLearn"))
                                {
                                    pres= new recordAchievementImp(RecordAchievement.this);
                                    pres.updateUserCoins(uid);
                                }
                                else
                                    incorrectBarCode();
                            }});
                    }})
                //error callback
                .onError(new ErrorCallback() {
                    @Override
                    public void onError(@NonNull final Exception error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RecordAchievement.this, error.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }}).build(this, scannerView);
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void addBadge(final boolean b) {

        new TTFancyGifDialog.Builder(RecordAchievement.this)
                .setTitle("Scanned successfully")
                .setMessage("Good job, \n your achievement have been recorded \n and you have got 10 Coins")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc")
                .setGifResource(R.drawable.hgif3)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        if(b) {
                            new TTFancyGifDialog.Builder(RecordAchievement.this)
                                    .setTitle("Good Job")
                                    .setMessage("Congratulations, \n you have got 'Learner' badge \n because this is your" +
                                            " 5th recorded achievement")
                                    .setPositiveBtnText("Ok")
                                    .setPositiveBtnBackground("#9577bc")
                                    .setGifResource(R.drawable.badgec4)      //pass your gif, png or jpg
                                    .isCancellable(true)
                                    .OnPositiveClicked(new TTFancyGifDialogListener() {
                                        @Override
                                        public void OnClick() {
                                            //Toast.makeText(WatchVideo.this,"Ok",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })
                                    .build();
                        }
                        else
                            finish();
                    }
                })
                .build();

    }

    public void incorrectBarCode()
    {
        new TTFancyGifDialog.Builder(RecordAchievement.this)
                .setTitle("Incorrect QR code")
                .setMessage("Check the correct QR code, then try again")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc")
                .setGifResource(R.drawable.hgif4)
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                }).build();
    }
}

