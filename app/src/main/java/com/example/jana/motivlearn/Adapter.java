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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class Adapter extends RecyclerView.Adapter<Adapter.ProductViewHolder> {

    private Context mCtx;
    private List<question> questionList;
    public Adapter(Context mCtx, List<question> productList) {
        this.mCtx = mCtx;
        this.questionList = productList;
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
        question questions = questionList.get(position);
        //binding the data with the viewholder views
        holder.textViewTitle.setText(questions.getTitle());
        holder.textViewName.setText(String.valueOf(questions.getWriter()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(questions.getImage()));
        holder.fieldimage.setImageDrawable(mCtx.getResources().getDrawable(questions.getField()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx ,"here you will add small code for question page ",Toast.LENGTH_LONG).show();
                mCtx.startActivity(new Intent(mCtx,displayfillBlanck.class));

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

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewName = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            fieldimage=itemView.findViewById(R.id.imageView2);
        }
    }
}