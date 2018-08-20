package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.IRepository;
import cn.smallc.footballcollection.entity.Match;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMatchRepository extends IRepository<Match>{

}
