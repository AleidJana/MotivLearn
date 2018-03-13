package com.example.jana.motivlearn;

import android.app.Activity;
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
                String VedioLink = VedioLinkF.getText().toString();
                SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
                int uid =sp1.getInt("user_id", 0);
                mSuggestVedio.performSuggestVedio(VedioLink, uid);
            }
        });
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // mToolbar.setTitle("");
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
    public void SuggestVedioValidations() {
        new TTFancyGifDialog.Builder(suggestVedio.this)
                .setTitle("suggested successfully")
                .setMessage("Thank you, \n your video has been recorded \n and you have got 5 Coins")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc")
                .setGifResource(R.drawable.hgif3)      //pass your gif, png or jpg
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

    @Override
    public void wrongFormat() {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.myLayout) ;
        Noty.init(suggestVedio.this, "Enter the link with http:// or https:// format", rl,
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
