package com.example.jana.motivlearn;

/**
 * Created by haifamajid on 3/2/2018 AD.
 */


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.example.jana.motivlearn.model.myProfileImp;
import com.example.jana.motivlearn.presenter.myProfilePresenter;
import com.example.jana.motivlearn.view.myProfileView;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;


import com.codemybrainsout.ratingdialog.RatingDialog;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.victor.loading.newton.NewtonCradleLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class userSprofile extends AppCompatActivity implements myProfileView {
    TabHost tabHost;
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<Entry> entries2 = new ArrayList<>();
    BoomMenuButton bmb ;
    private ArrayList<RadarDataSet> dataSets = new ArrayList<>();
    private myProfilePresenter pres;
    private String username;
    boolean teamwork=true;
    boolean presentationskill=true;
    int uid;
    int uid2;
    ProgressDialog progressDialog;
    ImageButton helep;
    ImageButton logout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suserprofile);

       uid = getIntent().getIntExtra("id", 0);
       // Toast.makeText(this,uid+"", Toast.LENGTH_SHORT).show();

        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.VISIBLE);
        newtonCradleLoading.start();

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("User Profile");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        helep=(ImageButton)findViewById(R.id.help) ;
        helep.setVisibility(View.VISIBLE);
        helep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userSprofile.this, Help.class);
                startActivity(intent);
            }
        });
        bmb= (BoomMenuButton) findViewById(R.id.bmb);
        SharedPreferences sp1= userSprofile.this.getSharedPreferences("Login", MODE_PRIVATE);
        uid2 =sp1.getInt("user_id", 0);
        if(uid == uid2) {
            bmb.setVisibility(View.INVISIBLE);
            logout=(ImageButton)findViewById(R.id.logout) ;
            logout.setVisibility(View.VISIBLE);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences =getSharedPreferences("Login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(userSprofile.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_teamwork)
                .normalText("Teamwork").listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        if (teamwork) {
                            // Toast.makeText(getContext(),"dfdfd",Toast.LENGTH_LONG).show();
                            final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userSprofile.this)
                                    .icon(getResources().getDrawable(R.drawable.rateheader))
                                    .title("Rate " + username + "'s 'teamwork' skill")
                                    .titleTextColor(R.color.black)
                                    .negativeButtonText("cancel")
                                    .positiveButtonText("submit")
                                    .positiveButtonTextColor(R.color.colorAccent)
                                    .negativeButtonTextColor(R.color.text)
                                    .ratingBarColor(R.color.rating)
                                    .onThresholdCleared(new com.example.jana.motivlearn.RatingDialog.Builder.RatingThresholdClearedListener() {
                                        @Override
                                        public void onThresholdCleared(com.example.jana.motivlearn.RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                                        }
                                    })
                                    .onRatingChanged(new com.example.jana.motivlearn.RatingDialog.Builder.RatingDialogListener() {
                                        @Override
                                        public void onRatingSelected(float rating, boolean thresholdCleared) {

                                        }
                                    })
                                    .build();
                            ratingDialog.show();
                            ratingDialog.getTvPositive().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //  Toast.makeText(userTprofile.this,ratingDialog.getValue()+"", Toast.LENGTH_SHORT).show();
                                    progressDialog = ProgressDialog.show(userSprofile.this, "", "Please wait...");
                                    pres.rateSkill(uid2, uid, "teamwork", ratingDialog.getValue());
                                    pres.getSkill(uid);
                                    ratingDialog.dismiss();
                                }
                            });

                            ratingDialog.getTvNegative().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ratingDialog.dismiss();
                                }
                            });
                        }else
                            Toast.makeText(userSprofile.this,"You already evaluate this user skill",Toast.LENGTH_LONG).show();

                    }
                });
        bmb.addBuilder(builder);

        TextOutsideCircleButton.Builder builder2 = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_presentation)
                .normalText("Presentation").listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        if (presentationskill) {
                            // Toast.makeText(getContext(),"dfdfd",Toast.LENGTH_LONG).show();
                            final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userSprofile.this)
                                    .icon(getResources().getDrawable(R.drawable.rateheader))
                                    .title("Rate " + username + "'s 'presentation' skill")
                                    .titleTextColor(R.color.black)
                                    .negativeButtonText("cancel")
                                    .positiveButtonText("submit")
                                    .positiveButtonTextColor(R.color.colorAccent)
                                    .negativeButtonTextColor(R.color.text)
                                    .ratingBarColor(R.color.rating)
                                    .onThresholdCleared(new com.example.jana.motivlearn.RatingDialog.Builder.RatingThresholdClearedListener() {
                                        @Override
                                        public void onThresholdCleared(com.example.jana.motivlearn.RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                                        }
                                    })
                                    .onRatingChanged(new com.example.jana.motivlearn.RatingDialog.Builder.RatingDialogListener() {
                                        @Override
                                        public void onRatingSelected(float rating, boolean thresholdCleared) {

                                        }
                                    })
                                    .build();
                            ratingDialog.show();
                            ratingDialog.getTvPositive().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    progressDialog = ProgressDialog.show(userSprofile.this, "", "Please wait...");
                                    pres.rateSkill(uid2, uid, "presentation", ratingDialog.getValue());
                                    pres.getSkill(uid);
                                    ratingDialog.dismiss();
                                }
                            });

                            ratingDialog.getTvNegative().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ratingDialog.dismiss();
                                }
                            });
                        }else
                            Toast.makeText(userSprofile.this,"You already evaluate this user skill",Toast.LENGTH_LONG).show();
                    }
                });
        bmb.addBuilder(builder2);


        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Skills");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Skills");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Badges");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Badges");
        host.addTab(spec);

        pres = new myProfileImp(userSprofile.this);
        pres.getUserInfo(uid);
     }


    @Override
    public void displayInfo(String res) {
      //  Toast.makeText(this,"here", Toast.LENGTH_SHORT).show();

        try {

            JSONObject obj = new JSONObject(res);
            String userstr = obj.getString("user");
            userstr = userstr.substring(1, userstr.length() - 1);
            JSONObject obj2 = new JSONObject(userstr);

            TextView uname = findViewById(R.id.uname);
            uname.setText(obj2.getString("u_name"));
            username = obj2.getString("u_name");

            @SuppressLint("WrongViewCast") TextView coins = findViewById(R.id.coins);
            coins.setText(obj2.getInt("u_coins")+"");

            int levelid = getResources().getIdentifier("level"+obj2.getInt("u_level"), "drawable", getPackageName());
            ImageView level = findViewById(R.id.level);
            level.setImageDrawable(getResources().getDrawable(levelid));

            int depid = getResources().getIdentifier((obj2.getString("u_department")).toLowerCase(), "drawable", getPackageName());
            ImageView dep = findViewById(R.id.dep);
            dep.setImageDrawable(getResources().getDrawable(depid));


            String skillstr = obj.getString("skills");
            updateChart(skillstr);

            String badgestr = obj.getString("badges");
            JSONArray arr2 = new JSONArray(badgestr);

            for (int i = 0; i < arr2.length(); i++) {
                JSONObject jsonobject = arr2.getJSONObject(i);
                int badgeid = getResources().getIdentifier("badgec"+jsonobject.getInt("b_id"), "drawable", getPackageName());
                int m = jsonobject.getInt("b_id");
                ImageView coloring = findViewById(R.id.badge+(m));
                coloring.setImageDrawable(getResources().getDrawable(badgeid));
            }
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
        catch (Exception e){}

        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.GONE);

        ScrollView whole = findViewById(R.id.wholeview);
        whole.setFocusable(false);
        whole.smoothScrollTo(0,0);
        whole.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateChart(String skillstr)
    {
        try {
            JSONArray arr = new JSONArray(skillstr);//convert user's skill info to JSON
            ArrayList<Entry> entries2 = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonobject = arr.getJSONObject(i);
                float ff = (float)jsonobject.getDouble("average");//get the value of each skill
                entries2.add(new Entry(ff, i));//store the average to display it on the radar chart
            }

            RadarChart chart = (RadarChart)findViewById(R.id.chart);
            chart.clear();
            RadarDataSet dataset_comp2 = new RadarDataSet(entries2, " ");//create dataset with values
            //customize chart
            dataset_comp2.setColor(Color.BLUE);
            dataset_comp2.setValueTextSize(10f);
            dataset_comp2.setDrawFilled(true);
            //create label for each corner that indicate to a skill
            ArrayList<String> labels = new ArrayList<String>();
            labels.add("Activation");
            labels.add("Teamwork");
            labels.add("Programming");
            labels.add("Presentation");
            labels.add("Technical Knowledge");
            dataSets.clear();
            dataSets.add(dataset_comp2);

            RadarData data = new RadarData(labels, dataSets);
            chart.setData(data);
            chart.setWebLineWidthInner(2f);
            chart.invalidate();
            chart.animate();
            pres.didRate(uid2,uid);
        }
        catch (Exception e){}
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        pres.didRate(uid2,uid);

    }
    @Override
    public void cantRate(String skill) {
        JSONArray arr = null;
        try {
            arr = new JSONArray(skill);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonobject = arr.getJSONObject(i);
                String res = jsonobject.getString("type");
                switch (res)
                {
                    case "teamwork":
                        teamwork=false;
                        break;
                    case "presentation":
                        presentationskill=false;
                        break;
                }
            }
            if((!teamwork)&&(!presentationskill))
            {
                bmb.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
