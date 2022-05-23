package com.example.hw01.Objects;

public class Record {
    private String name;
    private String  date ;
    private int score = 0;
    private int distance;
    private Position Position;

    public Record() { }

    public Record( Position Position,String date, int score,String name) {
        this.name=name;
        this.date = date;
        this.score = score;
        this.Position=Position;

    }

    public String getDate() {
        return date;
    }

    public Record setDate(String date) {
        this.date = date;
        return this;
    }

    public int getScore() {
        return score;
    }



    public Record setScore(int score) {
        this.score = score;
        return this;
    }


    public Position getPosition() {
        return Position;

    }

    public Record setPosition(Position Position) {
        this.Position = Position;
        return this;
    }

    public Record setName(String name) {

        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Record setDistance(int distance) {
        this.distance = distance;
        return this;
    }
    public int getDistance() {
        return distance;
    }
}
