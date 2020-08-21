package org.sdblt.common.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.message.ProcessStatus;
import org.sdblt.common.message.ResponseStatus;
import org.sdblt.modules.common.dto.Message;
import org.sdblt.modules.common.utils.CookieUtil;
import org.sdblt.utils.DateUtils;
import org.sdblt.utils.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 异常处理 统一抛出500
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	// 在进入Handler方法之前执行了，使用于身份认证，身份授权，登陆校验等，比如身份认证，用户没有登陆，拦截不再向下执行，返回值为 false
	// ，即可实现拦截；否则，返回true时，拦截不进行执行；
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		String baseUrl = request.getContextPath();

		// 验证cookie 中token 登录
		Message message = CookieUtil.validate(request);// 0 失败 1 成功
		String msg = "";
		if (null != message) {
			String code = message.getCode();
			msg = message.getMessage();
			if ("200".equals(code)) {
				return true;// 用户校验成功
			}
		}

		// 进行页面跳转
		if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
			// AJAX请求
			onAuthFail(response);
		} else {
			String sendUrl = baseUrl+"/login";
			String param = "";
			if(!StringUtils.isNull(requestURI)){
				//param += String.format("redirectURL=%s&", requestURI);
			}
			if(!StringUtils.isNull(msg)){
				param += String.format("errorMsg=%s&", msg);
			}
			if(!StringUtils.isNull(param)){
				sendUrl = sendUrl+"?"+param;
			}
			
			// 传统页面 重定向到登陆界面
			response.getWriter().write("<script>top.window.parent.location.href=window.location.href='"+sendUrl+"';</script>");
		}

		return false;
	}
	
	/**
	 * 
	 * <li>描述:身份认证错误默认返回401状态码</li>
	 * <li>方法名称:onAuthFail</li>
	 * <li>参数:@param response
	 * <li>参数:@throws Exception</li>
	 * <li>返回类型:void</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	private void onAuthFail(ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		response.setContentType("application/json;charset=UTF-8");
		JsonResult ret = new JsonResult("身份认证错误", ProcessStatus.AUTH_ERROR);
		ret.setResponseStatus(ResponseStatus.HTTP_UNAUTHORIZED);
		httpResponse.getWriter().write(ret.toJsonString());
		httpResponse.getWriter().flush();
		httpResponse.getWriter().close();
	}
}
