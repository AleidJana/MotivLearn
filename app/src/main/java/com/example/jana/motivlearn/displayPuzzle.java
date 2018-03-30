package com.example.jana.motivlearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.jana.motivlearn.model.displayPuzzleImp;
import com.example.jana.motivlearn.presenter.displayPuzzlePresenter;
import com.example.jana.motivlearn.view.displayPuzzleView;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class displayPuzzle extends MyListActivity implements displayPuzzleView {
    TextView Ttitle , Tqustion ;
    EditText Uanswer;
    Button submit ;
    int count ,  coins;
    String [] answer1;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    CountDownTimer countDownTimer;
    int time;
    displayPuzzlePresenter pre;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_puzzle);

        Ttitle = findViewById(R.id.textView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2) ;
       // Tqustion = findViewById(R.id.textViewTitle);
    /*    Button submit = (Button) findViewById(R.id.submit1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DynamicListView listView = (DynamicListView) findViewById(R.id.dynamiclistview);
                ListAdapter ll = listView.getAdapter();
                boolean flag = true;
                for(int i=0; i<ll.getCount(); i++)
                {
                    //  Toast.makeText(getApplicationContext(),ll.getItem(i-1)+"", Toast.LENGTH_SHORT).show();
                    //  TextView t = (TextView)
                    if((ll.getItem(i)+"").equals("Haifa "+i))
                        continue;

                    flag = false;
                    Toast.makeText(getApplicationContext(),"Wrong order", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(flag)
                    Toast.makeText(getApplicationContext(),"Correct order", Toast.LENGTH_SHORT).show();

            }
        });*/

        final int challNum = getIntent().getIntExtra("id", 0);

        SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
        final int uid =sp1.getInt("user_id", 0);

        pre = new displayPuzzleImp(this);
        pre.peformGetPuzzle(challNum);

    }

    @Override
    public void displayFailed() {

    }

    @Override
    public void setChallenge(String responseString) {
        final int challNum = getIntent().getIntExtra("id", 0);

        SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
        final int uid =sp1.getInt("user_id", 0);


        try {
            JSONObject obj = new JSONObject(responseString);
            String title = obj.getString("challenge_title");
            final String question = obj.getString("question");
            DynamicListView listView = (DynamicListView) findViewById(R.id.dynamiclistview);
            ArrayAdapter<String> adapter = new MyListAdapter(this, question);

            listView.setAdapter(adapter);
            listView.enableDragAndDrop();
            listView.setDraggableManager(new TouchViewDraggableManager(R.id.list_row_draganddrop_textview));
            listView.setOnItemMovedListener(new MyOnItemMovedListener(adapter));

            time= obj.getInt("time");
            coins = obj.getInt("coins");
            Ttitle.setText(title);
            progressBar.setMax(time);
            progressBar.setProgress(time);
            time=time*1000;
            countDownTimer= new CountDownTimer(time, 1000) {

                public void onTick(long millisUntilFinished) {
                    progressBar.setProgress((int) (millisUntilFinished / 1000));
                }
                public void onFinish() {
                    progressBar.setProgress(0);
                    pre.crrectAnswer(uid, challNum, "timeout", "gg", 0 , 0);
                }
            }.start();


            submit = findViewById(R.id.submit1);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    countDownTimer.cancel();
                    progressDialog = ProgressDialog.show(displayPuzzle.this, "", "Please wait...");
                    DynamicListView listView = (DynamicListView) findViewById(R.id.dynamiclistview);
                    ListAdapter ll = listView.getAdapter();
                    try {
                        JSONArray jsonArray = new JSONArray(question);
                        boolean flag = true;
                        for (int i = 0; i < ll.getCount(); i++) {
                            if ((ll.getItem(i) + "").equals(jsonArray.get(i)))
                                continue;

                            flag = false;
                            //Toast.makeText(getApplicationContext(), "Wrong order", Toast.LENGTH_SHORT).show();
                            pre.crrectAnswer(uid, challNum, "fail", "gg", 0 , 0);
                            break;
                        }
                        if (flag)
                        {   //Toast.makeText(getApplicationContext(), "Correct order", Toast.LENGTH_SHORT).show();
                            pre.selectRank(uid,challNum,"pass","gg",3);
                        }
                    } catch (Exception e) {
                    }

                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public void correct(int coins, String status) {
        if(progressDialog!=null&&progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        String msg1="", msg2="";
        int dr1 = 0;
        if(status.equals("pass"))
        {
            msg1 = "Congratulations";
            msg2= "You Have got "+coins+" Coins";
            dr1 = getResources().getIdentifier("hgif1", "drawable", getPackageName());
        }
        if(status.equals("fail"))
        {
            msg1 = "Unfortunately";
            msg2= "you didn't get any coins";
            dr1 = getResources().getIdentifier("losse", "drawable", getPackageName());
        }
        if(status.equals("timeout"))
        {
            msg1 = "OOPS!";
            msg2= "Question time is finished";
            dr1 = getResources().getIdentifier("time", "drawable", getPackageName());
        }

        new TTFancyGifDialog.Builder(displayPuzzle.this)
                .setTitle(msg1)
                .setMessage(msg2)
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc")
                .setGifResource(dr1)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("nextFrag", "cha");
                        startActivity(intent);
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(this,"can't go back", Toast.LENGTH_SHORT).show();
    }
///////////////////////////////////////////////////////////////////////////////////////
    private static class MyListAdapter extends ArrayAdapter<String>{

        private final Context mContext;

        MyListAdapter(final Context context, String question) {
            mContext = context;
            try {
                JSONArray jsonArray = new JSONArray(question);
                jsonArray = shuffleJsonArray(jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    add(jsonArray.get(i)+"");
                    // add(mContext.getString(R.string.row_number, i));
                }
            }
            catch(Exception e){}
        }

        @Override
        public long getItemId(final int position) {
            return getItem(position).hashCode();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.list_row_dynamiclistview, parent, false);
            }

            ((TextView) view.findViewById(R.id.list_row_draganddrop_textview)).setText(getItem(position));

            return view;
        }

    public static JSONArray shuffleJsonArray (JSONArray array) throws JSONException {
        // Implementing Fisher–Yates shuffle
        Random rnd = new Random();
        for (int i = array.length() - 1; i >= 0; i--)
        {
            int j = rnd.nextInt(i + 1);
            // Simple swap
            Object object = array.get(j);
            array.put(j, array.get(i));
            array.put(i, object);
        }
        return array;
    }
    }


    private class MyOnItemMovedListener implements OnItemMovedListener {

        private final ArrayAdapter<String> mAdapter;

        private Toast mToast;

        MyOnItemMovedListener(final ArrayAdapter<String> adapter) {
            mAdapter = adapter;
        }

        @Override
        public void onItemMoved(final int originalPosition, final int newPosition) {
            if (mToast != null) {
                mToast.cancel();
            }
            //  mToast = Toast.makeText(getApplicationContext(), getString(R.string.moved , mAdapter.getItem(newPosition), newPosition), Toast.LENGTH_SHORT);
            //  mToast.show();
        }
    }

}