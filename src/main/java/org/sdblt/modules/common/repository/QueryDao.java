package org.sdblt.modules.common.repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.sdblt.common.exception.CommonException;
import org.sdblt.common.page.PageModel;
import org.sdblt.utils.DateUtils;
import org.sdblt.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class QueryDao {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Inject
	protected EntityManager entityManager;

	private final ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

	/**
	 * 
	 * @Description 执行sql语句
	 * @param sql
	 * @param paramObject
	 * @return 返回执行条数
	 * @author sen
	 * @Date 2017年1月8日 下午1:10:49
	 */
	protected int execute(String sql, Object paramObject, List<String>... dateFieldLists) {
		Map<String, Object> paramMap = null;
		if (null != paramObject) {
			paramMap = new HashMap<String, Object>();

			if (paramObject instanceof Map) {
				paramMap = (Map<String, Object>) paramObject;
			} else if (paramObject.getClass() instanceof Class<?>) {
				// 根据sql 和obj 获取注入参数
				getParamMap(sql, paramObject, paramMap);
			} else {
				throw new CommonException(90001, paramObject.getClass() + "错误");
			}
		}
		int count = 0;
		List<String> dateFieldList = null;
		if (null != dateFieldLists && dateFieldLists.length > 0) {
			dateFieldList = dateFieldLists[0];
		}
		Query query = createNativeQuery(sql, paramMap, dateFieldList);

		count = query.executeUpdate();

		paramMap.clear();
		paramMap = null;

		return count;
	}

	/**
	 * 
	 * @Description 获取单条数据
	 * @param sql
	 * @param paramMap
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 上午9:09:15
	 */
	protected <T> T queryOne(String sql, Object paramObject, Class<T> clazz) {

		Map<String, Object> paramMap = null;
		if (null != paramObject) {
			paramMap = new HashMap<String, Object>();

			if (paramObject instanceof Map) {
				paramMap = (Map<String, Object>) paramObject;
			} else if (paramObject.getClass() instanceof Class<?>) {
				// 根据sql 和obj 获取注入参数
				getParamMap(sql, paramObject, paramMap);
			} else {
				throw new CommonException(90001, clazz + "错误");
			}
		}

		List<T> list = queryResultList(sql, paramMap, 0, 1, clazz);

		paramMap.clear();
		paramMap = null;

		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * @Description 查询数据集合
	 * @param sql
	 * @param paramMap
	 * @param clazz
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 下午1:09:52
	 */
	protected <T> List<T> queryList(String sql, Object paramObject, Class<T> clazz) {

		Map<String, Object> paramMap = null;
		if (null != paramObject) {
			paramMap = new HashMap<String, Object>();

			if (paramObject instanceof Map) {
				paramMap = (Map<String, Object>) paramObject;
			} else if (paramObject.getClass() instanceof Class<?>) {
				// 根据sql 和obj 获取注入参数
				getParamMap(sql, paramObject, paramMap);
			} else {
				throw new CommonException(90001, clazz + "错误");
			}
		}

		List<T> list = queryResultList(sql, paramMap, 0, 0, clazz);

		return list;
	}

	/**
	 * 
	 * @Description 分页查询
	 * @param sql
	 * @param paramMap
	 * @param page
	 * @param clazz
	 *            查询数据映射class
	 * @author sen
	 * @Date 2016年11月23日 下午2:30:50
	 */
	protected <T> void queryPageList(String sql, Object paramObject, PageModel page, Class<T> clazz) {

		Assert.notNull(clazz, "Page generic paradigm must not be null!");

		Map<String, Object> paramMap = null;
		if (null != paramObject) {
			paramMap = new HashMap<String, Object>();

			if (paramObject instanceof Map) {
				paramMap = (Map<String, Object>) paramObject;
			} else if (paramObject.getClass() instanceof Class<?>) {
				// 根据sql 和obj 获取注入参数
				getParamMap(sql, paramObject, paramMap);
			} else {
				throw new CommonException(90001, clazz + "错误");
			}
		}

		// 总数量
		long count = queryCount(sql, paramMap);
		page.setCount(count);

		List<T> list = queryResultList(sql, paramMap, page.getNumIndex() - 1, page.getPageSize(), clazz);

		page.setList(list);
	}

	private long queryCount(String sql, Map<String, Object> paramMap) {
		String countSql = String.format("select count(1) from ( %s ) tmp_count", sql);
		Query query = createNativeQuery(countSql, paramMap, null);
		Object obj = query.getSingleResult();
		if (obj != null) {
			return StringUtils.toLong(obj);
		}
		return 0;
	}

	/**
	 * 
	 * @Description 获取查询数据
	 * @param sql
	 * @param paramMap
	 * @param firstIndex
	 * @param maxResult
	 * @param clazz
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 上午11:40:45
	 */
	private <T> List<T> queryResultList(String sql, Map<String, Object> paramMap, int firstIndex, int maxResult,
			Class<T> clazz) {
		Query query = createNativeQuery(sql, paramMap, null);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		if (firstIndex > 0) {
			query.setFirstResult(firstIndex);
		}

		if (maxResult > 0) {
			query.setMaxResults(maxResult);
		}

		List<Map<String, Object>> resultMapList = query.getResultList();

		logger.info(String.format("参数为:%s", paramMap));

		// 如果class 为 MAP 类型 直接返回
		if (clazz == Map.class) {
			return (List<T>) resultMapList;
		}
		// 为基本数据类型
		if (clazz.isPrimitive() || clazz == String.class || clazz == Integer.class || clazz == Long.class
				|| clazz == Double.class || clazz == Float.class || clazz == Date.class) {
			return mapToBasicType(clazz, resultMapList);
		}

		if (resultMapList != null && resultMapList.size() > 0) {
			return mapToObjectList(clazz, resultMapList);
		}
		return null;
	}

	/**
	 * @Description 将结果填充到类中
	 * @param sql
	 * @param resultObj
	 * @param clazz
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 上午10:16:49
	 */
	private <T> T queryFillClass(String sql, Object resultObj, Class<T> clazz) {
		if (null == resultObj) {
			return null;
		}
		try {
			T result = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;

	}

	protected Query createNativeQuery(String sql, Map<String, Object> paramMap, List<String> dateFieldList) {
		Query query = createNativeQuery(sql);

		if (null != paramMap) {
			// 遍历参数
			for (String key : paramMap.keySet()) {
				if (null != dateFieldList && dateFieldList.indexOf(key) >= 0) {
					Date value = (Date) paramMap.get(key);
					query.setParameter(key, value, TemporalType.DATE);
				} else {
					Object value = paramMap.get(key);
					query.setParameter(key, value);
				}
			}
		}
		return query;
	}

	private Query createNativeQuery(String sql) {
		Assert.notNull(sql, "SQL must not be null!");
		return entityManager.createNativeQuery(sql);
	}

	/**
	 * 
	 * @Description map 转object
	 * @param clazz
	 * @param resultMap
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 下午1:40:02
	 */
	private <T> List<T> mapToObjectList(Class<T> clazz, List<Map<String, Object>> resultMapList) {
		if (resultMapList != null && resultMapList.size() > 0) {
			List<T> list = new ArrayList<T>();

			Map<String, Field> fieldMap = getField(clazz);
			// 如果为基本数据类型，则返回

			for (Map<String, Object> resultMap : resultMapList) {

				T obj = mapToObject(fieldMap, clazz, resultMap);
				if (null != obj) {
					list.add(obj);
				}
			}
			return list;
		}
		return null;
	}

	/**
	 * 
	 * @Description 获取基本类型
	 * @param clazz
	 * @param resultMapList
	 * @return
	 * @author sen
	 * @Date 2016年11月24日 上午10:33:28
	 */
	private List mapToBasicType(Class clazz, List<Map<String, Object>> resultMapList) {
		if (resultMapList != null && resultMapList.size() > 0) {
			List list = new ArrayList();
			for (Map<String, Object> resultMap : resultMapList) {
				for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
					list.add(getValue(clazz, entry.getValue()));
				}
			}
			return list;
		}
		return null;
	}

	private <T> T mapToObject(Map<String, Field> fieldMap, Class<T> clazz, Map<String, Object> resultMap) {
		if (resultMap != null) {
			try {
				T object = clazz.newInstance();
				for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
					String key = entry.getKey();
					key = key.toUpperCase().replace("_", "");
					Object value = entry.getValue();
					if (null == value) {
						continue;
					}
					if (fieldMap.containsKey(key)) {
						Field field = fieldMap.get(key);
						// 得到property对应的setter方法
						try {
							Method method = clazz.getMethod(getSetMethodName(field.getName()), field.getType());
							method.invoke(object, getValue(field.getType(), value));
						} catch (Exception e) {
							logger.error(String.format("%s字段转换失败", key));
							e.printStackTrace();
						}
					}
				}
				return object;
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	private Object getValue(Type type, Object obj) {

		if (type == int.class || type == Integer.class) {
			return StringUtils.toInteger(obj);
		} else if (type == String.class) {
			return StringUtils.toString(obj);
		} else if (type == boolean.class) {
			return StringUtils.toBoolean(obj);
		} else if (type == float.class || type == Float.class) {
			return StringUtils.toFloat(obj);
		} else if (type == double.class || type == Double.class) {
			return StringUtils.toDouble(obj);
		} else if (type == long.class || type == Long.class) {
			return StringUtils.toLong(obj);
		} else if (type == Date.class) {
			return DateUtils.parseDate(obj);
		} else {
			if (type instanceof Map) {
				Map<String, Field> fieldMap = getField((Class) type);
				return mapToObject(fieldMap, (Class) type, (Map) obj);
			}
		}

		return null;
	}

	private String getSetMethodName(String fieldName) {
		StringBuffer methodName = new StringBuffer("set");

		if (StringUtils.isNull(fieldName)) {
			return "";
		}

		if (fieldName.length() > 1) {
			// 判断第二个字母
			String fieldTwo = fieldName.substring(1, 2);
			if (!fieldTwo.equals(fieldTwo.toUpperCase())) {
				methodName.append(fieldName.substring(0, 1).toUpperCase());
			} else {
				methodName.append(fieldName.substring(0, 1));
			}
			methodName.append(fieldName.substring(1, fieldName.length()));
			return methodName.toString();
		} else {
			return fieldName.toUpperCase();
		}
	}

	private String getMethodName(String fieldName) {
		StringBuffer methodName = new StringBuffer("get");

		if (StringUtils.isNull(fieldName)) {
			return "";
		}

		if (fieldName.length() > 1) {
			// 判断第二个字母
			String fieldTwo = fieldName.substring(1, 2);
			if (!fieldTwo.equals(fieldTwo.toUpperCase())) {
				methodName.append(fieldName.substring(0, 1).toUpperCase());
			} else {
				methodName.append(fieldName.substring(0, 1));
			}
			methodName.append(fieldName.substring(1, fieldName.length()));
			return methodName.toString();
		} else {
			return fieldName.toUpperCase();
		}
	}

	/**
	 * 
	 * @Description 获取字段
	 * @param clazz
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 下午12:50:04
	 */
	private Map<String, Field> getField(Class clazz) {
		Map<String, Field> fieldMap = new HashMap<String, Field>();

		Field[] f = clazz.getDeclaredFields();
		List<Field[]> flist = new ArrayList<Field[]>();
		flist.add(f);

		Class superClazz = clazz.getSuperclass();
		while (superClazz != null) {
			f = superClazz.getFields();
			flist.add(f);
			superClazz = superClazz.getSuperclass();
		}

		// 遍历所有属性
		for (Field[] fields : flist) {
			for (Field field : fields) {

				String fieldName = field.getName();
				// 将其转为 大写
				fieldName = fieldName.toUpperCase().replace("_", "");
				fieldMap.put(fieldName, field);
			}
		}
		return fieldMap;
	}

	private void getParamMap(String sql, Object paramObject, Map<String, Object> paramMap) {
		// 获取所有的需要注入的参数
		// Pattern pat = Pattern.compile("\\:[^(\\s|,)]*(\\s|,)");
		Pattern pat = Pattern.compile("\\:[0-9a-zA-z|-|_|.]*");
		Matcher mat = pat.matcher(sql);
		while (mat.find()) {
			String key = mat.group(0).substring(1);
			if (!paramMap.containsKey(key)) {
				Object value = getValue(key, paramObject);
				paramMap.put(key, value);
			}
		}

	}

	public Object getValue(String fieldName, Object obj) {

		Object value = obj;
		if (fieldName.indexOf(".") < 0) {
			value = getObject(fieldName, value);

		} else {

			String[] fieldArray = fieldName.split("\\.");

			for (String fieldItem : fieldArray) {
				value = getObject(fieldItem, value);
				if (value == null) {
					return null;
				}
			}
		}

		return value;
	}

	public Object getObject(String fieldName, Object obj) {
		// 获取value
		if (obj == null) {
			return null;
		}

		Object value = null;

		if (obj instanceof Map) {
			// MAP
			Map<Object, Object> map = (Map<Object, Object>) obj;
			value = map.get(fieldName);
		} else if (obj instanceof List) {
			// 集合
			List<Object> list = (List<Object>) obj;
			int index = StringUtils.toInteger(fieldName);
			value = list.get(index);
		} else {
			// 类
			Class clazz = obj.getClass();
			try {
				Method method = clazz.getMethod(getMethodName(fieldName));
				value = method.invoke(obj);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
				throw new CommonException(20001,String.format("%s 类中未发现属性 %s", clazz.getName(), fieldName));
			}

		}

		return value;
	}

}
