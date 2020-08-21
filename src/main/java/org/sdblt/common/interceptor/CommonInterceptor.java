package org.sdblt.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.message.JsonResult;
import org.sdblt.common.message.ProcessStatus;
import org.sdblt.utils.DateUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

public class CommonInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		response.setHeader("Expires", (System.currentTimeMillis() / 1000) + "");
		response.setHeader("DATE", DateUtils.formatDateTime(System.currentTimeMillis()));
	}

	/**
	 * 异常处理 统一抛出500
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (ex != null) {
//			int code = 90001;
//			if (ex instanceof CommonException) {
//				ex.printStackTrace();
//				code = ((CommonException) ex).getCode();
//
//			}
//			String msg = ex.getMessage();
//			// 返回
//			ProcessStatus status = new ProcessStatus(code, msg);
//			JsonResult result = new JsonResult(null, status);
//
//			response.setStatus(500);
//			/*
//			 * 是设置响应连接过来的编码格式 比如我像一个url说：你好，那么传过来的话我就默认用UTF-8编码接受。
//			 **/
//			response.setCharacterEncoding("UTF-8");
//
//			/**
//			 * 设置页面以什么编码格式显示，这里设置为UTF-8
//			 **/
//			response.setContentType("text/html;charset=UTF-8");
//
//			String message = JSON.toJSONString(result);
//
//			response.getWriter().write(message);
//			response.getWriter().flush();
		}
	}
}
