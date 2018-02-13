package com.example.jana.motivlearn;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                mSuggestVedio.performSuggestVedio(VedioLink);
            }
        });
    }

    @Override
    public void suggestVrdioError() {
        Toast.makeText(getApplicationContext(),"Error was found",Toast.LENGTH_LONG).show();

    }

    @Override
    public void addBadgySuccess() {
        Toast.makeText(getApplicationContext(),"geting educator success",Toast.LENGTH_LONG).show();

    }

    @Override
    public void SuggestVedioValidations() {
        LinearLayout linlay = findViewById(R.id.errorlay);
        linlay.setVisibility(View.VISIBLE);

        TextView eror2 = findViewById(R.id.textView8);
        eror2.setVisibility(View.GONE);

        TextView eror = findViewById(R.id.textView7);
        eror.setVisibility(View.VISIBLE);


    }

    @Override
    public void wrongFormat() {
        LinearLayout linlay = findViewById(R.id.errorlay);
        linlay.setVisibility(View.VISIBLE);

        TextView eror = findViewById(R.id.textView7);
        eror.setVisibility(View.GONE);

        TextView eror2 = findViewById(R.id.textView8);
        eror2.setVisibility(View.VISIBLE);

    }

}
