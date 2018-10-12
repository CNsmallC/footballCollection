package cn.smallc.footballcollection.biz;

import cn.smallc.footballcollection.entity.*;
import cn.smallc.footballcollection.entity.enums.Em_PlayType;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import cn.smallc.footballcollection.util.GetDoc;
import cn.smallc.footballcollection.util.MD5;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author smallC
 * @Date 2018/10/11
 * @Description
 */

//500业务处理类
public class URL_500_Biz {

    public static List<Match> detailMatchLoad(Elements elementAllNormal , Elements elementAllMore){
        List<Match> matchList = new ArrayList<>();

        for (int index = 0;index < elementAllNormal.size();index++){

            try {

                Match match = new Match();
                List<Score> scoreList = new ArrayList<>();

                //普通信息
                Element elementNormal = elementAllNormal.get(index);
                //更多玩法信息
                Element elementMore = elementAllMore.get(index);



                //比赛时间
                String matchDayStr = elementNormal.attr("data-matchdate");
                String matchTimeStr = elementNormal.attr("data-matchtime");

                SimpleDateFormat sdfMatchDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date matchDate = null;
                try {
                    String matchDateStr = matchDayStr + " " + matchTimeStr;
                    matchDate = sdfMatchDate.parse(matchDateStr);
                }catch (ParseException e){
                    e.printStackTrace();
                }

                match.setMatchTime(matchDate);

                //最后购买时间
                String matchEndBuyStr = elementNormal.attr("data-buyendtime");

                SimpleDateFormat sdfEndBuyDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date endBuyDate = null;
                try {
                    endBuyDate = sdfEndBuyDate.parse(matchEndBuyStr);
                }catch (ParseException e){
                    e.printStackTrace();
                }

                match.setEndBuyTime(endBuyDate);

                //让球
                if (elementNormal.attr("data-rangqiu")!=null){
                    try {
                        match.setRq(Integer.valueOf(elementNormal.attr("data-rangqiu")));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                //赛事类型
                if (elementNormal.attr("data-simpleleague")!=null){
                    String leagueTypeNickName = elementNormal.attr("data-simpleleague");
                    LeagueType leagueType = SharedRepositoryFactory.getLeagueTypeRepository().getByNickName(leagueTypeNickName);

                    if (leagueType==null){
                        if (!elementNormal.select("td[class=td td-evt]").isEmpty()){
                            Document leagueURL = GetDoc.getdoc4Chrome(elementNormal.select("td[class=td td-evt] a").attr("abs:href"),2000,0,2);
                            String leagueString = leagueURL.select("title").text();
                            String fullName = leagueString.split("】")[0].split("_")[1];
                            leagueType = SharedRepositoryFactory.getLeagueTypeRepository().getByNickName(fullName);
                        }
                    }
                    match.setLeagueType(leagueType);


                }//主队客队
                if (elementNormal.select("div[class=team] span[class=team-l]")!=null
                        &&elementNormal.select("div[class=team] span[class=team-r]")!=null){
                    String homesTeamName = elementNormal.select("div[class=team] span[class=team-l] a").attr("title").trim();
                    String awaysTeamName = elementNormal.select("div[class=team] span[class=team-r] a").attr("title").trim();


                    Team homeTeam = SharedRepositoryFactory.getTeamRepository().findByTeamName_C(homesTeamName);
                    Team awaysTeam = SharedRepositoryFactory.getTeamRepository().findByTeamName_C(awaysTeamName);

                    //如果数据库没有队伍,可能是主页显示和队伍页面的名字不同,进入队伍页面地址,走网页拿队伍的处理流程
                    if (homeTeam==null){
                        String url = elementNormal.select("div[class=team] span[class=team-l] a").attr("abs:href").trim();
                        homeTeam = TeamBiz.getTeamByUrl(url);
                    }
                    if (awaysTeam==null){
                        String url = elementNormal.select("div[class=team] span[class=team-r] a").attr("abs:href").trim();
                        awaysTeam = TeamBiz.getTeamByUrl(url);
                    }

                    match.setHomeTeam(homeTeam);
                    match.setAwayTeam(awaysTeam);

                }

                //matchCode
                String matchCode_MD5 = MD5.GetMD5Code(String.valueOf(matchDate.getTime()) + "_"
                        + match.getHomeTeam().getTeamName_C() + "_" + match.getAwayTeam().getTeamName_C());
                match.setMatchCode(matchCode_MD5);

//              判断比赛是否有赛果
                if (new Date().getTime() > matchDate.getTime()
                        && !elementNormal.select("div[class=team] i[class=team-vs team-bf]").isEmpty()
                        && !"VS".equals(elementNormal.select("div[class=team] i[class=team-vs team-bf]").text())){
                    MatchResult matchResult = new MatchResult();

                    //赛果标志为 true
                    match.setHasMatchResult(true);

                    matchResult.setMatchCode(matchCode_MD5);
                    String endScore = elementNormal.select("div[class=team] i[class=team-vs team-bf]").text();
                    matchResult.setEndScore(endScore);
                    int homeTeamScore = Integer.valueOf(endScore.split(":")[0]);
                    int awayTeamScore = Integer.valueOf(endScore.split(":")[1]);
                    int allGoal = homeTeamScore + awayTeamScore;
                    matchResult.setAllGoal(allGoal);

                    match.setMatchResult(matchResult);
                }


                //玩法赔率,胜平负
                if (elementNormal.select("td[class=td td-betbtn]")!=null){
                    Score spf_score = new Score();
                    spf_score.setPlayType(Em_PlayType.NORMAL);

                    Elements spf = elementNormal.select("div[class=betbtn-row itm-rangB1]");

//                  System.out.println(spf);

                    String spfString = "3|"+spf.select("p[data-type=nspf]").select("p[data-value=3]").attr("data-sp")
                            +",1|"+spf.select("p[data-type=nspf]").select("p[data-value=1]").attr("data-sp")
                            +",0|"+spf.select("p[data-type=nspf]").select("p[data-value=0]").attr("data-sp");

                    //如果有赛果,加入胜平负赛果
                    if (match.isHasMatchResult() && !spf.select("p[class=betbtn betbtn-ok]").isEmpty()){
                        match.getMatchResult().setEndNormal(spf.select("p[class=betbtn betbtn-ok]").attr("data-value"));
                    }

                    spf_score.setScore_Odds(spfString);

                    scoreList.add(spf_score);

                    System.out.println("胜平负赔率String:"+spfString);
                }//玩法赔率,让球胜平负
                if (elementNormal.select("td[class=td td-betbtn]")!=null){
                    Score rqspf_score = new Score();
                    rqspf_score.setPlayType(Em_PlayType.RQ_NORMAL);

                    Elements spf = elementNormal.select("div[class=betbtn-row itm-rangB2]");

                    String rqspfString = "3|"+spf.select("p[data-type=spf]").select("p[data-value=3]").attr("data-sp")+
                            ",1|"+spf.select("p[data-type=spf]").select("p[data-value=1]").attr("data-sp")
                            +",0|"+spf.select("p[data-type=spf]").select("p[data-value=1]").attr("data-sp");

                    //如果有赛果,加入胜平负赛果
                    if (match.isHasMatchResult() && !spf.select("p[class=betbtn betbtn-ok]").isEmpty()){
                        match.getMatchResult().setEndNormal(spf.select("p[class=betbtn betbtn-ok]").attr("data-value"));
                    }

                    rqspf_score.setScore_Odds(rqspfString);
                    scoreList.add(rqspf_score);

                    System.out.println("让球胜平负赔率String:"+rqspfString);
                }
                //更多玩法
                morePlay(match,scoreList,elementMore);

//                match.setScores(scoreList);



                System.out.println(match.toString());


//              SharedRepositoryFactory.getMatchRepository().insertMatch(match);
                matchList.add(match);

                //比赛爬取数量控制
//                if (index>2){
//                    break;
//                }

//              只爬取一个比赛
//              break;

            }catch (Exception e ){
                e.printStackTrace();
                continue;
            }
        }
        return matchList;
    }



//  更多玩法数据组装
    private static void morePlay(Match match, List<Score> scoreList,Element elementMore){
        if (elementMore.select("table[class=bet-more-tb]")!=null){

            //玩法赔率,半全场
            if (elementMore.select("table[class=bet-more-tb]").select("p[data-type=bqc]")!=null){
                Score spf_score = new Score();
                spf_score.setPlayType(Em_PlayType.HALF_ALL);

                Elements spf = elementMore.select("table[class=bet-more-tb]").select("p[data-type=bqc]");

                String spfString = "33|"+spf.select("p[data-value=3-3]").attr("data-sp")
                        + ",31|"+spf.select("p[data-value=3-1]").attr("data-sp")
                        + ",30|"+spf.select("p[data-value=3-0]").attr("data-sp")
                        + ",13|"+spf.select("p[data-value=1-3]").attr("data-sp")
                        + ",11|"+spf.select("p[data-value=1-1]").attr("data-sp")
                        + ",10|"+spf.select("p[data-value=1-0]").attr("data-sp")
                        + ",03|"+spf.select("p[data-value=0-3]").attr("data-sp")
                        + ",01|"+spf.select("p[data-value=0-1]").attr("data-sp")
                        + ",00|"+spf.select("p[data-value=0-0]").attr("data-sp");

                if(match.isHasMatchResult() && !spf.select("p[class=sbetbtn  sbetbtn-ok]").isEmpty()){
                    match.getMatchResult().setHalfAndAll(spf.select("p[class=sbetbtn  sbetbtn-ok]").attr("data-value").replace("-",""));
                }

                spf_score.setScore_Odds(spfString);
                scoreList.add(spf_score);

                System.out.println("半全场比分赔率String:"+spfString);
            }

            //玩法赔率,比分
            if (elementMore.select("table[class=bet-more-tb]").select("p[data-type=bf]")!=null){
                Score bf_score = new Score();
                bf_score.setPlayType(Em_PlayType.SCORE);

                Elements bf = elementMore.select("table[class=bet-more-tb]").select("p[data-type=bf]");

                String bfString = "10|" + bf.select("p[data-value=1:0]").attr("data-sp")
                        + ",20|" + bf.select("p[data-value=2:0]").attr("data-sp")
                        + ",21|" + bf.select("p[data-value=2:1]").attr("data-sp")
                        + ",30|" + bf.select("p[data-value=3:0]").attr("data-sp")
                        + ",31|" + bf.select("p[data-value=3:1]").attr("data-sp")
                        + ",32|" + bf.select("p[data-value=3:2]").attr("data-sp")
                        + ",40|" + bf.select("p[data-value=4:0]").attr("data-sp")
                        + ",41|" + bf.select("p[data-value=4:1]").attr("data-sp")
                        + ",42|" + bf.select("p[data-value=4:2]").attr("data-sp")
                        + ",50|" + bf.select("p[data-value=5:0]").attr("data-sp")
                        + ",51|" + bf.select("p[data-value=5:1]").attr("data-sp")
                        + ",52|" + bf.select("p[data-value=5:2]").attr("data-sp")
                        + ",胜其他|" + bf.select("p[data-value=胜其它]").attr("data-sp")

                        + ",00|" + bf.select("p[data-value=0:0]").attr("data-sp")
                        + ",11|" + bf.select("p[data-value=1:1]").attr("data-sp")
                        + ",22|" + bf.select("p[data-value=2:2]").attr("data-sp")
                        + ",33|" + bf.select("p[data-value=3:3]").attr("data-sp")
                        + ",平其他|" + bf.select("p[data-value=平其它]").attr("data-sp")

                        + ",01|" + bf.select("p[data-value=0:1]").attr("data-sp")
                        + ",02|" + bf.select("p[data-value=0:2]").attr("data-sp")
                        + ",12|" + bf.select("p[data-value=1:2]").attr("data-sp")
                        + ",03|" + bf.select("p[data-value=0:3]").attr("data-sp")
                        + ",13|" + bf.select("p[data-value=1:3]").attr("data-sp")
                        + ",23|" + bf.select("p[data-value=2:3]").attr("data-sp")
                        + ",04|" + bf.select("p[data-value=0:4]").attr("data-sp")
                        + ",14|" + bf.select("p[data-value=1:4]").attr("data-sp")
                        + ",24|" + bf.select("p[data-value=2:4]").attr("data-sp")
                        + ",05|" + bf.select("p[data-value=0:5]").attr("data-sp")
                        + ",15|" + bf.select("p[data-value=1:5]").attr("data-sp")
                        + ",25|" + bf.select("p[data-value=2:5]").attr("data-sp")
                        + ",负其他|" + bf.select("p[data-value=负其它]").attr("data-sp")
                        ;

                bf_score.setScore_Odds(bfString);
                scoreList.add(bf_score);

                System.out.println("比分赔率String:"+bfString);
            }

            //玩法赔率,进球数
            if (elementMore.select("table[class=bet-more-tb]").select("p[data-type=jqs]")!=null){
                Score allGoal_score = new Score();
                allGoal_score.setPlayType(Em_PlayType.ALL_GOAL);

                Elements allGoal = elementMore.select("table[class=bet-more-tb]").select("p[data-type=jqs]");

                String jqsString = "0|" + allGoal.select("p[data-value=0]").attr("data-sp")
                        +",1|" + allGoal.select("p[data-value=1]").attr("data-sp")
                        +",2|" + allGoal.select("p[data-value=2]").attr("data-sp")
                        +",3|" + allGoal.select("p[data-value=3]").attr("data-sp")
                        +",4|" + allGoal.select("p[data-value=4]").attr("data-sp")
                        +",5|" + allGoal.select("p[data-value=5]").attr("data-sp")
                        +",6|" + allGoal.select("p[data-value=6]").attr("data-sp")
                        +",7+|" + allGoal.select("p[data-value=7]").attr("data-sp")
                        ;

                allGoal_score.setScore_Odds(jqsString);
                scoreList.add(allGoal_score);

                System.out.println("进球数赔率String:"+jqsString);
            }

            match.setScores(scoreList);

        }
    }

}
