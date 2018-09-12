package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.IRepository;
import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.entity.Team;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ITeamRepository extends IRepository<Team>{

    int insertTeam(Team team);

    void insertLeagueTypeRelation(Map<String,Integer> map);

    Team findByTeamName_C(String teamName_C);

}
