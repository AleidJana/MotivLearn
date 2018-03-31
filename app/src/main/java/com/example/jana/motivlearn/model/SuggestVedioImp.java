package com.example.jana.motivlearn.model;

import android.text.TextUtils;

import com.example.jana.motivlearn.presenter.SuggestVedioPresenter;
import com.example.jana.motivlearn.view.SuggestVedioView;

import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by reemaibrahim on 09/02/2018 AD.
 */

public class SuggestVedioImp implements SuggestVedioPresenter {

    SuggestVedioView suggestVedioView;

    public  SuggestVedioImp(SuggestVedioView suggestVedioView){

        this.suggestVedioView=suggestVedioView;
    }

    @Override
    public void performSuggestVedio(String vedioLink, final int userId) {
        if(TextUtils.isEmpty(vedioLink)){
           // suggestVedioView.SuggestVedioValidations();
            suggestVedioView.suggestVrdioError();
        }
        else {
            if ((vedioLink.toLowerCase().contains("http://")) || (vedioLink.toLowerCase().contains("https://"))){
               // final int userId=1;
                final AsyncHttpClient client = new AsyncHttpClient();
                final RequestParams params = new RequestParams();
                client.get("https://api.appery.io/rest/1/apiexpress/api/27_SuggestVideo/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&id="+userId+"&link="+vedioLink, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                        suggestVedioView.suggestVrdioError();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        try {
                            response = response.substring(1, response.length() - 1);
                            JSONObject obj = new JSONObject(response);
                            int videos = Integer.parseInt(obj.getString("count(*)"));
                            if(videos == 5)
                            {
                                suggestVedioView.SuggestVedioValidations(true);
                                addBadge(userId);
                            }
                            else
                                suggestVedioView.SuggestVedioValidations(false);
                        }
                        catch(Exception e){}
                    }
                });
              }
            else {
                suggestVedioView.wrongFormat();
            }
        }

    }

    public void addBadge(int userId)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/AddBadge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid="+userId+"&bid=13", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //suggestVedioView.addBadgySuccess();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //suggestVedioView.addBadgySuccess();

            }
        });
    }
}

