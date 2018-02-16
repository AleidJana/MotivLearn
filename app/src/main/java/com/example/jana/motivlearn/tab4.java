package com.example.jana.motivlearn;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.example.jana.motivlearn.model.challengeBoardImp;
import com.example.jana.motivlearn.presenter.challengeBoardPresenter;
import com.example.jana.motivlearn.view.challengeBoardView;
import com.victor.loading.newton.NewtonCradleLoading;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;


public class tab4 extends Fragment implements challengeBoardView {
    List<question> challengeList = new ArrayList<>();
    challengeBoardPresenter pres;
    private String res;
    View view;
    RecyclerView recyclerView;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_tab4, container, false);

          SharedPreferences sp1= this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
          int uid =sp1.getInt("user_id", 0);
          pres = new challengeBoardImp(tab4.this);
          pres.getChallengeBoard(uid);

        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)view.findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.VISIBLE);
        newtonCradleLoading.start();

        PullRefreshLayout layout = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferences sp1= getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                int uid =sp1.getInt("user_id", 0);
                pres = new challengeBoardImp(tab4.this);
                pres.getChallengeBoard(uid);
            }
        });

      return view;
    }

    @Override
    public void setResult(String res) {
        challengeList.clear();
        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                int cid = object.getInt("challenge_id");
                int duration = object.getInt("duration");
                String writer = object.getString("u_name");
                String type = object.getString("type");
                String field = object.getString("field");
                String title = object.getString("challenge_title");

                switch(type)
                {
                    case "MC":
                        type = "choice";
                        break;
                    case "CO":
                        type = "code";
                        break;
                    case "FB":
                        type = "fillblank";
                        break;
                    case "PZ":
                        type = "puzzle";
                        break;
                }

                int typeid = getResources().getIdentifier(type, "drawable", getActivity().getPackageName());
                int fieldid = getResources().getIdentifier(field, "drawable", getActivity().getPackageName());

                challengeList.add(new question(
                        cid,
                        title,
                        writer,
                        typeid,
                        fieldid,
                        duration
                ));
            }
        }
        catch (Exception e){}

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Adapter adapter = new Adapter(tab4.this.getActivity(), challengeList);

        recyclerView.setAdapter(adapter);
        NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)view.findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setVisibility(View.GONE);

        PullRefreshLayout layout = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        layout.setRefreshing(false);
    }
}
