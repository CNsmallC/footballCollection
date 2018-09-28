package cn.smallc.footballcollection.biz;

import cn.smallc.footballcollection.entity.LeagueType;
import cn.smallc.footballcollection.entity.Team;
import cn.smallc.footballcollection.entity.enums.Em_TeamType;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import cn.smallc.footballcollection.util.GetDoc;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author smallC
 * @Date 2018/9/27
 * @Description
 */
public class TeamBiz {

    public static void main(String[] args) {
        getTeamByUrl("http://liansai.500.com/team/239/");
    }

    public static Team getTeamByUrl(String url) {

        try {
            Team team = new Team();

            Document doc = GetDoc.getdoc4Chrome(url, 3000, 0, 2);

            String fullName = doc.select("div[class=lsnav_qdnav clearfix] [class=lsnav_qdnav_name]").text();

            //如果能根据nickname拿,就直接返回team,否则走添加team流程
            Team returnTeam;
            returnTeam = SharedRepositoryFactory.getTeamRepository().findByTeamName_C(fullName);
            if (returnTeam!=null){
                return returnTeam;
            }

            String nickNameStr = doc.head().select("meta").attr("name", "Keywords").attr("content");
            String nickName = "";

            String country = doc.select("div[class=itm_bd]").select("tr").get(1).select("td").get(0).text();

            if (fullName == null || fullName.equals("") || nickNameStr == null || nickNameStr.equals("")) {
                return null;
            }

            if (nickNameStr != null && !nickNameStr.equals("")) {
                nickName = nickNameStr.split(",")[0];
            }
            if (country != null && !country.equals("")) {
                country = country.replace("国家地区：", "");
            }


            //装载
            team.setTeamName_C(fullName);
            team.setNickName_C(nickName);
            team.setCountryName(country);
            if (fullName.equals(country)) {
                team.setTeamType(Em_TeamType.NATIONAL_TEAM);
            } else {
                team.setTeamType(Em_TeamType.CLUB);
            }

            Team dateBaseTeam = SharedRepositoryFactory.getTeamRepository().findByTeamName_C(team.getTeamName_C());

            //如果没有这个球队,就存入
            if (dateBaseTeam == null) {
                SharedRepositoryFactory.getTeamRepository().insertTeam(team);
//                System.out.println("存进去啦!");
            } else if (dateBaseTeam.getNickName_C() == null || "".equals(dateBaseTeam.getNickName_C())) {
                dateBaseTeam.setNickName_C(team.getNickName_C().trim());

                SharedRepositoryFactory.getTeamRepository().update(dateBaseTeam);
//                System.out.println("更新啦!");
            }
            return dateBaseTeam;

//            System.out.println("全名:" + fullName + "\t简称:" + nickName + "\t国家:" + country);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
