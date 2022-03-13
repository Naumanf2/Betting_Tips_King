package com.bettingtipsking.app.activity.fixtures.model;

import java.util.List;

public class TeamWithPlayers {
    String id;
    String name;
    String logo;
    String coachName;
    String coachPhoto;
    String formation;
    List<TeamWithPlayers> goalKeeper;
    List<TeamWithPlayers> defender;
    List<TeamWithPlayers> midfielder;
    List<TeamWithPlayers> forward;

    String playerId;
    String playerName;
    String playerNumber;
    String playerPos;
    String playerGrid;
    String playerPhoto;


    public TeamWithPlayers(String id, String name, String logo, String coachName, String coachPhoto, String formation, List<TeamWithPlayers> goalKeeper, List<TeamWithPlayers> defender, List<TeamWithPlayers> midfielder, List<TeamWithPlayers> forward) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.coachName = coachName;
        this.coachPhoto = coachPhoto;
        this.formation = formation;
        this.goalKeeper = goalKeeper;
        this.defender = defender;
        this.midfielder = midfielder;
        this.forward = forward;
    }

    public TeamWithPlayers(String playerId, String playerName, String playerNumber, String playerPos, String playerGrid, String playerPhoto) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.playerPos = playerPos;
        this.playerGrid = playerGrid;
        this.playerPhoto = playerPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachPhoto() {
        return coachPhoto;
    }

    public void setCoachPhoto(String coachPhoto) {
        this.coachPhoto = coachPhoto;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public List<TeamWithPlayers> getGoalKeeper() {
        return goalKeeper;
    }

    public void setGoalKeeper(List<TeamWithPlayers> goalKeeper) {
        this.goalKeeper = goalKeeper;
    }

    public List<TeamWithPlayers> getDefender() {
        return defender;
    }

    public void setDefender(List<TeamWithPlayers> defender) {
        this.defender = defender;
    }

    public List<TeamWithPlayers> getMidfielder() {
        return midfielder;
    }

    public void setMidfielder(List<TeamWithPlayers> midfielder) {
        this.midfielder = midfielder;
    }

    public List<TeamWithPlayers> getForward() {
        return forward;
    }

    public void setForward(List<TeamWithPlayers> forward) {
        this.forward = forward;
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

    public String getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(String playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(String playerPos) {
        this.playerPos = playerPos;
    }

    public String getPlayerGrid() {
        return playerGrid;
    }

    public void setPlayerGrid(String playerGrid) {
        this.playerGrid = playerGrid;
    }

    public String getPlayerPhoto() {
        return playerPhoto;
    }

    public void setPlayerPhoto(String playerPhoto) {
        this.playerPhoto = playerPhoto;
    }
}
