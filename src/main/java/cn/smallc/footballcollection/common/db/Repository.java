package cn.smallc.footballcollection.common.db;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class Repository<T extends IAggregateRoot, K extends IRepository<T>> {

    @Autowired
    protected K repository;
    /**
     * @Title: Get
     * @Description: 获取对象
     * @param @param value 主键值
     * @param @return
     * @return T    返回类型
     * @throws
     */
    public T get(Object value){
        return repository.get(value);
    }
    /**
     * @Title: Insert
     * @Description: 对象持久化，将对象存入数据库
     * @param @param entity    对象实体
     * @return void    返回类型
     * @throws
     */
    public void insert(T entity){
        repository.insert(entity);
    }
    /**
     * @Title: BatchInsert
     * @Description: 批量对象持久化
     * @param @param entities
     * @return void    返回类型
     * @throws
     */
    public void batchInsert(List<T> entities){
        repository.batchInsert(entities);
    }
    /**
     * @Title: Update
     * @Description: 对象持久化，更新数据库对象
     * @param @param entity 对象实体
     * @return void    返回类型
     * @throws
     */
    public void update(T entity){
        repository.update(entity);
    }
    /**
     * @Title: Remove
     * @Description: 将对象从数据库中移除
     * @param @param value 主键值
     * @return void    返回类型
     * @throws
     */
    public void remove(Object value){
        repository.remove(value);
    }
    /**
     * @Title: QueryForList
     * @Description: 查询对象
     * @param @param parameter 查询条件对象
     * @param @return
     * @return List<T>    返回类型
     * @throws
     */
    public List<T> queryForList(Map<String,Object> parameter){
        return repository.queryForList(parameter);
    }
    /**
     * @Title: queryForPaginatedResult
     * @Description: 分页查询对象 需同时在sql中实现QueryForPaginatedList和GetPaginatedResultCount
     *               上下限含尾不含头
     * @param @param resultset
     * @param @return
     * @return List<T>    返回类型
     * @throws
     */

}
