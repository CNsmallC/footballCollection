package cn.smallc.footballcollection.main;

import cn.smallc.footballcollection.entity.LeagueType;
import cn.smallc.footballcollection.entity.Team;
import cn.smallc.footballcollection.entity.enums.Em_LeagueType;
import cn.smallc.footballcollection.entity.enums.Em_TeamType;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import cn.smallc.footballcollection.util.SC_IOUtils;

import java.util.List;

public class Test {

    public static void main(String[] args) {

        addTeam("E:\\smallC\\workspace\\footballCollection\\src\\main\\resources\\Test.txt");

    }

    public static void addTeam(String filePath){
        List<String> file = SC_IOUtils.bufferedReadFile(filePath);

        for (String f : file) {

//            System.out.println(f);

            Team team = new Team();

            String[] s = f.split("\t");
            team.setTeamName_C(s[0]);
            team.setTeamName_E(s[1]);
            team.setNickName(s[2]);
            team.setCountryName(s[3]);
            LeagueType lt = SharedRepositoryFactory.getLeagueTypeRepository().getByNickName(Em_LeagueType.Ligue_1.getValue());
            team.setLeagueType(lt);
            team.setTeamType(Em_TeamType.CLUB);

            SharedRepositoryFactory.getTeamRepository().insertTeam(team);

        }
    }

}
