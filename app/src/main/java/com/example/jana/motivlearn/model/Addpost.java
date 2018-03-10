package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.presenter.AddpostPresenter;
import com.example.jana.motivlearn.view.AddpostView;
import com.example.jana.motivlearn.view.SuggestVedioView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jana on 3/10/2018 AD.
 */

public class Addpost implements AddpostPresenter{
    AddpostView addpostView;
    public Addpost(AddpostView addpostView){

        this.addpostView=addpostView;
    }
    @Override
    public void addPost(String massage,int userid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.get("https://api.appery.io/rest/1/apiexpress/api/26_addPost/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&massage="+massage+"&userId="+userid, params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        //Toast.makeText((Context) vue, res,Toast.LENGTH_SHORT).show();
                        addpostView.addPostSuccess();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        //  Toast.makeText((Context) vue, res,Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }
}
