package com.example.jana.motivlearn;

/**
 * Created by jana on 2/9/2018 AD.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.jana.motivlearn.tab2.pres;
import static com.example.jana.motivlearn.tab4.presenter;

/**
 * Created by Belal on 10/18/2017.
 */


public class Adapter extends RecyclerView.Adapter<Adapter.ProductViewHolder> {

    private Context mCtx;
    String typeu;
    String uName;
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
        SharedPreferences sp1= mCtx.getSharedPreferences("Login", MODE_PRIVATE);
         typeu =sp1.getString("user_type", null);
        uName=sp1.getString("user_name", null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx ,"here you will add small code for question page ",Toast.LENGTH_LONG).show();
                if(typeu.equals("S")) {
                    Intent intent = null;
                    String type = questions.getType();
                    if (type.equals("choice"))
                        intent = new Intent(mCtx, displaychoice.class);
                    else if (type.equals("code"))
                        intent = new Intent(mCtx, displayCodeOutput.class);
                    else if (type.equals("fillblank"))
                        intent = new Intent(mCtx, displayfillBlanck.class);
                    else
                        intent = new Intent(mCtx, displayPuzzle.class);

                        intent.putExtra("type", type);
                        intent.putExtra("id", questions.getId());
                        mCtx.startActivity(intent);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(typeu.equals("T") && uName.equals(questions.getWriter())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                    builder.setMessage("Do you want to delete this Question?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    presenter.deleteQuestion(questions.getId());
                                    int potition = questionList.indexOf(questions);
                                    questionList.remove(potition);
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
                    alert.setTitle("Delete Question");
                    alert.show();
                }
                    return true;
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