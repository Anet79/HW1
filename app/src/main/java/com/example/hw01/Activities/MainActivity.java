/*package com.example.hw01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw01.R;
import com.example.hw01.player;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageButton main_BTN_left;
    private ImageButton main_BTN_right;
    private ImageButton main_BTN_up;
    private ImageButton main_BTN_down;
    private ImageView[] main_IMG_heart;
    private TextView main_TXT_score;
    private ImageView[][] main_IMG_matrix_view;
    private int score = 0;
    private int lives = 3;
    private Timer timer;
    private player police;
    private player thief;
    private final int DELAY = 1000;
    private SensorManager sensorManager;
    private Sensor accSensor;

    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }

    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        findViews();
        initButton();

        police = new player();
        thief = new player();
        startGame();
        actionClicked();

    }

    private void findViews() {
        main_BTN_up = findViewById(R.id.main_BTN_up);
        main_BTN_down = findViewById(R.id.main_BTN_down);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_TXT_score=findViewById(R.id.main_TXT_score);
        //array for hearts
        main_IMG_heart = new ImageView[]{
                findViewById(R.id.main_IMG_heart_left),
                findViewById(R.id.main_IMG_heart_mid),
                findViewById(R.id.main_IMG_heart_right)
        };
        //matrix for players
        main_IMG_matrix_view=new ImageView[][]{
                {findViewById(R.id.main_IMG_matrix_1),findViewById(R.id.main_IMG_matrix_2),findViewById(R.id.main_IMG_matrix_3),findViewById(R.id.main_IMG_matrix_4),findViewById(R.id.main_IMG_matrix_5)},
                {findViewById(R.id.main_IMG_matrix_6),findViewById(R.id.main_IMG_matrix_7),findViewById(R.id.main_IMG_matrix_8),findViewById(R.id.main_IMG_matrix_9), findViewById(R.id.main_IMG_matrix_10)},

                {findViewById(R.id.main_IMG_matrix_11),findViewById(R.id.main_IMG_matrix_12), findViewById(R.id.main_IMG_matrix_13),findViewById(R.id.main_IMG_matrix_14),findViewById(R.id.main_IMG_matrix_15)},

                {findViewById(R.id.main_IMG_matrix_16),findViewById(R.id.main_IMG_matrix_17),findViewById(R.id.main_IMG_matrix_18),findViewById(R.id.main_IMG_matrix_19),findViewById(R.id.main_IMG_matrix_20)},
                {findViewById(R.id.main_IMG_matrix_21), findViewById(R.id.main_IMG_matrix_22),findViewById(R.id.main_IMG_matrix_23),findViewById(R.id.main_IMG_matrix_24),findViewById(R.id.main_IMG_matrix_25)},

                {findViewById(R.id.main_IMG_matrix_26),findViewById(R.id.main_IMG_matrix_27),findViewById(R.id.main_IMG_matrix_28),findViewById(R.id.main_IMG_matrix_29),findViewById(R.id.main_IMG_matrix_30)}

        };


    }


    //update life for the game
    private void updateLifeUI() {
        for (int i = 0; i < main_IMG_heart.length; i++) {
            main_IMG_heart[i].setVisibility(lives > i ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void startGame() {
        int startDirection = -1;
        int directionForPoliceInX = 0;
        int directionForPoliceInY = 5;
        int directionForThiefInX = 4;
        int directionForThiefInY = 0;
        police.setDirection(startDirection);
        police.setCurrentDirectionInY(directionForPoliceInY);
        police.setCurrentDirectionInX(directionForPoliceInX);
        thief.setDirection(startDirection);
        thief.setCurrentDirectionInY(directionForThiefInY);
        thief.setCurrentDirectionInX(directionForThiefInX);
        updateUI(directionForPoliceInX,directionForPoliceInY,R.drawable.img_police);
        updateUI(directionForThiefInX,directionForThiefInY,R.drawable.img_thief);


    }

    private void updateForUI(){
        if(lives>0){
            MoveForThief();

            if(police.getCurrentDirectionInX()==thief.getCurrentDirectionInX()&&police.getCurrentDirectionInY()==thief.getCurrentDirectionInY()){
                updateUI(thief.getCurrentDirectionInX(),thief.getCurrentDirectionInY(),0);
                updateUI(police.getCurrentDirectionInX(),police.getCurrentDirectionInY(),0);

                lives--;
                if(lives==0){
                    onStop();
                }
                else {

                    Toast.makeText(getApplicationContext(),"Your Catch",Toast.LENGTH_LONG).show();
                }
                updateLifeUI();
                startGame();
                return;
            }
            updateThePolice();

        }

    }

    /*
    direction:
    left-0
    right-1
    up-2
    down-3
     */
