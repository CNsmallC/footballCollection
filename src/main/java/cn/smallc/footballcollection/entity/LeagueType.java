package cn.smallc.footballcollection.entity;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;

public class LeagueType implements IAggregateRoot {

    private int ID;
    private String leagueName;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }
}
