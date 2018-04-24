package com.example.jana.motivlearn.model;

import android.content.Context;
import android.widget.Toast;

import com.example.jana.motivlearn.WatchVideo;
import com.example.jana.motivlearn.presenter.watchVideoPresenter;
import com.example.jana.motivlearn.view.watchVideoView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 2/17/2018 AD.
 */

public class watchVideoImp implements watchVideoPresenter {
    watchVideoView vue;

    public watchVideoImp(WatchVideo v)
    {
        vue = v;
    }

    @Override
    public void getVideoLink(int uid)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/18_watchEducationalVideo1/" +
                "?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&userId="+uid, params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        if(res.length() > 3)
                            vue.setUrl(res);
                        else
                            vue.noVid();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );
    }

    @Override
    public void updateUserCoins(int uid, int vid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/19_WatchEducationalVider2/?" +
                "apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&userId="+uid+"&videoId="+vid, params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );

    }
}
