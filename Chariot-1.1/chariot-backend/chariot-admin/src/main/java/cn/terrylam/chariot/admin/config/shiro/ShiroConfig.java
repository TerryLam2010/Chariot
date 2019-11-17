package cn.terrylam.chariot.admin.config.shiro;

import cn.terrylam.chariot.base.dao.system.ResourceDao;
import cn.terrylam.chariot.base.entity.system.Resource;
import cn.terrylam.framework.util.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Import(ShiroManager.class)
public class ShiroConfig {

	@Autowired
	ResourceDao resourceDao;

	@Bean(name = "realm")
	@DependsOn("lifecycleBeanPostProcessor")
	@ConditionalOnMissingBean
	public Realm realm() {
		return new MyRealm();
	}
	
	 /**
     * 用户授权信息Cache
     */
    @Bean(name = "shiroCacheManager")
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }


    @Bean(name = "securityManager")
    @ConditionalOnMissingBean
    public DefaultSecurityManager securityManager() {
        DefaultSecurityManager sm = new DefaultWebSecurityManager();
        sm.setCacheManager(cacheManager());
        return sm;
    }

	@Bean(name = "shiroFilter")
	@DependsOn("securityManager")
	@ConditionalOnMissingBean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager securityManager, Realm realm) {

		securityManager.setRealm(realm);
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/admin/login");
		shiroFilter.setSuccessUrl("/admin/index");
		shiroFilter.setUnauthorizedUrl("/previlige/no");
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		filters.put("anyPerms", new PermsAuthorizationFilter());
		shiroFilter.setFilters(filters);
		setUrlFilter(shiroFilter);
		return shiroFilter;
	}

	private void setUrlFilter(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		List<Resource> resources = resourceDao.findAll();
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/admin/login", "anon");
		filterChainDefinitionMap.put("/admin/session", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");


		//权限范围过滤器从小写到大
		Map<String,String> urls = new LinkedHashMap<>();
		for (Resource resource : resources) {

			if (StringUtils.isNotBlank(resource.getSourceUrl()) && StringUtils.isNotBlank(resource.getSourceKey())) {
				if(urls.containsKey(resource.getSourceUrl())){
					String value = urls.get(resource.getSourceUrl()) + "," + resource.getSourceKey();
					urls.put(resource.getSourceUrl(),value);
				}else{
					urls.put(resource.getSourceUrl(),resource.getSourceKey());
				}
			}
		}
		urls.forEach((key,value)->{
			filterChainDefinitionMap.put(key, "anyPerms[" + value + "]");
		});

		filterChainDefinitionMap.put("/admin/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

	}

}
