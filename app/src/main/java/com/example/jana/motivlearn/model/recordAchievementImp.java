package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.RecordAchievement;
import com.example.jana.motivlearn.presenter.recordAchievementPresenter;
import com.example.jana.motivlearn.view.recordAchievementView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 2/20/2018 AD.
 */

public class recordAchievementImp implements recordAchievementPresenter {
private recordAchievementView vue;



public recordAchievementImp(RecordAchievement v)
{ vue = v;}

    @Override
    public void updateUserCoins(int uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/13_RecordAchievment/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid="+uid+"&skill=null", params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        //  Toast.makeText((Context) vue, res,Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );
    }
}
