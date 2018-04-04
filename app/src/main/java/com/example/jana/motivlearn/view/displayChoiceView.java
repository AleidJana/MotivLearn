package com.example.jana.motivlearn.view;

/**
 * Created by reemaibrahim on 15/02/2018 AD.
 */

public interface displayChoiceView {
    void displayFailed();
    void setR(String responseString);
    void correct(int coinns, String status, int badge);
}
