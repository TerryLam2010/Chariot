package cn.terrylam.chariot.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import cn.terrylam.framework.util.SpringCtxUtils;

/**
 * 应用spring主配置
 * 
 * @author TerryLam
 *
 */
@Configuration
public class BaseConfig {

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SpringCtxUtils configSpringCtxUtils() {
		return new SpringCtxUtils();
	}

}
