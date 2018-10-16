package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.IRepository;
import cn.smallc.footballcollection.entity.Match;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMatchRepository extends IRepository<Match>{

    List<String> getAllMatchCode();

    void updateHasMatchResultTrueByMatchCodes(List<String> matchCodes);

}
