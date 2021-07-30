package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;
//为了使配置文件中关于redis的配置信息有效  @EnableConfigurationProperties(CacheProperties.class)
//@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class myCacheConfig {
	
	
	
	@Bean
	RedisCacheConfiguration getCacheConfiguration(CacheProperties cacheProperties) {
		
		
		
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//		configuration.entryTtl(null);
		/**
		 * 修改redis的健值存储的类型
		 */
		config=config.serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()));
		config=config.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		
		
		Redis redisProperties = cacheProperties.getRedis();
		
		if (redisProperties.getTimeToLive() != null) {
			config = config.entryTtl(redisProperties.getTimeToLive());
		}
		if (redisProperties.getKeyPrefix() != null) {
			config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
		}
		if (!redisProperties.isCacheNullValues()) {
			config = config.disableCachingNullValues();
		}
		if (!redisProperties.isUseKeyPrefix()) {
			config = config.disableKeyPrefix();
		}
		return config;
	}
	
}
