package cn.smallc.footballcollection.repository.page;

import cn.smallc.crawlercollection.common.db.IRepository;
import cn.smallc.crawlercollection.entity.Page;
import cn.smallc.footballcollection.common.db.IRepository;
import cn.smallc.footballcollection.entity.crawler.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPageRepository extends IRepository<Page>,IRepository<Page> {

//    int transfer(@Param("money") double money, @Param("id") int id);




}
