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

        List<MatchResult> matchResults = new ArrayList<>();

        matches.stream().forEach(match->{
            List<Score> one_score = match.getScores();
            one_score.stream().forEach(m->m.setMatchCode(match.getMatchCode()));
            scores.addAll(one_score);
            //如果有赛果,则添加赛果
            if (match.isHasMatchResult()){
                matchResults.add(match.getMatchResult());
            }
        });

        SharedRepositoryFactory.getScoreRepository().batchInsert(scores);
        //如果有赛果,则添加赛果
        if (matchResults.size()>0){
            SharedRepositoryFactory.getMatchResultRepository().batchInsert(matchResults);
        }
    }

    /**
     * 更新赛事信息
     * @param matches
     */
    @Transactional
    public void updateMatches(List<Match> matches){
        List<String> deleteMatchCodes = matches.stream().map(m->m.getMatchCode()).distinct().collect(Collectors.toList());

        List<Score> scores = new ArrayList<>();

        List<MatchResult> matchResults = new ArrayList<>();

        matches.stream().forEach(match->{
            List<Score> one_scores = match.getScores();
            one_scores.stream().forEach(m->m.setMatchCode(match.getMatchCode()));
            scores.addAll(one_scores);
            //如果有赛果,则添加赛果
            if (match.isHasMatchResult()){
                matchResults.add(match.getMatchResult());
            }
        });

//        List<Score> insertScores = matches.stream().flatMap(m->m.getScores().stream()).collect(Collectors.toList());
        SharedRepositoryFactory.getScoreRepository().deleteScoresByMatchCodes(deleteMatchCodes);

        SharedRepositoryFactory.getScoreRepository().batchInsert(scores);

        //如果有赛果,则添加赛果
        if (matchResults.size()>0){
            List<String> matchResultCodes = new ArrayList<>();

            matchResults.stream().forEach(matchResult -> matchResultCodes.add(matchResult.getMatchCode()));
            SharedRepositoryFactory.getMatchResultRepository().deleteMatchResultByMatchCode(matchResultCodes);

            //将比赛表中的比赛是否有赛果改成true
            repository.updateHasMatchResultTrueByMatchCodes(matchResultCodes);

            SharedRepositoryFactory.getMatchResultRepository().batchInsert(matchResults);
        }

    }

    public List<String> getAllMatchCode(){
        return repository.getAllMatchCode();
    }


}
