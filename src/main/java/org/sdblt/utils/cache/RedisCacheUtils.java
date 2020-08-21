//package org.sdblt.utils.cache;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.sdblt.utils.StringUtils;
//import org.sdblt.utils.json.JsonUtil;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import com.alibaba.fastjson.JSON;
//
///**
// * 
// * @ClassName RedisCacheUtils
// * @Description redisCache工具类
// * @author sen
// * @Date 2016年11月17日 下午1:23:35
// * @version 1.0.0
// */
//public class RedisCacheUtils {
//
//	// /**
//	// * 普通redis缓存
//	// */
//	// private static RedisTemplate<String, Object> cacheRedisTemplate =
//	// InstanceFactory.getInstance(RedisTemplate.class,
//	// "cacheRedisTemplate");
//	// /**
//	// * 系统redis缓存
//	// */
//	// private static RedisTemplate<String, Object> sysRedisTemplate =
//	// InstanceFactory.getInstance(RedisTemplate.class,
//	// "sysRedisTemplate");
//	// /**
//	// * 用户redis缓存
//	// */
//	// private static RedisTemplate<String, Object> userRedisTemplate =
//	// InstanceFactory.getInstance(RedisTemplate.class,
//	// "userRedisTemplate");
//
//	/**
//	 * 
//	 * @Description redis存储数据
//	 * @param redisCacheEmun
//	 * @param key
//	 * @param value
//	 * @author sen
//	 * @Date 2016年11月17日 下午1:39:44
//	 */
//	public static void put(RedisCacheEmun redisCacheEmun, Object key, Object value) {
//		put(redisCacheEmun.getRedisTemplate(), key, value, redisCacheEmun.getLiveTime());
//	}
//
//	/**
//	 * 
//	 * @Description 获取redis缓存
//	 * @param redisCacheEmun
//	 * @param key
//	 * @return
//	 * @author sen
//	 * @Date 2016年11月17日 下午1:42:00
//	 */
//	public static <T> T get(RedisCacheEmun redisCacheEmun, Object key, Class<T> clazz) {
//		return get(redisCacheEmun.getRedisTemplate(), key, clazz, redisCacheEmun.getLiveTime());
//	}
//
//	/**
//	 * @Description 获取redis缓存 集合
//	 * @param redisCacheEmun
//	 * @param key
//	 * @param clazz
//	 * @return
//	 * @author sen
//	 * @Date 2017年1月17日 下午1:33:30
//	 */
//	public static <T> List<T> getArray(RedisCacheEmun redisCacheEmun, Object key, Class<T> clazz) {
//		return getArray(redisCacheEmun.getRedisTemplate(), key, clazz, redisCacheEmun.getLiveTime());
//	}
//
//	/**
//	 * 
//	 * @Description 删除缓存
//	 * @param redisCacheEmun
//	 * @param key
//	 * @author sen
//	 * @Date 2016年11月17日 下午1:42:29
//	 */
//	public static void delete(RedisCacheEmun redisCacheEmun, Object key) {
//		delete(redisCacheEmun.getRedisTemplate(), key);
//	}
//
//	/**
//	 * 
//	 * @Description 存入值
//	 * @param key
//	 * @param value
//	 * @author sen
//	 * @Date 2016年11月17日 上午9:27:49
//	 */
//	public static void put(RedisTemplate<String, Object> redisTemplate, Object key, Object value) {
//		put(redisTemplate, key, value, 0);
//	}
//
//	/**
//	 * 加入失效时间
//	 */
//	public static void put(RedisTemplate<String, Object> redisTemplate, Object key, Object value, long liveTime) {
//
//		if (null == value) {
//			return;
//		}
//		if (value instanceof String) {
//			if (StringUtils.isEmpty(value.toString())) {
//				return;
//			}
//		}
//
//		final String keyf = StringUtils.toString(key);
//		if (StringUtils.isNull(keyf)) {
//			return;
//		}
//		final Object valuef = value;
//		final long liveTimef = liveTime;
//		redisTemplate.execute(new RedisCallback<Long>() {
//			public Long doInRedis(RedisConnection connection) throws DataAccessException {
//				byte[] keyb = keyf.getBytes();
//
//				// 将数据转为json
//				String json = JSON.toJSONString(valuef, JsonUtil.getSerializeconfigcamelcase());
//
//				byte[] valueb = StringUtils.getBytes(json);
//				connection.set(keyb, valueb);
//
//				if (liveTimef > 0) {
//					connection.expire(keyb, liveTimef);
//				}
//
//				return 1L;
//			}
//		});
//	}
//
//	/**
//	 * 
//	 * @Description 获取值
//	 * @param key
//	 * @return
//	 * @author sen
//	 * @Date 2016年11月17日 上午9:28:26
//	 */
//	public static <T> T get(RedisTemplate<String, Object> redisTemplate, Object key, Class<T> clazz) {
//
//		return get(redisTemplate, key, clazz, 0);
//	}
//
//	public static <T> T get(RedisTemplate<String, Object> redisTemplate, Object key, final Class<T> clazz,
//			long liveTime) {
//		final String keyf = StringUtils.toString(key);
//		System.out.println(keyf + "剩余失效" + redisTemplate.getExpire(key.toString()));
//		final long liveTimef = liveTime;
//		if (StringUtils.isNull(keyf)) {
//			return null;
//		}
//
//		Object object;
//		object = redisTemplate.execute(new RedisCallback<Object>() {
//			public Object doInRedis(RedisConnection connection) throws DataAccessException {
//				byte[] keyb = keyf.getBytes();
//				byte[] value = connection.get(keyb);
//
//				if (liveTimef > 0) {
//					connection.expire(keyb, liveTimef);
//				}
//				if (value == null) {
//					return null;
//				}
//				String json = StringUtils.toString(value);
//				// 将json----》object
//				Object obj = null;
//				try {
//					obj = JSON.parseObject(json, clazz, JsonUtil.getParserconfigcamelcase());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return obj;
//			}
//
//		});
//
//		return (T) object;
//	}
//
//	public static <T> List<T> getArray(RedisTemplate<String, Object> redisTemplate, Object key, final Class<T> clazz,
//			long liveTime) {
//		final String keyf = StringUtils.toString(key);
//		System.out.println(keyf + "剩余失效" + redisTemplate.getExpire(key.toString()));
//		final long liveTimef = liveTime;
//		if (StringUtils.isNull(keyf)) {
//			return null;
//		}
//
//		Object object;
//		object = redisTemplate.execute(new RedisCallback<Object>() {
//			public Object doInRedis(RedisConnection connection) throws DataAccessException {
//				byte[] keyb = keyf.getBytes();
//				byte[] value = connection.get(keyb);
//
//				if (liveTimef > 0) {
//					connection.expire(keyb, liveTimef);
//				}
//				if (value == null) {
//					return null;
//				}
//				String json = StringUtils.toString(value);
//				// 将json----》object
//				List<T> objArray = null;
//				try {
//					objArray = JSON.parseArray(json, clazz);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return objArray;
//			}
//
//		});
//
//		return (List<T>) object;
//	}
//
//	public static void refreshLiveTime(RedisCacheEmun redisCacheEmun, Object key) {
//		System.out.println(redisCacheEmun + "重置失效时间:" + StringUtils.toString(key) + ":剩余失效"
//				+ redisCacheEmun.getRedisTemplate().getExpire(key.toString()));
//		refreshLiveTime(redisCacheEmun.getRedisTemplate(), key, redisCacheEmun.getLiveTime());
//	}
//
//	private static void refreshLiveTime(RedisTemplate<String, Object> redisTemplate, Object key, long liveTime) {
//		if (liveTime > 0) {
//			final String keyf = StringUtils.toString(key);
//			if (StringUtils.isNull(keyf)) {
//				redisTemplate.expire(keyf, liveTime, TimeUnit.SECONDS);
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @Description 删除缓存
//	 * @param key
//	 * @author sen
//	 * @Date 2016年11月17日 上午9:30:02
//	 */
//	public static void delete(RedisTemplate<String, Object> redisTemplate, Object key) {
//		final String keyf = StringUtils.toString(key);
//		if (StringUtils.isNull(keyf)) {
//			return;
//		}
//		redisTemplate.execute(new RedisCallback<Object>() {
//			public Object doInRedis(RedisConnection connection) throws DataAccessException {
//				byte[] keyb = keyf.getBytes();
//				byte[] value = connection.get(keyb);
//
//				connection.del(keyb);
//				return null;
//			}
//		});
//	}
//
//	/**
//	 * 
//	 * @Description 将byte转object
//	 * @param bytes
//	 * @return
//	 * @author sen
//	 * @Date 2016年11月17日 上午9:23:51
//	 */
//	private static Object toObject(byte[] bytes) {
//		Object obj = null;
//		try {
//			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
//			ObjectInputStream ois = new ObjectInputStream(bis);
//			obj = ois.readObject();
//			ois.close();
//			bis.close();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
//		}
//		return obj;
//	}
//
//	/**
//	 * 
//	 * @Description 将object 转为byte
//	 * @param obj
//	 * @return
//	 * @author sen
//	 * @Date 2016年11月17日 上午9:19:14
//	 */
//	private static byte[] toByteArray(Object obj) {
//
//		byte[] bytes = null;
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		try {
//			ObjectOutputStream oos = new ObjectOutputStream(bos);
//			oos.writeObject(obj);
//			oos.flush();
//			bytes = bos.toByteArray();
//			oos.close();
//			bos.close();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//
//		return bytes;
//
//	}
//}
