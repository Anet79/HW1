package com.example.hw01.Objects;

import java.util.ArrayList;

public class MyDB {
    private ArrayList<Record> records;

    public MyDB() { };

    public ArrayList<Record> getRecords() {
        if(records == null){
            records = new ArrayList<Record>();
        }
        return records;
    }

    public MyDB setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }
}
