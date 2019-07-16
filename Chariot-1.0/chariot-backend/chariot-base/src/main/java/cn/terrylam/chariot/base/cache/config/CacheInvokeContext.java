/**
 * Created on  13-10-02 16:10
 */
package cn.terrylam.chariot.base.cache.config;

import cn.terrylam.chariot.base.cache.MyCache;
import cn.terrylam.chariot.base.cache.method.CacheInvokeConfig;
import cn.terrylam.chariot.base.cache.method.Invoker;
import cn.terrylam.chariot.base.cache.support.CacheAnnoConfig;

import java.lang.reflect.Method;
import java.util.function.BiFunction;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class CacheInvokeContext {
    private Invoker invoker;
    private Method method;
    private Object[] args;
    private CacheInvokeConfig cacheInvokeConfig;

    private BiFunction<CacheInvokeContext, CacheAnnoConfig, MyCache> cacheFunction;
    private String[] hiddenPackages;

    public CacheInvokeContext(){
    }


    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setCacheInvokeConfig(CacheInvokeConfig cacheInvokeConfig) {
        this.cacheInvokeConfig = cacheInvokeConfig;
    }

    public CacheInvokeConfig getCacheInvokeConfig() {
        return cacheInvokeConfig;
    }

    public void setHiddenPackages(String[] hiddenPackages) {
        this.hiddenPackages = hiddenPackages;
    }

    public String[] getHiddenPackages() {
        return hiddenPackages;
    }

    public void setCacheFunction(BiFunction<CacheInvokeContext, CacheAnnoConfig, MyCache> cacheFunction) {
        this.cacheFunction = cacheFunction;
    }

    public BiFunction<CacheInvokeContext, CacheAnnoConfig, MyCache> getCacheFunction() {
        return cacheFunction;
    }

    public Object[] getArgs() {
        return args;
    }

}
