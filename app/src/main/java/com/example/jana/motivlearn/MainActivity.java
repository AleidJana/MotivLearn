package com.example.jana.motivlearn;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
public class MainActivity extends AppCompatActivity {
    public Fragment t1;
    public  String type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Here WE Getting The User Type Attribute From Shared Preferences To Check The User Type
        SharedPreferences sp1= MainActivity.this.getSharedPreferences("Login", MODE_PRIVATE);
        type =sp1.getString("user_type", null);
        // Identify the BottomBar View to set Listeners
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                // The Movement Between System Tabs Done By The Following Switch Statement
                switch (tabId)
                {
                    case R.id.tab_Profile:
                    {
                        if(type.equals("S"))
                            t1 = new tab1(); //Open Student Profile fragment
                        else
                            t1 = new TeacherProfile(); //Open Teacher Profile fragment
                    }
                    break;
                    case R.id.tab_Timeline: {t1 = new tab2();} //Open Timeline fragment
                    break;
                    case R.id.tab_Leaderboard: {t1 = new tab3();} //Open LeaderBoard fragment
                    break;
                    case R.id.tab_Challengeboard: {t1 = new tab4();} //Open ChallengeBoard fragment
                    break;
                    case R.id.tab_Services:
                    {
                        if(type.equals("T"))
                            t1 = new tab5(); //Open Student Services fragment
                        else
                            t1 = new StudentServices(); //Open Teacher Services fragment
                    }
                    break;
                }
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Change The Layout And Set The fragment Was Created In The Switch Based On The Clicked Tab
                transaction.replace(R.id.frame_layout, t1);
                transaction.commit();
            }
        });

      if(getIntent().hasExtra("nextFrag")) {
          String str = getIntent().getStringExtra("nextFrag");
          if (str.equals("cha"))
              bottomBar.selectTabAtPosition(3);
      }
    }
    @Override
    public void onBackPressed() {
    }
}

