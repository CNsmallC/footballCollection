package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.Repository;
import cn.smallc.footballcollection.entity.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeamRepository extends Repository<Team,ITeamRepository> {


    @Transactional
    public void insertTeam(Team team) {
        repository.insertTeam(team);

        int teamID = team.getID();

        Map<String,Integer> parameter = new HashMap<>();
        parameter.put("teamID",teamID);
        parameter.put("leagueID",team.getLeagueType().getID());

        repository.insertLeagueTypeRelation(parameter);

    }
}
