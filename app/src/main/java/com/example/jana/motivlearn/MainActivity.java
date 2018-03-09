package com.example.jana.motivlearn;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jana.motivlearn.model.challengeBoardImp;
import com.example.jana.motivlearn.presenter.challengeBoardPresenter;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    public static FragmentManager fm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();


        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {


                switch (tabId)
                {
                    case R.id.tab_Profile:
                    {
                        Fragment t1;
                        SharedPreferences sp1= MainActivity.this.getSharedPreferences("Login", MODE_PRIVATE);
                        String type =sp1.getString("user_type", null);
                        if(type.equals("S"))
                            t1 = new tab1();
                        else
                            t1 = new TeacherProfile();

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, t1);
                        transaction.commit();
                    }
                    break;
                    case R.id.tab_Timeline:
                    {
                        Fragment t1 = new tab2();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, t1);
                        transaction.commit();
                    }
                    break;
                    case R.id.tab_Leaderboard:
                    {
                        Fragment t1 = new tab3();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, t1);
                        transaction.commit();
                    }
                    break;
                    case R.id.tab_Challengeboard:
                    {
                        Fragment t1 = new tab4();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, t1);
                        transaction.commit();
                    }
                    break;
                    case R.id.tab_Services:
                    {
                        Fragment t1;
                        SharedPreferences sp1= MainActivity.this.getSharedPreferences("Login", MODE_PRIVATE);
                        String type =sp1.getString("user_type", null);
                        if(type.equals("T"))
                            t1 = new tab5();
                        else
                            t1 = new StudentServices();

                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, t1);
                            transaction.commit();
                    }
                    break;
                }

            }
        });

        /*String s = getIntent().getStringExtra("id");
        if(s!=null)
        bottomBar.selectTabAtPosition(Integer.parseInt(s));*/

        fm = getFragmentManager();

    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}

