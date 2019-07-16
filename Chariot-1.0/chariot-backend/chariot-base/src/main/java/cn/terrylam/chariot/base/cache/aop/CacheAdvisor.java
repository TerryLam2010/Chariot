/**
 * Created on  13-09-19 20:40
 */
package cn.terrylam.chariot.base.cache.aop;

import cn.terrylam.chariot.base.cache.support.ConfigMap;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

public class CacheAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    public static final String CACHE_ADVISOR_BEAN_NAME = "jetcache2.internalCacheAdvisor";

    private ConfigMap cacheConfigMap;

    private String[] basePackages;

    @Override
    public Pointcut getPointcut() {
        CachePointcut pointcut = new CachePointcut(basePackages);
        pointcut.setCacheConfigMap(cacheConfigMap);
        return pointcut;
    }

    public void setCacheConfigMap(ConfigMap cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }
}
