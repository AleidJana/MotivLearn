package com.example.jana.motivlearn;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jana.motivlearn.R;

import java.util.ArrayList;
import java.util.List;


public class tab4 extends Fragment {
    List<question> productList;

    //the recyclerview
    RecyclerView recyclerView;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tab4, container, false);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        productList = new ArrayList<>();

        //adding some items to our list

        productList.add(
                new question(
                        1,
                        "Highest rate",
                        "    Jana Aleid",
                        R.drawable.code, R.drawable.html));

        productList.add(
                new question(
                        1,
                        "Highest rate",
                        "Nouf Alsedairy",
                        R.drawable.code, R.drawable.java));
        productList.add(
                new question(
                        1,
                        "Highest rate",
                        "Haifa Alabduljabbar",
                        R.drawable.code, R.drawable.js));
        productList.add(
                new question(
                        1,
                        "Highest rate",
                        "Ragha Alsaleh",
                        R.drawable.fillblank, R.drawable.ethics));
        productList.add(
                new question(
                        1,
                        "Highest rate",
                        "Reema Alswailam",
                        R.drawable.puzzel, R.drawable.php));
        productList.add(
                new question(
                        1,
                        "Highest rate",
                        "    Jana Aleid",
                        R.drawable.code, R.drawable.html));

        productList.add(
                new question(
                        1,
                        "Highest rate",
                        "Ragha Alsaleh",
                        R.drawable.code, R.drawable.html));

        //creating recyclerview adapter
        Adapter adapter = new Adapter(getActivity(), productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);






        return view;
    }

}
