package org.sdblt.modules.common.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.message.ProcessStatus;
import org.sdblt.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

/**
 * rest controller基类
 */
public abstract class BaseController {

	@Resource
	protected HttpServletResponse response;

	@Resource
	protected HttpServletRequest request;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 页面转发
	 * <li>描述:</li>
	 * <li>方法名称:forword</li>
	 * <li>参数:@param viewName
	 * <li>参数:@param context
	 * <li>参数:@return</li>
	 * <li>返回类型:ModelAndView</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	protected ModelAndView forword(String viewName, Map<String, Object> ... context) {
		ModelAndView modelView = new ModelAndView();

		modelView.setViewName(viewName);
		if(null != context && context.length > 0 ){
			modelView.addAllObjects(context[0]);
		}
		return modelView;
	}

	/**
	 * 页面重定向
	 * <li>描述:</li>
	 * <li>方法名称:redirect</li>
	 * <li>参数:@param viewName
	 * <li>参数:@param context
	 * <li>参数:@return</li>
	 * <li>返回类型:ModelAndView</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	protected ModelAndView redirect(String viewName, Map<String, Object> ... context) {
		ModelAndView modelView = new ModelAndView();
		viewName = "redirect:" + viewName;
		modelView.setViewName(viewName);
		if(null != context && context.length > 0 ){
			modelView.addAllObjects(context[0]);
		}
		return modelView;
	}

	/**
	 * 
	 * <li>描述:结果集 默认状态为0</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public JsonResult jsonResult(Object data) {
		JsonResult result = new JsonResult(data);
		return result;
	}

	public JsonResult jsonResult() {
		JsonResult result = new JsonResult(null);
		return result;
	}

	/**
	 * 
	 * <li>描述:结果集加状态</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data
	 * <li>参数:@param status
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public JsonResult jsonResult(Object data, ProcessStatus status) {
		JsonResult result = new JsonResult(data, status);
		return result;
	}

	/**
	 * 
	 * <li>描述:</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data 结果
	 * <li>参数:@param propertyKey 配置文件中的key
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public JsonResult jsonResult(Object data, String propertyKey) {
		ProcessStatus status = new ProcessStatus();
		JsonResult result = new JsonResult(data, status);
		return result;
	}

	/**
	 * 
	 * <li>描述:</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data
	 * <li>参数:@param retCode 状态码
	 * <li>参数:@param retMsg 描述
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public JsonResult jsonResult(Object data, int retCode, String retMsg) {
		ProcessStatus status = new ProcessStatus(retCode, retMsg);
		JsonResult result = new JsonResult(data, status);
		return result;
	}
	
	/**
	 * 
	*<li>描述:获取参数</li>
	*<li>方法名称:getParam</li>
	*<li>参数:@param request
	*<li>参数:@return</li>
	*<li>返回类型:String</li>
	*<li>最后更新作者:gaoqs</li>
	 */
	public String getParam(HttpServletRequest request){
		String paramStr = "";
		try {
			InputStream inStream = request.getInputStream();
			byte[] buffer = new byte[request.getContentLength()];
			inStream.read(buffer);
			paramStr = new String(buffer,"utf-8");
			logger.info("参数为："+paramStr);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		if(StringUtils.isNull(paramStr)){
			paramStr = "{}";
		}
		return paramStr;
	}
}
