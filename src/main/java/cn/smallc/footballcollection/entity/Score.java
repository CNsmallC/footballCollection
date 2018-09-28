package cn.smallc.footballcollection.entity;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;
import cn.smallc.footballcollection.entity.enums.Em_PlayType;

public class Score implements IAggregateRoot {

    private int ID;
//  不同玩法
    private Em_PlayType playType;
//    比分和赔率的特殊字符串  比分|赔率,比分|赔率...
    private String score_Odds;
    private int matchID;
    private String matchCode;

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Em_PlayType getPlayType() {
        return playType;
    }

    public void setPlayType(Em_PlayType playType) {
        this.playType = playType;
    }

    public String getScore_Odds() {
        return score_Odds;
    }

    public void setScore_Odds(String score_Odds) {
        this.score_Odds = score_Odds;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }
}
