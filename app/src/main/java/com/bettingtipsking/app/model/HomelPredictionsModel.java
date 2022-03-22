package com.bettingtipsking.app.model;

public class HomelPredictionsModel {

    int id;
    String status;
    String sort;
    String owner;
    String created_on;
    String modified_on;
    String league_name;
    String match_id;
    String home_team;
    String away_team;
    String odd_value;
    String team_home_score;
    String team_away_score;
    String match_minute;
    String coupon_name;
    String game_prediction;
    String match_date;
    String match_status;
    String match_time;
    String match_timestamp;
    String sport_type;

    public HomelPredictionsModel(int id, String status, String sort,
                                 String owner, String created_on, String modified_on,
                                 String league_name, String match_id,
                                 String home_team, String away_team, String odd_value,
                                 String team_home_score, String team_away_score,
                                 String match_minute, String coupon_name,
                                 String game_prediction, String match_date,
                                 String match_status, String match_time,
                                 String match_timestamp, String sport_type) {
        this.id = id;
        this.status = status;
        this.sort = sort;
        this.owner = owner;
        this.created_on = created_on;
        this.modified_on = modified_on;
        this.league_name = league_name;
        this.match_id = match_id;
        this.home_team = home_team;
        this.away_team = away_team;
        this.odd_value = odd_value;
        this.team_home_score = team_home_score;
        this.team_away_score = team_away_score;
        this.match_minute = match_minute;
        this.coupon_name = coupon_name;
        this.game_prediction = game_prediction;
        this.match_date = match_date;
        this.match_status = match_status;
        this.match_time = match_time;
        this.match_timestamp = match_timestamp;
        this.sport_type = sport_type;
        this.favStatus = favStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public String getOdd_value() {
        return odd_value;
    }

    public void setOdd_value(String odd_value) {
        this.odd_value = odd_value;
    }

    public String getTeam_home_score() {
        return team_home_score;
    }

    public void setTeam_home_score(String team_home_score) {
        this.team_home_score = team_home_score;
    }

    public String getTeam_away_score() {
        return team_away_score;
    }

    public void setTeam_away_score(String team_away_score) {
        this.team_away_score = team_away_score;
    }

    public String getMatch_minute() {
        return match_minute;
    }

    public void setMatch_minute(String match_minute) {
        this.match_minute = match_minute;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getGame_prediction() {
        return game_prediction;
    }

    public void setGame_prediction(String game_prediction) {
        this.game_prediction = game_prediction;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getMatch_timestamp() {
        return match_timestamp;
    }

    public void setMatch_timestamp(String match_timestamp) {
        this.match_timestamp = match_timestamp;
    }

    public String getSport_type() {
        return sport_type;
    }

    public void setSport_type(String sport_type) {
        this.sport_type = sport_type;
    }

}

