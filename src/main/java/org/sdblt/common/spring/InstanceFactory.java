package org.sdblt.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring 托管bean管理
 *<li>当前位置:org.sdblt.common.spring</li> 
 *<li>标题:InstanceFactory</li>
 *<li>描述:</li>
 *<li>公司:</li>
 *<li>版本:1.0</li>
 *@author gaoqs
 *@date 2016年5月10日 下午5:52:57
 */
public class InstanceFactory implements ApplicationContextAware { 

	// Spring应用上下文环境  
    private static ApplicationContext applicationContext;  
    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     *  
     * @param applicationContext 
     */  
    @Override
	public void setApplicationContext(ApplicationContext applicationContext) {  
    	InstanceFactory.applicationContext = applicationContext;  
    }  
    /** 
     * @return ApplicationContext 
     */  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }
	
	/**
	 * 获取托管bean
	 * @param beanType
	 * @return
	 */
	public static <T> T getInstance(Class<T> beanType){
		return applicationContext.getBean(beanType);
	}
	
	/**
	 * 根据beanName获取托管bean
	 * @param beanName
	 * @return
	 */
	public static Object getInstance(String beanName){
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * 根据bean类型和beanname获取托管bean
	 * @param beanType
	 * @param beanName
	 * @return
	 */
	public static <T> T getInstance(Class<T> beanType, String beanName){
		return applicationContext.getBean(beanName, beanType);
	}
}
