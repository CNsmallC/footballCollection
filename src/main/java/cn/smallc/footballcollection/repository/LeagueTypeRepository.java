package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.Repository;
import cn.smallc.footballcollection.entity.LeagueType;
import org.springframework.stereotype.Service;

@Service
public class LeagueTypeRepository extends Repository<LeagueType,ILeagueTypeRepository> {

    public LeagueType getByNickName(String nickName){
        return repository.getByNickName(nickName);
    }

    public LeagueType getByLeagueName(String leagueName){
        return  repository.getByLeagueName(leagueName);
    }

}
