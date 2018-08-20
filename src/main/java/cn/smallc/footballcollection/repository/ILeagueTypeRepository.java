package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.IRepository;
import cn.smallc.footballcollection.entity.LeagueType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILeagueTypeRepository extends IRepository<LeagueType>{

    LeagueType getByNickName(String nickName);


}
