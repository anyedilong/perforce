package org.sdblt.utils.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 字段校验
 * 
 * @author gaoqs
 * @date 2016年10月31日 下午2:04:32
 */
@Documented
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
public @interface ValidateMethod {

	/**
	 * 校验 信息 配置文件中对应key
	 */
	String msgProperty() default "GENERAL_MSG";

	/**
	 * 校验类型
	 */
	ValidateType type() default ValidateType.GENERAL;

}
