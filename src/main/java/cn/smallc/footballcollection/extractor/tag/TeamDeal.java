package cn.smallc.footballcollection.extractor.tag;

import cn.smallc.footballcollection.entity.LeagueType;
import cn.smallc.footballcollection.util.GetDoc;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author smallC
 * @Date 2018/9/11
 * @Description
 */
public class TeamDeal {


//  最后一个team的地址: http://liansai.500.com/team/10898
    public static void main(String[] args) throws Exception {
        checkOnePage("http://liansai.500.com/");

    }

    private static void checkOnePage(String url) {
        Document doc = GetDoc.getdoc4Chrome(url, 3000, 0, 2);

        Elements elements = doc.select("div[class=lallrace_main] ul[class=lallrace_main_list clearfix] div[class=lallrace_pop_in] a");

        List<String> stringList = elements.eachAttr("abs:href");

        List<LeagueType> leagueTypes = new ArrayList<>();

        elements.stream().forEach(m->{
            LeagueType leagueType = new LeagueType().init(m.attr("title"),m.text());

            leagueTypes.add(leagueType);
        });


        leagueTypes.stream().forEach(m-> System.out.println(m.toString()));






//        stringList.stream().forEach(m->
//            System.out.println(m+"teams/")
//        );


//        System.out.println(doc.select("div[class=lallrace_main] ul[class=lallrace_main_list clearfix] div[class=lallrace_pop_in] a")
//                .eachAttr("abs:href"));




    }
}
