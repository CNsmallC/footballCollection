package cn.smallc.footballcollection.repository;

import cn.smallc.footballcollection.common.db.Repository;
import cn.smallc.footballcollection.entity.Match;
import org.springframework.stereotype.Service;

@Service
public class MatchRepository extends Repository<Match,IMatchRepository> {

//    @Transactional
//    public void transfer(){
//        repository.transfer(90,1);
////        int i=1/0;
//        repository.transfer(110,2);
//    }

}
