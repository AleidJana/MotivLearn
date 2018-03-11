package com.example.jana.motivlearn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jana.motivlearn.model.Timeline;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.victor.loading.newton.NewtonCradleLoading;

import static com.example.jana.motivlearn.tab2.likedposts;
import static com.example.jana.motivlearn.tab2.pres;

public class Comment extends AppCompatActivity {

    ListView simpleList;
    TextView textViewHours,textViewName,textViewLike,textViewContent,textViewComment;
    LikeButton like;
    ImageButton delete ;
    String NameList[] = {"Haifa Alabduljabbar", "Jana Aleid", "Haifa Alabduljabbar", "Jana Aleid","Haifa Alabduljabbar", "Jana Aleid"};
    String HoursList[] = {"3h", "5h", "6h", "7h", "8h", "9h"};
    String ContentList[] = {"The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post."};
    TimeLineInfo post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        SharedPreferences sp1= this.getSharedPreferences("Login", MODE_PRIVATE);
        final int uid =sp1.getInt("user_id", 0);

        post = (TimeLineInfo) getIntent().getSerializableExtra("post");
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewHours = (TextView)findViewById(R.id.textViewHours);
        textViewContent = (TextView)findViewById(R.id.textViewContent);
        textViewLike =(TextView) findViewById(R.id.textViewLikes);
        textViewComment=(TextView)findViewById(R.id.textViewComment);
        delete=(ImageButton) findViewById(R.id.ib_delete);
        if(post.getUserid()!= uid) {
            delete.setVisibility(View.INVISIBLE);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Comment.this);
                builder.setMessage("are you sure you want to delete this post?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                pres.deletepost(post.getPostid());
                               finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Post");
                alert.show();
            }
        });
        like=(LikeButton) findViewById(R.id.like_button);
        if(likedposts.contains(post.getPostid()))
        {
            like.setLiked(true);
        }
        like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                pres.addlike(post.getPostid(),uid);
                textViewLike.setText(String.valueOf(post.getLikes()+1));


            }

            @Override
            public void unLiked(LikeButton likeButton) {
              like.setLiked(true);
            }
        });
       if(post!=null) {
            textViewName.setText(post.getName());
            textViewHours.setText(post.getHours());
            textViewContent.setText(post.getContent());
            textViewLike.setText(String.valueOf(post.getLikes()));
            textViewComment.setText(String.valueOf(post.getComment()));
        }
        simpleList = (ListView) findViewById(R.id.listview);
        CommentAdapter customAdapter = new CommentAdapter(getApplicationContext(), NameList, HoursList,ContentList);
        simpleList.setAdapter(customAdapter);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Comment.this, NameList[position], Toast.LENGTH_SHORT).show();
            }
        });

    }
    
}
