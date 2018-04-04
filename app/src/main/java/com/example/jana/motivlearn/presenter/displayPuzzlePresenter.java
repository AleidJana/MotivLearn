package com.example.jana.motivlearn.presenter;

/**
 * Created by haifamajid on 3/30/2018 AD.
 */

public interface displayPuzzlePresenter {
    void peformGetPuzzle(int challenge_id);
    void crrectAnswer(int user_id ,int challenge_id , String stutes  , String skillType , int rateValue , int coins, int badge);
    void selectRank(final int user_id, final int challenge_id, final String stutes, final String skillType, final int rateValue, String field);
}