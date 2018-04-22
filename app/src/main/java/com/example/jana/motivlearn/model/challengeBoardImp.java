package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.presenter.challengeBoardPresenter;
import com.example.jana.motivlearn.question;
import com.example.jana.motivlearn.tab4;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by haifamajid on 2/14/2018 AD.
 */

public class challengeBoardImp implements challengeBoardPresenter{
        tab4 vue;
        private String result;

    public challengeBoardImp(tab4 t)
    {
        vue= t;
    }

    @Override
    public String getChallengeBoard(int uid) {
     AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/3_ViewChallengeBoard/?" +
                    "apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&id="+uid, params, new TextHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String res) {
                            vue.setResult(res);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                        }
                    }
            );
            return result;
        }

    @Override
    public String deleteQuestion(int uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/DeleteChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&cid="+uid, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

            }
        });


        return null;
    }
}
