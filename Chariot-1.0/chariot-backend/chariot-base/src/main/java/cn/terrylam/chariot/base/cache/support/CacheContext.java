/**
 * Created on  13-09-04 15:34
 */
package cn.terrylam.chariot.base.cache.support;

import cn.terrylam.chariot.base.cache.MyCache;
import cn.terrylam.chariot.base.cache.config.CacheInvokeContext;
import cn.terrylam.framework.util.SpringCtxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class CacheContext {

    private static Logger logger = LoggerFactory.getLogger(CacheContext.class);

    private ConcurrentHashMap<String, MyCache> cacheManager;

    public CacheContext() {
        init();
    }

    public synchronized void init() {
        if (cacheManager == null) {
            this.cacheManager = new ConcurrentHashMap<>();
        }
    }

    public CacheInvokeContext createCacheInvokeContext(ConfigMap configMap) {
        CacheInvokeContext c = newCacheInvokeContext();
        c.setCacheFunction((invokeContext, cacheAnnoConfig) -> {
            MyCache cache = cacheAnnoConfig.getCache();
            if (cache == null) {
                if (cacheAnnoConfig instanceof CachedAnnoConfig) {
                    cache = createCacheByCachedConfig((CachedAnnoConfig) cacheAnnoConfig,
                            invokeContext.getMethod(), invokeContext.getHiddenPackages());
                }
                cacheAnnoConfig.setCache(cache);
            }
            return cache;
        });
        return c;
    }

    private MyCache createCacheByCachedConfig(CachedAnnoConfig ac, Method method, String[] hiddenPackages) {
        String area = ac.getArea();
        String cacheName = ac.getName();
        MyCache cache = createOrGetCache(ac, area, cacheName);
        return cache;
    }

    private MyCache createOrGetCache(CachedAnnoConfig ac,String area,String cacheName){
        RedisTemplate redisTemplate = SpringCtxUtils.getBean(RedisTemplate.class);

        MyCache myCache = null;
        String key = area+"_"+cacheName;
        if(cacheManager.contains(key)){
            myCache = cacheManager.get(key);
        }else{
            myCache = new MyCache().setRedisTemplate(redisTemplate).setCachedAnnoConfig(ac);
            cacheManager.put(key,myCache);
        }
        return myCache;
    }


    protected CacheInvokeContext newCacheInvokeContext() {
        return new CacheInvokeContext();
    }



}
