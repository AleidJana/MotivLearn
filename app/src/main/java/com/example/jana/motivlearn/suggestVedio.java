package com.example.jana.motivlearn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.emredavarci.noty.Noty;
import com.example.jana.motivlearn.model.SuggestVedioImp;
import com.example.jana.motivlearn.presenter.SuggestVedioPresenter;
import com.example.jana.motivlearn.view.SuggestVedioView;

public class suggestVedio extends Activity implements SuggestVedioView {

    EditText VedioLinkF ;
    Button SubmitB ;
    SuggestVedioPresenter mSuggestVedio;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_vedio);
        VedioLinkF = findViewById(R.id.editText7);
        SubmitB=findViewById(R.id.button2);
        mSuggestVedio = new SuggestVedioImp(suggestVedio.this);
        SubmitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(suggestVedio.this, "", "Please wait...");
                String VedioLink = VedioLinkF.getText().toString();
                SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
                int uid =sp1.getInt("user_id", 0);
                mSuggestVedio.performSuggestVedio(VedioLink, uid);
            }
        });
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Suggest Video");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void suggestVrdioError() {
        progressDialog.dismiss();

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        Noty.init(suggestVedio.this, "fill the field please", rl,
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
    public void addBadgySuccess() {
      //  Toast.makeText(getApplicationContext(),"geting educator success",Toast.LENGTH_LONG).show();

    }

    @Override
    public void SuggestVedioValidations(final boolean badge) {
        progressDialog.dismiss();
        new TTFancyGifDialog.Builder(suggestVedio.this)
                .setTitle("suggested successfully")
                .setMessage("Thank you, \n your video has been recorded \n and you have got 5 Coins")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc")
                .setGifResource(R.drawable.hgif1)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        //Toast.makeText(WatchVideo.this,"Ok",Toast.LENGTH_SHORT).show();
                        if(badge) {
                            new TTFancyGifDialog.Builder(suggestVedio.this)
                                    .setTitle("Good Job")
                                    .setMessage("Congratulations, \n you have got 'Edugator' badge \n because this is your" +
                                            " 5th suggested video")
                                    .setPositiveBtnText("Ok")
                                    .setPositiveBtnBackground("#9577bc")
                                    .setGifResource(R.drawable.badgec13)      //pass your gif, png or jpg
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

    @Override
    public void wrongFormat(int type) {
        progressDialog.dismiss();
        String message = null;
        if(type==2)
        {
            message="Enter the link with http:// or https:// format";
        }else
        if (type==1)
        {
            message="Enter the link with MP4 format";
        }
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        Noty.init(suggestVedio.this, message, rl,
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
