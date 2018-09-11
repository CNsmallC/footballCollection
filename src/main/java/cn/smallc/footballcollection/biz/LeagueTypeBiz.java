package cn.smallc.footballcollection.biz;

import cn.smallc.footballcollection.entity.LeagueType;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;

/**
 * @Author smallC
 * @Date 2018/9/11
 * @Description
 */
public class LeagueTypeBiz {

    public static boolean leagueTypeRepeatByNickName(String nickName){

        LeagueType dataBaseLeaueType = SharedRepositoryFactory.getLeagueTypeRepository().getByNickName(nickName);

        return dataBaseLeaueType==null;
    }

    public static boolean leagueTypeRepeatByLeagueName(String leagueName){

        LeagueType dataBaseLeaueType = SharedRepositoryFactory.getLeagueTypeRepository().getByLeagueName(leagueName);

        return dataBaseLeaueType==null;
    }

}
