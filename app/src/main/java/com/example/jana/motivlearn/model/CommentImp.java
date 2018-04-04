package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.presenter.CommentPresenter;
import com.example.jana.motivlearn.view.CommentView;
import com.example.jana.motivlearn.view.TimelineView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jana on 4/1/2018 AD.
 */

public class CommentImp implements CommentPresenter {
    CommentView ve;
    private String result;
    public CommentImp(CommentView t)
    {
        ve= t;
    }

    @Override
    public String getComments(int postid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/21_viewPostComments/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&postId="+postid, params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        ve.setResult(res);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );
        return result;
    }
    @Override
    public void addComment(String massage,int userid,int postid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.get("https://api.appery.io/rest/1/apiexpress/api/25_addComment/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&id="+userid+"&masseg="+massage+"&postId="+postid, params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        //Toast.makeText((Context) vue, res,Toast.LENGTH_SHORT).show();
                        ve.addCommentSuccess();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        //  Toast.makeText((Context) vue, res,Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }


}
