package com.example.hw01.Activities;


import android.app.Activity;

import android.content.Intent;

import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.hw01.CallBacks.Label;

import com.example.hw01.R;
import com.example.hw01.utils.ScreenUtils;

import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.material.button.MaterialButton;


public class MenuActivity extends AppCompatActivity implements Label {

    private Location theLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    private MaterialButton menu_BTN_sensors;
    private MaterialButton menu_BTN_high_scores;
    private MaterialButton menu_BTN_withoutSensors;
    private EditText menu_EDT_name;
    private MaterialButton menu_BTN_next;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ScreenUtils.hideSystemUI(this);

        findViews();
        initButtons();
        menu_BTN_withoutSensors.setVisibility(View.INVISIBLE);
        menu_BTN_sensors.setVisibility(View.INVISIBLE);
        menu_BTN_high_scores.setVisibility(View.INVISIBLE);


    }

    private void checkIfPutName() {
        if(menu_EDT_name.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter Your Name", Toast.LENGTH_LONG).show();

        }
        else{
            name = menu_EDT_name.getText().toString();
            menu_EDT_name.setVisibility(View.INVISIBLE);
            menu_BTN_next.setVisibility(View.INVISIBLE);
            menu_BTN_withoutSensors.setVisibility(View.VISIBLE);
            menu_BTN_sensors.setVisibility(View.VISIBLE);
            menu_BTN_high_scores.setVisibility(View.VISIBLE);



        }

    }

    private void putNameToBundle(String name) {
        Intent gameIntent = new Intent(this, GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(GameActivity.PLAYER_NAME, name);


        gameIntent.putExtras(bundle);

       openGameBundle(bundle);
    }

    private void initButtons() {
        menu_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkIfPutName();


            }
        });


        menu_BTN_withoutSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(false);
            }
        });

        menu_BTN_sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(true);
            }
        });

        menu_BTN_high_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHighScores(MenuActivity.this);
            }
        });

    }

    private void findViews() {
        menu_BTN_withoutSensors = findViewById(R.id.menu_BTN_withoutSensors);
        menu_BTN_high_scores = findViewById(R.id.menu_BTN_high_scores);
        menu_BTN_sensors = findViewById(R.id.menu_BTN_sensors);
        menu_EDT_name=findViewById(R.id.menu_EDT_name);
        menu_BTN_next=findViewById(R.id.menu_BTN_next);
    }

    private void showHighScores(Activity activity) {
        Intent highScoresIntent = new Intent(this, TopTenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFromGame",false );
        highScoresIntent.putExtras(bundle);
        startActivity(highScoresIntent);
    }

    public void startGame(Boolean sensor) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(GameActivity.SENSOR_MODE, sensor);
        bundle.putString(GameActivity.PLAYER_NAME, name);
        bundle.putBoolean("isFromGame",true );
        openGameBundle(bundle);



    }
    public void openGameBundle(Bundle bundle){
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtras(bundle);
        startActivity(gameIntent);
    }





}