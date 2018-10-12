package cn.smallc.footballcollection.main;

import cn.smallc.footballcollection.biz.MatchBiz;
import cn.smallc.footballcollection.biz.TeamBiz;
import cn.smallc.footballcollection.biz.URL_500_Biz;
import cn.smallc.footballcollection.entity.LeagueType;
import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.entity.Score;
import cn.smallc.footballcollection.entity.Team;
import cn.smallc.footballcollection.entity.enums.Em_PlayType;
import cn.smallc.footballcollection.extractor.tag.TeamDeal;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import cn.smallc.footballcollection.util.GetDoc;
import cn.smallc.footballcollection.util.MD5;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;



/**
 * @Author smallC
 * @Date 2018/9/17
 * @Description
 */
public class Main_500MixMatch_New {

    public static Logger logger;

    static {
        System.setProperty("log4j2Filename","500insertMatch_new_log");
        logger = LogManager.getLogger(TeamDeal.class);
    }
//  http://live.500.com/?e=2018-09-01   比分赛果
    public static void main(String[] args) {

        Insert_CrawlerDeal_500MixMatch();

    }

    public static void Insert_CrawlerDeal_500MixMatch() {
        //抓取网页信息
//        String url = "http://trade.500.com/jczq/index.php?playid=312";
        String url = "http://trade.500.com/jczq/index.php?playid=312&g=2";



        //获取当天比赛页面信息
        Document doc = GetDoc.getdoc(url,2000,0,2);

        //所有信息
        Elements elements = doc.select("div[class=bet-main bet-main-dg]");

        //今日比赛处理
//        dataDeal(elements);



        //昨日比赛处理
        String yesterdayString = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(!elements.select("div[class=bet-date-wrap] span[class=bet-date]").isEmpty()){
            yesterdayString = elements.select("div[class=bet-date-wrap] span[class=bet-date]").first().text();
        }

        try {
            Date thisDayDate = sdf.parse(yesterdayString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(thisDayDate);
            calendar.add(Calendar.DATE,-1);

            Date yesterdayDate = calendar.getTime();

            yesterdayString = sdf.format(yesterdayDate);

        } catch (ParseException e) {
            e.printStackTrace();
            // todo 日志
        }

//        System.out.println(yesterdayString);

        if (yesterdayString!=null){
            yesterdayString = yesterdayString.split(" ")[0];
            String yesterdayUrl = "http://trade.500.com/jczq/index.php?playid=312&g=2&date=" + yesterdayString;

//            System.out.println(yesterdayUrl);

            Document yesterdayDoc = GetDoc.getdoc(yesterdayUrl,2000,0,2);

            //所有信息
            Elements yesterdayElements = yesterdayDoc.select("div[class=bet-main bet-main-dg]");

//            System.out.println(yesterdayElements);

            dataDeal(yesterdayElements);

        }

//        System.out.println(elements);

    }


    private static void dataDeal(Elements elements){
        //所有比赛的行
        Elements elementAllNormal = elements.select("tbody").select("tr[data-infomatchid]");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //过滤已经结束的比赛,因为一结束的比赛,更多比赛的标签会变化
        Elements elementNotBegin = new Elements( elementAllNormal.stream().filter(normal -> {
            try {
                return new Date().getTime() < sdf1.parse(normal.attr("data-buyendtime")).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList()));

        Elements elementNotBeginMore = elements.select("tbody").select("tr[data-infomatchid] + tr[class=bet-more-wrap hide]");

        System.out.println(elementNotBegin.size() + "\t" + elementNotBeginMore.size());

        //所有比赛列表
        List<Match> matchList = new ArrayList<>();
        //未开始的比赛内容装载
        if (elementNotBegin.size()==elementNotBeginMore.size()){
            matchList = URL_500_Biz.detailMatchLoad(elementNotBegin,elementNotBeginMore);
        }else {
            //写入日志文件
        }

        //已开始的比赛内容装载
        Elements elementBegined = new Elements( elementAllNormal.stream().filter(normal -> {
            try {
                return new Date().getTime() > sdf1.parse(normal.attr("data-buyendtime")).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList()));

        Elements elementBeginedMore = elements.select("tbody").select("tr[data-infomatchid] + tr[class=bet-more-wrap bet-tb-end hide]");

        System.out.println(elementBegined.size() + "\t" + elementBeginedMore.size());

        //未开始的比赛内容装载
        if (elementBegined.size()==elementBeginedMore.size()){
            matchList.addAll(URL_500_Biz.detailMatchLoad(elementBegined,elementBeginedMore));
        }else {
            //写入日志文件
        }


        //内容存储
        MatchBiz.matchesDeal(matchList);

    }




}
