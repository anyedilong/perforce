//package org.sdblt.utils.cache;
//
//import org.sdblt.common.spring.InstanceFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//public enum RedisCacheEmun {
//	/* 一般缓存 */
//	COMM_CACHE(InstanceFactory.getInstance(RedisTemplate.class, "cacheRedisTemplate")),
//	/* 系统缓存 */
//	SYS_CACHE(InstanceFactory.getInstance(RedisTemplate.class, "sysRedisTemplate")),
//	/* 用户缓存 */
//	USER_CACHE(InstanceFactory.getInstance(RedisTemplate.class, "userRedisTemplate"), 60 * 60);
//
//	private RedisTemplate<String, Object> redisTemplate;
//	private long liveTime = 0;// 过期时间
//
//	private RedisCacheEmun(RedisTemplate<String, Object> redisTemplate) {
//		this.redisTemplate = redisTemplate;
//	}
//
//	private RedisCacheEmun(RedisTemplate<String, Object> redisTemplate, long liveTime) {
//		this.redisTemplate = redisTemplate;
//		this.liveTime = liveTime;
//	}
//
//	public RedisTemplate<String, Object> getRedisTemplate() {
//		return redisTemplate;
//	}
//
//	public long getLiveTime() {
//		return liveTime;
//	}
//}