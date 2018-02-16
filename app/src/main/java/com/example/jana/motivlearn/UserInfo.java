package com.example.jana.motivlearn;

/**
 * Created by jana on 2/9/2018 AD.
 */


    public class UserInfo {
        private int id;
        private String Name;
        private int coins;
        private int order;
        private int image;

    public UserInfo(int uid, String name, int coins, int order, int image) {
        id = uid;
        Name = name;
        this.coins = coins;
        this.order = order;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public int getCoins() {
        return coins;
    }

    public int getOrder() {
        return order;
    }

    public int getImage() {
        return image;
    }
}