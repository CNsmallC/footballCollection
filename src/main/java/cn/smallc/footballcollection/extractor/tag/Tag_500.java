package cn.smallc.footballcollection.extractor.tag;

import cn.smallc.footballcollection.util.GetDoc;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.stream.Stream;

public class Tag_500 {

    public static void main(String[] args) throws Exception {
        checkOnePage("http://trade.500.com/jczq/index.php?playid=312");
    }
    private static void checkOnePage(String url) {
        Document doc = GetDoc.getdoc4Chrome(url, 3000, 0, 2);

        Elements tableElement = doc.select("[id = d_星期四 201]").select("tr");

        int todayMatchCount = tableElement.size();



        tableElement.stream().forEach(s->{


        });

        Elements trs = tableElement.select(".match_time");





//        System.out.println(trs.eachAttr("title"));
        System.out.println(tableElement.size());


    }
}
