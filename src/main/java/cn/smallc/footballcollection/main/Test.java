package cn.smallc.footballcollection.main;

import cn.smallc.footballcollection.entity.LeagueType;
import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.entity.Score;
import cn.smallc.footballcollection.entity.Team;
import cn.smallc.footballcollection.entity.enums.Em_LeagueType;
import cn.smallc.footballcollection.entity.enums.Em_TeamType;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import cn.smallc.footballcollection.util.SC_IOUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {

//        addTeam("E:\\smallC\\workspace\\footballCollection\\src\\main\\resources\\Test.txt");
        test();


    }

    public static void test() {

        Match match = new Match();
        List<Score> scoreList = new ArrayList<>();

        Score score1 = new Score();
        score1.setMatchCode("a111");
        scoreList.add(score1);

        Score score2 = new Score();
        score2.setMatchCode("a222");
        scoreList.add(score2);

        Score score3 = new Score();
        score3.setMatchCode("a333");
        scoreList.add(score3);

        match.setScores(scoreList);


        Match match1 = new Match();
        List<Score> scoreList1 = new ArrayList<>();

        Score score11 = new Score();
        score11.setMatchCode("b111");
        scoreList1.add(score11);

        Score score22 = new Score();
        score22.setMatchCode("b222");
        scoreList1.add(score22);

        Score score33 = new Score();
        score33.setMatchCode("b333");
        scoreList1.add(score33);

        match1.setScores(scoreList1);

        List<Match> matches = new ArrayList<>();
        matches.add(match);
        matches.add(match1);


        List<Score> out = matches.stream().flatMap(m->m.getScores().stream()).collect(Collectors.toList());


        out.stream().forEach(m-> System.out.println(m.getMatchCode()));


    }



    public static void addTeam(String filePath){
        List<String> file = SC_IOUtils.bufferedReadFile(filePath);

        for (String f : file) {

//            System.out.println(f);

            Team team = new Team();

            String[] s = f.split("\t");
            team.setTeamName_C(s[0]);
            team.setTeamName_E(s[1]);
            team.setNickName_E(s[2]);
            team.setCountryName(s[3]);
            LeagueType lt = SharedRepositoryFactory.getLeagueTypeRepository().getByNickName(Em_LeagueType.La_Liga.getValue());
            team.setLeagueType(lt);
            team.setTeamType(Em_TeamType.CLUB);

            SharedRepositoryFactory.getTeamRepository().insertTeam(team);

        }
    }

}
