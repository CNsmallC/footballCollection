package cn.smallc.footballcollection.common.db;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;

import java.util.List;
import java.util.Map;

public interface IRepository <T extends IAggregateRoot>{
    T get(Object value);

    void insert(T entity);

    void batchInsert(List<T> entities);

    void update(T entity);

    void remove(Object value);

    List<T> queryForList(Map<String, Object> parameter);



}
