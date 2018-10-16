package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.IRepository;
import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.entity.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IScoreRepository extends IRepository<Score>{
    List<Score> getByMatchID(int matchID);

    void deleteScoresByMatchCodes(@Param("matchCodes")List<String> matchCodes);

}
