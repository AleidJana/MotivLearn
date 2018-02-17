package com.example.jana.motivlearn;

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

import java.util.ArrayList;

public class TeacherProfile extends Fragment {
    TabHost tabHost;
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<Entry> entries2 = new ArrayList<>();
    BoomMenuButton bmb ;
    private ArrayList<RadarDataSet> dataSets = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);
        TabHost host = (TabHost)view.findViewById(R.id.tabHost);
        host.setup();


        RadarChart chart = (RadarChart) view.findViewById(R.id.chart);


        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(1f, 0));
        entries2.add(new Entry(5f, 1));
        entries2.add(new Entry(6f, 2));
        entries2.add(new Entry(3f, 3));
        entries2.add(new Entry(4f, 4));
        entries2.add(new Entry(8f, 5));
        RadarDataSet dataset_comp2 = new RadarDataSet(entries2, "nouf");

        dataset_comp2.setColor(Color.BLUE);
        dataset_comp2.setValueTextSize(10f);
        dataset_comp2.setDrawFilled(true);

        ArrayList<String> labels = new ArrayList<String>();

        labels.add("Communication");
        labels.add("Technical Knowledge");
        labels.add("Team Player");
        labels.add("Meeting Deadlines");
        labels.add("Problem Solving");
        labels.add("Punctuality");
        dataSets.add(dataset_comp2);
        RadarData data = new RadarData(labels, dataSets);
        chart.setData(data);
        chart.setWebLineWidthInner(3f);
        chart.invalidate();
        chart.animate();
        bmb= (BoomMenuButton) view.findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                    .normalImageRes(R.drawable.ic_teamwork)
                    .normalText("Team Work!").listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // Toast.makeText(getContext(),"dfdfd",Toast.LENGTH_LONG).show();
                            final RatingDialog ratingDialog = new RatingDialog.Builder(getActivity())
                                    .threshold(4)
                                    .onThresholdCleared(new RatingDialog.Builder.RatingThresholdClearedListener() {
                                        @Override
                                        public void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                                            //do something
                                            Toast.makeText(getContext(),"rating value is : "+rating,Toast.LENGTH_LONG).show();

                                            ratingDialog.dismiss();
                                        }
                                    }).build();

                            ratingDialog.show();
                        }
                    });
            bmb.addBuilder(builder);
        }

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Skills");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Skills");
        host.addTab(spec);


        // Inflate the layout for this fragment
        return view;    }



}
