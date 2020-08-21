package org.sdblt.utils.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 字段校验
 *@author gaoqs
 *@date 2016年10月31日 下午2:04:32
 */
@Documented
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({  ElementType.TYPE ,ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
public @interface Validate {
	/**
	 * 检验命名 支持通配符
	 */
	String[] name() default {};
	
	/**
	 * 是否必填
	 */
	boolean required() default false;
	
	/**
	 * 校验类型
	 */
	ValidateType[] type() default {ValidateType.GENERAL};
	
	/**
	 *长度 
	 */
	int length() default 0;
	
	/**
	 * 最大长度
	 */
	int maxLength() default 0;
	
	/**
	 * 最小长度
	 */
	int minLength() default 0;
	
	/**
	 * 检验字段
	 */
	String fieldName() default "";
}

