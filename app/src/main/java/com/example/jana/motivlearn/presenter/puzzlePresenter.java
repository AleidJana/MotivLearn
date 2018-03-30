package com.example.jana.motivlearn.presenter;

/**
 * Created by haifamajid on 3/30/2018 AD.
 */

public interface puzzlePresenter {

    void performPuzzle(int uid, String code , String outPut , String challengeTitle ,
                       String challengeType , String challengeField,int challengetime ,int challemgeCoins);

    void suggestPuzzle(int uid, String code , String outPut , String challengeTitle ,
                       String challengeType , String challengeField,int challengetime ,int challemgeCoins);

}
