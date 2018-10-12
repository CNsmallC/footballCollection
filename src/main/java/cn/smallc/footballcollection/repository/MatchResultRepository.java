package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.Repository;
import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.entity.MatchResult;
import cn.smallc.footballcollection.entity.Score;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchResultRepository extends Repository<MatchResult,IMatchResultRepository> {



}
