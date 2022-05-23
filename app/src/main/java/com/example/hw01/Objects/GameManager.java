package com.example.hw01.Objects;

import android.widget.ImageView;

import com.example.hw01.CallBacks.Label;
import com.example.hw01.player;

import java.util.ArrayList;

public class GameManager  {
    private int score=0;

    private Life life;
    private player police;
    private player thief;
    private int startDirection ;
    private int directionForPoliceInX ;
    private int directionForPoliceInY ;
    private int directionForThiefInX ;
    private int directionForThiefInY ;
    private int directionForCoinInX ;
    private int directionForCoinInY ;

    private float currentZ;
    private boolean flag;
    private int currPos;
    private Position position;
    private player coin;
    private int randCoin;
    private int distance;
    private String name;



   //  public GameManager(TopTen topTen, Position position, float currentZ){}
    public GameManager(){
        this.life=new Life();
        this.police = new player();
        this.thief = new player();
        this.coin=new player();
        this.startDirection = -1;
        this.currPos=-1;
        this.directionForPoliceInX = 0;
        this.directionForPoliceInY = 5;
        this.directionForThiefInX = 4;
        this.directionForThiefInY = 0;
        this.directionForCoinInY=1;
        this.directionForCoinInX=1;
        this.distance=0;
        this.flag=true;

    }

    public player getCoin() {
        return coin;
    }

    public void setCoin(player coin) {
        this.coin = coin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void updateLife(){
        life.reduceLives();
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getDistance() {
        return distance;
    }

    public int getLife() {
        return life.getLives();
    }

    public player getPolice() {
        return police;
    }

    public player getThief() {
        return thief;
    }

    public int getStartDirection() {
        return startDirection;
    }

    public int getDirectionForPoliceInX(){
        return police.getCurrentDirectionInX();
    }

    public int getDirectionForPoliceInY(){
        return police.getCurrentDirectionInY();
    }

    public int getDirectionForCoinInX() {
        return coin.getCurrentDirectionInX();
    }

    public int getDirectionForCoinInY() {
        return coin.getCurrentDirectionInY();
    }

    public int getDirectionForThiefInX(){
        return thief.getCurrentDirectionInX();
    }

    public int getDirectionForThiefInY(){
        return thief.getCurrentDirectionInY();
    }


    public int getScore(){
        return score;
    }


    public void updateCoinScore(){
        score+=10;
    }
    public void updateScore(){

        score++;
    }



    public void startGame(){
        police.setDirection(startDirection);
        police.setCurrentDirectionInY(directionForPoliceInY);
        police.setCurrentDirectionInX(directionForPoliceInX);
        thief.setDirection(startDirection);
        thief.setCurrentDirectionInY(directionForThiefInY);
        thief.setCurrentDirectionInX(directionForThiefInX);
        coin.setCurrentDirectionInX(directionForCoinInX);
        coin.setCurrentDirectionInY(directionForCoinInY);
    }

    public void setDirectionForPoliceInX(int directionForPoliceInX) {
        this.directionForPoliceInX = directionForPoliceInX;
    }

    public void setDirectionForPoliceInY(int directionForPoliceInY) {
        this.directionForPoliceInY = directionForPoliceInY;
    }

    public void setDirectionForThiefInX(int directionForThiefInX) {
        this.directionForThiefInX = directionForThiefInX;
    }

    public void setDirectionForThiefInY(int directionForThiefInY) {
        this.directionForThiefInY = directionForThiefInY;
    }

    public void setDirectionForCoinInX(int directionForCoinInX) {
        this.directionForCoinInX = directionForCoinInX;
    }

    public void setDirectionForCoinInY(int directionForCoinInY) {
        this.directionForCoinInY = directionForCoinInY;
    }

    private int updateIndex(int direction, int currentDirection){
        if(direction==0 ) {
            return currentDirection-1;

        }
        if(direction==2 ) {
            return currentDirection-1;

        }
        if(direction==1){

            return currentDirection+1;
        }
        if(direction==3){
            return currentDirection+1;
        }
        return currentDirection;
    }

    private int checkIfCanMove(int rand,int directInX,int directInY){
        //check if can left
        if(directInX != 0 && rand==0)
            return 0;
        //check if can right
        if(directInX !=4 && rand==1)
            return 1;
        //check if can up
        if(directInY !=0 && rand==2)
            return 2;
        //check if can down
        if(directInY!=5 && rand==3)
            return 3;

        return -1;
    }

    public void updateThePolice(ImageView[][] main_IMG_matrix_view){
        MoveForPolice( police.getDirection(),main_IMG_matrix_view);
    }

    public void setCurrPos(int currPos) {
        this.currPos = currPos;
    }

    public boolean isFlag() {
        return flag;
    }

    private void MoveForPolice(int direction, ImageView[][] main_IMG_matrix_view){
        int tempDirectionPolice;
        tempDirectionPolice=checkIfCanMove(direction, police.getCurrentDirectionInX(), police.getCurrentDirectionInY());

        if(tempDirectionPolice!=-1) {
            police.setDirection(tempDirectionPolice);
            main_IMG_matrix_view[police.getCurrentDirectionInY()][police.getCurrentDirectionInX()].setImageResource(0);

            if (tempDirectionPolice == 0 || tempDirectionPolice == 1) {
                police.setCurrentDirectionInX(updateIndex(tempDirectionPolice, police.getCurrentDirectionInX()));
            } else {
                police.setCurrentDirectionInY(updateIndex(tempDirectionPolice, police.getCurrentDirectionInY()));
            }
        }



    }

    public void MoveForThief(ImageView[][] main_IMG_matrix_view){
        int tempDirectionPolice = -1;
        int currentDirectionInX = thief.getCurrentDirectionInX();
        int currentDirectionInY=thief.getCurrentDirectionInY();
        while(tempDirectionPolice == -1) {
            int randomDirectionForPolice = (int) Math.floor(Math.random() * 4);
            tempDirectionPolice = checkIfCanMove(randomDirectionForPolice, currentDirectionInX, currentDirectionInY);
        }
        main_IMG_matrix_view[currentDirectionInY][currentDirectionInX].setImageResource(0);

        if(tempDirectionPolice==0||tempDirectionPolice==1){
            thief.setCurrentDirectionInX(updateIndex(tempDirectionPolice,currentDirectionInX));
        } else {
            thief.setCurrentDirectionInY(updateIndex(tempDirectionPolice, currentDirectionInY));
        }

    }



}
