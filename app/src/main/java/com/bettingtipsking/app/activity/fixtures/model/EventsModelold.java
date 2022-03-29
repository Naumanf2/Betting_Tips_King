package com.bettingtipsking.app.activity.fixtures.model;

public class EventsModelold {
    String type;
    String timeElapsed;
    String playerId;
    String playerName;
    String assistId;
    String assistName;
    String details;

    public EventsModelold(String type, String timeElapsed, String playerId, String playerName, String assistId, String assistName, String details) {
        this.type = type;
        this.timeElapsed = timeElapsed;
        this.playerId = playerId;
        this.playerName = playerName;
        this.assistId = assistId;
        this.assistName = assistName;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(String timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAssistId() {
        return assistId;
    }

    public void setAssistId(String assistId) {
        this.assistId = assistId;
    }

    public String getAssistName() {
        return assistName;
    }

    public void setAssistName(String assistName) {
        this.assistName = assistName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
