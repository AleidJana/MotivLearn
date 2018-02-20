package com.example.jana.motivlearn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

/**
 * Created by jana on 2/19/2018 AD.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    private Context mCtx;
    private List<TimeLineInfo> InfoList;
    public TimeLineAdapter(Context mCtx, List<TimeLineInfo> InfoList) {
        this.mCtx = mCtx;
        this.InfoList = InfoList;
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.time_line_item, null);
        return new TimeLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        //getting the product of the specified position
        TimeLineInfo item = InfoList.get(position);
        //binding the data with the viewholder views
        holder.textViewName.setText(item.getName());
        holder.textViewHours.setText(String.valueOf(item.getHours()));
        holder.textViewContent.setText(String.valueOf(item.getContent()));
        holder.textViewLike.setText(String.valueOf(item.getLikes()));
        holder.textViewComment.setText(String.valueOf(item.getComment()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(item.getImage()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Toast.makeText(mCtx ,"here you will add small code for question page ",Toast.LENGTH_LONG).show();
                 mCtx.startActivity(new Intent(mCtx,Comment.class));
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx ,"view profile by image ",Toast.LENGTH_LONG).show();
            }
        });
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx ,"view profile by name",Toast.LENGTH_LONG).show();

            }
        });
        holder.like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx ,"comment button ",Toast.LENGTH_LONG).show();
                mCtx.startActivity(new Intent(mCtx,Comment.class));

            }
        });
        holder.delete.setVisibility(View.INVISIBLE);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx ,"delete button",Toast.LENGTH_LONG).show();

            }
        });

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
}
