package com.example.jana.motivlearn;

/**
 * Created by jana on 2/9/2018 AD.
 */


    public class question {
        private int id;
        private String title;
        private String writer;
        private String type;
        private int image;
        private int field;
        private int duration;

    public question(int id, String title, String writer, String type, int image, int field, int duration) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.type = type;
        this.image = image;
        this.field = field;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getType() {
        return type;
    }

    public int getImage() {
        return image;
    }

    public int getField() {
        return field;
    }

    public int getDuration() {
        return duration;
    }
}