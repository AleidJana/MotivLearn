package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.displayCodeOutput;
import com.example.jana.motivlearn.displayfillBlanck;
import com.example.jana.motivlearn.presenter.displayCodeOutputPresenter;
import com.example.jana.motivlearn.view.displayCodeOutputView;
import com.example.jana.motivlearn.view.displayfillBlanckView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by reemaibrahim on 18/02/2018 AD.
 */

public class displayCodeOutputImp implements displayCodeOutputPresenter {

    int coins1;
    int rank;
    public int    coins;


    displayCodeOutputView displayCodeOutput1 ;

 public displayCodeOutputImp(displayCodeOutput displayCodeOutput){
     this.displayCodeOutput1=displayCodeOutput;
 }


    @Override
    public void peformdisplayfillBlanck(int challenge_id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHandle requestHandle = client.get("https://api.appery.io/rest/1/apiexpress/api/4_TakePublicChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&id=" + challenge_id, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                displayCodeOutput1.fileView();


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

             //   displayCodeOutput1.succesView();
                responseString = responseString.substring(1,responseString.length()-1);

                try {
                    JSONObject obj = new JSONObject(responseString);
                    coins=obj.getInt("coins");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                displayCodeOutput1.setR(responseString);





            }

        });

    }



    @Override
    public void crrectAnswer(int user_id, int challenge_id, String stutes, String skillType, int rateValue ,int coins) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final String status2=stutes;
        if(stutes.equals("timeout")) {
            stutes="fail" ;
        }
        RequestHandle requestHandle = client.get("https://api.appery.io/rest/1/apiexpress/api/5_TakePublicChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid="+user_id+"&cid="+challenge_id+"&coins="+coins+"&status="+stutes+"&rateValue="+rateValue+"&skill="+skillType, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                displayCodeOutput1.fileView();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                displayCodeOutput1.correct(coins1, status2);

            }
        });

    }


    @Override
    public void selectRank(final int user_id, final int challenge_id, final String stutes, final String skillType, final int rateValue) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHandle requestHandle = client.get("https://api.appery.io/rest/1/apiexpress/api/selectRank/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&challengeId=" + challenge_id, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    responseString = responseString.replace("[{","{");
                    responseString = responseString.replace("}]","}");
                    JSONObject obj = new JSONObject(responseString);
                    JSONObject object = obj.getJSONObject("Branch1");
                    rank = object.getInt("rank");
                  //  rank=rank-1;

                    switch (rank){
                        case 1:coins1=10+coins;
                            break;
                        case 2:coins1=5+coins;
                            break;
                        case 3:coins1=3+coins;
                            break;
                        default:coins1=1+coins;

                    }
                }
                catch(Exception e){

                }
                crrectAnswer(user_id, challenge_id,  stutes, skillType, rateValue,coins1);

            }
        });



    }




}
