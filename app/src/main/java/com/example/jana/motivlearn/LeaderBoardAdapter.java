package com.example.jana.motivlearn;

/**
 * Created by jana on 2/9/2018 AD.
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<UserInfo> usersList;
    public LeaderBoardAdapter(Context mCtx, List<UserInfo> productList) {
        this.mCtx = mCtx;
        this.usersList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.leaderboarditem, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        final UserInfo product = usersList.get(position);
        //binding the data with the viewholder views
        holder.textViewName.setText(product.getName());
        holder.textViewCoins.setText(String.valueOf(product.getCoins()));
        holder.textViewOrder.setText(String.valueOf(product.getOrder()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(mCtx, tab1.class);
             //   Toast.makeText(mCtx ,"here you will add small code for question page ",Toast.LENGTH_LONG).show();
              Intent intent = null;
                String type = product.getType();
                if(type.equals("T"))
                    intent = new Intent(mCtx, userTprofile.class);
                else
                    intent = new Intent(mCtx, userSprofile.class);

               // intent.putExtra("type", type);
                intent.putExtra("id", product.getId());
                mCtx.startActivity(intent);
               //BottomBar bottomBar = (BottomBar) MainActivity.findViewById(R.id.bottomBar);
            /* intent.putExtra("page", 2);
             mCtx.startActivity(intent);*/
            }
        });
    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCoins,textViewName,textViewOrder;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCoins = itemView.findViewById(R.id.textViewCoins);
            textViewOrder = itemView.findViewById(R.id.textViewOrder);
            imageView = itemView.findViewById(R.id.imageView3);
        }
    }
}