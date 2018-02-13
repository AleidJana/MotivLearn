package com.example.jana.motivlearn.model;

import android.text.TextUtils;

import com.example.jana.motivlearn.presenter.CodeOutputPresenter;
import com.example.jana.motivlearn.view.CodeOutputView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by reemaibrahim on 10/02/2018 AD.
 */

public class CodeOutputImp implements CodeOutputPresenter{

    CodeOutputView codeOutputView ;

    public CodeOutputImp( CodeOutputView codeOutput){
        this.codeOutputView=codeOutput;

    }

    @Override
    public void performCodeOutput(String code, String outPut , String challengeTitle , String challengeType , String challengeField ,int challengeTime ,int challengeCoins) {
        if(TextUtils.isEmpty(code)|| TextUtils.isEmpty(outPut)){
            codeOutputView.codeOutputFail("codeoutput_empty");
        }
        //String type="CO";
       // String field ="java";
       // String title="firstQ";
       // int coins=5;
     //   int time=20;
        else {
            int userId = 3;
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            client.get(
                    "https://api.appery.io/rest/1/apiexpress/api/7_CreatePublicChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&type=" + challengeType + "&question=" + code + "&answer=" + outPut + "&time=" + challengeTime + "&field=" + challengeField + "&uid=" + userId + "&coins=" + challengeCoins + "&title=" + challengeTitle, params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            codeOutputView.codeOutputFail("bad_connection");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            codeOutputView.codeOutputSuccess("successCreation");
                        }
                    });
        }
    }
}

