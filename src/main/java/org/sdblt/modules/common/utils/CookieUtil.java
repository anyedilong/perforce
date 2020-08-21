package org.sdblt.modules.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.spring.InstanceFactory;
import org.sdblt.modules.common.dto.Message;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.service.SysService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.sdblt.utils.encode.HttpEncode;
import org.sdblt.utils.sha1.SHA1Encrypt;

import com.alibaba.fastjson.JSON;

public class CookieUtil {

	public static final String COOKIE_CACHE = "cookie_";

	public static final String COOKIE_TOKEN = "usertoken";
	public static final String COOKIE_USER_NAME = "userunick";

	/**
	 * <li>描述:从cookie中获取</li>
	 * <li>方法名称:getCustomer</li>
	 * <li>参数:@return</li>
	 * <li>返回类型:CustomerInfo</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public static Message validate(HttpServletRequest request) {
		Message message = null;
		try {
			Cookie[] cookies = request.getCookies();
			if (null != cookies) {
				for (Cookie cookie : cookies) {
					if (COOKIE_TOKEN.equals(cookie.getName())) {
						String encodeStr = cookie.getValue();
						String decodeStr = HttpEncode.getInstance().parseDecode(encodeStr);
						// 将数据转
						CookieUser cookieUser = JSON.parseObject(decodeStr, CookieUser.class);

						if (null != cookieUser) {
							// 根据登录名获取用户信息
							String username = cookieUser.getUsername();
							String securityToken = cookieUser.getSecurityToken();

							if (!StringUtils.isNull(username) && !StringUtils.isNull(securityToken)) {

								// 在缓存中获取用户信息 如果缓存中用户信息不存在，则数据库查询
								String key = COOKIE_CACHE + username;
								CookieUser cacheUser = CacheUtils.get(CacheEmun.USER_CACHE, key, CookieUser.class);

								// 数据库查询用户信息
								if (null == cacheUser) {
									SysUser user = InstanceFactory.getInstance(SysService.class)
											.getUserByUsername(username);
									if (null != user) {
										cacheUser = new CookieUser(user.getUsername(), user.getSecurityToken());
										cacheUser.setStatus(user.getStatus());
									}
								}

								if (null == cacheUser) {
									// 用户登陆失效，请重新登陆
									message = new Message("-1", "用户登陆失效，请重新登陆");
								} else {
									// 验证用户是否相同
									if (!cookieUser.equals(cacheUser)) {
										// 用户登陆失效，请重新登陆
										message = new Message("-1", "用户登陆失效，请重新登陆");
									}

									// 状态 1 正常 2 冻结 3 注销
									String status = cacheUser.getStatus();
									if ("1".equals(status)) {
										message = new Message("200", "SUCCESS");
										CacheUtils.put(CacheEmun.USER_CACHE, key, cacheUser);
									} else if ("2".equals(status)) {
										// 用户冻结,联系管理员解冻之后操作
										message = new Message("-1", "用户已冻结,联系管理员解冻之后操作");
									} else if ("3".equals(status)) {
										// TODO 用户注销
										message = new Message("-1", "用户已注销");
									}
								}
							}
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 
	 * @Description 获取缓存user信息
	 * @param request
	 * @return
	 * @author sen
	 * @Date 2017年2月17日 上午11:40:34
	 */
	public static CookieUser getCookieUser(HttpServletRequest request){
		try {
			Cookie[] cookies = request.getCookies();
			if (null != cookies) {
				for (Cookie cookie : cookies) {
					if (COOKIE_TOKEN.equals(cookie.getName())) {
						String encodeStr = cookie.getValue();
						String decodeStr = HttpEncode.getInstance().parseDecode(encodeStr);
						// 将数据转
						CookieUser cookieUser = JSON.parseObject(decodeStr, CookieUser.class);
						return cookieUser;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <li>描述：将登录信息放入cookie</li>
	 * <li>方法名称:setCookie</li>
	 * <li>参数:@param response</li>
	 * <li>返回类型:void</li>
	 * <li>最后更新作者:gaoqs</li>
	 * 
	 * @param cookieUser
	 * @param autoLogin 
	 */
	public static void setCookie(HttpServletResponse response, CookieUser cookieUser, String autoLogin) {

		// 加密数据
		String jsonStr = JSON.toJSONString(cookieUser);
		String encodeStr = HttpEncode.getInstance().createEncode(jsonStr);

		// 登录成功 将数据放入cookie
		Cookie cookie = new Cookie(COOKIE_TOKEN, encodeStr);
		// 设置Cookie时间 一个月
		// 判断是否自动登录
		if ("on".equals(autoLogin)) {
			cookie.setMaxAge(60 * 60 * 24 * 30);
		}else{
			cookie.setMaxAge(-1);
		}
		cookie.setPath("/");

		response.addCookie(cookie);

		// 将登录名放入cookie
		Cookie cookieUsername = new Cookie(COOKIE_USER_NAME, cookieUser.getUsername());
		// 设置Cookie时间 一个月
		cookieUsername.setMaxAge(60 * 60 * 24 * 365);
		cookieUsername.setPath("/");

		response.addCookie(cookieUsername);
	}

	public static void remove(HttpServletResponse response) {
		// 清除cookie
		Cookie cookie = new Cookie(COOKIE_TOKEN, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");

		response.addCookie(cookie);
	}

}
