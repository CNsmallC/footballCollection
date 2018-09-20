package cn.smallc.footballcollection.extractor.tag;

import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.entity.Score;
import cn.smallc.footballcollection.entity.enums.Em_PlayType;
import cn.smallc.footballcollection.util.GetDoc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author smallC
 * @Date 2018/9/17
 * @Description
 */
public class Insert_CrawlerDeal_500MixMatch {

    public static Logger logger;

    static {
        System.setProperty("log4j2Filename","insertMatch_log");
        logger = LogManager.getLogger(TeamDeal.class);
    }
//  http://live.500.com/?e=2018-09-01   比分赛果
    public static void main(String[] args) {

        Insert_CrawlerDeal_500MixMatch();

    }

    public static void Insert_CrawlerDeal_500MixMatch() {
        //todo 抓取网页信息
//        String url = "http://trade.500.com/jczq/index.php?playid=312";
        String url = "http://trade.500.com/jczq/index.php?playid=312&g=2";

        Document doc = GetDoc.getdoc(url,2000,0,2);

        //所有信息
        Elements elements = doc.select("div[class=bet_container bet_container_hhgg]");
        //所有比赛的行
        elements = elements.select("tbody").select("tr[zid]");

        System.out.println(elements);

        //todo 内容装载

        List<Match> matchList = detailMatchLoad(elements);


        Date date = null;


        //todo 内容存储

    }

    private static List<Match> detailMatchLoad(Elements elements){
        List<Match> matchList = new ArrayList<>();

        for (Element element:elements){
            Match match = new Match();
            List<Score> scoreList = new ArrayList<>();
            //让球
            if (element.attr("rq")!=null){
                try {
                    match.setRq(Integer.valueOf(element.attr("rq")));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //赛事类型
            if (element.attr("lg")!=null){
                String leagueTypeNickName = element.attr("lg");
                //todo 从数据库找赛事

            }//主队客队
            if (element.attr("homesxname")!=null&&element.attr("awaysxname")!=null){
                String homesNickName = element.attr("homesxname").trim();
                String awaysNickName = element.attr("awaysxname").trim();
                //todo 从数据库球队

            }//玩法赔率,胜平负
            if (element.select("div[class=bet_odds  bet_btns]")!=null){
                Score spf_score = new Score();
                spf_score.setPlayType(Em_PlayType.NORMAL);

                Elements spf = element.select("div[class=bet_odds  bet_btns]");

                String spfString = "3|"+spf.select("span[data-type=nspf]").select("span[value=3]").text()+
                        ",1|"+spf.select("span[value=1]").text()
                        +",0|"+spf.select("span[value=0]").text();

                spf_score.setScore_Odds(spfString);

                scoreList.add(spf_score);

                System.out.println("胜平负赔率String:"+spfString);
            }//玩法赔率,让球胜平负
            if (element.select("div[class=bet_odds  bet_btns]")!=null){
                Score rqspf_score = new Score();
                rqspf_score.setPlayType(Em_PlayType.RQ_NORMAL);

                Elements spf = element.select("div[class=bet_odds  bet_btns]");

                String rqspfString = "3|"+spf.select("span[data-type=spf]").select("span[value=3]").text()+
                        ",1|"+spf.select("span[value=1]").text()
                        +",0|"+spf.select("span[value=0]").text();

                rqspf_score.setScore_Odds(rqspfString);
                scoreList.add(rqspf_score);

                System.out.println("让球胜平负赔率String:"+rqspfString);
            }//玩法赔率,比分
            if (element.select("td[class=bf-box] div[class=bet_odds bet_more]")!=null){
                Score bf_score = new Score();
                bf_score.setPlayType(Em_PlayType.SCORE);

                Elements bf = element.select("td[class=bf-box] div[class=bet_odds bet_more]");

                String bfString = bf.select("span[class=odds_item more_item]").attr("data-sp");

                bf_score.setScore_Odds(bfString);
                scoreList.add(bf_score);

                System.out.println("比分赔率String:"+bfString);
            }//玩法赔率,进球数
            if (element.select("td[class= jqs-box] div[class=bet_odds bet_more]")!=null){
                Score allGoal_score = new Score();
                allGoal_score.setPlayType(Em_PlayType.ALL_GOAL);

                Elements allGoal = element.select("td[class= jqs-box] div[class=bet_odds bet_more]");

                String spfString = allGoal.select("span[class=odds_item more_item] [data-type=jqs]").attr("data-sp");

                allGoal_score.setScore_Odds(spfString);

                System.out.println("进球数赔率String:"+spfString);
            }//玩法赔率,半全场
            if (element.select("td[class= bqc-box] div[class=bet_odds bet_more]")!=null){
                Score spf_score = new Score();
                spf_score.setPlayType(Em_PlayType.ALL_GOAL);

                Elements spf = element.select("td[class= bqc-box] div[class=bet_odds bet_more]");

                String spfString = spf.select("span[class=odds_item more_item] [data-type=bqc]").attr("data-sp");

                spf_score.setScore_Odds(spfString);

                System.out.println("半全场比分赔率String:"+spfString);
            }

//          只爬取一个比赛
            break;

        }

        return matchList;

    }




}
