package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.presenter.fillBlankPresenter;
import com.example.jana.motivlearn.view.fillBlankView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 2/12/2018 AD.
 */

public class fillBlankImp implements fillBlankPresenter {
    private fillBlankView vue;


    public fillBlankImp(fillBlankView fbView) {
        vue = fbView;
    }

    @Override
    public void addPublicChallenge(int user, String question, String title
            , String field, int time, int coins) {
        if(question.length() < 2)
            vue.fillBlankFail("fillblank_empty");
        else if(!question.contains("{{") || !question.contains("}}"))
            vue.fillBlankFail("fillblank_noblank");
        else if(question.contains("write the answer here"))
            vue.fillBlankFail("fillblank_emptyblank");
        else {
        int lastIndex = 0;
        JSONObject jo = new JSONObject();
        for(int i=0 ; i<question.length() ; i++) {
            int first = question.indexOf("{{", lastIndex);
            int last = question.indexOf("}}", lastIndex);
            if (first == -1 || last == -1)
                break;
            String answer = question.substring(first+2, last);
            try {
                jo.put(i + "", answer);
            } catch (Exception e) {
            }
            question = (question.substring(0, first)+".......").concat(question.substring(last+2));
            lastIndex = last;
        }
            String abstractQuestion = question;

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();

            client.get(
                    "https://api.appery.io/rest/1/apiexpress/api/7_CreatePublicChallenge/" +
                            "?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&type=FB&question="
                            +abstractQuestion+"&answer="+jo.toString()+"&time="+time+"&field="+field+"&uid="
                            +user+"&coins="+coins+"&title="+title, params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            vue.fillBlankFail("bad_connection");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            vue.fillBlankSuccess("successCreation");
                        }
                    });

        }
    }


    @Override
    public void suggestFillBlank(int user, String question, String title
            , String field, int time, int coins) {
        if(question.length() < 2)
            vue.fillBlankFail("fillblank_empty");
        else if(!question.contains("{{") || !question.contains("}}"))
            vue.fillBlankFail("fillblank_noblank");
        else if(question.contains("write the answer here"))
            vue.fillBlankFail("fillblank_emptyblank");
        else {
            int lastIndex = 0;
            JSONObject jo = new JSONObject();
            for(int i=0 ; i<question.length() ; i++) {
                int first = question.indexOf("{{", lastIndex);
                int last = question.indexOf("}}", lastIndex);
                if (first == -1 || last == -1)
                    break;
                String answer = question.substring(first+2, last);
                try {
                    jo.put(i + "", answer);
                } catch (Exception e) {
                }
                question = (question.substring(0, first)+".......").concat(question.substring(last+2));
                lastIndex = last;
            }
            String abstractQuestion = question;

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();

            client.get("https://api.appery.io/rest/1/apiexpress/api/SuggestQuestion/?" +
                    "apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&type=FB&question=" + abstractQuestion +
                    "&answer=" + jo.toString() + "&field=" + field +"&coins=" + coins + "&title=" + title+
                    "&uid=" + user + "&time=" + time, params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            vue.fillBlankFail("bad_connection");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            vue.fillBlankSuccess("successCreation");
                        }
                    });

        }
    }
}
