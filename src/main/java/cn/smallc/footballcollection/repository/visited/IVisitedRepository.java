package cn.smallc.footballcollection.repository.visited;

import cn.smallc.footballcollection.common.db.IRepository;
import cn.smallc.footballcollection.entity.crawler.Visited;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IVisitedRepository extends IRepository<Visited> {

//    int transfer(@Param("money") double money, @Param("id") int id);

    List<Visited> getByTitleAndUrl(@Param("title") String title, @Param("url") String url);

    Visited getByTitle(String title);

    Visited getByUrl(String url);

    List<Visited> getAll();

    void removeByUrlAndTitle(Visited visited);


}
