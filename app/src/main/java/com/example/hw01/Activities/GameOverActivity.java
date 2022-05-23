package com.example.hw01.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.hw01.Objects.GpsTracker;
import com.example.hw01.Objects.MyDB;
import com.example.hw01.Objects.Position;
import com.example.hw01.Objects.Record;
import com.example.hw01.Objects.SortScores;
import com.example.hw01.R;
import com.example.hw01.utils.MSPV3;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class GameOverActivity extends AppCompatActivity  {
    private TextView gameOver_TVW_Name;
    private TextView gameOver_TVW_Score;

    private Button gameOver_BTN_save;
    private int score;
    private String name;


    //Location Service
   private   GpsTracker gpsService;

    private MyDB myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        findViews();
        initButtons();
        Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_LONG).show();
        score = getIntent().getExtras().getInt("Score");
        name=getIntent().getExtras().getString("PLAYER_NAME");
        gameOver_TVW_Score.setText(""+ score);
        gameOver_TVW_Name.setText(name);

        //----- Get Location use permission and check -----
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GameOverActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        gameOver_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = 0.0;
                double longitude = 0.0;

                gpsService = new GpsTracker(GameOverActivity.this);
                if (gpsService.canGetLocation()) {
                    latitude = gpsService.getLatitude();
                    longitude = gpsService.getLongitude();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                } else {
                    gpsService.showSettingsAlert();
                }
                saveRecordsForTopTen(longitude ,latitude, score, name);
                openTopTenActivity();

            }
        });



    }

    private void saveRecordsForTopTen(double longitude,double latitude ,int score,String name){
    String js = MSPV3.getMe().getString("MY_DB", "");
    myDB = new Gson().fromJson(js, MyDB.class);
    Position position=new Position();
    position.setLongitude(longitude);
    position.setLatitude(latitude);
    SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd-yyyy-MM ");
    Date GetDate = new Date();
    String date = timeStampFormat.format(GetDate);
    myDB.getRecords().add(new Record().setScore(score).setName(name).setPosition(position).setDate(date));
    Collections.sort(myDB.getRecords(), new SortScores());
    String json = new Gson().toJson(myDB);
    MSPV3.getMe().putString("MY_DB", json);


}


    private void openTopTenActivity() {
        Intent myIntent = new Intent(this, TopTenActivity.class);
        myIntent.putExtra("isFromGame",true);
        startActivity(myIntent);
         this.finish();
    }


    private void findViews() {
        gameOver_TVW_Score = findViewById(R.id.gameOver_TVW_Score);
        gameOver_BTN_save = findViewById(R.id.gameOver_BTN_save);
        gameOver_TVW_Name=findViewById(R.id.gameOver_TVW_Name);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

