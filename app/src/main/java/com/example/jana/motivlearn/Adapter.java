package com.example.jana.motivlearn;

/**
 * Created by jana on 2/9/2018 AD.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class Adapter extends RecyclerView.Adapter<Adapter.ProductViewHolder> {

    private Context mCtx;
    private List<question> questionList;
    public Adapter(Context mCtx, List<question> InfoList) {
        this.mCtx = mCtx;
        this.questionList = InfoList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        final question questions = questionList.get(position);
        //binding the data with the viewholder views
        holder.textViewTitle.setText(questions.getTitle());
        holder.textViewName.setText(String.valueOf(questions.getWriter()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(questions.getImage()));
        holder.fieldimage.setImageDrawable(mCtx.getResources().getDrawable(questions.getField()));
        holder.progressbar.setProgress(questions.getDuration());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx ,"here you will add small code for question page ",Toast.LENGTH_LONG).show();
                Intent intent = null;
                String type = questions.getType();
                if(type.equals("choice"))
                    intent = new Intent(mCtx, displaychoice.class);
                else if(type.equals("code"))
                    intent = new Intent(mCtx, displayCodeOutput.class);
                    else if(type.equals("fillblank"))
                    intent = new Intent(mCtx, displayfillBlanck.class);

                intent.putExtra("type", type);
                intent.putExtra("id", questions.getId());
                mCtx.startActivity(intent);
           //     mCtx.startActivity(new Intent(mCtx,displayfillBlanck.class));

            }
        });
    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle,textViewName;
        ImageView imageView,fieldimage;
        ProgressBar progressbar;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewName = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            fieldimage=itemView.findViewById(R.id.imageView2);
            progressbar = itemView.findViewById(R.id.progressBar);
        }
    }
}