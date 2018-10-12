package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.IRepository;
import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.entity.MatchResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMatchResultRepository extends IRepository<MatchResult>{

    void deleteMatchResultByMatchCode();

}
