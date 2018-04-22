package com.example.jana.motivlearn;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jana.motivlearn.model.Addpost;
import com.example.jana.motivlearn.model.Timeline;
import com.example.jana.motivlearn.presenter.TimelinePresenter;
import com.example.jana.motivlearn.view.AddpostView;
import com.example.jana.motivlearn.view.TimelineView;

import static com.example.jana.motivlearn.tab2.InfoList;
import static com.example.jana.motivlearn.tab2.pres;
import static java.lang.String.valueOf;

public class AddPost extends AppCompatActivity implements AddpostView {
Button addpost;
EditText message;
    Addpost pres;
    ProgressDialog progressDialog;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        SharedPreferences sp1= this.getSharedPreferences("Login", MODE_PRIVATE);
        final int uid =sp1.getInt("user_id", 0);
        pres = new Addpost(AddPost.this);
        addpost=(Button)findViewById(R.id.addpost);
        message=(EditText)findViewById(R.id.post);
        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().toString().equals(null)||message.getText().toString().equals("")) {
                    Toast.makeText(AddPost.this,"Please Enter text to post it",Toast.LENGTH_LONG).show();

                }else {
                    String m = message.getText().toString();
                    pres.addPost(m, uid);
                    progressDialog = ProgressDialog.show(AddPost.this, "", "Please wait...");

                }
            }
        });
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Add Post");
        mToolbar.setTitleTextColor(R.color.white);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void addPostSuccess() {
      // pres.notifyDataSetChanged();
                // dismiss the progress dialog
                progressDialog.dismiss();
                //Toast.makeText(AddPost.this,"Your post added successfully",Toast.LENGTH_LONG).show();

        this.finish();
    }
}
