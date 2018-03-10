package com.example.jana.motivlearn;

import java.io.Serializable;

/**
 * Created by jana on 2/19/2018 AD.
 */

public class TimeLineInfo implements Serializable {
    private String Name;
    private String content;
    private String  hours;
    private int likes;
    private int comment;
    private int image;
    private int userid;
    private int postid;
    private String usertype;

    public TimeLineInfo(String name, String content, String hours, int likes, int comment, int image, int userid, int postid,String utype) {
        Name = name;
        this.content = content;
        this.hours = hours;
        this.likes = likes;
        this.comment = comment;
        this.image = image;
        this.userid = userid;
        this.postid = postid;
        usertype=utype;
    }

    public String getName() {
        return Name;
    }

    public String getContent() {
        return content;
    }


    public String getHours() {
        return hours;
    }

    public int getLikes() {
        return likes;
    }

    public int getComment() {
        return comment;
    }

    public int getImage() {
        return image;
    }

    public int getUserid() {
        return userid;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public int getPostid() {
        return postid;
    }
}
