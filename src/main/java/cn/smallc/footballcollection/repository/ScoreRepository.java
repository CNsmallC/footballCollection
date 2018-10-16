package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.Repository;
import cn.smallc.footballcollection.entity.Score;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreRepository extends Repository<Score,IScoreRepository> {

    public List<Score> getByMatchID(int matchID){
        return repository.getByMatchID(matchID);
    }

    public void deleteScoresByMatchCodes(List<String> matchCodes){
        repository.deleteScoresByMatchCodes(matchCodes);
    }

}
