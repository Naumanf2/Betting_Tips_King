package com.bettingtipsking.app.activity.fixtures.model;

public class StatsModel {
    String title;
    String teamAScore;
    String teamBScore;

    public StatsModel(String title, String teamAScore, String teamBScore) {
        this.title = title;
        this.teamAScore = teamAScore;
        this.teamBScore = teamBScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(String teamAScore) {
        this.teamAScore = teamAScore;
    }

    public String getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(String teamBScore) {
        this.teamBScore = teamBScore;
    }
}
