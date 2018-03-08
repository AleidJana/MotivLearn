package com.example.jana.motivlearn.presenter;

/**
 * Created by reemaibrahim on 15/02/2018 AD.
 */

public interface displayChoicePresenter {
    void peformDisplayChoice(int challenge_id);
   // String getS();
   void crrectAnswer(int user_id ,int challenge_id , String stutes , String skillType , int rateValue ,int rank);
void selectRank(int user_id, int challenge_id, String stutes, String skillType, int rateValue );
}
