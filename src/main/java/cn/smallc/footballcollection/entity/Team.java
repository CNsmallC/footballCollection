package cn.smallc.footballcollection.entity;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;
import cn.smallc.footballcollection.entity.enums.Em_TeamType;

public class Team implements IAggregateRoot {

    private int ID;
    private String teamName_C;
    private String teamName_E;
    //缩写名
    private String nickName_C;
    private String nickName_E;
    private String countryName;
    private Em_TeamType teamType;
    private LeagueType leagueType;

    @Override
    public String toString() {
        return "Team{" +
                "ID=" + ID +
                ", teamName_C='" + teamName_C + '\'' +
                ", nickName_C='" + nickName_C + '\'' +
                ", countryName='" + countryName + '\'' +
                ", teamType=" + teamType +
                '}';
    }

    public String getNickName_C() {
        return nickName_C;
    }

    public void setNickName_C(String nickName_C) {
        this.nickName_C = nickName_C;
    }

    public LeagueType getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(LeagueType leagueType) {
        this.leagueType = leagueType;
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

    public String getNickName_E() {
        return nickName_E;
    }

    public void setNickName_E(String nickName_E) {
        this.nickName_E = nickName_E;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Em_TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(Em_TeamType teamType) {
        this.teamType = teamType;
    }
}
