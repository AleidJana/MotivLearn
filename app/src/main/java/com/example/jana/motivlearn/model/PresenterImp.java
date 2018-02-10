package com.example.jana.motivlearn.model;

import android.text.TextUtils;
import android.widget.RadioButton;
import com.example.jana.motivlearn.presenter.RegisterPresenter;


import com.example.jana.motivlearn.view.RegisterView;
import com.loopj.android.http.*;
 import cz.msebera.android.httpclient.Header;
/**
 * Created by reemaibrahim on 04/02/2018 AD.
 */

public class PresenterImp  implements RegisterPresenter {
    RegisterView mRegisterView;
    String type;

    public PresenterImp(RegisterView RegisterView) {

        this.mRegisterView = RegisterView;

    }

    @Override
    public void performRegister(String userName, String email, String password, String conPassword, RadioButton depament) {

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conPassword)) {
            mRegisterView.registerValidations();
        } else {
            if(email.contains("@"))
            { if ((email.substring(email.indexOf("@"))).toLowerCase().equals("@student.ksu.edu.sa") || (email.substring(email.indexOf("@"))).toLowerCase().equals("@ksu.edu.sa")) {
                if (password.equals(conPassword)) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();

                    if (email.substring(email.indexOf("@")).equals("@student.ksu.edu.sa")) {
                        type = "S";
                    } else {
                        type = "T";
                    }

                    client.get("https://api.appery.io/rest/1/apiexpress/api/1_Register/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4&utype=" + type + "&uname=" + userName + "&uemail=" + email + "&upassword=" + password + "&udepartment=" + depament.getText(), params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            mRegisterView.registerError();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            mRegisterView.registerSuccess();

                        }
                    });
                } else {
                    mRegisterView.passwordMatch();
                }

            }}
            else {
                mRegisterView.emailError();
            }

        }
    }
}







































