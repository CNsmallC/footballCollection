package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.Repository;
import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.entity.Score;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchRepository extends Repository<Match,IMatchRepository> {

    @Transactional
    public void insertMatch(Match match){

        repository.insert(match);
        List<Score> scores = match.getScores();
        scores.stream().forEach(m->m.setMatchID(match.getID()));

        SharedRepositoryFactory.getScoreRepository().batchInsert(scores);

    }

    @Transactional
    public void insertMatches(List<Match> matches){

        repository.batchInsert(matches);

        List<Score> scores = new ArrayList<>();

        matches.stream().forEach(match->{
            List<Score> one_scores = match.getScores();
            one_scores.stream().forEach(m->m.setMatchCode(match.getMatchCode()));
            scores.addAll(one_scores);
        });

        SharedRepositoryFactory.getScoreRepository().batchInsert(scores);
    }

    /**
     * 更新赛事信息
     * @param matches
     */
    @Transactional
    public void updateMatches(List<Match> matches){
        List<String> deleteMatchCodes = matches.stream().map(m->m.getMatchCode()).distinct().collect(Collectors.toList());

        List<Score> scores = new ArrayList<>();

        matches.stream().forEach(match->{
            List<Score> one_scores = match.getScores();
            one_scores.stream().forEach(m->m.setMatchCode(match.getMatchCode()));
            scores.addAll(one_scores);
        });

//        List<Score> insertScores = matches.stream().flatMap(m->m.getScores().stream()).collect(Collectors.toList());
        SharedRepositoryFactory.getScoreRepository().deleteScoresByMatchCode(deleteMatchCodes);

        SharedRepositoryFactory.getScoreRepository().batchInsert(scores);
    }

    public List<String> getAllMatchCode(){
        return repository.getAllMatchCode();
    }


}
