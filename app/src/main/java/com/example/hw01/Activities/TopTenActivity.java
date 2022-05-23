package com.example.hw01.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw01.CallBacks.callBackList;
import com.example.hw01.utils.ScreenUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.button.MaterialButton;

import com.example.hw01.Fragments.FragmentList;
import com.example.hw01.Fragments.FragmentMap;
import com.example.hw01.R;


public class TopTenActivity extends AppCompatActivity{

    private FragmentList fragmentList;
    private FragmentMap fragmentMap;
    private GoogleMap mMap;
    private MaterialButton top_10_BTN_Return;
    private MaterialButton menu_BTN_play_again;
    public String name;
    private boolean isFromGame=false,isFromGame1=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_10);
        ScreenUtils.hideSystemUI(this);
        isFromGame=getIntent().getExtras().getBoolean("isFromGame");
        fragmentList = new FragmentList();
        fragmentList.setActivity(this);
        fragmentList.setCallBackList(CallBackList);
        getSupportFragmentManager().beginTransaction().add(R.id.top_10_LAY_list, fragmentList).commit();

        top_10_BTN_Return = findViewById(R.id.top_10_BTN_return);
        top_10_BTN_Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenu();
            }
        });

        fragmentMap = new FragmentMap();
        getSupportFragmentManager().beginTransaction().add(R.id.top_10_LAY_map, fragmentMap).commit();


        menu_BTN_play_again=findViewById(R.id.menu_BTN_play_again);
        menu_BTN_play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPlayGame();

            }
        });

        if (!getIntent().getExtras().getBoolean("isFromGame")){
            menu_BTN_play_again.setVisibility(View.INVISIBLE);

        }else{
            isFromGame1 = getIntent().getExtras().getBoolean(GameActivity.SENSOR_MODE);
            name = getIntent().getExtras().getString("name");
        }


    }


    callBackList CallBackList = new callBackList() {
        @Override
        public void location(double longitude, double latitude) {
            zoom(longitude, latitude);
        }

        @Override
        public void clearListClicked() {
            backToMenu();
        }
    };

    private void backToMenu() {
        this.finish();
    }

    private void zoom(double longitude, double latitude) {
        fragmentMap.getLocation(longitude , latitude);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.finish();
    }
    public void newPlayGame(){
        Intent myIntent = new Intent(this, GameActivity.class);
        startActivity(myIntent);
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        bundle.putBoolean("isFromGame1",isFromGame1);
        bundle.putBoolean(GameActivity.SENSOR_MODE, getIntent().getExtras().getBoolean(GameActivity.SENSOR_MODE));
        myIntent.putExtras(bundle);
        startActivity(myIntent);
        finish();

    }




    }








