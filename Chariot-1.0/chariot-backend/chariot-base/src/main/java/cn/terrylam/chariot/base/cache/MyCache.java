package cn.terrylam.chariot.base.cache;

import cn.terrylam.chariot.base.cache.support.CachedAnnoConfig;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.function.Function;

/**
 * @author TerryLam 2019/7/15
 * @version 1.0
 * @description
 **/
public class MyCache<String,V> {


    private RedisTemplate<String,V> redisTemplate;

    private CachedAnnoConfig cachedAnnoConfig;

    public V get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void put(String key,V value){
        redisTemplate.opsForValue().setIfAbsent(key,value,cachedAnnoConfig.getExpire(),cachedAnnoConfig.getTimeUnit());
    }

    public MyCache setRedisTemplate(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        return this;
    }

    public V computeIfAbsent(String key, Function<String, V> loader, boolean cacheNullWhenLoaderReturnNull) {
        V r = get(key);
        if (r != null) {
            return r;
        } else {
            V loadedValue = loader.apply(key);
            if (loadedValue != null || cacheNullWhenLoaderReturnNull) {
                put(key, loadedValue);
            }
            return loadedValue;
        }
    }

    public MyCache setCachedAnnoConfig(CachedAnnoConfig cachedAnnoConfig) {
        this.cachedAnnoConfig = cachedAnnoConfig;
        return this;
    }
}
