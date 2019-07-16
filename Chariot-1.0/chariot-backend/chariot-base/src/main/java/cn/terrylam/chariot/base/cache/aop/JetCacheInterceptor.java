/**
 * Created on  13-09-18 20:33
 */
package cn.terrylam.chariot.base.cache.aop;

import cn.terrylam.chariot.base.cache.config.CacheInvokeContext;
import cn.terrylam.chariot.base.cache.method.CacheHandler;
import cn.terrylam.chariot.base.cache.method.CacheInvokeConfig;
import cn.terrylam.chariot.base.cache.support.CacheContext;
import cn.terrylam.chariot.base.cache.support.ConfigMap;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

public class JetCacheInterceptor implements MethodInterceptor, ApplicationContextAware {

    private ConfigMap cacheConfigMap;
    private ApplicationContext applicationContext;

    private CacheContext cacheContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        cacheContext = new CacheContext();
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object obj = invocation.getThis();
        CacheInvokeConfig cac = null;
        if (obj != null) {
            String key = CachePointcut.getKey(method, obj.getClass());
            cac  = cacheConfigMap.getByMethodInfo(key);
        }

        if (cac == null) {
            return invocation.proceed();
        }
        CacheInvokeContext context = cacheContext.createCacheInvokeContext(cacheConfigMap);
        context.setInvoker(invocation::proceed);
        context.setMethod(method);
        context.setArgs(invocation.getArguments());
        context.setCacheInvokeConfig(cac);
        return CacheHandler.invoke(context);
    }

    public void setCacheConfigMap(ConfigMap cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }

}
