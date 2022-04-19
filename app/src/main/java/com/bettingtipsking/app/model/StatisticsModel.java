package com.bettingtipsking.app.model;

public class StatisticsModel {
    int teamId;
    String teamName;
    String teamLogo;
    String statisticsType;
    String statisticsVale;

    public StatisticsModel(int teamId, String teamName, String teamLogo, String statisticsType, String statisticsVale) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamLogo = teamLogo;
        this.statisticsType = statisticsType;
        this.statisticsVale = statisticsVale;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public String getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType;
    }

    public String getStatisticsVale() {
        return statisticsVale;
    }

    public void setStatisticsVale(String statisticsVale) {
        this.statisticsVale = statisticsVale;
    }
}
