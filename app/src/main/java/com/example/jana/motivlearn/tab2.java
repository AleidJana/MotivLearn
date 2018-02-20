package com.example.jana.motivlearn;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jana.motivlearn.R;

import java.util.ArrayList;
import java.util.List;


public class tab2 extends Fragment {
    List<TimeLineInfo> InfoList;

    //the recyclerview
    RecyclerView recyclerView;
    FloatingActionButton addPost;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addPost=(FloatingActionButton) view.findViewById(R.id.fab);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getActivity(), "your post added", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getActivity(),AddPost.class));

            }
        });
        //initializing the productlist
        InfoList = new ArrayList<>();
        //adding some items to our list

        InfoList.add(
                new TimeLineInfo(
                        "Haifa Alabduljabbar ","Templates save time requi"
                        ,"3h",2000,1200,
                        R.drawable.profileimg));


        InfoList.add(
                new TimeLineInfo(
                        "Jana Aleid ","Templates save time required to define the document's structure and ensure the quality and completion of them.Templates save time required to define the document's structure and ensure the quality and completion of them."
                        ,"5h",1050,1200,
                        R.drawable.profileimg));


        InfoList.add(
                new TimeLineInfo(
                        "Jana Aleid ","Templates save time required to define the document's structure and ensure the quality and completion of them.Templates save time required to define the document's structure and ensure the quality and completion of them."
                        ,"5h",1050,1200,
                        R.drawable.profileimg));

        InfoList.add(
                new TimeLineInfo(
                        "Jana Aleid ","Templates save time required to define the document's structure and ensure the quality and completion of them.Templates save time required to define the document's structure and ensure the quality and completion of them."
                        ,"5h",1050,1200,
                        R.drawable.profileimg));

        InfoList.add(
                new TimeLineInfo(
                        "Jana Aleid ","Templates save time required to define the document's structure and ensure the quality and completion of them.Templates save time required to define the document's structure and ensure the quality and completion of them."
                        ,"5h",1050,1200,
                        R.drawable.profileimg));

        InfoList.add(
                new TimeLineInfo(
                        "Jana Aleid ","Templates save time required to define the document's structure and ensure the quality and completion of them.Templates save time required to define the document's structure and ensure the quality and completion of them."
                        ,"5h",1050,1200,
                        R.drawable.profileimg));

        //creating recyclerview adapter
        TimeLineAdapter adapter = new TimeLineAdapter(getActivity(), InfoList);
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }


}
