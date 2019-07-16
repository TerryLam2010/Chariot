package cn.terrylam.chariot.base.cache.config;

import cn.terrylam.chariot.base.cache.anno.EnableMethodCache;
import cn.terrylam.chariot.base.cache.aop.CacheAdvisor;
import cn.terrylam.chariot.base.cache.aop.JetCacheInterceptor;
import cn.terrylam.chariot.base.cache.support.ConfigMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created on 2016/11/16.
 *
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
@Configuration
public class JetCacheProxyConfiguration implements ImportAware, ApplicationContextAware {

    protected AnnotationAttributes enableMethodCache;
    private ApplicationContext applicationContext;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableMethodCache = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableMethodCache.class.getName(), false));
        if (this.enableMethodCache == null) {
            throw new IllegalArgumentException(
                    "@EnableMethodCache is not present on importing class " + importMetadata.getClassName());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean(name = CacheAdvisor.CACHE_ADVISOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAdvisor jetcacheAdvisor() {
        ConfigMap configMap = new ConfigMap();

        JetCacheInterceptor jetCacheInterceptor = new JetCacheInterceptor();
        jetCacheInterceptor.setCacheConfigMap(configMap);
        jetCacheInterceptor.setApplicationContext(applicationContext);

        CacheAdvisor advisor = new CacheAdvisor();
        advisor.setAdviceBeanName(CacheAdvisor.CACHE_ADVISOR_BEAN_NAME);
        advisor.setAdvice(jetCacheInterceptor);
        advisor.setBasePackages(this.enableMethodCache.getStringArray("basePackages"));
        advisor.setCacheConfigMap(configMap);
        advisor.setOrder(this.enableMethodCache.<Integer>getNumber("order"));
        return advisor;
    }

}