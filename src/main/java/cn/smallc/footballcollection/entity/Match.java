package cn.smallc.footballcollection.entity;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;

import java.util.Date;

public class Match  implements IAggregateRoot {

    private int ID;
    private int homeTeamID;
    private int awayTeamID;
    private int leagueTypeID;
    private Date matchTime;
    private int todayMatchID;
    private boolean isHot;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getHomeTeamID() {
        return homeTeamID;
    }

    public void setHomeTeamID(int homeTeamID) {
        this.homeTeamID = homeTeamID;
    }

    public int getAwayTeamID() {
        return awayTeamID;
    }

    public void setAwayTeamID(int awayTeamID) {
        this.awayTeamID = awayTeamID;
    }

    public int getLeagueTypeID() {
        return leagueTypeID;
    }

    public void setLeagueTypeID(int leagueTypeID) {
        this.leagueTypeID = leagueTypeID;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public int getTodayMatchID() {
        return todayMatchID;
    }

    public void setTodayMatchID(int todayMatchID) {
        this.todayMatchID = todayMatchID;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }
}
