package com.example.jana.motivlearn;

/**
 * Created by haifamajid on 3/2/2018 AD.
 */

import android.app.Fragment;
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

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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

public class userTprofile extends AppCompatActivity implements myProfileView {
    TabHost host;
    ArrayList<Entry> entries2 = new ArrayList<>();
    private ArrayList<RadarDataSet> dataSets = new ArrayList<>();
    private myProfilePresenter pres;
    private BoomMenuButton bmb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_teacher_profile);

        int uid = getIntent().getIntExtra("id", 0);
      //  SharedPreferences sp1= this.getSharedPreferences("Login", MODE_PRIVATE);
      //  int uid =sp1.getInt("user_id", 0);
       // Toast.makeText(this,uid+"", Toast.LENGTH_SHORT).show();

        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.VISIBLE);
        newtonCradleLoading.start();

        host = (TabHost)findViewById(R.id.tabHost);
        host.setup();


        bmb= (BoomMenuButton)findViewById(R.id.bmb);
        //bmb.setVisibility(View.INVISIBLE);
        TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_creativity)
                .normalText("Creativity").listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // Toast.makeText(getContext(),"dfdfd",Toast.LENGTH_LONG).show();
                        final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userTprofile.this)
                                .icon(getResources().getDrawable(R.drawable.rateheader))
                                .title("How was your experience with us?")
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
                                Toast.makeText(userTprofile.this,ratingDialog.getValue()+"", Toast.LENGTH_SHORT).show();
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
                        final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userTprofile.this)
                                .icon(getResources().getDrawable(R.drawable.rateheader))
                                .title("How was your experience with us?")
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
                                Toast.makeText(userTprofile.this,ratingDialog.getValue()+"", Toast.LENGTH_SHORT).show();
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

        TextOutsideCircleButton.Builder builder3 = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_acommunication)
                .normalText("Communication").listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // Toast.makeText(getContext(),"dfdfd",Toast.LENGTH_LONG).show();
                        final com.example.jana.motivlearn.RatingDialog ratingDialog = new com.example.jana.motivlearn.RatingDialog.Builder(userTprofile.this)
                                .icon(getResources().getDrawable(R.drawable.rateheader))
                                .title("How was your experience with us?")
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
                                Toast.makeText(userTprofile.this,ratingDialog.getValue()+"", Toast.LENGTH_SHORT).show();
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
        bmb.addBuilder(builder3);


        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Skills");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Skills");
        host.addTab(spec);

        pres = new myProfileImp(userTprofile.this);
        pres.getUserInfo(uid);

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

            TextView coins = findViewById(R.id.coins);
            coins.setText(obj2.getInt("u_coins")+"");

            int levelid = getResources().getIdentifier("level"+obj2.getInt("u_level"), "drawable", getPackageName());
            ImageView level = findViewById(R.id.level);
            level.setImageDrawable(getResources().getDrawable(levelid));

            int depid = getResources().getIdentifier((obj2.getString("u_department")).toLowerCase(), "drawable", getPackageName());
            ImageView dep = findViewById(R.id.dep);
            dep.setImageDrawable(getResources().getDrawable(depid));


            String skillstr = obj.getString("skills");
            JSONArray arr = new JSONArray(skillstr);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonobject = arr.getJSONObject(i);
                float ff = (float)jsonobject.getDouble("average");
                entries2.add(new Entry(ff, i));
            }

            RadarChart chart = (RadarChart)findViewById(R.id.chart);
            RadarDataSet dataset_comp2 = new RadarDataSet(entries2, "nouf");

            dataset_comp2.setColor(Color.BLUE);
            dataset_comp2.setValueTextSize(10f);
            dataset_comp2.setDrawFilled(true);

            ArrayList<String> labels = new ArrayList<String>();

            labels.add("Presentation");
            labels.add("Communication");
            labels.add("Creativity");
            dataSets.add(dataset_comp2);
            RadarData data = new RadarData(labels, dataSets);
            chart.setData(data);
            chart.setWebLineWidthInner(2f);
            chart.invalidate();
            chart.animate();
        }
        catch (Exception e){}

        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.GONE);

        ScrollView whole = findViewById(R.id.wholeview);
        whole.setVisibility(View.VISIBLE);

    }

}
