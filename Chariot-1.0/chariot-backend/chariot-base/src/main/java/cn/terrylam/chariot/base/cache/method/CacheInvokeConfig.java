/**
 * Created on  13-09-21 23:04
 */
package cn.terrylam.chariot.base.cache.method;


import cn.terrylam.chariot.base.cache.support.CachedAnnoConfig;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class CacheInvokeConfig {
    private CachedAnnoConfig cachedAnnoConfig;
    private boolean enableCacheContext;

    private static final CacheInvokeConfig noCacheInvokeConfigInstance = new CacheInvokeConfig();

    public static CacheInvokeConfig getNoCacheInvokeConfigInstance() {
        return noCacheInvokeConfigInstance;
    }

    public CachedAnnoConfig getCachedAnnoConfig() {
        return cachedAnnoConfig;
    }

    public void setCachedAnnoConfig(CachedAnnoConfig cachedAnnoConfig) {
        this.cachedAnnoConfig = cachedAnnoConfig;
    }

    public boolean isEnableCacheContext() {
        return enableCacheContext;
    }

    public void setEnableCacheContext(boolean enableCacheContext) {
        this.enableCacheContext = enableCacheContext;
    }

}
