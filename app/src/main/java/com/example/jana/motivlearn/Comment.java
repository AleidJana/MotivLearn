package com.example.jana.motivlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

public class Comment extends AppCompatActivity {

    ListView simpleList;
    TextView textViewHours,textViewName,textViewLike,textViewContent,textViewComment;
    LikeButton like;
    ImageButton delete ;
    String NameList[] = {"Haifa Alabduljabbar", "Jana Aleid", "Haifa Alabduljabbar", "Jana Aleid","Haifa Alabduljabbar", "Jana Aleid"};
    String HoursList[] = {"3h", "5h", "6h", "7h", "8h", "9h"};
    String ContentList[] = {"The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post.", "51. The system shall be able to give the post’s owner 1 coin per like on her post."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewHours = (TextView)findViewById(R.id.textViewHours);
        textViewContent = (TextView)findViewById(R.id.textViewContent);
        textViewLike =(TextView) findViewById(R.id.textViewLikes);
        textViewComment=(TextView)findViewById(R.id.textViewComment);
        delete=(ImageButton) findViewById(R.id.ib_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Comment.this ,"delete button",Toast.LENGTH_LONG).show();

            }
        });
        like=(LikeButton) findViewById(R.id.like_button);
        like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
        textViewName.setText("jana s aleid");
        textViewHours.setText("10h");
        textViewContent.setText("dtfguhijokofgwyebuhnoierftgyhiufdeftgyuhuirftygyuhiufdehj");
        textViewLike.setText("1263");
        textViewComment.setText("5568");

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
