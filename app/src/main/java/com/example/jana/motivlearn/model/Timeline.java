package com.example.jana.motivlearn.model;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.jana.motivlearn.presenter.TimelinePresenter;
import com.example.jana.motivlearn.tab2;
import com.example.jana.motivlearn.view.TimelineView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jana on 3/4/2018 AD.
 */

public class Timeline implements TimelinePresenter {
    TimelineView vue;
    private String result;
    public Timeline(TimelineView t)
    {
        vue= t;
    }


    @Override
    public String getTimeline() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/20_viewTimeLine/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4", params, new TextHttpResponseHandler()
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
    public void deletepost(int postid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.get("https://api.appery.io/rest/1/apiexpress/api/22_deletePost/" +
                        "?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&postId="+postid, params, new TextHttpResponseHandler()
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

    @Override
    public void addlike(int postid,int userid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.get("https://api.appery.io/rest/1/apiexpress/api/23_likePost/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&postId="+postid+"&userId="+userid, params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                         //Toast.makeText((Context) vue, res,Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        //  Toast.makeText((Context) vue, res,Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

   @Override
    public String likedposts(int userid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/likedPosts/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid="+userid, params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        vue.setlikedResult(res);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );
        return result;
    }
}


