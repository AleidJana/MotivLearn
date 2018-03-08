package com.example.jana.motivlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    displayCodeOutputPresenter displayCodeOutputP;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeoutput);
        Ttitle = findViewById(R.id.textView);
        Tqustion = findViewById(R.id.textViewTitle);
        displayCodeOutputP = new displayCodeOutputImp(displayCodeOutput.this);
        displayCodeOutputP.peformdisplayfillBlanck(25);



    }


    @Override
    public void succesView() {
        Toast.makeText(getApplicationContext(), "View success",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void fileView() {
        Toast.makeText(getApplicationContext(), "View fail",
                Toast.LENGTH_LONG).show();

    }
    @Override
    public void setR(String responseString) {
        try {
            JSONObject obj = new JSONObject(responseString);
            String title = obj.getString("challenge_title");
            String question = obj.getString("question");
            coins = obj.getInt("coins");
            Ttitle.setText(title);
            Tqustion.setText(question);
             answer = obj.getString("answer");
            submit = findViewById(R.id.button);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uanswer = findViewById(R.id.editText);
                    String userAnswer = Uanswer.getText().toString().trim();


                        if (userAnswer.equals(answer.trim())) {
                            Toast.makeText(getApplicationContext(), "correct",
                                    Toast.LENGTH_LONG).show();
                         displayCodeOutputP.selectRank(4,25,"pass","gg",22);

                        } else {
                            Toast.makeText(getApplicationContext(), "not correct", Toast.LENGTH_LONG).show();
                        }





                }
            });






        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void correct() {
        Toast.makeText(getApplicationContext(), "ccooorreect",
                Toast.LENGTH_LONG).show();
    }
}






