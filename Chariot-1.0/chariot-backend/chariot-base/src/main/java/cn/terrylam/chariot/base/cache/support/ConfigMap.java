/**
 * Created on 2018/1/22.
 */
package cn.terrylam.chariot.base.cache.support;

import cn.terrylam.chariot.base.cache.anno.CacheConsts;
import cn.terrylam.chariot.base.cache.method.CacheInvokeConfig;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class ConfigMap {
    private ConcurrentHashMap<String, CacheInvokeConfig> methodInfoMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, CacheInvokeConfig> cacheNameMap = new ConcurrentHashMap<>();

    public void putByMethodInfo(String key, CacheInvokeConfig config) {
        methodInfoMap.put(key, config);
        CachedAnnoConfig cac = config.getCachedAnnoConfig();
        if (cac != null && !CacheConsts.UNDEFINED_STRING.equals(cac.getName())) {
            cacheNameMap.put(cac.getArea() + "_" + cac.getName(), config);
        }
    }

    public CacheInvokeConfig getByMethodInfo(String key) {
        return methodInfoMap.get(key);
    }

    public CacheInvokeConfig getByCacheName(String area, String cacheName) {
        return cacheNameMap.get(area + "_" + cacheName);
    }
}
