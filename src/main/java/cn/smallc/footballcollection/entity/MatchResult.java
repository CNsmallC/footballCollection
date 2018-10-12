package cn.smallc.footballcollection.entity;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;

/**
 * @Author smallC
 * @Date 2018/10/11
 * @Description
 */
public class MatchResult implements IAggregateRoot {

    //比赛码
    private String matchCode;
    //比赛结束比分 : 隔开
    private String endScore;
    //胜负平
    private String endNormal;
    //让球胜负平
    private String endRQ_Normal;
    //全半场结果 3胜,1平,0负
    private String halfAndAll;
    //总进球数
    private int allGoal;
    //是否胜其他
    private Boolean isWinOther = false;
    //是否平其他
    private Boolean isDrawOther = false;
    //是否负其他
    private Boolean isLoseOther = false;

    //根据比分判断是否是胜负平其他
    private void isOtherEnd(String endScore){
        int homeTeamScore = Integer.valueOf(endScore.split(":")[0]);
        int awayTeamScore = Integer.valueOf(endScore.split(":")[1]);

        if ((homeTeamScore > awayTeamScore && homeTeamScore >= 6)
                || (homeTeamScore==4 && awayTeamScore==3)
                || (homeTeamScore==5 && (awayTeamScore==3 || awayTeamScore==4))){
            this.isWinOther = true;
            return;
        }

        if (( awayTeamScore > homeTeamScore && awayTeamScore >= 6)
                || (awayTeamScore==4 && homeTeamScore==3)
                || (awayTeamScore==5 && (homeTeamScore==3 || homeTeamScore==4))){
            this.isLoseOther = true;
            return;
        }

        if( homeTeamScore == awayTeamScore && homeTeamScore>=4 ){
            this.isDrawOther = true;
        }


    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getEndScore() {
        return endScore;
    }

    public void setEndScore(String endScore) {
        isOtherEnd(endScore);
        this.endScore = endScore;
    }

    public String getEndNormal() {
        return endNormal;
    }

    public void setEndNormal(String endNormal) {
        this.endNormal = endNormal;
    }

    public String getEndRQ_Normal() {
        return endRQ_Normal;
    }

    public void setEndRQ_Normal(String endRQ_Normal) {
        this.endRQ_Normal = endRQ_Normal;
    }

    public String getHalfAndAll() {
        return halfAndAll;
    }

    public void setHalfAndAll(String halfAndAll) {
        this.halfAndAll = halfAndAll;
    }

    public int getAllGoal() {
        return allGoal;
    }

    public void setAllGoal(int allGoal) {
        this.allGoal = allGoal;
    }

    public Boolean getWinOther() {
        return isWinOther;
    }

    public void setWinOther(Boolean winOther) {
        isWinOther = winOther;
    }

    public Boolean getDrawOther() {
        return isDrawOther;
    }

    public void setDrawOther(Boolean drawOther) {
        isDrawOther = drawOther;
    }

    public Boolean getLoseOther() {
        return isLoseOther;
    }

    public void setLoseOther(Boolean loseOther) {
        isLoseOther = loseOther;
    }
}
