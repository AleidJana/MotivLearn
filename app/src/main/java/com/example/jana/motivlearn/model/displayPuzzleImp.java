package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.displayPuzzle;
import com.example.jana.motivlearn.displayfillBlanck;
import com.example.jana.motivlearn.presenter.displayPuzzlePresenter;
import com.example.jana.motivlearn.presenter.displayfillBlanckPresenter;
import com.example.jana.motivlearn.view.displayPuzzleView;
import com.example.jana.motivlearn.view.displayfillBlanckView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 3/30/2018 AD.
 */

public class displayPuzzleImp implements displayPuzzlePresenter
{
        displayPuzzleView vue ;
        int coins1;
        int rank;
        public int    coins;

    public displayPuzzleImp (displayPuzzle v ){
        vue =v;

    }


        @Override
        public void peformGetPuzzle(int challenge_id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHandle requestHandle = client.get("https://api.appery.io/rest/1/apiexpress/api/4_TakePublicChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&id=" + challenge_id, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                vue.displayFailed();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                responseString = responseString.substring(1,responseString.length()-1);
                try {
                    JSONObject obj = new JSONObject(responseString);
                    coins=obj.getInt("coins");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                vue.setChallenge(responseString);
            }
        });
    }

        @Override
        public void crrectAnswer(int user_id, int challenge_id, String stutes, String skillType, int rateValue , final int coins, final int badge) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final String status2=stutes;
        if(stutes.equals("timeout")||stutes.equals("fialBack")) {
            stutes="fail" ;
        }
        RequestHandle requestHandle = client.get("https://api.appery.io/rest/1/apiexpress/api/5_TakePublicChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&uid="+user_id+"&cid="+challenge_id+"&coins="+coins+"&status="+stutes+"&rateValue="+rateValue+"&skill="+skillType, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                vue.displayFailed();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                vue.correct(coins, status2, badge);

            }
        });

    }


    @Override //this function will be called just if the user answered the challenge correctly
    public void selectRank(final int user_id, final int challenge_id, final String stutes,
                           final String skillType, final int rateValue, final String field) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        //send request to the API to get the student rank between
        //all students who answerd this challenge correctly
        RequestHandle requestHandle = client.get("https://api.appery.io/rest/1/apiexpress" +
                "/api/selectRank?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&challengeId="
                + challenge_id + "&field=" + field + "&uid=" + user_id, params,
                new TextHttpResponseHandler() {

            @Override //In case of failed API connection
            public void onFailure(int status, Header[] headers, String response, Throwable throwable)
            {}

            @Override //after get API response successfully
            public void onSuccess(int status, Header[] headers, String response) {
                try {
                    response = response.substring(1,response.length()-1); //convert the response to JSON string format
                    JSONObject obj = new JSONObject(response); //convert the response to JSON format
                    JSONObject object = obj.getJSONObject("Branch1");
                    //this function will be called just if the user answered the challenge correctly
                    rank = object.getInt("rank"); // get the rank from API>DataBase

                    switch (rank) {
                        //check the rank to give the appropriate coins(challenge coins + Bounus) to the user
                        case 1:
                            coins1 = coins + 10;
                            break;
                        case 2:
                            coins1 = coins + 7;
                            break;
                        case 3:
                            coins1 = coins + 5;
                            break;
                        default:
                            coins1 = coins + 1;
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

    public int findBadge(int numOfpass, String field) {
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

