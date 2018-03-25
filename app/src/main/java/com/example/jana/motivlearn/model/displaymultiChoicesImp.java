package com.example.jana.motivlearn.model;

import android.widget.Toast;

import com.example.jana.motivlearn.displaychoice;
import com.example.jana.motivlearn.presenter.displayChoicePresenter;
import com.example.jana.motivlearn.view.displayChoiceView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by reemaibrahim on 15/02/2018 AD.
 */

public class displaymultiChoicesImp implements displayChoicePresenter {
    displayChoiceView displaychoice;

    int coins1;
    int rank;
    private int coins;


    public displaymultiChoicesImp(displaychoice displaychoice) {
        this.displaychoice=displaychoice;
    }



    @Override
    public void peformDisplayChoice(int challenge_id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHandle requestHandle = client.get("https://api.appery.io/rest/1/apiexpress/api/4_TakePublicChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&id=" + challenge_id, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                displaychoice.displayFailed();


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                responseString = responseString.substring(1,responseString.length()-1);
             //   displaychoice.displaySuccess();

                try {
                    JSONObject obj = new JSONObject(responseString);
                    coins=obj.getInt("coins");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                displaychoice.setR(responseString);

            }

        });




    }

    @Override
    public void crrectAnswer(int user_id, int challenge_id, String status, String skillType, int rateValue , int rank) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final String status2=status;
        if(status.equals("timeout")) {
            status="fail" ;
        }
            RequestHandle requestHandle1 = client.get("https://api.appery.io/rest/1/apiexpress/api/5_TakePublicChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid=" + user_id + "&cid=" + challenge_id + "&coins=" + rank + "&status=" + status + "&rateValue=" + rateValue + "&skill=" + skillType, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    displaychoice.displayFailed();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    displaychoice.correct(coins1, status2);
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
                           // rank=rank-1;

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



