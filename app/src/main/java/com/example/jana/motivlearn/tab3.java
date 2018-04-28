package com.example.jana.motivlearn;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.jana.motivlearn.model.challengeBoardImp;
import com.example.jana.motivlearn.model.leaderBoardImp;
import com.example.jana.motivlearn.presenter.leaderBoardPresenter;
import com.example.jana.motivlearn.view.leaderBoardView;
import com.victor.loading.newton.NewtonCradleLoading;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.jana.motivlearn.tab2.pres;

public class tab3 extends Fragment implements leaderBoardView {
    leaderBoardPresenter pres;
    TabHost tabHost;
    List<UserInfo> studentList;
    List<UserInfo> teacherList;
    //the recyclerview
    RecyclerView SrecyclerView;
    RecyclerView TrecyclerView;
    TabHost.TabSpec spec;
    TabHost host;
    View view;
    FloatingActionButton share;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.fragment_tab3, container, false);
        pres = new leaderBoardImp(tab3.this);
        pres.getLeaderBoard();
        share=(FloatingActionButton) view.findViewById(R.id.fab);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to share your rank on twitter ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                       //here we will add share rank on twitter code
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Share Rank");
                alert.show();
            }
        });
        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)view.findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.VISIBLE);
        newtonCradleLoading.start();

        PullRefreshLayout layout = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pres = new leaderBoardImp(tab3.this);
                pres.getLeaderBoard();
            }
        });

        PullRefreshLayout layout2 = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout2);
        layout2.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pres = new leaderBoardImp(tab3.this);
                pres.getLeaderBoard();
            }
        });

        host = (TabHost)view.findViewById(R.id.tabHost);
        host.setup();

        spec = host.newTabSpec("Teacher");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Teacher");
        host.addTab(spec);

        spec = host.newTabSpec("Student");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Student");
        host.addTab(spec);

        studentList = new ArrayList<>();
        teacherList = new ArrayList<>();

        return view;
    }



    @Override
    public void setStudent(String res) {
    //    Toast.makeText(getActivity(), res, Toast.LENGTH_LONG).show();

        studentList.clear();
        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                int uid = object.getInt("user_id");
                String name = object.getString("u_name");
                int coins = object.getInt("u_coins");

                String s;
                switch(i+1)
                {
                    case 1:case 2:case 3:
                    s = "medal"+(i+1);
                        break;
                    default:
                        s = "medal";
                }

                int img;

                    img = getResources().getIdentifier(s, "drawable", getActivity().getPackageName());

                studentList.add(new UserInfo(
                        uid,
                        name,
                        "S",
                        coins,
                        i+1,
                        img
                ));
            }
        }
        catch (Exception e){}

        SrecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
        SrecyclerView.setHasFixedSize(true);
        SrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LeaderBoardAdapter adapter2 = new LeaderBoardAdapter(getActivity(), studentList);
        SrecyclerView.setAdapter(adapter2);

        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)view.findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.GONE);

        PullRefreshLayout layout = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout2);
        layout.setRefreshing(false);

    }

    @Override
    public void setTeacher(String res) {

           teacherList.clear();
        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                int uid = object.getInt("user_id");
                String name = object.getString("u_name");
                int coins = object.getInt("u_coins");

                String s;
                switch(i+1)
                {
                    case 1:case 2:case 3:
                    s = "medal"+(i+1);
                        break;
                    default:
                        s = "medal";
                }

                int img;

                    img = getResources().getIdentifier(s, "drawable", getActivity().getPackageName());

               teacherList.add(new UserInfo(
                        uid,
                        name,
                        "T",
                        coins,
                        i+1,
                        img
                ));
            }
        }
        catch (Exception e){}
        //creating recyclerview adapter

        TrecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        TrecyclerView.setHasFixedSize(true);
        TrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LeaderBoardAdapter adapter = new LeaderBoardAdapter(getActivity(), teacherList);

        //setting adapter to recyclerview
        TrecyclerView.setAdapter(adapter);


        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)view.findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.GONE);

        PullRefreshLayout layout = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        layout.setRefreshing(false);


    }
}
