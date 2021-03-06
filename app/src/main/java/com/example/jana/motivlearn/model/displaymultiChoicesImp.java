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
        this.displaychoice = displaychoice;
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
                responseString = responseString.substring(1, responseString.length() - 1);
                //   displaychoice.displaySuccess();

                try {
                    JSONObject obj = new JSONObject(responseString);
                    coins = obj.getInt("coins");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                displaychoice.setR(responseString);

            }

        });


    }

    @Override
    public void crrectAnswer(int user_id, int challenge_id, String status, String skillType, int rateValue, int rank, final int badge) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final String status2 = status;
        if (status.equals("timeout") || status.equals("fialBack")) {
            status = "fail";
        }
        RequestHandle requestHandle1 = client.get("https://api.appery.io/rest/1/apiexpress/api/5_TakePublicChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid=" + user_id + "&cid=" + challenge_id + "&coins=" + rank + "&status=" + status + "&rateValue=" + rateValue + "&skill=" + skillType, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                displaychoice.displayFailed();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                displaychoice.correct(coins1, status2, badge);
            }
        });

    }

    @Override
    public void selectRank(final int user_id, final int challenge_id, final String stutes, final String skillType, final int rateValue, final String field) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHandle requestHandle = client.get("https://api.appery.io/rest/1/apiexpress/api/selectRank/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&challengeId=" + challenge_id + "&field=" + field + "&uid=" + user_id, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    responseString = responseString.replace("[{", "{");
                    responseString = responseString.replace("}]", "}");
                    JSONObject obj = new JSONObject(responseString);
                    JSONObject object = obj.getJSONObject("Branch1");
                    rank = object.getInt("rank");
                    // rank=rank-1;

                    switch (rank) {
                        case 1:
                            coins1 = 10 + coins;
                            break;
                        case 2:
                            coins1 = 5 + coins;
                            break;
                        case 3:
                            coins1 = 3 + coins;
                            break;
                        default:
                            coins1 = 1 + coins;

                    }

                    if (field.equals("java") || field.equals("c") || field.equals("javascript")
                            || field.equals("html") || field.equals("css") || field.equals("php")) {
                        JSONObject object2 = obj.getJSONObject("Branch2");
                        int numOfpass = object2.getInt("numOfPass") + 1;

                        int badge = findBadge(numOfpass, field);

                        if(badge != 0)
                            addBadge(user_id, badge);

                        crrectAnswer(user_id, challenge_id, stutes, skillType, rateValue, coins1, badge);
                    }
                } catch (Exception e) {

                }


            }
        });


    }

    public void addBadge(int userId, int num)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://api.appery.io/rest/1/apiexpress/api/AddBadge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid="+userId+"&bid="+num, params, new TextHttpResponseHandler() {
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

    int findBadge(int numOfpass, String field) {
        if(numOfpass==50)
            return 12;

        switch (field) {
            case "java":
                if (numOfpass == 5)
                    return 17;
                else if (numOfpass == 10)
                    return 18;
                else if(numOfpass==20)
                    return 19;
                break;

            case "c":
                if (numOfpass == 5)
                    return 1;
                else if (numOfpass == 10)
                    return 2;
                else if(numOfpass==20)
                    return 3;
                break;

            case "html":
                if (numOfpass == 5)
                    return 23;
                else if (numOfpass == 10)
                    return 24;
                else if(numOfpass==20)
                    return 25;
                break;

            case "css":
                if (numOfpass == 5)
                    return 14;
                else if (numOfpass == 10)
                    return 15;
                else if(numOfpass==20)
                    return 16;
                break;

            case "javascript":
                if (numOfpass == 5)
                    return 20;
                else if (numOfpass == 10)
                    return 21;
                else if(numOfpass==20)
                    return 22;
                break;

            case "php":
                if (numOfpass == 5)
                    return 26;
                else if (numOfpass == 10)
                    return 27;
                else if(numOfpass==20)
                    return 28;
                break;

                default:
                    return 0;
        }

        return 0;
    }


}