/*
    private void MoveForThief(){
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
        updateUI(thief.getCurrentDirectionInX(),thief.getCurrentDirectionInY(),R.drawable.img_thief);
    }





private void updateThePolice(){
    MoveForPolice( police.getDirection());
}

private void MoveForPolice(int direction){
    int tempDirectionPolice;
    tempDirectionPolice=checkIfCanMove(direction, police.getCurrentDirectionInX(), police.getCurrentDirectionInY());

       if(tempDirectionPolice!=-1) {
           thief.setDirection(tempDirectionPolice);
//           main_IMG_matrix_view[thief.getCurrentDirectionInY()][thief.getCurrentDirectionInX()].setVisibility(View.INVISIBLE);
           main_IMG_matrix_view[police.getCurrentDirectionInY()][police.getCurrentDirectionInX()].setImageResource(0);

           if (tempDirectionPolice == 0 || tempDirectionPolice == 1) {
               police.setCurrentDirectionInX(updateIndex(tempDirectionPolice, police.getCurrentDirectionInX()));
           } else {
               police.setCurrentDirectionInY(updateIndex(tempDirectionPolice, police.getCurrentDirectionInY()));
           }
       }

    updateUI(police.getCurrentDirectionInX(),police.getCurrentDirectionInY(),R.drawable.img_police);

}





   private int updateIndex(int direction,int currentDirection){
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


    private void updateUI(int currentDirectionInX,int currentDirectionInY,int src){
            main_IMG_matrix_view[currentDirectionInY][currentDirectionInX].setImageResource(src);
    }

    private int checkIfCanMove(int rand,int directInX,int directInY){
        //check if can left
        if(directInX != 0 && rand==0)
            return 0;
        //check if can right
        if(directInX !=2 && rand==1)
            return 1;
        //check if can up
        if(directInY !=0 && rand==2)
            return 2;
        //check if can down
        if(directInY!=4 && rand==3)
            return 3;

        return -1;
    }

    private void actionClicked() {
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
            timerStatus = TIMER_STATUS.OFF;
        } else {
            startTimer();

        }
    }

    private void tick() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateForUI();
                score++;
                main_TXT_score.setText(String.valueOf(score));

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
            timerStatus = TIMER_STATUS.PAUSE;
        }
        Toast.makeText(getApplicationContext(),"Game Over",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timerStatus == TIMER_STATUS.PAUSE) {
            startTimer();
        }
    }
    private void startTimer() {
        timerStatus = TIMER_STATUS.RUNNING;
        timer=new Timer();
        //startGame();


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, DELAY);
    }
    private void stopTimer() {

        timer.cancel();
    }
    private void initButton(){

        main_BTN_down.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                police.setDirection(3);
                //upDateTheThief();


            }
        }));
        main_BTN_up.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                police.setDirection(2);
               // upDateTheThief();
             //   MoveForThief(2);

            }
        }));
        main_BTN_right.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                police.setDirection(1);
               // upDateTheThief();
             //   MoveForThief(1);

            }
        }));
        main_BTN_left.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                police.setDirection(0);
               // upDateTheThief();
               // MoveForThief(0);

            }
        }));
    }


}*/
