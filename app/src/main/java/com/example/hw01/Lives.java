package com.example.hw01;

import android.widget.ImageView;

public class Lives {

    private int lives = 3;

    public Lives() {
    }


    public int getLives() {
        return lives;
    }

    public int reduceLive() {
        return lives--;
    }


}
