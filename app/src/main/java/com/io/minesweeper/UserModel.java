package com.io.minesweeper;

public class UserModel {

    static final long MILLISECONDS_IN_2_HOURS = 7200000;

    private int id;
    private String name;
    private long hard_score;
    private long medium_score;
    private long easy_score;

    public UserModel(int id, String name) {
        this.id = id;
        this.name = name.toUpperCase();
        this.hard_score = MILLISECONDS_IN_2_HOURS;
        this.medium_score = MILLISECONDS_IN_2_HOURS;
        this.easy_score = MILLISECONDS_IN_2_HOURS;
    }

    public UserModel(int id, String name, long hard_score, long medium_score, long easy_score) {
        this.id = id;
        this.name = name.toUpperCase();
        this.hard_score = hard_score;
        this.medium_score = medium_score;
        this.easy_score = easy_score;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hard_score=" + hard_score +
                ", medium_score=" + medium_score +
                ", easy_score=" + easy_score +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHardScore() {
        return hard_score;
    }

    public void setHardScore(long hard_score) {
        this.hard_score = hard_score;
    }

    public long getMediumScore() {
        return medium_score;
    }

    public void setMediumScore(long medium_score) {
        this.medium_score = medium_score;
    }

    public long getEasyScore() {
        return easy_score;
    }

    public void setEasyScore(long easy_score) {
        this.easy_score = easy_score;
    }
}