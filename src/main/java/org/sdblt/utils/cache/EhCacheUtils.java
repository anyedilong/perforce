package org.sdblt.utils.cache;

import org.sdblt.common.spring.InstanceFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 
 * @ClassName EhCacheUtils
 * @Description ehCache缓存工具
 * @author sen
 * @Date 2016年11月17日 下午1:49:46
 * @version 1.0.0
 */
public class EhCacheUtils {

	private static CacheManager cacheManager = ((CacheManager)InstanceFactory.getInstance(CacheManager.class));
	
	/**
	 * 获取  缓存
	 * @param CacheType
	 * @param key
	 * @return
	 */
	public static Object get(EhCacheEmun CacheType,String key) {
		return get(CacheType.getValue(), key);
	}
	
	/**
	 * 写入缓存
	 * @param CacheType
	 * @param key
	 * @param value
	 */
	public synchronized static void put(EhCacheEmun CacheType,String key, Object value) {
		put(CacheType.getValue(), key, value);
	}
	
	/**
	 * 从缓存中移除
	 * @param CacheType
	 * @param key
	 */
	public static void remove(EhCacheEmun CacheType,String key) {
		remove(CacheType.getValue(), key);
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element==null?null:element.getObjectValue();
	}

	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public synchronized static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		
		//如果为客户信息缓存，则过期时间为1个小时
//		if(cacheName.equals("userCache")){
//			element.setTimeToIdle(10 );
//		}
//		if(cacheName.equals("dockCache")){
//			element.setTimeToIdle(30 * 60);
//		}
		
		getCache(cacheName).put(element);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName
	 * @param key
	 */
	public synchronized static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	
	/**
	 * 获得一个Cache，没有则创建一个。
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}

	public synchronized static void putLiveTime(String cacheName, String key, Object value,long liveTime) {
		Element element = new Element(key, value);
		
		element.setTimeToLive((int)liveTime);
		
		getCache(cacheName).put(element);
	}
	
}

