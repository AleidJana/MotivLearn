package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.presenter.loginPresenter;
import com.example.jana.motivlearn.view.loginView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by haifamajid on 2/3/2018 AD.
 */

public class loginImp implements loginPresenter {
    private loginView logView;

    public loginImp(loginView logView) {
        this.logView = logView;
    }

    @Override
    public void login(String email, String password) {
        if(email.equals("") || password.equals(""))
          logView.loginFail("login_error_empty");
        else {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            //  params.put("key", "value");
            //   params.put("more", "data");
            client.get("https://api.appery.io/rest/1/apiexpress/api/2_login/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&email=" + email + "&password=" + password, params, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String res) {
                            // called when response HTTP status is "200 OK"
                            if (res.length() > 2)
                                logView.loginSuccess("login_success");
                            else
                                logView.loginFail("login_error_notFound");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            logView.loginFail("bad_connection");
                        }
                    }
            );
        }

    }

}
