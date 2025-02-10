package com.example.pingponggame;

public class score {
    private String userID;
    private String userName;
    private int score;

    public score() {
    }

    public score(String userID, String userName, int score) {
        this.score = score;
        this.userID = userID;
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
