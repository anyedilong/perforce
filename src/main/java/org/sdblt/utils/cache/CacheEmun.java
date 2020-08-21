package org.sdblt.utils.cache;

import org.sdblt.common.spring.InstanceFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 
 * @ClassName EhRedisCacheEmun
 * @Description eh与redis 枚举
 * @author sen
 * @Date 2016年11月17日 下午6:52:37
 * @version 1.0.0
 */
public enum CacheEmun {
	/* 一般缓存 */
	COMM_CACHE(EhCacheEmun.COMM_CACHE),
	/* 系统缓存 */
	SYS_CACHE(EhCacheEmun.SYS_CACHE),
	/* 用户缓存 */
	USER_CACHE(EhCacheEmun.USER_CACHE);

	private EhCacheEmun ehCacheEmun;

	private CacheEmun(EhCacheEmun ehCacheEmun) {
		this.ehCacheEmun = ehCacheEmun;
	}

	public EhCacheEmun getEhCacheEmun() {
		return ehCacheEmun;
	}
   
}