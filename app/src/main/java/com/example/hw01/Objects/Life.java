package com.example.hw01.Objects;

public class Life {

    private int lives ;

    public Life() {
        lives=3;
    }


    public int getLives() {
        return lives;
    }

    public int reduceLives() {
        return lives--;
    }


}
