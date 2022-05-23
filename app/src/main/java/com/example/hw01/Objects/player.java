package com.example.hw01.Objects;

public class player {
    private int direction =0;
    private int currentDirectionInX=0;
    private int currentDirectionInY=0;

    public player() {
    }


    public player(int direction, int currentDirectionInX, int currentDirectionInY) {
        this.direction = direction;
        this.currentDirectionInX = currentDirectionInX;
        this.currentDirectionInY = currentDirectionInY;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getCurrentDirectionInX() {
        return currentDirectionInX;
    }

    public void setCurrentDirectionInX(int currentDirectionInX) {
        this.currentDirectionInX = currentDirectionInX;
    }

    public int getCurrentDirectionInY() {
        return currentDirectionInY;
    }

    public void setCurrentDirectionInY(int currentDirectionInY) {
        this.currentDirectionInY = currentDirectionInY;
    }
}
