package org.sdblt.common.spring;

import javax.servlet.ServletContext;

import org.sdblt.modules.system.service.SysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

public class InitDataListener implements InitializingBean, ServletContextAware {

	/**
	 * 日志对象
	 */
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void setServletContext(ServletContext servletContext) {
		// 在这个方法里面写 初始化的数据也可以。
		InstanceFactory.getInstance(SysService.class).initData();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// 在这个方法里面写 初始化的数据也可以。

	}

}
