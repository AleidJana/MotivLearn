package com.example.jana.motivlearn;

/**
 * Created by jana on 2/19/2018 AD.
 */

public class TimeLineInfo {
    private String Name;
    private String content;
    private String  hours;
    private int likes;
    private int comment;
    private int image;

    public TimeLineInfo(String name, String content, String hours, int likes, int comment, int image) {
        Name = name;
        this.content = content;
        this.hours = hours;
        this.likes = likes;
        this.comment = comment;
        this.image = image;
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

}
