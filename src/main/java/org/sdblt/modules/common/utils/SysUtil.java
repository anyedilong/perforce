package org.sdblt.modules.common.utils;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.message.ProcessStatus;
import org.sdblt.common.message.ResponseStatus;
import org.sdblt.common.spring.InstanceFactory;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.service.SysService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.sdblt.utils.sha1.SHA1Encrypt;

/**
 * Created by Administrator on 2016/8/6.
 */
public class SysUtil {

	/**
	 * 获取缓存用户信息
	 * 
	 * @return
	 */
	public static UserCache sysUser(HttpServletRequest request, HttpServletResponse response) {
		CookieUser cookieUser = CookieUtil.getCookieUser(request);

		if (null == cookieUser) {
			try {
				onAuthFail(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 客户端传入的用户身份
		String userName = cookieUser.getUsername();
		// 客户端请求的授权码
		String securityToken = cookieUser.getSecurityToken();
		// 将用户信息放入缓存中， key为 以securityToken加密的username
		String key = SHA1Encrypt.encryptPassword(userName, securityToken);
		// 验证缓存中无数据，需要重新获取缓存信息
		UserCache userCache = CacheUtils.get(CacheEmun.USER_CACHE, key, UserCache.class);

		// 如果缓存数据为空，需要重新放入缓存
		if (null == userCache || StringUtils.isNull(userCache.getUserId())) {
			userCache = InstanceFactory.getInstance(SysService.class).getUserInfo(userName, securityToken);
			// EhCacheUtils.put(EhCacheEmun.USER_CACHE, key, userCache);
			CacheUtils.put(CacheEmun.USER_CACHE, key, userCache);
		}

		if (null == userCache) {
			try {
				onAuthFail(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return userCache;
	}

	/**
	 * 
	 * @Description 清除该用户缓存
	 * @param request
	 * @param response
	 * @author sen
	 * @Date 2017年2月17日 上午11:44:37
	 */
	public static void removeThis(HttpServletRequest request, HttpServletResponse response) {
		CookieUser cookieUser = CookieUtil.getCookieUser(request);
		if (null != cookieUser) {
			// 客户端传入的用户身份
			String userName = cookieUser.getUsername();
			// 客户端请求的授权码
			String securityToken = cookieUser.getSecurityToken();
			// 将用户信息放入缓存中， key为 以securityToken加密的username
			String key = SHA1Encrypt.encryptPassword(userName, securityToken);

			CacheUtils.delete(CacheEmun.USER_CACHE, key);
		}

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
	private static void onAuthFail(ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		response.setContentType("application/json;charset=UTF-8");
		JsonResult ret = new JsonResult("身份认证错误", ProcessStatus.AUTH_ERROR);
		ret.setResponseStatus(ResponseStatus.HTTP_UNAUTHORIZED);
		httpResponse.getWriter().write(ret.toJsonString());
		httpResponse.getWriter().flush();
		httpResponse.getWriter().close();
	}
}
