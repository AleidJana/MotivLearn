package com.example.jana.motivlearn.model;

import android.text.TextUtils;

import com.example.jana.motivlearn.createChallenge;
import com.example.jana.motivlearn.presenter.CreateChallengePresenter;
import com.example.jana.motivlearn.view.CreateChallengeView;

/**
 * Created by reemaibrahim on 12/02/2018 AD.
 */

public class CreateChallengeImp implements CreateChallengePresenter {


    CreateChallengeView CreateChallengeView ;

    public CreateChallengeImp(Class<createChallenge> CreateChallenge ){
        this.CreateChallengeView=CreateChallengeView;
    }
    @Override
    public void pformvalidet(String title) {
        if (TextUtils.isEmpty(title)){
            CreateChallengeView.CreateChallengeValidations();

        }
    }
}
