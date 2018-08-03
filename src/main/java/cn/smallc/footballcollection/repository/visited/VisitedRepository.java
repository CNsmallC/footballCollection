package cn.smallc.footballcollection.repository.visited;

import cn.smallc.footballcollection.common.db.Repository;
import cn.smallc.footballcollection.entity.crawler.Visited;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitedRepository extends Repository<Visited, IVisitedRepository> {


    public List<Visited> getByTitleAndUrl(String title, String url) {
        return repository.getByTitleAndUrl(title, url);
    }

    public Visited getByTitle(String title) {
        return repository.getByTitle(title);
    }

    public Visited getByUrl(String url) {
        return repository.getByUrl(url);
    }

    public List<Visited> getAll() {
        return repository.getAll();
    }

    public void removeByUrlAndTitle(Visited visited) {
        repository.removeByUrlAndTitle(visited);
    }


//    @Transactional
//    public void transfer(){
//        repository.transfer(90,1);
////        int i=1/0;
//        repository.transfer(110,2);
//    }


}
