package cn.terrylam.chariot.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * 应用spring主配置
 * 
 * @author TerryLam
 *
 */
@Configuration
public class AdminConfig {

	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

}
