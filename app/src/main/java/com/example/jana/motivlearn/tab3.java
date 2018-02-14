package com.example.jana.motivlearn;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import java.util.ArrayList;
import java.util.List;

public class tab3 extends Fragment {
    TabHost tabHost;
    List<UserInfo> studentList;
    List<UserInfo> teacherList;
    //the recyclerview
    RecyclerView SrecyclerView;
    RecyclerView TrecyclerView;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        TabHost host = (TabHost)view.findViewById(R.id.tabHost);
        host.setup();

        //getting the recyclerview from xml
        TrecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        TrecyclerView.setHasFixedSize(true);
        TrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        teacherList = new ArrayList<>();

        //adding some items to our list

        teacherList.add(
                new UserInfo(
                        "Noura Alomar ",20
                        ,5,
                        R.drawable.medal1));

        teacherList.add(
                new UserInfo(
                        "Sadeem Alsudais ",
                        18,7,
                        R.drawable.medal2));

        teacherList.add(
                new UserInfo(
                        "Bayan Alarifi",
                        16,9,
                        R.drawable.medal3));

        teacherList.add(
                new UserInfo(
                        "Bushra Alkadhi",
                        14,4,
                        R.drawable.medal));

        //creating recyclerview adapter
        LeaderBoardAdapter adapter = new LeaderBoardAdapter(getActivity(), teacherList);

        //setting adapter to recyclerview
        TrecyclerView.setAdapter(adapter);
        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Teacher");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Teacher");
        host.addTab(spec);



        SrecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
        SrecyclerView.setHasFixedSize(true);
        SrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        studentList = new ArrayList<>();

        //adding some items to our list

        studentList.add(
                new UserInfo(
                        "Haifa Alabduljabbar ",20
                        ,5,
                        R.drawable.medal1));

        studentList.add(
                new UserInfo(
                        "Jana Aleid",
                        18,7,
                        R.drawable.medal2));

        studentList.add(
                new UserInfo(
                        "Raghad Alsaleh",
                        16,9,
                        R.drawable.medal3));

        studentList.add(
                new UserInfo(
                        "Raghad Alsaleh",
                        14,4,
                        R.drawable.medal));

        //creating recyclerview adapter
        LeaderBoardAdapter adapter2 = new LeaderBoardAdapter(getActivity(), studentList);

        //setting adapter to recyclerview
        SrecyclerView.setAdapter(adapter2);

        //Tab 2
        spec = host.newTabSpec("Student");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Student");
        host.addTab(spec);

        // Inflate the layout for this fragment
        return view;
    }


}
