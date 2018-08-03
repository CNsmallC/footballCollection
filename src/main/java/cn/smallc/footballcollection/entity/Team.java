package cn.smallc.footballcollection.entity;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;
import cn.smallc.footballcollection.entity.enums.Em_TeamType;

public class Team implements IAggregateRoot {

    private int ID;
    private String teamName_C;
    private String teamName_E;
    //缩写名
    private String nickName;
    private Country country;
    private Em_TeamType teamType;
    private int leagueTypeID;

    public int getLeagueTypeID() {
        return leagueTypeID;
    }

    public void setLeagueTypeID(int leagueTypeID) {
        this.leagueTypeID = leagueTypeID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTeamName_C() {
        return teamName_C;
    }

    public void setTeamName_C(String teamName_C) {
        this.teamName_C = teamName_C;
    }

    public String getTeamName_E() {
        return teamName_E;
    }

    public void setTeamName_E(String teamName_E) {
        this.teamName_E = teamName_E;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Em_TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(Em_TeamType teamType) {
        this.teamType = teamType;
    }
}
