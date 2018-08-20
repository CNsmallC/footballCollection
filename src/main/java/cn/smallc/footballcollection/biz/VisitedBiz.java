package cn.smallc.footballcollection.biz;


import cn.smallc.footballcollection.entity.crawler.Page;
import cn.smallc.footballcollection.entity.crawler.Visited;

import java.util.List;
import java.util.stream.Collectors;

public class VisitedBiz {



    public static boolean isVisited(Page page, List<Visited> visiteds){
        if (visiteds.stream().map(m->m.getUrl()).collect(Collectors.toList()).contains(page.getUrl())||
                visiteds.stream().anyMatch(m->m.getTitle()==page.getTitle())){
            return true;
        }
        return false;
    }


}
