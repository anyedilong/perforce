package org.sdblt.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.sdblt.utils.ConvertUtil;

/**
 * 
 * @author Administrator
 *
 */
public class ReflectUtils {
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Map<String,Object> param , Class clazz){
		
		Object value = null;
		
		Class[] paramTypes = new Class[1];
		
		Object obj = null;
		
		try {
			//创建实例
			obj = clazz.newInstance();
			Field[] f = clazz.getDeclaredFields();
			List<Field[]> flist = new ArrayList<Field[]>();
			flist.add(f);
			
			Class superClazz = clazz.getSuperclass();
			while(superClazz != null){
				f = superClazz.getFields();
				flist.add(f);
				superClazz = superClazz.getSuperclass();
			}
			
			for (Field[] fields : flist) {
				for (Field field : fields) {
					
					
					
					String fieldName = field.getName();
					value = param.get(fieldName);
					if(value != null){
						paramTypes[0] = field.getType();
						//调用相应对象的set方法
						StringBuffer methodName = new StringBuffer("set");
						methodName.append(fieldName.substring(0, 1).toUpperCase());
						methodName.append(fieldName.substring(1, fieldName.length()));
						//测试输出
						System.out.println(paramTypes[0].getName());
						if(field.toString().indexOf("public") == 0){
							field.set(obj, ConvertUtil.getValue(value.toString(), fieldName, paramTypes[0]));
						}else{
							Method method = null;
							
							try {
								method = clazz.getMethod(methodName.toString(), paramTypes);
								method.invoke(obj, ConvertUtil.getValue(value.toString(), fieldName, paramTypes[0]));
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return (T)obj;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBeanInitValue(Class clazz){
		
		Object value = null;
		
		Class[] paramTypes = new Class[1];
		
		Object obj = null;
		
		try {
			//创建实例
			obj = clazz.newInstance();
			Field[] f = clazz.getDeclaredFields();
			List<Field[]> flist = new ArrayList<Field[]>();
			flist.add(f);
			
			Class superClazz = clazz.getSuperclass();
			while(superClazz != null){
				f = superClazz.getFields();
				flist.add(f);
				superClazz = superClazz.getSuperclass();
			}
			int i = 0;
			for (Field[] fields : flist) {
				for (Field field : fields) {
					i++;
					
					
					String fieldName = field.getName();
					value = "1"+i;
					if(value != null){
						paramTypes[0] = field.getType();
						//调用相应对象的set方法
						StringBuffer methodName = new StringBuffer("set");
						methodName.append(fieldName.substring(0, 1).toUpperCase());
						methodName.append(fieldName.substring(1, fieldName.length()));
						//测试输出
						System.out.println(paramTypes[0].getName());
						if(field.toString().indexOf("public") == 0){
							field.set(obj, ConvertUtil.getValue(value.toString(), fieldName, paramTypes[0]));
						}else{
							Method method = null;
							
							try {
								method = clazz.getMethod(methodName.toString(), paramTypes);
								if("java.util.Date".equals(paramTypes[0].getName())){
									method.invoke(obj, new Date());
								}else{
									method.invoke(obj, ConvertUtil.getValue(value.toString(), fieldName, paramTypes[0]));
								}
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return (T)obj;
	}
	
}
