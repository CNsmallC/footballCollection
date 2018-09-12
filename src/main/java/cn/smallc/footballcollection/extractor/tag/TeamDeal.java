package cn.smallc.footballcollection.extractor.tag;

import cn.smallc.footballcollection.biz.LeagueTypeBiz;
import cn.smallc.footballcollection.entity.LeagueType;
import cn.smallc.footballcollection.entity.Team;
import cn.smallc.footballcollection.entity.enums.Em_TeamType;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import cn.smallc.footballcollection.util.GetDoc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author smallC
 * @Date 2018/9/11
 * @Description
 */
public class TeamDeal {



    static Logger logger = LogManager.getLogger(TeamDeal.class);


//  最后一个team的地址: http://liansai.500.com/team/10898
    public static void main(String[] args) throws Exception {
        getLeagueMap("http://liansai.500.com/");

//        insertAllTeam();
    }

    public static void insertAllTeam() {
//        int index = 10906;

        String url = "http://liansai.500.com/team/";

        for (int index = 1;index<=10;index++){
            try {
                Team team = new Team();

                Document doc = GetDoc.getdoc4Chrome(url + String.valueOf(index), 3000, 0, 2);
//            Document doc = GetDoc.getdoc(url + "653", 3000, 0, 2);

                String fullName = doc.select("div[class=lsnav_qdnav clearfix] [class=lsnav_qdnav_name]").text();

                String nickName = doc.head().select("meta").attr("name","Keywords").attr("content");

                String country = doc.select("div[class=itm_bd]").select("tr").get(1).select("td").get(0).text();

                if (fullName==null||fullName.equals("")||nickName==null&&nickName.equals("")){
                    logger.info("没有插入成功\t" + index);
                }

                if(nickName!=null&&!nickName.equals("")){
                    nickName = nickName.split(",")[0];
                }
                if(country!=null&&!country.equals("")){
                    country = country.replace("国家地区：","");
                }



                //装载
                team.setTeamName_C(fullName);
                team.setNickName_C(nickName);
                team.setCountryName(country);
                if (fullName.equals(country)){
                    team.setTeamType(Em_TeamType.NATIONAL_TEAM);
                }else{
                    team.setTeamType(Em_TeamType.CLUB);
                }

                Team dateBaseTeam = SharedRepositoryFactory.getTeamRepository().findByTeamName_C(team.getTeamName_C());

                //如果
                if (dateBaseTeam==null){
                    SharedRepositoryFactory.getTeamRepository().insertTeam(team);
                }


                logger.info("插入成功\t" + index);

                System.out.println("全名:" + fullName + "\t简称:" + nickName + "\t国家:" + country);
            }catch (Exception e){
                e.printStackTrace();
                logger.info("没有插入成功\t" + index);
            }
        }




//        for (;index<11100;index++){
//
//            Document doc = GetDoc.getdoc(url + String.valueOf(index), 3000, 0, 2);
//
//            String name = doc.select("div[class=lsnav_qdnav clearfix] h2[class=lsnav_qdnav_name]").text();
//
//            if (name!=null&&!"".equals(name)){
//                System.out.println(url+String.valueOf(index + "\t" + "还有数据:" + name));
//            }
//
//            try {
//                Thread.sleep(2000);
//                System.out.println(index);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }


    }

    private static List<Map<String,String>> getLeagueMap(String url) {
        Document doc = GetDoc.getdoc4Chrome(url, 3000, 0, 2);

        try {
            Elements elements = doc.select("div[class=lallrace_main] ul[class=lallrace_main_list clearfix] div[class=lallrace_pop_in] a");

//        所有联赛地址
            List<String> stringList = elements.eachAttr("abs:href");

            List<Map<String,String>> leagueTypes = new ArrayList<>();

            elements.stream().forEach(m->{
                Map<String,String> leagueMap = new HashMap<>();

                leagueMap.put(m.text(),m.attr("abs:href") + "teams/");

                System.out.println(m.text()+"\t"+m.attr("abs:href") + "teams/");

                leagueTypes.add(leagueMap);
            });

            return leagueTypes;
        }catch (NullPointerException e){
            getLeagueMap(url);
        }

        return null;
    }
}
