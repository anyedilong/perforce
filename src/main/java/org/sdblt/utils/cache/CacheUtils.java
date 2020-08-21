package org.sdblt.utils.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sdblt.common.spring.InstanceFactory;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 
 * @ClassName EhRedisCacheUtils
 * @Description ehCache与redis联合二级缓存工具类
 * @author sen
 * @Date 2016年11月17日 下午1:50:08
 * @version 1.0.0
 */
public class CacheUtils {

	/**
	 * 行政区划缓存
	 */
	public static final String SYS_AREA = "sys_area";
	/**
	 * 系统用户缓存
	 */
	public static final String SYS_USER = "sys_user";
	/**
	 * 字典缓存
	 */
	public static final String SYS_DICT = "sys_dict";
	/**
	 * 产品类型
	 */
	public static final String PRODUCT_TYPE = "product_type";
	/**
	 * 激活码
	 */
	public static  final String ACT_CODE="act_code";
	

	/**
	 * 
	 * @Description 获取缓存数据
	 * @param ehRedisCacheEmun
	 * @param key
	 * @return
	 * @author sen
	 * @Date 2016年11月17日 下午8:56:16
	 */
	public static <T> List<T> getArray(CacheEmun ehRedisCacheEmun, Object key, Class<T> clazz) {
		// delete(ehRedisCacheEmun, key);
		final String _key = StringUtils.toString(key);

		if (StringUtils.isNull(_key)) {
			return null;
		}

		Object ehObject = EhCacheUtils.get(ehRedisCacheEmun.getEhCacheEmun(), _key);

		return (List<T>) ehObject;

	}

	/**
	 * 
	 * @Description 获取缓存数据
	 * @param ehRedisCacheEmun
	 * @param key
	 * @return
	 * @author sen
	 * @Date 2016年11月17日 下午8:56:16
	 */
	public static <T> T get(CacheEmun ehRedisCacheEmun, Object key, Class<T> clazz) {
		// delete(ehRedisCacheEmun, key);

		final String _key = StringUtils.toString(key);

		if (StringUtils.isNull(_key)) {
			return null;
		}
		Object ehObject = EhCacheUtils.get(ehRedisCacheEmun.getEhCacheEmun(), _key);
		return (T) ehObject;

	}

	/**
	 * 
	 * @Description 存储缓存数据
	 * @param ehRedisCacheEmun
	 * @param key
	 * @param value
	 * @author sen
	 * @Date 2016年11月17日 下午8:56:09
	 */
	public static void put(CacheEmun ehRedisCacheEmun, Object key, Object value) {

		String _key = StringUtils.toString(key);

		if (StringUtils.isNull(_key) || value == null) {
			return;
		}
		// 放入eh 缓存中
		EhCacheUtils.put(ehRedisCacheEmun.getEhCacheEmun(), _key, value);
	}
	
	public static void put(CacheEmun cacheEmun, Object key, Object value,long liveTime) {

		String _key = StringUtils.toString(key);

		if (StringUtils.isNull(_key) || value == null) {
			return;
		}

		// 放入eh 缓存中
		EhCacheUtils.putLiveTime(cacheEmun.getEhCacheEmun().getValue(), _key, value,liveTime);

	}

	/**
	 * 
	 * @Description 删除缓存数据
	 * @param ehRedisCacheEmun
	 * @param key
	 * @author sen
	 * @Date 2016年11月17日 下午9:07:45
	 */
	public static void delete(CacheEmun ehRedisCacheEmun, Object key) {
		String _key = StringUtils.toString(key);

		if (StringUtils.isNull(_key)) {
			return;
		}
		
		// 删除eh 缓存
		EhCacheUtils.remove(ehRedisCacheEmun.getEhCacheEmun(), _key);
	}

}
