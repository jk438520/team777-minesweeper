package com.io.minesweeper;

public class UserModel {

    static final long MILLISECONDS_IN_2_HOURS = 7200000;

    private int id;
    private String name;
    private long hardScore;
    private long mediumScore;
    private long easyScore;

    public UserModel(int id, String name) {
        this.id = id;
        this.name = name.toUpperCase();
        this.hardScore = MILLISECONDS_IN_2_HOURS;
        this.mediumScore = MILLISECONDS_IN_2_HOURS;
        this.easyScore = MILLISECONDS_IN_2_HOURS;
    }

    public UserModel(int id, String name, long hardScore, long mediumScore, long easyScore) {
        this.id = id;
        this.name = name.toUpperCase();
        this.hardScore = hardScore;
        this.mediumScore = mediumScore;
        this.easyScore = easyScore;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hardScore=" + hardScore +
                ", mediumScore=" + mediumScore +
                ", easyScore=" + easyScore +
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
        return hardScore;
    }

    public void setHardScore(long hardScore) {
        this.hardScore = hardScore;
    }

    public long getMediumScore() {
        return mediumScore;
    }

    public void setMediumScore(long mediumScore) {
        this.mediumScore = mediumScore;
    }

    public long getEasyScore() {
        return easyScore;
    }

    public void setEasyScore(long easyScore) {
        this.easyScore = easyScore;
    }
}