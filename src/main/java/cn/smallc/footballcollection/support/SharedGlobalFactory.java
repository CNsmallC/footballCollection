package cn.smallc.footballcollection.support;

import cn.smallc.footballcollection.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SharedGlobalFactory {
    /**
     * Redis工具类
     */
    private static RedisUtils redisUtils;

    public static RedisUtils getRedisUtils() {
        return redisUtils;
    }
    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        SharedGlobalFactory.redisUtils = redisUtils;
    }
}
