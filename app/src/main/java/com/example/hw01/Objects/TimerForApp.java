/*package com.example.hw01.Objects;

import com.example.hw01.Activities.MainActivity;

import java.util.Timer;
import java.util.TimerTask;


public class TimerForApp {

    private Timer timer;
    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;
    private final int DELAY = 1000;



    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }

    private void stopTimer() {

        timer.cancel();
    }

    private void startTimer() {
        timerStatus = MainActivity.TIMER_STATUS.RUNNING;
        timer=new Timer();
        //startGame();


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, DELAY);
    }






}
        */