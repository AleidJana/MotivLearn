package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.presenter.leaderBoardPresenter;
import com.example.jana.motivlearn.tab3;
import com.example.jana.motivlearn.tab4;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 2/16/2018 AD.
 */

public class leaderBoardImp implements leaderBoardPresenter {
    tab3 vue;
    private String result;

    public leaderBoardImp(tab3 t)
    {
        vue= t;
    }

    @Override
    public void getLeaderBoard() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/9_ViewLeaderboard/" +
                "?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4", params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        vue.setStudent(res);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );

        AsyncHttpClient client2 = new AsyncHttpClient();
        RequestParams params2 = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/10_ViewLeaderboard/" +
                "?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4", params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        vue.setTeacher(res);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );
    }
}

