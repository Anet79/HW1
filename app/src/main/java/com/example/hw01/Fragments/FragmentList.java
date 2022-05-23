package com.example.hw01.Fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hw01.CallBacks.callBackList;
import com.example.hw01.Objects.MyDB;
import com.example.hw01.Objects.Record;

import com.example.hw01.R;
import com.example.hw01.utils.MSPV3;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.ArrayList;



public class FragmentList extends Fragment {
    private static final int TOP_TEN= 10;
    private  ArrayList<MaterialTextView> allNames;
    private  ArrayList<MaterialTextView> allDates;
    private  ArrayList<MaterialTextView> allScores;
    private  ArrayList<ImageButton> allLocations;
    private callBackList callBackTopTenList;
    private AppCompatActivity activity;
    private MaterialButton[] top_Ten;
    private MaterialButton list_BTN_Reset;


    public FragmentList() {
        allNames= new ArrayList<MaterialTextView>();
        allDates= new ArrayList<MaterialTextView>();
        allScores= new ArrayList<MaterialTextView>();
        allLocations= new ArrayList<ImageButton>();

    }


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackList(callBackList callBackTopTenList) {
        this.callBackTopTenList = callBackTopTenList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_10, container, false);
        findViews(view);
        initViews();
       // clearButtonInit();
        return view;
    }


//    public void clearButtonInit() {
//        list_BTN_Reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyDB myDB = new MyDB();
//
//                String json = new Gson().toJson(myDB);
//                MSPV3.getMe().putString("MY_DB", json);
//
//                initViews();
//                callBackTopTenList.clearListClicked();
//            }
//        });
//    }


    private void initViews() {
        String js = MSPV3.getMe().getString("MY_DB", "");
        MyDB md = new Gson().fromJson(js, MyDB.class);
        ArrayList<Record> rec = md.getRecords();
       // Collections.sort(md.getRecords(), new SortScores());
        for (int i = 0; i < rec.size(); i++) {
            Record temp = rec.get(i);
            allNames.get(i).setText(temp.getName());
            allScores.get(i).setText("" + temp.getScore());
            allDates.get(i).setText(temp.getDate());
            allLocations.get(i).setVisibility(View.VISIBLE);
            allLocations.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackTopTenList.location(temp.getPosition().getLongitude(), temp.getPosition().getLatitude());

                }
            });

        }

    }

    private void findViews(View view) {
        for (int i = 0; i < TOP_TEN; i++) {
            allNames.add(view.findViewById(view.getResources().getIdentifier("fragment_TVW_name_" + i, "id", getActivity().getPackageName())));
            allDates.add(view.findViewById(view.getResources().getIdentifier("fragment_TVW_date_" + i, "id", getActivity().getPackageName())));
            allScores.add(view.findViewById(view.getResources().getIdentifier("fragment_TVW_score_" + i, "id", getActivity().getPackageName())));
            allLocations.add(view.findViewById(view.getResources().getIdentifier("fragment_BTN_location_" + i, "id", getActivity().getPackageName())));
        }

    }
}
