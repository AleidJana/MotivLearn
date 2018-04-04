package com.example.jana.motivlearn.presenter;

/**
 * Created by reemaibrahim on 18/02/2018 AD.
 */

public interface displayCodeOutputPresenter {
    void peformdisplayfillBlanck(int challenge_id);
    void crrectAnswer(int user_id ,int challenge_id , String stutes , String skillType , int rateValue ,int coins, int badge);
   void selectRank(final int user_id, final int challenge_id, final String stutes, final String skillType, final int rateValue, String field);
}
