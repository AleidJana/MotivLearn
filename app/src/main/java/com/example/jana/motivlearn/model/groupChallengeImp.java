package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.presenter.groupChallengePresenter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 4/24/2018 AD.
 */

public class groupChallengeImp implements groupChallengePresenter {

    @Override
    public void updateCoinsWinner(int uid, int coins) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(
                "https://api.appery.io/rest/1/apiexpress/api/GroupChallengeResult/?apiKey=cb85dda5-927f-4408-844b-44bb" +
                        "99347ed4&uid="+uid+"&coins="+coins, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    }
                });
    }

    @Override
    public void updateCoins(int uid, int coins) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(
                "https://api.appery.io/rest/1/apiexpress/api/updateCoins/?apiKey=cb85dda5-927f-4408-844b-" +
                        "44bb99347ed4&uid="+uid+"&coins="+coins, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    }
                });
    }
}
