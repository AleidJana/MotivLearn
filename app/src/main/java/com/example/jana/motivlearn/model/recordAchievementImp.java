package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.RecordAchievement;
import com.example.jana.motivlearn.presenter.recordAchievementPresenter;
import com.example.jana.motivlearn.view.recordAchievementView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 2/20/2018 AD.
 */

public class recordAchievementImp implements recordAchievementPresenter {
private recordAchievementView vue;



public recordAchievementImp(RecordAchievement v)
{ vue = v;}

    @Override
    public void updateUserCoins(final int uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/13_RecordAchievment/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid="+uid+"&skill=null", params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        try {
                            res = res.substring(1, res.length() - 1);
                            JSONObject obj = new JSONObject(res);
                            int ach = Integer.parseInt(obj.getString("achievement"));
                            if(ach == 10)
                            {
                                vue.addBadge(true);
                                addBadge(uid);
                            }
                            else
                                vue.addBadge(false);
                        }
                        catch(Exception e){}
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );
    }

    void addBadge(int uid)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/AddBadge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid="+uid+"&bid=4", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

            }
        });
    }
}
