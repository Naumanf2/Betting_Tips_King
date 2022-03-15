package com.bettingtipsking.app.Model;

public class InjuryModel {
    String playerName;

    public InjuryModel(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
