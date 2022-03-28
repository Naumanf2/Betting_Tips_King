package com.bettingtipsking.app.model;

public class FinalPredictionsModel {
    String homeTeamName;
    String homeTeamLogo;
    String awayTeamName;
    String awayTeamLogo;
    String comparisonTitle;
    String comparisonHome;
    String comparisonAway;

    public FinalPredictionsModel(String homeTeamName, String homeTeamLogo, String awayTeamName, String awayTeamLogo, String comparisonTitle, String comparisonHome, String comparisonAway) {
        this.homeTeamName = homeTeamName;
        this.homeTeamLogo = homeTeamLogo;
        this.awayTeamName = awayTeamName;
        this.awayTeamLogo = awayTeamLogo;
        this.comparisonTitle = comparisonTitle;
        this.comparisonHome = comparisonHome;
        this.comparisonAway = comparisonAway;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getHomeTeamLogo() {
        return homeTeamLogo;
    }

    public void setHomeTeamLogo(String homeTeamLogo) {
        this.homeTeamLogo = homeTeamLogo;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getAwayTeamLogo() {
        return awayTeamLogo;
    }

    public void setAwayTeamLogo(String awayTeamLogo) {
        this.awayTeamLogo = awayTeamLogo;
    }

    public String getComparisonTitle() {
        return comparisonTitle;
    }

    public void setComparisonTitle(String comparisonTitle) {
        this.comparisonTitle = comparisonTitle;
    }

    public String getComparisonHome() {
        return comparisonHome;
    }

    public void setComparisonHome(String comparisonHome) {
        this.comparisonHome = comparisonHome;
    }

    public String getComparisonAway() {
        return comparisonAway;
    }

    public void setComparisonAway(String comparisonAway) {
        this.comparisonAway = comparisonAway;
    }
}
