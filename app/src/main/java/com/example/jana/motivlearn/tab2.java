package com.example.jana.motivlearn;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.jana.motivlearn.model.Timeline;
import com.example.jana.motivlearn.presenter.TimelinePresenter;
import com.example.jana.motivlearn.view.TimelineView;
import com.victor.loading.newton.NewtonCradleLoading;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class tab2 extends Fragment implements TimelineView {
    public static List<TimeLineInfo> InfoList= new ArrayList<>();
    public static List<Integer> likedposts= new ArrayList<>();
    View view;
   public static TimelinePresenter pres;
    RecyclerView recyclerView;
    FloatingActionButton addPost;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab2, container, false);
        SharedPreferences sp1= this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        int uid =sp1.getInt("user_id", 0);
        pres = new Timeline(tab2.this);
        pres.likedposts(uid);

        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)view.findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.VISIBLE);
        newtonCradleLoading.start();
        PullRefreshLayout layout = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        layout.setRefreshing(true);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferences sp1= getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                int uid =sp1.getInt("user_id", 0);
                pres = new Timeline(tab2.this);
                pres.likedposts(uid);
            }
        });
        addPost=(FloatingActionButton) view.findViewById(R.id.fab);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(),AddPost.class));

            }
        });

        return view;
    }

    @Override
    public void setResult(String res) {
        InfoList.clear();
        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                int commentNO = object.getInt("numcomments");
                int likeNo = object.getInt("numlikes");
                int uid = object.getInt("user_id");
                int postid = object.getInt("post_id");
                String name = object.getString("u_name");
                String post = object.getString("message");
                String hours ="5h";
                String utype = object.getString("u_type");
                ///object.getString("post_date");
                InfoList.add(
                        new TimeLineInfo(
                                name,post
                                ,hours,likeNo,commentNO,
                                R.drawable.profileimg,uid,postid,utype));
            }
        }
        catch (Exception e){}

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TimeLineAdapter adapter = new TimeLineAdapter(tab2.this.getActivity(), InfoList,likedposts);
        recyclerView.setAdapter(adapter);
        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)view.findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.GONE);
        PullRefreshLayout layout = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        layout.setRefreshing(false);

    }

    @Override
    public void setlikedResult(String resu) {

        try {
            JSONArray jsonArray = new JSONArray(resu);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                int postid = object.getInt("post_id");
                likedposts.add(postid);
            }
            pres.getTimeline();
        }
        catch (Exception e){}

    }

    @Override
    public void deleteSuccess() {

    }


}
