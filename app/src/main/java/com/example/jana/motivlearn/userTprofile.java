package com.example.jana.motivlearn;

/**
 * Created by haifamajid on 3/2/2018 AD.
 */


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jana.motivlearn.model.myProfileImp;
import com.example.jana.motivlearn.presenter.myProfilePresenter;
import com.example.jana.motivlearn.view.myProfileView;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import java.util.ArrayList;
import com.victor.loading.newton.NewtonCradleLoading;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class userTprofile extends AppCompatActivity implements myProfileView {
    TabHost host;
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<Entry> entries2 = new ArrayList<>();
    private ArrayList<RadarDataSet> dataSets = new ArrayList<>();
    private myProfilePresenter pres;
    private BoomMenuButton bmb ;
    private String username;
    int uid;
    int uid2;
    boolean creativity=true;
    boolean presentation=true;
    boolean communication=true;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuserprofile);
        uid = getIntent().getIntExtra("id", 0);
        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.VISIBLE);
        newtonCradleLoading.start();
        host = (TabHost)findViewById(R.id.tabHost);
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

        bmb= (BoomMenuButton)findViewById(R.id.bmb);
        SharedPreferences sp1= userTprofile.this.getSharedPreferences("Login", MODE_PRIVATE);
        uid2 =sp1.getInt("user_id", 0);
        if(uid == uid2)
            bmb.setVisibility(View.INVISIBLE);

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Skills");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Skills");
        host.addTab(spec);
        pres = new myProfileImp(userTprofile.this);
        pres.getUserInfo(uid);
        TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_creativity)
                .normalText("Creativity").listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        if(creativity) {
                        final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userTprofile.this)
                                .icon(getResources().getDrawable(R.drawable.rateheader))
                                .title("Rate " + username + "'s 'creativity' skill")
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

                                progressDialog = ProgressDialog.show(userTprofile.this, "", "Please wait...");
                                pres.rateSkill(uid2, uid, "creativity", ratingDialog.getValue());
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
                        Toast.makeText(userTprofile.this,"You already evaluate this user skill",Toast.LENGTH_LONG).show();
                    }
                });
        bmb.addBuilder(builder);
        TextOutsideCircleButton.Builder builder2 = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_presentation)
                .normalText("Presentation").listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        if (presentation) {
                            final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userTprofile.this)
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

                                    progressDialog = ProgressDialog.show(userTprofile.this, "", "Please wait...");
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
                            Toast.makeText(userTprofile.this,"You already evaluate this user skill",Toast.LENGTH_LONG).show();

                    }
                });
        bmb.addBuilder(builder2);

        TextOutsideCircleButton.Builder builder3 = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_acommunication)
                .normalText("Communication").listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        if(communication) {
                        final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userTprofile.this)
                                .icon(getResources().getDrawable(R.drawable.rateheader))
                                .title("Rate "+username+"'s 'communication' skill")
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
                                    progressDialog = ProgressDialog.show(userTprofile.this, "", "Please wait...");
                                    pres.rateSkill(uid2, uid, "communication", ratingDialog.getValue());
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
                            Toast.makeText(userTprofile.this,"You already evaluate this user skill",Toast.LENGTH_LONG).show();

                    }
                });
        bmb.addBuilder(builder3);


        // Inflate the layout for this fragment
    }

    @Override
    public void displayInfo(String res) {
        try {
            JSONObject obj = new JSONObject(res);
            String userstr = obj.getString("user");
            userstr = userstr.substring(1, userstr.length() - 1);
            JSONObject obj2 = new JSONObject(userstr);
            TextView uname = findViewById(R.id.uname);
            uname.setText(obj2.getString("u_name"));
            username = obj2.getString("u_name");
            TextView coins = findViewById(R.id.coins);
            coins.setText(obj2.getInt("u_coins")+"");
            int levelid = getResources().getIdentifier("level"+obj2.getInt("u_level"), "drawable", getPackageName());
            ImageView level = findViewById(R.id.level);
            level.setImageDrawable(getResources().getDrawable(levelid));
            int depid = getResources().getIdentifier((obj2.getString("u_department")).toLowerCase(), "drawable", getPackageName());
            ImageView dep = findViewById(R.id.dep);
            dep.setImageDrawable(getResources().getDrawable(depid));
            String skillstr = obj.getString("skills");
            updateChart(skillstr);
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
        //  last();
    }

    @Override
    public void updateChart(String skillstr)
    {
        try {
            JSONArray arr = new JSONArray(skillstr);
            ArrayList<Entry> entries2 = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonobject = arr.getJSONObject(i);
                float ff = (float)jsonobject.getDouble("average");
                entries2.add(new Entry(ff, i));
            }
            RadarChart chart = (RadarChart)findViewById(R.id.chart);
            chart.clear();
            RadarDataSet dataset_comp2 = new RadarDataSet(entries2, "");
            dataset_comp2.setColor(Color.BLUE);
            dataset_comp2.setValueTextSize(10f);
            dataset_comp2.setDrawFilled(true);
            ArrayList<String> labels = new ArrayList<String>();
            labels.add("Creativity");
            labels.add("Communication");
            labels.add("Presentation");
            dataSets.clear();
            dataSets.add(dataset_comp2);
            RadarData data = new RadarData(labels, dataSets);
            chart.setData(data);
            chart.setWebLineWidthInner(2f);
            chart.invalidate();
            chart.animate();
        }
        catch (Exception e){}
        pres.didRate(uid2,uid);

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
                    case "creativity":
                        creativity=false;
                        break;
                    case "presentation":
                        presentation=false;
                        break;
                    case "communication":
                        communication=false;
                        break;
                }
            }
            if((!creativity)&&(!presentation)&&(!communication))
            {
                bmb.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
