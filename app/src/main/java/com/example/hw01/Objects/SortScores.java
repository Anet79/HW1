package com.example.hw01.Objects;

import java.util.Comparator;

public class SortScores implements Comparator<Record> {
    @Override
    public int compare(Record r1, Record r2) {
        return r2.getScore()-r1.getScore();
    }
}
