package cn.terrylam.chariot.base.cache.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * @author TerryLam 17:37 2019/7/15
 * @description
 **/
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {


	@Bean
	public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(lettuceConnectionFactory).build();
		return redisCacheManager;
	}

	@Bean
	public RedisTemplate redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) throws UnknownHostException {
		RedisTemplate template = new RedisTemplate();
		template.setConnectionFactory(lettuceConnectionFactory);
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringSerializer);
		template.setHashKeySerializer(stringSerializer);
		return template;
	}
}
