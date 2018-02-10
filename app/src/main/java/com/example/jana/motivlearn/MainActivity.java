package com.example.jana.motivlearn;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {


                switch (tabId)
                {
                    case R.id.tab_Profile:
                    {
                        Fragment t1 = new tab1();
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
                        Fragment t1 = new tab5();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, t1);
                        transaction.commit();
                    }
                    break;
                }

            }
        });
    }
}

