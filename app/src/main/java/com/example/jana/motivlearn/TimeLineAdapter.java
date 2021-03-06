package com.example.jana.motivlearn;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import java.lang.Object;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.jana.motivlearn.model.Timeline;
import com.example.jana.motivlearn.presenter.TimelinePresenter;
import com.example.jana.motivlearn.view.TimelineView;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static com.example.jana.motivlearn.tab2.pres;
import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by jana on 2/19/2018 AD.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    private Context mCtx;
   // private TimeLineInfo item;
   // TimelinePresenter pres;
    private List<TimeLineInfo> InfoList;
    private List<Integer> likedposts;
    public TimeLineAdapter(Context mCtx, List<TimeLineInfo> InfoList,List<Integer> likedposts) {
        this.mCtx = mCtx;
        this.InfoList = InfoList;
        this.likedposts=likedposts;
    }

    public TimeLineAdapter() {

    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.time_line_item, null);
        return new TimeLineViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final TimeLineViewHolder holder, int position) {
        SharedPreferences sp1= mCtx.getSharedPreferences("Login", MODE_PRIVATE);
        final int uid =sp1.getInt("user_id", 0);
        //getting the post of the specified position
        final TimeLineInfo item = InfoList.get(position);
        //binding the data with the viewholder views
        String time0 = formatTime(String.valueOf(item.getHours()));
        holder.textViewName.setText(item.getName());
        holder.textViewHours.setText(time0);
        holder.textViewContent.setText(String.valueOf(item.getContent()));
        holder.textViewLike.setText(String.valueOf(item.getLikes()));
        holder.textViewComment.setText(String.valueOf(item.getComment()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(item.getImage()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx, String.valueOf(item.getContent()), Toast.LENGTH_SHORT).show();
               Intent intent=new Intent(mCtx,Comment.class);
                intent.putExtra("post",item);
                 mCtx.startActivity(intent);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openusersprofile(item);
            }
        });
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openusersprofile(item);
            }
        });
        if(likedposts.contains(item.getPostid()))
        {
            holder.like.setLiked(true);
        }
        holder.like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                //if(uid!=item.getUserid()) {
                pres.addlike(item.getPostid(), uid);
                addlikersult(item);

        }
            @Override
            public void unLiked(LikeButton likeButton) {
               holder.like.setLiked(true);
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx ,"comment button ",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(mCtx,Comment.class);
                intent.putExtra("post",item);
                mCtx.startActivity(intent);
            }
        });
        if(item.getUserid()!= uid) {
            holder.delete.setVisibility(View.INVISIBLE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setMessage("Do you want to delete this post?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                pres.deletepost(item.getPostid());
                                int potition = InfoList.indexOf(item);
                                InfoList.remove(potition);
                                notifyItemRemoved(potition);
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

    }

    public String formatTime(String s) {//receive the post's timestamp
        String result="";
        s=s.substring(0, s.length()-2); //convert the parameter to the wanted format of timestamp
        try {
            //fixed values
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            //create a date format of the current date and time (same format of timestamp)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            //convert the post'd date and the current date to Date
            Date postDate = dateFormat.parse(s);
            Date currentDate = dateFormat.parse(c);
            //convert the post date to Calendar to easily add time (conver it to local time)
            Calendar c0 = Calendar.getInstance();
            c0.setTime(postDate);
            //add 10 hours (th difference between the local time and database time)
            c0.add(Calendar.HOUR_OF_DAY, 10);
            int year = c0.get(Calendar.YEAR);
            int month = c0.get(Calendar.MONTH) + 1;
            int day = c0.get(Calendar.DAY_OF_MONTH);
            int hour = c0.get(Calendar.HOUR_OF_DAY);
            int minute = c0.get(Calendar.MINUTE);
            int second = c0.get(Calendar.SECOND);
            postDate = dateFormat.parse(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
            long diff = currentDate.getTime() - postDate.getTime(); // find the difference
            //determine the difference (seconds - minutes - 3days- or more) range
            if(diff < minutesInMilli)
                result=TimeUnit.MILLISECONDS.toSeconds(diff)+"s";
            else if(diff < hoursInMilli)
                result= TimeUnit.MILLISECONDS.toMinutes(diff)+"m";
            else if(diff < daysInMilli)
                result= TimeUnit.MILLISECONDS.toHours(diff)+"h";
            else if(diff < (daysInMilli)*3)
                result=(TimeUnit.MILLISECONDS.toDays(diff)+1)+"d";
            else
                result=s.substring(0,10);
        } catch(Exception e){}
        return result;
    }


    @Override
    public int getItemCount() {
        return InfoList.size();
    }


     class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView textViewHours,textViewName,textViewLike,textViewContent,textViewComment;
        ImageView imageView;
        LikeButton like;
        ImageButton comment , delete ;
        public TimeLineViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewHours = itemView.findViewById(R.id.textViewHours);
            textViewLike = itemView.findViewById(R.id.textViewLikes);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewComment=itemView.findViewById(R.id.textViewComment);
            imageView = itemView.findViewById(R.id.user_image);
            like=itemView.findViewById(R.id.like_button);
            comment = itemView.findViewById(R.id.ib_comment);
            delete=itemView.findViewById(R.id.ib_delete);

        }
    }
public void addlikersult(TimeLineInfo item)
{
    int potition = InfoList.indexOf(item);
    InfoList.get(potition).setLikes(item.getLikes()+1);
    notifyItemChanged(potition,item);
    //notifyDataSetChanged();
}
public void openusersprofile(TimeLineInfo item)
{
    Intent intent = null;
    String type = item.getUsertype();
    if(type.equals("T"))
        intent = new Intent(mCtx, userTprofile.class);
    else
        intent = new Intent(mCtx, userSprofile.class);
    intent.putExtra("id", item.getUserid());
    mCtx.startActivity(intent);
}
}
