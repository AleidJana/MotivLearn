package com.example.jana.motivlearn.presenter;

/**
 * Created by jana on 4/1/2018 AD.
 */

public interface CommentPresenter {
    String getComments(int post);
    void addComment(String massage,int userid,int postid);
}
