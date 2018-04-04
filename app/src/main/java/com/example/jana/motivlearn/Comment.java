package com.example.jana.motivlearn;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.jana.motivlearn.model.CommentImp;
import com.example.jana.motivlearn.model.Timeline;
import com.example.jana.motivlearn.presenter.CommentPresenter;
import com.example.jana.motivlearn.presenter.TimelinePresenter;
import com.example.jana.motivlearn.view.CommentView;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.victor.loading.newton.NewtonCradleLoading;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.jana.motivlearn.tab2.likedposts;
import static com.example.jana.motivlearn.tab2.pres;

public class Comment extends AppCompatActivity implements CommentView{

    ListView simpleList;
    TextView textViewHours,textViewName,textViewLike,textViewContent,textViewComment;
    LikeButton like;
    ImageButton delete ;
    CommentPresenter pres2;
    ProgressDialog progressDialog;
    List<String> NameList=new ArrayList<String>();
    List<String> HoursList=new ArrayList<String>();
    List<String> ContentList=new ArrayList<String>();
    TimeLineInfo post;
    EditText Content;
    int commentCounter;
    CommentAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        SharedPreferences sp1= this.getSharedPreferences("Login", MODE_PRIVATE);
        final int uid =sp1.getInt("user_id", 0);
        post = (TimeLineInfo) getIntent().getSerializableExtra("post");
        pres2 = new CommentImp(this);
        pres2.getComments(post.getPostid());
        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.VISIBLE);
        newtonCradleLoading.start();
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewHours = (TextView)findViewById(R.id.textViewHours);
        textViewContent = (TextView)findViewById(R.id.textViewContent);
        textViewLike =(TextView) findViewById(R.id.textViewLikes);
        textViewComment=(TextView)findViewById(R.id.textViewComment);
        Content = (EditText) findViewById(R.id.content);
        ImageButton imageButton=(ImageButton)findViewById(R.id.edit);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pres2.addComment(String.valueOf(Content.getText()),post.getUserid(),post.getPostid());
                progressDialog = ProgressDialog.show(Comment.this, "", "Please wait...");
                pres2.getComments(post.getPostid());
            }
        });
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
            textViewHours.setText(formatTime(post.getHours()));
            textViewContent.setText(post.getContent());
            textViewLike.setText(String.valueOf(post.getLikes()));
            textViewComment.setText(String.valueOf(post.getComment()));
        }


    }


    @Override
    public void setResult(String res) {
            NameList.clear();
        ContentList.clear();
        HoursList.clear();

        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                String comment = object.getString("comment");
                String name = object.getString("u_name");
                String hours =formatTime(object.getString("comment_date"));
                NameList.add(name);
                ContentList.add(comment);
                HoursList.add(hours);
            }
        }
        catch (Exception e){}

        simpleList = (ListView) findViewById(R.id.listview);
        customAdapter = new CommentAdapter(getApplicationContext(), NameList, HoursList,ContentList);
        simpleList.setAdapter(customAdapter);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Comment.this,"this item is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.GONE);
    }
    @Override
    public void addCommentSuccess() {
        pres2.getComments(post.getPostid());
        customAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
        commentCounter++;
        textViewComment.setText(String.valueOf(post.getComment()+commentCounter));
        Content.setText("");

    }
    private String formatTime(String s) {
        String result="";
        s=s.substring(0, s.length()-2);
        try {
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            Date postDate = dateFormat.parse(s);
            Date currentDate = dateFormat.parse(c);

            Calendar c0 = Calendar.getInstance();
            c0.setTime(postDate);
            c0.add(Calendar.HOUR_OF_DAY, 10);
            int year = c0.get(Calendar.YEAR);
            int month = c0.get(Calendar.MONTH) + 1;
            int day = c0.get(Calendar.DAY_OF_MONTH);
            int hour = c0.get(Calendar.HOUR_OF_DAY);
            int minute = c0.get(Calendar.MINUTE);
            int second = c0.get(Calendar.SECOND);
            postDate = dateFormat.parse(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);

            long diff = currentDate.getTime() - postDate.getTime();

            if(diff < minutesInMilli)
                result= TimeUnit.MILLISECONDS.toSeconds(diff)+"s";
            else if(diff < hoursInMilli)
                result= TimeUnit.MILLISECONDS.toMinutes(diff)+"m";
            else if(diff < (daysInMilli)*3)
                result=(TimeUnit.MILLISECONDS.toDays(diff)+1)+"d";
            else
                result=s.substring(0,10);
        } catch(Exception e){}
        return result;
    }
}
