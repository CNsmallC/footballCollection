package cn.smallc.footballcollection.extractor.tag;

import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.util.GetDoc;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Tag_500 {

    public static void main(String[] args) throws Exception {
        checkOnePage("http://trade.500.com/jczq/index.php");
    }
    private static void checkOnePage(String url) {
        Document doc = GetDoc.getdoc4Chrome(url, 3000, 0, 2);

        //表格所有内容行
        Elements tableElement = doc.select("[id = d_星期一 201]").select("tr");

//        tableElement.get(0);


        int todayMatchCount = tableElement.size();


        List<Match> matches = new ArrayList<>();



        tableElement.stream().forEach(s->{

            Match match = new Match();

            System.out.println();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //比赛时间
                match.setMatchTime(sdf.parse(s.attr("pendtime")));
                //比赛队伍
                String homeTeamName = s.attr("homesxname");
                String awayTeamName = s.attr("awaysxname");

                //TODO 从数据库查找比赛队伍


                //比赛类型
                System.out.println(s.select(".league").text());


                System.out.println(sdf.parse(s.attr("pendtime")));
            } catch (ParseException e) {
                e.printStackTrace();
            }


        });

        Elements trs = tableElement.select(".match_time");


//        System.out.println(trs.eachAttr("title"));
//        System.out.println(tableElement.size());
        System.out.println(tableElement.get(1));


    }
}
