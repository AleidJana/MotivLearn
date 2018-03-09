package com.example.jana.motivlearn.model;

import android.content.Context;
import android.widget.Toast;

import com.example.jana.motivlearn.RecordAchievement;
import com.example.jana.motivlearn.TeacherProfile;
import com.example.jana.motivlearn.presenter.myProfilePresenter;
import com.example.jana.motivlearn.tab1;
import com.example.jana.motivlearn.userSprofile;
import com.example.jana.motivlearn.userTprofile;
import com.example.jana.motivlearn.view.myProfileView;
import com.example.jana.motivlearn.view.recordAchievementView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 3/6/2018 AD.
 */

public class myProfileImp implements myProfilePresenter {

    private myProfileView vue;
   /* private TeacherProfile vue0;
    private userTprofile vue1;
    private userSprofile vue2;*/



    public myProfileImp(tab1 v)
    { vue = v;}

    public myProfileImp(TeacherProfile v)
    { vue = v;}

    public myProfileImp(userTprofile v)
    { vue = v;}

    public myProfileImp(userSprofile v)
    { vue = v;}

    @Override
    public void getUserInfo(int uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/8_ViewProfile/" +
                "?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid="+uid, params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                     //   Toast.makeText((Context) vue,"owwww", Toast.LENGTH_SHORT).show();
                        vue.displayInfo(res);

                    /*    else
                            vue2.displayInfo(res);*/

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );
    }

}
