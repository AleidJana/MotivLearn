package com.example.jana.motivlearn;

/**
 * Created by haifamajid on 3/2/2018 AD.
 */


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suserprofile);

        final int uid = getIntent().getIntExtra("id", 0);
       // Toast.makeText(this,uid+"", Toast.LENGTH_SHORT).show();

        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.VISIBLE);
        newtonCradleLoading.start();

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        bmb= (BoomMenuButton) findViewById(R.id.bmb);
        SharedPreferences sp1= userSprofile.this.getSharedPreferences("Login", MODE_PRIVATE);
        final int uid2 =sp1.getInt("user_id", 0);
        if(uid == uid2)
            bmb.setVisibility(View.INVISIBLE);

        TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_teamwork)
                .normalText("Teamwork").listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {

                        // Toast.makeText(getContext(),"dfdfd",Toast.LENGTH_LONG).show();
                        final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userSprofile.this)
                                .icon(getResources().getDrawable(R.drawable.rateheader))
                                .title("Rate "+username+"'s 'teamwork' skill")
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
                                pres.rateSkill(uid2, uid, "teamwork", ratingDialog.getValue());
                                pres.getSkill(uid);
                               /* Intent refresh = new Intent(userTprofile.this, userTprofile.class);
                                refresh.putExtra("id", uid);
                                startActivity(refresh);
                                userTprofile.this.finish();*/
                                ratingDialog.dismiss();
                            }
                        });

                        ratingDialog.getTvNegative().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ratingDialog.dismiss();
                            }
                        });
                    }
                });
        bmb.addBuilder(builder);

        TextOutsideCircleButton.Builder builder2 = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_presentation)
                .normalText("Presentation").listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // Toast.makeText(getContext(),"dfdfd",Toast.LENGTH_LONG).show();
                        final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userSprofile.this)
                                .icon(getResources().getDrawable(R.drawable.rateheader))
                                .title("Rate "+username+"'s 'presentation' skill")
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
                                // Toast.makeText(userTprofile.this,ratingDialog.getValue()+"", Toast.LENGTH_SHORT).show();
                                //   pres.didRate(uid2, uid, "presentation");
                                pres.rateSkill(uid2, uid, "presentation", ratingDialog.getValue());
                                pres.getSkill(uid);
                               /* Intent refresh = new Intent(userTprofile.this, userTprofile.class);
                                refresh.putExtra("id", uid);
                                startActivity(refresh);
                                userTprofile.this.finish();*/
                                ratingDialog.dismiss();
                            }
                        });

                        ratingDialog.getTvNegative().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ratingDialog.dismiss();
                            }
                        });
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
            JSONArray arr = new JSONArray(skillstr);
            ArrayList<Entry> entries2 = new ArrayList<>();
            // entries2.clear();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonobject = arr.getJSONObject(i);
                float ff = (float)jsonobject.getDouble("average");
               // Toast.makeText(this,i+"", Toast.LENGTH_SHORT).show();
                entries2.add(new Entry(ff, i));
            }

            RadarChart chart = (RadarChart)findViewById(R.id.chart);
            chart.clear();
            RadarDataSet dataset_comp2 = new RadarDataSet(entries2, "nouf");
            //dataset_comp2.clear();

            dataset_comp2.setColor(Color.BLUE);
            dataset_comp2.setValueTextSize(10f);
            dataset_comp2.setDrawFilled(true);

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
        }
        catch (Exception e){}
    }

}
