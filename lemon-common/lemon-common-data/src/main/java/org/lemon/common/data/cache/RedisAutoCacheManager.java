package org.lemon.common.data.cache;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.CacheConstant;
import org.lemon.common.data.tenant.TenantContextHolder;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * redis cache 扩展cache name自动化配置
 * cachename = xx#ttl
 *
 * @author wwc
 */
@Slf4j
public class RedisAutoCacheManager extends RedisCacheManager {
	private static final String SPLIT_FLAG = "#";
	private static final int CACHE_LENGTH = 2;

	RedisAutoCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
						  Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
		super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
	}

	@Override
	protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
		if (StrUtil.isBlank(name) || !name.contains(SPLIT_FLAG)) {
			return super.createRedisCache(name, cacheConfig);
		}

		String[] cacheArray = name.split(SPLIT_FLAG);
		if (cacheArray.length < CACHE_LENGTH) {
			return super.createRedisCache(name, cacheConfig);
		}

		if (cacheConfig != null) {
			Duration duration = DurationStyle.detectAndParse(cacheArray[1], ChronoUnit.SECONDS);
			cacheConfig = cacheConfig.entryTtl(duration);
		}

		return super.createRedisCache(name, cacheConfig);
	}

	/**
	 * 从上下文中获取租户ID，重写@Cacheable value 值
	 *
	 * @param name
	 * @return
	 */
	@Override
	public Cache getCache(String name) {
		if (name.startsWith(CacheConstant.GLOBALLY)) {
			return super.getCache(name);
		}
		return super.getCache(TenantContextHolder.getTenantId() + StrUtil.COLON + name);
	}

}
