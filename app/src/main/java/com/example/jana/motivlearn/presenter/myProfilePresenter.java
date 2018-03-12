package com.example.jana.motivlearn.presenter;

/**
 * Created by haifamajid on 3/6/2018 AD.
 */

public interface myProfilePresenter {

    void getUserInfo(int uid);
    void rateSkill(int rid , int uid, String skill, double val);
    void didRate(int rid , int uid, String skill);
}
