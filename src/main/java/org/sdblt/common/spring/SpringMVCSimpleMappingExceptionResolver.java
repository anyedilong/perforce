package org.sdblt.common.spring;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.message.JsonResult;
import org.sdblt.common.message.ProcessStatus;
import org.sdblt.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSON;

public class SpringMVCSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {  
      
    protected Logger logger = LoggerFactory.getLogger(getClass());

      
    @Override  
    protected ModelAndView doResolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {  
        ModelAndView mv = null;  
        String accept = request.getHeader("accept");  
        
        String servletUrl = request.getServletPath();
        if ((StringUtils.isNull(servletUrl) || servletUrl.indexOf("/t/")!= 0) &&
        		(accept != null && !(accept.indexOf("application/json") > -1   
                || (request.getHeader("X-Requested-With") != null   
                && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)))) {  
            mv = super.doResolveException(request, response, handler, ex);  
        } else {  
            try {   
                // json 请求返回  
            	int code = 90001;
        		if (ex instanceof CommonException) {
        			ex.printStackTrace();
        			code = ((CommonException) ex).getCode();

        		}
        		String msg = ex.getMessage();
        		// 返回
        		ProcessStatus status = new ProcessStatus(code, msg);
        		JsonResult result = new JsonResult(null, status);

        		response.setStatus(500);
        		/*
        		 * 是设置响应连接过来的编码格式 比如我像一个url说：你好，那么传过来的话我就默认用UTF-8编码接受。
        		 **/
        		response.setCharacterEncoding("UTF-8");

        		/**
        		 * 设置页面以什么编码格式显示，这里设置为UTF-8
        		 **/
        		response.setContentType("text/html;charset=UTF-8");

        		String message = JSON.toJSONString(result);
        		
        		response.getWriter().write(message);
        		response.getWriter().flush();
        		
            } catch (IOException e) {  
               e.printStackTrace();
            }  
        }  
        doLog((HandlerMethod) handler, ex);  
        return mv;  
    }  
      
    /** 
     * 记录异常日志 
     *  
     * @param handler 
     * @param excetpion 
     */  
    private void doLog(HandlerMethod handler, Exception excetpion) {  
        
    }     
}  