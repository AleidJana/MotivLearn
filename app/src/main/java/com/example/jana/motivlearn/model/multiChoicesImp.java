package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.presenter.multiChoicesPresenter;
import com.example.jana.motivlearn.view.multiChoicesView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 2/12/2018 AD.
 */

public class multiChoicesImp implements multiChoicesPresenter {
    multiChoicesView vue;

    public multiChoicesImp(multiChoicesView mcView) {
        vue = mcView;
    }

    @Override
    public void addPublicChallenge(int user, String q, String c1, String c2, String c3, String c4,
                                   int id, String title, String field, int time, int coins) {


        int validChoices=0;
        if(!c1.equals(""))
            validChoices++;
        if(!c2.equals(""))
            validChoices++;
        if(!c3.equals(""))
            validChoices++;
        if(!c4.equals(""))
            validChoices++;

        if(q.length()<2)
            vue.multiChoiceFail("multichoices_emptyQuestion");

        else if(validChoices<=1)
            vue.multiChoiceFail("multichoices_fewChoices");

        else if(id == -1)
            vue.multiChoiceFail("multichoices_noAnswer");

      else if((id==0 && c1.equals("")) ||
                (id==1 && c2.equals("")) ||
                (id==2 && c3.equals("")) ||
                (id==3 && c4.equals("")))
        { vue.multiChoiceFail("multichoices_validAnswer"); }
      else
        { int counter = 0;
            JSONObject jo = new JSONObject();
                try {
                    if(!c1.equals(""))
                        jo.put("choice"+(++counter), c1);
                    if(!c2.equals(""))
                        jo.put("choice"+(++counter), c2);
                    if(!c3.equals(""))
                        jo.put("choice"+(++counter), c3);
                    if(!c4.equals(""))
                        jo.put("choice"+(++counter), c4);

                    if(id==0)
                        jo.put("answer", c1);

                    else if (id==1)
                        jo.put("answer", c2);

                    else if (id==2)
                        jo.put("answer", c3);

                    else if (id==3)
                        jo.put("answer", c4);


                } catch (Exception e) {
                }

                String answer = jo.toString();
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();

            client.get(
                    "https://api.appery.io/rest/1/apiexpress/api/7_CreatePublicChallenge/" +
                            "?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&type=MC&question="+q+
                            "&answer="+answer+"&time="+time+"&field="+field+"&uid="
                            +user+"&coins="+coins+"&title="+title, params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            vue.multiChoiceFail("bad_connection");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            vue.multiChoiceSuccess("successCreation");
                        }
                    });

        }



    }
}
