/**
 * Created on  13-09-09 15:59
 */
package cn.terrylam.chariot.base.cache.method;

import cn.terrylam.chariot.base.cache.MyCache;
import cn.terrylam.chariot.base.cache.config.CacheInvokeContext;
import cn.terrylam.chariot.base.cache.exception.CacheInvokeException;
import cn.terrylam.chariot.base.cache.support.CachedAnnoConfig;
import cn.terrylam.chariot.base.cache.support.ConfigMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class CacheHandler implements InvocationHandler {
    private static Logger logger = LoggerFactory.getLogger(CacheHandler.class);

    private Object src;
    private Supplier<CacheInvokeContext> contextSupplier;
    private String[] hiddenPackages;
    private ConfigMap configMap;


    public CacheHandler(Object src, ConfigMap configMap, Supplier<CacheInvokeContext> contextSupplier, String[] hiddenPackages) {
        this.src = src;
        this.configMap = configMap;
        this.contextSupplier = contextSupplier;
        this.hiddenPackages = hiddenPackages;
    }

    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
        CacheInvokeContext context = null;

        String sig = ClassUtil.getMethodSig(method);
        CacheInvokeConfig cac = configMap.getByMethodInfo(sig);
        if (cac != null) {
            context = contextSupplier.get();
            context.setCacheInvokeConfig(cac);
        }
        if (context == null) {
            return method.invoke(src, args);
        } else {
            context.setInvoker(() -> method.invoke(src, args));
            context.setHiddenPackages(hiddenPackages);
            context.setArgs(args);
            context.setMethod(method);
            return invoke(context);
        }
    }

    public static Object invoke(CacheInvokeContext context) throws Throwable {
        if (context.getCacheInvokeConfig().isEnableCacheContext()) {
            try {
                return doInvoke(context);
            } finally {
            }
        } else {
            return doInvoke(context);
        }
    }

    private static Object doInvoke(CacheInvokeContext context) throws Throwable {
        CacheInvokeConfig cic = context.getCacheInvokeConfig();
        CachedAnnoConfig cachedConfig = cic.getCachedAnnoConfig();
        if (cachedConfig != null && (cachedConfig.isEnabled())) {
            return invokeWithCached(context);
        } else {
            return invokeOrigin(context);
        }
    }

    private static Object invokeWithInvalidateOrUpdate(CacheInvokeContext context) throws Throwable {
        Object originResult = invokeOrigin(context);
        CacheInvokeConfig cic = context.getCacheInvokeConfig();




        return originResult;
    }

    private static Object invokeWithCached(CacheInvokeContext context)
            throws Throwable {
        CacheInvokeConfig cic = context.getCacheInvokeConfig();
        CachedAnnoConfig cac = cic.getCachedAnnoConfig();
        MyCache cache = context.getCacheFunction().apply(context, cac);
        if (cache == null) {
            logger.error("no cache with name: " + context.getMethod());
            return invokeOrigin(context);
        }

        Object key = ExpressionUtil.evalKey(context, cic.getCachedAnnoConfig());
        if (key == null) {
            return null;
        }
        try {
            Object result = cache.computeIfAbsent(key, (k) -> {
                try {
                    return invokeOrigin(context);
                } catch (Throwable e) {
                    throw new CacheInvokeException(e.getMessage(), e);
                }
            },true);
            return result;
        } catch (CacheInvokeException e) {
            throw e.getCause();
        }
    }


    private static Object invokeOrigin(CacheInvokeContext context) throws Throwable {
        return context.getInvoker().invoke();
    }



}
