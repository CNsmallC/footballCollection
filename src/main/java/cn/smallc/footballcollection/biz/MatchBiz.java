package cn.smallc.footballcollection.biz;

import cn.smallc.footballcollection.entity.Match;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author smallC
 * @Date 2018/9/27
 * @Description
 */
public class MatchBiz {

    public static void matchesDeal(List<Match> matches){
        List<String> allMatchCode = SharedRepositoryFactory.getMatchRepository().getAllMatchCode();
        List<Match> insertMatches = new ArrayList<>();
        List<Match> updateMatches = new ArrayList<>();

        matches.stream().forEach(match->{
            //如果没有存这个比赛,则insert
            if (!allMatchCode.contains(match.getMatchCode())){
                insertMatches.add(match);
            }else{//如果已存在次比赛code,则update该比赛的scores
                updateMatches.add(match);
            }
        });

        //插入新的赛事
        if (insertMatches.size()>0){
            SharedRepositoryFactory.getMatchRepository().insertMatches(insertMatches);
        }
        //更新原有赛事的赔率
        if (updateMatches.size()>0){
            SharedRepositoryFactory.getMatchRepository().updateMatches(updateMatches);
        }

    }

}
