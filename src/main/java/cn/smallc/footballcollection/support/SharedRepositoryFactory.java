package cn.smallc.footballcollection.support;

import cn.smallc.footballcollection.entity.MatchResult;
import cn.smallc.footballcollection.entity.Score;
import cn.smallc.footballcollection.repository.*;
import cn.smallc.footballcollection.repository.page.PageRepository;
import cn.smallc.footballcollection.repository.visited.VisitedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SharedRepositoryFactory {

    /**
     * 页面仓储
     */
    private static PageRepository pageRepository;

    /**
     * 联赛分类仓储
     */
    private static LeagueTypeRepository leagueTypeRepository;

    /**
     * 联赛分类仓储
     */
    private static MatchRepository matchRepository;

    /**
     * 联赛分类仓储
     */
    private static ScoreRepository ScoreRepository;

    /**
     * 去重仓储
     */
    private static VisitedRepository visitedRepository;

    /**
     * 联赛分类仓储
     */
    private static TeamRepository teamRepository;

    /**
     * 赛果仓储
     */
    private static MatchResultRepository matchResultRepository;

    public static MatchResultRepository getMatchResultRepository() {
        return matchResultRepository;
    }

    @Autowired
    public void setMatchResultRepository(MatchResultRepository matchResultRepository) {
        SharedRepositoryFactory.matchResultRepository = matchResultRepository;
    }

    public static ScoreRepository getScoreRepository() {
        return ScoreRepository;
    }

    @Autowired
    public void setScoreRepository(ScoreRepository scoreRepository) {
        ScoreRepository = scoreRepository;
    }

    public static VisitedRepository getVisitedRepository() {
        return visitedRepository;
    }

    @Autowired
    public void setVisitedRepository(VisitedRepository visitedRepository) {
        SharedRepositoryFactory.visitedRepository = visitedRepository;
    }

    public static LeagueTypeRepository getLeagueTypeRepository() {
        return leagueTypeRepository;
    }

    @Autowired
    public void setLeagueTypeRepository(LeagueTypeRepository leagueTypeRepository) {
        SharedRepositoryFactory.leagueTypeRepository = leagueTypeRepository;
    }

    public static MatchRepository getMatchRepository() {
        return matchRepository;
    }

    @Autowired
    public void setMatchRepository(MatchRepository matchRepository) {
        SharedRepositoryFactory.matchRepository = matchRepository;
    }

    public static TeamRepository getTeamRepository() {
        return teamRepository;
    }

    @Autowired
    public void setTeamRepository(TeamRepository teamRepository) {
        SharedRepositoryFactory.teamRepository = teamRepository;
    }

    public static PageRepository getPageRepository() {
        return pageRepository;
    }

    @Autowired
    public void setPageRepository(PageRepository pageRepository) {
        SharedRepositoryFactory.pageRepository = pageRepository;
    }

}
