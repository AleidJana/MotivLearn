package com.example.jana.motivlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
displayfillBlanckPresenter displayfillBlanckP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillblank);
        Ttitle = findViewById(R.id.textView);
        Tqustion = findViewById(R.id.textViewTitle);
        displayfillBlanckP = new displayfillBlanckImp(displayfillBlanck.this);
        displayfillBlanckP.peformdisplayfillBlanck(34);

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
            String Allanswer = obj.getString("answer");
            JSONObject obj2 = new JSONObject(Allanswer);
            count = obj2.length();
            answer1 = new String[obj2.length()];
           for (int i = 1; i <= obj2.length() ; i++) {
               int j=i-1;
             answer1[j]=obj2.getString(""+j);
            }

            submit = findViewById(R.id.button);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uanswer = findViewById(R.id.editText);
                    String userAnswer = Uanswer.getText().toString();
                    String[] answer = userAnswer.split(",");

                    if (answer.length == answer1.length) {
                        boolean flag = true;
                        for (int l = 0; l < answer.length; l++) {
                            if ((answer[l]).trim().equals(answer1[l].trim())){
                                flag = true;
                            }
                            else{

                                flag=false;
                                break;
                            }
                        }
                        if (flag) {
                            Toast.makeText(getApplicationContext(), "correct",
                                    Toast.LENGTH_LONG).show();
                            displayfillBlanckP.selectRank(4,34,"pass","gg",22);

                        } else {
                            Toast.makeText(getApplicationContext(), "not correct", Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "small or long",
                                Toast.LENGTH_LONG).show();
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

