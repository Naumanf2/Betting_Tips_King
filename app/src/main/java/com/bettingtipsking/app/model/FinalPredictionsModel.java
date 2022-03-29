package com.bettingtipsking.app.model;

public class FinalPredictionsModel {
    String homeTeamName;
    String homeTeamLogo;
    String awayTeamName;
    String awayTeamLogo;
    String comparisonTitle;
    String comparisonHomeProgress;
    String comparisonAwayProgress;

    public FinalPredictionsModel(String homeTeamName, String homeTeamLogo, String awayTeamName, String awayTeamLogo, String comparisonTitle, String comparisonHomeProgress, String comparisonAwayProgress) {
        this.homeTeamName = homeTeamName;
        this.homeTeamLogo = homeTeamLogo;
        this.awayTeamName = awayTeamName;
        this.awayTeamLogo = awayTeamLogo;
        this.comparisonTitle = comparisonTitle;
        this.comparisonHomeProgress = comparisonHomeProgress;
        this.comparisonAwayProgress = comparisonAwayProgress;
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

    public String getComparisonHomeProgress() {
        return comparisonHomeProgress;
    }

    public void setComparisonHomeProgress(String comparisonHomeProgress) {
        this.comparisonHomeProgress = comparisonHomeProgress;
    }

    public String getComparisonAwayProgress() {
        return comparisonAwayProgress;
    }

    public void setComparisonAwayProgress(String comparisonAwayProgress) {
        this.comparisonAwayProgress = comparisonAwayProgress;
    }
}
