package com.example.jana.motivlearn.presenter;

import android.app.FragmentManager;

import com.example.jana.motivlearn.question;

import java.util.List;

/**
 * Created by haifamajid on 2/14/2018 AD.
 */

public interface challengeBoardPresenter {

    String getChallengeBoard(int uid);
    String deleteQuestion(int uid);
}
