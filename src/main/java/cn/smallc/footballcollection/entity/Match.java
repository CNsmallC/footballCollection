package cn.smallc.footballcollection.entity;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;

import java.util.Date;
import java.util.List;

public class Match  implements IAggregateRoot {

    private int ID;
    private LeagueType leagueType;
    private Date matchTime;
    private int todayMatchID;
    private boolean isHot;
    //比赛时间_主队_客队 的 MD5加密
    private String matchCode;

    //比赛队伍
    private Team homeTeam;
    private Team awayTeam;

    //让球
    private int rq;

    @Override
    public String toString() {
        return "Match{" +
                "ID=" + ID +
                ", leagueType=" + leagueType +
                ", matchTime=" + matchTime +
                ", todayMatchID=" + todayMatchID +
                ", isHot=" + isHot +
                ", matchCode='" + matchCode + '\'' +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", rq=" + rq +
                ", scores=" + scores +
                '}';
    }

    //比分玩法赔率list
    private List<Score> scores;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRq() {
        return rq;
    }

    public void setRq(int rq) {
        this.rq = rq;
    }

    public LeagueType getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(LeagueType leagueType) {
        this.leagueType = leagueType;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
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
