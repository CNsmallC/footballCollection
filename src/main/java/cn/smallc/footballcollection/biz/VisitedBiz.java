package cn.smallc.footballcollection.biz;


import cn.smallc.footballcollection.entity.crawler.Page;
import cn.smallc.footballcollection.entity.crawler.Visited;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;

import java.util.List;
import java.util.stream.Collectors;

public class VisitedBiz {

    public static void addVisited(Page page){
        Visited visited = new Visited(page.getTitle(),page.getUrl());

        SharedRepositoryFactory.getVisitedRepository().insert(visited);
    }

    public static boolean isVisited(Page page, List<Visited> visiteds){
        if (visiteds.stream().map(m->m.getUrl()).collect(Collectors.toList()).contains(page.getUrl())||
                visiteds.stream().anyMatch(m->m.getTitle()==page.getTitle())){
            return true;
        }
        return false;
    }


}
