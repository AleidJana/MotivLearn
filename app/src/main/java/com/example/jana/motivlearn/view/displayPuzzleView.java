package com.example.jana.motivlearn.view;

/**
 * Created by haifamajid on 3/30/2018 AD.
 */

public interface displayPuzzleView {

    void displayFailed();
    void setChallenge(String responseString);
    void correct(int coins, String status);
}
