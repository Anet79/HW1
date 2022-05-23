package com.example.hw01.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hw01.CallBacks.CallBackSensor;
import com.example.hw01.Objects.GameManager;
import com.example.hw01.R;
import com.example.hw01.utils.GameUtils;
import com.example.hw01.utils.ScreenUtils;


import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity  {
    private ImageButton main_BTN_left;
    private ImageButton main_BTN_right;
    private ImageButton main_BTN_up;
    private ImageButton main_BTN_down;
    private ImageView[] main_IMG_heart;
    private TextView main_TXT_score;
    private ImageView[][] main_IMG_matrix_view;
    private GameManager gameManager;
    private Boolean coinCollected=true;
   private SensorsActivity sensorsActivity;
    private String name;
     private boolean sensorMode = false;
    public static final String SENSOR_MODE = "SENSOR_MODE";
    public static final String PLAYER_NAME="PLAYER_NAME";


    private Timer timer;
    private final int DELAY = 1000;

    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }

    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenUtils.hideSystemUI(this);
        sensorMode=getIntent().getExtras().getBoolean(SENSOR_MODE);

        findViews();
        initButton();

       if (sensorMode) {
            sensorsActivity = new SensorsActivity(this);
            sensorsActivity.setCallBackSensor(callBackSensor);
            main_BTN_left.setVisibility(View.INVISIBLE);
            main_BTN_right.setVisibility(View.INVISIBLE);
            main_BTN_up.setVisibility(View.INVISIBLE);
            main_BTN_down.setVisibility(View.INVISIBLE);

        }
        initGame();





        updateUI(gameManager.getDirectionForPoliceInX(), gameManager.getDirectionForPoliceInY(), R.drawable.img_police);
        updateUI(gameManager.getDirectionForThiefInX(), gameManager.getDirectionForThiefInY(), R.drawable.img_thief);


        actionClicked();

    }
    private void initGame(){
        gameManager = new GameManager();
        gameManager.startGame();
        name=getIntent().getExtras().getString(PLAYER_NAME);
        gameManager.setName(name);

        coinCollected=false;
    }

    // update matrix view
    private void updateUI(int currentDirectionInX, int currentDirectionInY, int src) {
        main_IMG_matrix_view[currentDirectionInY][currentDirectionInX].setImageResource(src);
    }

    // init all the pictures
    private void findViews() {
        main_BTN_up = findViewById(R.id.main_BTN_up);
        main_BTN_down = findViewById(R.id.main_BTN_down);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_TXT_score = findViewById(R.id.main_TXT_score);
        //array for hearts
        main_IMG_heart = new ImageView[]{
                findViewById(R.id.main_IMG_heart_left),
                findViewById(R.id.main_IMG_heart_mid),
                findViewById(R.id.main_IMG_heart_right)
        };
        //matrix for players
        //matrix for players
        main_IMG_matrix_view = new ImageView[][]{
                {findViewById(R.id.main_IMG_matrix_1), findViewById(R.id.main_IMG_matrix_2), findViewById(R.id.main_IMG_matrix_3), findViewById(R.id.main_IMG_matrix_4), findViewById(R.id.main_IMG_matrix_5)},
                {findViewById(R.id.main_IMG_matrix_6), findViewById(R.id.main_IMG_matrix_7), findViewById(R.id.main_IMG_matrix_8), findViewById(R.id.main_IMG_matrix_9), findViewById(R.id.main_IMG_matrix_10)},
                {findViewById(R.id.main_IMG_matrix_11), findViewById(R.id.main_IMG_matrix_12), findViewById(R.id.main_IMG_matrix_13), findViewById(R.id.main_IMG_matrix_14), findViewById(R.id.main_IMG_matrix_15)},
                {findViewById(R.id.main_IMG_matrix_16), findViewById(R.id.main_IMG_matrix_17), findViewById(R.id.main_IMG_matrix_18), findViewById(R.id.main_IMG_matrix_19), findViewById(R.id.main_IMG_matrix_20)},
                {findViewById(R.id.main_IMG_matrix_21), findViewById(R.id.main_IMG_matrix_22), findViewById(R.id.main_IMG_matrix_23), findViewById(R.id.main_IMG_matrix_24), findViewById(R.id.main_IMG_matrix_25)},
                {findViewById(R.id.main_IMG_matrix_26), findViewById(R.id.main_IMG_matrix_27), findViewById(R.id.main_IMG_matrix_28), findViewById(R.id.main_IMG_matrix_29), findViewById(R.id.main_IMG_matrix_30)}

        };


    }

    //init all the Buttons
    private void initButton() {

        main_BTN_down.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.getPolice().setDirection(3);


            }
        }));
        main_BTN_up.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.getPolice().setDirection(2);


            }
        }));
        main_BTN_right.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.getPolice().setDirection(1);


            }
        }));
        main_BTN_left.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.getPolice().setDirection(0);


            }
        }));
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
                gameManager.updateThePolice(main_IMG_matrix_view);
                updateUI(gameManager.getDirectionForPoliceInX(), gameManager.getDirectionForPoliceInY(), R.drawable.img_police);
                updateMatUI();
                gameManager.MoveForThief(main_IMG_matrix_view);
                updateUI(gameManager.getDirectionForThiefInX(), gameManager.getDirectionForThiefInY(), R.drawable.img_thief);
                updateMatUI();
                gameManager.updateScore();


                if (gameManager.getScore() % 5 == 0 && !coinCollected && gameManager.getScore() > 10) {
                    updateUI(gameManager.getDirectionForCoinInY(),gameManager.getDirectionForCoinInX(),0);
                    coinCollected = true;
                }
                if (gameManager.getScore() % 10 == 0 ) {
                   randFuncForCoin();
                    coinCollected = false;
                }

            }
        });
    }
    private void randFuncForCoin() {
        updateUI(gameManager.getDirectionForCoinInY(),gameManager.getDirectionForCoinInX(),0);
        gameManager.getCoin().setCurrentDirectionInY((int) (Math.random() * 5));
        gameManager.getCoin().setCurrentDirectionInX((int) (Math.random() * 4));
        gameManager.setDirectionForCoinInY(gameManager.getCoin().getCurrentDirectionInY());
        gameManager.setDirectionForCoinInX(gameManager.getCoin().getCurrentDirectionInX());

       if ((gameManager.getDirectionForCoinInY() == gameManager.getDirectionForThiefInY() && gameManager.getDirectionForCoinInX() ==  gameManager.getDirectionForThiefInX())||(gameManager.getDirectionForCoinInY() == gameManager.getDirectionForPoliceInY() && gameManager.getDirectionForCoinInX() ==  gameManager.getDirectionForPoliceInX()))
           randFuncForCoin();
       else
           updateUI(gameManager.getDirectionForCoinInY(),gameManager.getDirectionForCoinInX(),R.drawable.img_coin);


    }
    private void isCollectCoin() {
        if (gameManager.getDirectionForCoinInY() == gameManager.getDirectionForThiefInY() && gameManager.getDirectionForCoinInX() ==  gameManager.getDirectionForThiefInX() && !coinCollected) {
            gameManager.updateCoinScore();
            updateUI(gameManager.getDirectionForCoinInY(),gameManager.getDirectionForCoinInX(),R.drawable.img_coin);
            coinCollected = true;
        } else if (gameManager.getDirectionForCoinInY()== gameManager.getDirectionForPoliceInY() && gameManager.getDirectionForCoinInX() ==  gameManager.getDirectionForPoliceInX() && !coinCollected) {
            GameUtils.getInstance().Vibrate(1000);
            GameUtils.getInstance().playSound(R.raw.coin_pick);
            updateUI(gameManager.getDirectionForCoinInY(),gameManager.getDirectionForCoinInX(),R.drawable.img_police);
            coinCollected = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
            timerStatus = TIMER_STATUS.PAUSE;
        }
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
        timer = new Timer();



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

    private void theThiefCatch(){
        if (gameManager.getDirectionForPoliceInX() == gameManager.getDirectionForThiefInX() && gameManager.getDirectionForPoliceInY() == gameManager.getDirectionForThiefInY()){
           GameUtils.getInstance().playSound(R.raw.conflict);
            GameUtils.getInstance().Vibrate(1000);

            gameManager.updateLife();

            updateMatrixUI();

        }
    }
    private void updateMatrixUI(){

            if (gameManager.getLife() == 0) {

                Toast.makeText(getApplicationContext(), "Your Lose", Toast.LENGTH_LONG).show();
                gameOver();
                onStop();
                return;
            } else {
                updateUI(gameManager.getDirectionForThiefInX(), gameManager.getDirectionForThiefInY(), 0);
                updateUI(gameManager.getDirectionForPoliceInX(), gameManager.getDirectionForPoliceInY(), 0);
                startNewGame();
                gameManager.startGame();

            }

    }
    private void startNewGame(){

        gameManager.setDirectionForPoliceInX( 0);
        gameManager.setDirectionForPoliceInY(5);
       // updateUI(gameManager.getDirectionForPoliceInX(), gameManager.getDirectionForPoliceInY(), R.drawable.img_police);
        gameManager.setDirectionForThiefInX(4);
        gameManager.setDirectionForThiefInY(0);
        //updateUI(gameManager.getDirectionForThiefInX(), gameManager.getDirectionForThiefInY(), R.drawable.img_thief);
        coinCollected=false;
    }

// old function
    private void updateForUI() {
        if (gameManager.getLife() > 0) {
            gameManager.MoveForThief(main_IMG_matrix_view);
            updateUI(gameManager.getDirectionForThiefInX(), gameManager.getDirectionForThiefInY(), R.drawable.img_thief);
           // isCollectCoin();
            if (gameManager.getDirectionForPoliceInX() == gameManager.getDirectionForThiefInX() && gameManager.getDirectionForPoliceInY() == gameManager.getDirectionForThiefInY()) {
                updateUI(gameManager.getDirectionForThiefInX(), gameManager.getDirectionForThiefInY(), 0);
                updateUI(gameManager.getDirectionForPoliceInX(), gameManager.getDirectionForPoliceInY(), 0);

                gameManager.updateLife();
                if (gameManager.getLife() == 0) {
                    onStop();
                    gameOver();
                } else {

                    Toast.makeText(getApplicationContext(), "Your Catch", Toast.LENGTH_LONG).show();
                }

                gameManager.startGame();
                coinCollected=false;
                return;
            }
            gameManager.updateThePolice(main_IMG_matrix_view);
            updateUI(gameManager.getDirectionForPoliceInX(), gameManager.getDirectionForPoliceInY(), R.drawable.img_police);
        }

    }


    //update life for the game
    private void updateMatUI() {
        theThiefCatch();
        isCollectCoin();
        main_TXT_score.setText(String.valueOf(gameManager.getScore()));
        for (int i = 0; i < main_IMG_heart.length; i++) {
            main_IMG_heart[i].setVisibility(gameManager.getLife() > i ? View.VISIBLE : View.INVISIBLE);
        }
    }
    private CallBackSensor callBackSensor = new CallBackSensor() {
        @Override
        public void moveSensorMode(float x,float y) {
            if(x>8.0000){
                Log.d("sensor", "=====LEFT====");
                gameManager.getPolice().setDirection(0);

            }
            else if(x<-8.0000){
                Log.d("sensor", "=====RIGHT====");
                gameManager.getPolice().setDirection(1);
            }
            else if(y < 8.0){
                Log.d("sensor", "=====UP====");
                gameManager.getPolice().setDirection(2);

            }

            else if(y > -8.0){
                Log.d("sensor", "=====DOWN====");
                gameManager.getPolice().setDirection(3);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorMode) {
            sensorsActivity.resumed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorMode) {
            sensorsActivity.paused();
        }
    }
    private void gameOver(){
        Intent gameOverIntent = new Intent(GameActivity.this, GameOverActivity.class);
        gameOverIntent.putExtra("Score", gameManager.getScore());
        gameOverIntent.putExtra(SENSOR_MODE, sensorMode);
        gameOverIntent.putExtra(PLAYER_NAME, gameManager.getName());
        startActivity(gameOverIntent);
        this.finish();
    }


}
