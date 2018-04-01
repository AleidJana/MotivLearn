package com.example.jana.motivlearn.model;

import android.net.Uri;
import android.text.TextUtils;

import com.example.jana.motivlearn.presenter.CodeOutputPresenter;
import com.example.jana.motivlearn.presenter.puzzlePresenter;
import com.example.jana.motivlearn.view.CodeOutputView;
import com.example.jana.motivlearn.view.puzzleView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.net.URI;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 3/30/2018 AD.
 */

public class puzzleImp implements puzzlePresenter {

    puzzleView vue ;

    public puzzleImp(puzzleView v){
        vue =v;

    }

    @Override
    public void performPuzzle(int userId, String question, String answer , String challengeTitle , String challengeType , String challengeField ,int challengeTime ,int challengeCoins) {
        if(TextUtils.isEmpty(question)){
            vue.puzzleFail("codeoutput_empty");
        }
        else {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            client.get(
                    "https://api.appery.io/rest/1/apiexpress/api/7_CreatePublicChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&type=PZ&question=" + question + "&answer=" + answer + "&time=" + challengeTime + "&field=" + challengeField + "&uid=" + userId + "&coins=" + challengeCoins + "&title=" + challengeTitle, params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            vue.puzzleFail("bad_connection");
                        }
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            vue.puzzleSuccess("successCreation");
                        }
                    });
        }
    }

    @Override
    public void suggestPuzzle(int userId, String question, String answer , String challengeTitle , String challengeType , String challengeField ,int challengeTime ,int challengeCoins) {
        if(TextUtils.isEmpty(question)){
            vue.puzzleFail("codeoutput_empty");
        }
        else {
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                // question = question.substring(1,question.length()-1);
                //encodeURIComponent()
                //URLEncoder.encode(question, "UTF-8")
                client.get(
                        "https://api.appery.io/rest/1/apiexpress/api/SuggestQuestion/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&type=PZ&question=" + URLEncoder.encode(question, "UTF-8") + "&answer=" + answer + "&field=" + challengeField + "&coins=" + challengeCoins + "&title=" + challengeTitle + "&uid=" + userId + "&time=" + challengeTime, params, new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                vue.puzzleFail("bad_connection");
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                vue.puzzleSuccess("successCreation");
                            }
                        });
            }
            catch(Exception e){}
        }
    }

}
