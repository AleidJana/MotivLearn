package com.example.jana.motivlearn.presenter;

/**
 * Created by reemaibrahim on 17/02/2018 AD.
 */

public interface displayfillBlanckPresenter {
    void peformdisplayfillBlanck(int challenge_id);
    void crrectAnswer(int user_id ,int challenge_id , String stutes  , String skillType , int rateValue , int coins, int badge);
    void selectRank( int user_id,  int challenge_id,  String stutes,  String skillType,  int rateValue, String field);
}
