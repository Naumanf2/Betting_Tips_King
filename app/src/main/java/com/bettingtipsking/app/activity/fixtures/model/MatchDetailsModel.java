package com.bettingtipsking.app.activity.fixtures.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MatchDetailsModel implements Parcelable {
    int fixtureId;
    String fixtureReferee;
    String fixtureTimezone;
    String fixtureDate;
    String fixtureTimestamp;

    String fixturePeriodsFirst;
    String fixturePeriodsSecond;

    String fixtureVenueId;
    String fixtureVenueName;
    String fixtureVenueCity;

    String fixtureStatusLong;
    String fixtureStatusShort;
    String fixtureStatusElapsed;

    String teamHomeId;
    String teamHomeName;
    String teamHomeLogo;

    String teamAwayId;
    String teamAwayName;
    String teamAwayLogo;

    String goalsHome;
    String goalsAway;

    String scoreHalftimeHome;
    String scoreHalftimeAway;

    String scoreFullTimeHome;
    String scoreFullTimeAway;

    String scoreExtraTimeHome;
    String scoreExtraTimeAway;

    String scorePenaltyHome;
    String scorePenaltyAway;

    public MatchDetailsModel(){}

    public MatchDetailsModel(int fixtureId, String fixtureReferee, String fixtureTimezone, String fixtureDate, String fixtureTimestamp, String fixturePeriodsFirst, String fixturePeriodsSecond, String fixtureVenueId, String fixtureVenueName, String fixtureVenueCity, String fixtureStatusLong, String fixtureStatusShort, String fixtureStatusElapsed, String teamHomeId, String teamHomeName, String teamHomeLogo, String teamAwayId, String teamAwayName, String teamAwayLogo, String goalsHome, String goalsAway, String scoreHalftimeHome, String scoreHalftimeAway, String scoreFullTimeHome, String scoreFullTimeAway, String scoreExtraTimeHome, String scoreExtraTimeAway, String scorePenaltyHome, String scorePenaltyAway) {
        this.fixtureId = fixtureId;
        this.fixtureReferee = fixtureReferee;
        this.fixtureTimezone = fixtureTimezone;
        this.fixtureDate = fixtureDate;
        this.fixtureTimestamp = fixtureTimestamp;
        this.fixturePeriodsFirst = fixturePeriodsFirst;
        this.fixturePeriodsSecond = fixturePeriodsSecond;
        this.fixtureVenueId = fixtureVenueId;
        this.fixtureVenueName = fixtureVenueName;
        this.fixtureVenueCity = fixtureVenueCity;
        this.fixtureStatusLong = fixtureStatusLong;
        this.fixtureStatusShort = fixtureStatusShort;
        this.fixtureStatusElapsed = fixtureStatusElapsed;
        this.teamHomeId = teamHomeId;
        this.teamHomeName = teamHomeName;
        this.teamHomeLogo = teamHomeLogo;
        this.teamAwayId = teamAwayId;
        this.teamAwayName = teamAwayName;
        this.teamAwayLogo = teamAwayLogo;
        this.goalsHome = goalsHome;
        this.goalsAway = goalsAway;
        this.scoreHalftimeHome = scoreHalftimeHome;
        this.scoreHalftimeAway = scoreHalftimeAway;
        this.scoreFullTimeHome = scoreFullTimeHome;
        this.scoreFullTimeAway = scoreFullTimeAway;
        this.scoreExtraTimeHome = scoreExtraTimeHome;
        this.scoreExtraTimeAway = scoreExtraTimeAway;
        this.scorePenaltyHome = scorePenaltyHome;
        this.scorePenaltyAway = scorePenaltyAway;
    }

    protected MatchDetailsModel(Parcel in) {
        fixtureId = in.readInt();
        fixtureReferee = in.readString();
        fixtureTimezone = in.readString();
        fixtureDate = in.readString();
        fixtureTimestamp = in.readString();
        fixturePeriodsFirst = in.readString();
        fixturePeriodsSecond = in.readString();
        fixtureVenueId = in.readString();
        fixtureVenueName = in.readString();
        fixtureVenueCity = in.readString();
        fixtureStatusLong = in.readString();
        fixtureStatusShort = in.readString();
        fixtureStatusElapsed = in.readString();
        teamHomeId = in.readString();
        teamHomeName = in.readString();
        teamHomeLogo = in.readString();
        teamAwayId = in.readString();
        teamAwayName = in.readString();
        teamAwayLogo = in.readString();
        goalsHome = in.readString();
        goalsAway = in.readString();
        scoreHalftimeHome = in.readString();
        scoreHalftimeAway = in.readString();
        scoreFullTimeHome = in.readString();
        scoreFullTimeAway = in.readString();
        scoreExtraTimeHome = in.readString();
        scoreExtraTimeAway = in.readString();
        scorePenaltyHome = in.readString();
        scorePenaltyAway = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fixtureId);
        dest.writeString(fixtureReferee);
        dest.writeString(fixtureTimezone);
        dest.writeString(fixtureDate);
        dest.writeString(fixtureTimestamp);
        dest.writeString(fixturePeriodsFirst);
        dest.writeString(fixturePeriodsSecond);
        dest.writeString(fixtureVenueId);
        dest.writeString(fixtureVenueName);
        dest.writeString(fixtureVenueCity);
        dest.writeString(fixtureStatusLong);
        dest.writeString(fixtureStatusShort);
        dest.writeString(fixtureStatusElapsed);
        dest.writeString(teamHomeId);
        dest.writeString(teamHomeName);
        dest.writeString(teamHomeLogo);
        dest.writeString(teamAwayId);
        dest.writeString(teamAwayName);
        dest.writeString(teamAwayLogo);
        dest.writeString(goalsHome);
        dest.writeString(goalsAway);
        dest.writeString(scoreHalftimeHome);
        dest.writeString(scoreHalftimeAway);
        dest.writeString(scoreFullTimeHome);
        dest.writeString(scoreFullTimeAway);
        dest.writeString(scoreExtraTimeHome);
        dest.writeString(scoreExtraTimeAway);
        dest.writeString(scorePenaltyHome);
        dest.writeString(scorePenaltyAway);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MatchDetailsModel> CREATOR = new Creator<MatchDetailsModel>() {
        @Override
        public MatchDetailsModel createFromParcel(Parcel in) {
            return new MatchDetailsModel(in);
        }

        @Override
        public MatchDetailsModel[] newArray(int size) {
            return new MatchDetailsModel[size];
        }
    };

    public int getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(int fixtureId) {
        this.fixtureId = fixtureId;
    }

    public String getFixtureReferee() {
        return fixtureReferee;
    }

    public void setFixtureReferee(String fixtureReferee) {
        this.fixtureReferee = fixtureReferee;
    }

    public String getFixtureTimezone() {
        return fixtureTimezone;
    }

    public void setFixtureTimezone(String fixtureTimezone) {
        this.fixtureTimezone = fixtureTimezone;
    }

    public String getFixtureDate() {
        return fixtureDate;
    }

    public void setFixtureDate(String fixtureDate) {
        this.fixtureDate = fixtureDate;
    }

    public String getFixtureTimestamp() {
        return fixtureTimestamp;
    }

    public void setFixtureTimestamp(String fixtureTimestamp) {
        this.fixtureTimestamp = fixtureTimestamp;
    }

    public String getFixturePeriodsFirst() {
        return fixturePeriodsFirst;
    }

    public void setFixturePeriodsFirst(String fixturePeriodsFirst) {
        this.fixturePeriodsFirst = fixturePeriodsFirst;
    }

    public String getFixturePeriodsSecond() {
        return fixturePeriodsSecond;
    }

    public void setFixturePeriodsSecond(String fixturePeriodsSecond) {
        this.fixturePeriodsSecond = fixturePeriodsSecond;
    }

    public String getFixtureVenueId() {
        return fixtureVenueId;
    }

    public void setFixtureVenueId(String fixtureVenueId) {
        this.fixtureVenueId = fixtureVenueId;
    }

    public String getFixtureVenueName() {
        return fixtureVenueName;
    }

    public void setFixtureVenueName(String fixtureVenueName) {
        this.fixtureVenueName = fixtureVenueName;
    }

    public String getFixtureVenueCity() {
        return fixtureVenueCity;
    }

    public void setFixtureVenueCity(String fixtureVenueCity) {
        this.fixtureVenueCity = fixtureVenueCity;
    }

    public String getFixtureStatusLong() {
        return fixtureStatusLong;
    }

    public void setFixtureStatusLong(String fixtureStatusLong) {
        this.fixtureStatusLong = fixtureStatusLong;
    }

    public String getFixtureStatusShort() {
        return fixtureStatusShort;
    }

    public void setFixtureStatusShort(String fixtureStatusShort) {
        this.fixtureStatusShort = fixtureStatusShort;
    }

    public String getFixtureStatusElapsed() {
        return fixtureStatusElapsed;
    }

    public void setFixtureStatusElapsed(String fixtureStatusElapsed) {
        this.fixtureStatusElapsed = fixtureStatusElapsed;
    }

    public String getTeamHomeId() {
        return teamHomeId;
    }

    public void setTeamHomeId(String teamHomeId) {
        this.teamHomeId = teamHomeId;
    }

    public String getTeamHomeName() {
        return teamHomeName;
    }

    public void setTeamHomeName(String teamHomeName) {
        this.teamHomeName = teamHomeName;
    }

    public String getTeamHomeLogo() {
        return teamHomeLogo;
    }

    public void setTeamHomeLogo(String teamHomeLogo) {
        this.teamHomeLogo = teamHomeLogo;
    }

    public String getTeamAwayId() {
        return teamAwayId;
    }

    public void setTeamAwayId(String teamAwayId) {
        this.teamAwayId = teamAwayId;
    }

    public String getTeamAwayName() {
        return teamAwayName;
    }

    public void setTeamAwayName(String teamAwayName) {
        this.teamAwayName = teamAwayName;
    }

    public String getTeamAwayLogo() {
        return teamAwayLogo;
    }

    public void setTeamAwayLogo(String teamAwayLogo) {
        this.teamAwayLogo = teamAwayLogo;
    }

    public String getGoalsHome() {
        return goalsHome;
    }

    public void setGoalsHome(String goalsHome) {
        this.goalsHome = goalsHome;
    }

    public String getGoalsAway() {
        return goalsAway;
    }

    public void setGoalsAway(String goalsAway) {
        this.goalsAway = goalsAway;
    }

    public String getScoreHalftimeHome() {
        return scoreHalftimeHome;
    }

    public void setScoreHalftimeHome(String scoreHalftimeHome) {
        this.scoreHalftimeHome = scoreHalftimeHome;
    }

    public String getScoreHalftimeAway() {
        return scoreHalftimeAway;
    }

    public void setScoreHalftimeAway(String scoreHalftimeAway) {
        this.scoreHalftimeAway = scoreHalftimeAway;
    }

    public String getScoreFullTimeHome() {
        return scoreFullTimeHome;
    }

    public void setScoreFullTimeHome(String scoreFullTimeHome) {
        this.scoreFullTimeHome = scoreFullTimeHome;
    }

    public String getScoreFullTimeAway() {
        return scoreFullTimeAway;
    }

    public void setScoreFullTimeAway(String scoreFullTimeAway) {
        this.scoreFullTimeAway = scoreFullTimeAway;
    }

    public String getScoreExtraTimeHome() {
        return scoreExtraTimeHome;
    }

    public void setScoreExtraTimeHome(String scoreExtraTimeHome) {
        this.scoreExtraTimeHome = scoreExtraTimeHome;
    }

    public String getScoreExtraTimeAway() {
        return scoreExtraTimeAway;
    }

    public void setScoreExtraTimeAway(String scoreExtraTimeAway) {
        this.scoreExtraTimeAway = scoreExtraTimeAway;
    }

    public String getScorePenaltyHome() {
        return scorePenaltyHome;
    }

    public void setScorePenaltyHome(String scorePenaltyHome) {
        this.scorePenaltyHome = scorePenaltyHome;
    }

    public String getScorePenaltyAway() {
        return scorePenaltyAway;
    }

    public void setScorePenaltyAway(String scorePenaltyAway) {
        this.scorePenaltyAway = scorePenaltyAway;
    }
}
