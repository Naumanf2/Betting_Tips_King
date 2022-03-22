package com.bettingtipsking.app.model;

import java.util.List;

public class MainMatchesModel {
    String countryName;
    List<MatchesModel> matchesList;

    public MainMatchesModel(String countryName, List<MatchesModel> matchesList) {
        this.countryName = countryName;
        this.matchesList = matchesList;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<MatchesModel> getMatchesList() {
        return matchesList;
    }

    public void setMatchesList(List<MatchesModel> matchesList) {
        this.matchesList = matchesList;
    }
}
