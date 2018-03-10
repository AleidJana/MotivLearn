package com.example.jana.motivlearn.presenter;

/**
 * Created by jana on 3/4/2018 AD.
 */

public interface TimelinePresenter {
    String getTimeline();
    void deletepost(int postid);
    void addlike(int postid,int userid);
    String likedposts(int userid);
}
