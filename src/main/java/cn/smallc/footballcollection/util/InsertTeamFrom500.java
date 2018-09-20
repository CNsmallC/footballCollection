package cn.smallc.footballcollection.util;

import cn.smallc.footballcollection.entity.Team;
import cn.smallc.footballcollection.entity.enums.Em_TeamType;
import cn.smallc.footballcollection.extractor.tag.TeamDeal;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author smallC
 * @Date 2018/9/17
 * @Description
 */
public class InsertTeamFrom500 {

    public static Logger logger;

    static {
        System.setProperty("log4j2Filename","notInsertTeamIndex_log");
        logger = LogManager.getLogger(TeamDeal.class);
    }



    //  最后一个team的地址: http://liansai.500.com/team/10898
    public static void main(String[] args) throws Exception {

//        getLeagueMap("http://liansai.500.com/");





//        failInsertIndex();

//        insertAllTeam();
    }


    public static  void failInsertIndex(){

        List<String> list = SC_IOUtils.bufferedReadFile("E:\\smallC\\workspace\\footballCollection\\src\\main\\resources\\test.txt");

        list.stream().forEach(m->{
            if (m.contains("直接爆炸啦")){
                System.out.println(m.split("\t")[1]);
            }
        });




    }

    public static void insertAllTeam() {
//        int index = 10905;

        String url = "http://liansai.500.com/team/";
        int successCount = 0;
        int nothaveCount = 0;
        int failCount = 0;

        Date date = new Date();

        List<String> list = SC_IOUtils.bufferedReadFile("E:\\smallC\\workspace\\footballCollection\\src\\main\\resources\\test.txt");

        ArrayList<String> teamIndexList = new ArrayList<>();

        list.stream().forEach(m->{
            if (m.contains("直接爆炸啦")){
                teamIndexList.add(m.split("\t")[1]);
            }
        });

//        for (int index = 2001;index<=11000;index++){
        for (String index:teamIndexList){
            try {
                Team team = new Team();

                Document doc = GetDoc.getdoc4Chrome(url + String.valueOf(index), 3000, 0, 2);

//              Document doc = GetDoc.getdoc(url + "653", 3000, 0, 2);

                String fullName = doc.select("div[class=lsnav_qdnav clearfix] [class=lsnav_qdnav_name]").text();

                String nickName = doc.head().select("meta").attr("name","Keywords").attr("content");

                String country = doc.select("div[class=itm_bd]").select("tr").get(1).select("td").get(0).text();

                if (fullName==null||fullName.equals("")||nickName==null||nickName.equals("")){
                    logger.info(date.getTime()+"\t没有插入成功\t" + index);
                    nothaveCount = nothaveCount++;
                    continue;
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

                //如果没有这个球队,就存入
                if (dateBaseTeam==null){
                    SharedRepositoryFactory.getTeamRepository().insertTeam(team);
                    System.out.println("存进去啦!");
                    logger.info(date.getTime()+"\t插入成功\t" + index);
                    successCount=successCount++;
                }

                System.out.println("全名:" + fullName + "\t简称:" + nickName + "\t国家:" + country);
            }catch (Exception e){
                e.printStackTrace();
                logger.info(date.getTime()+"\t直接爆炸啦!!!!!!!!!\t" + index);
                failCount=failCount++;
            }
        }

        System.out.println("存入成功的数量:"+successCount);
        System.out.println("存入失败的数量:"+failCount);
        System.out.println("没有账号的数量:"+nothaveCount);




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

    private static List<String[]> getLeagueMap(String url) {
        Document doc = GetDoc.getdoc4Chrome(url, 3000, 0, 2);

        try {
            Elements elements = doc.select("div[class=lallrace_main] ul[class=lallrace_main_list clearfix] div[class=lallrace_pop_in] a");

//        所有联赛地址
            List<String> stringList = elements.eachAttr("abs:href");

            List<String[]> leagueTypes = new ArrayList<>();

            elements.stream().forEach(m->{

                String[] strs = new String[]{m.text().trim(),m.attr("abs:href") + "teams/"};

                System.out.println(strs[0]+","+strs[1]);

                leagueTypes.add(strs);
            });

            return leagueTypes;
        }catch (NullPointerException e){
            getLeagueMap(url);
        }

        return null;
    }
}
