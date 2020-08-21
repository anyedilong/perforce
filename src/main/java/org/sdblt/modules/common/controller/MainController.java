package org.sdblt.modules.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.spring.InstanceFactory;
import org.sdblt.modules.common.dto.Message;
import org.sdblt.modules.common.utils.CookieUser;
import org.sdblt.modules.common.utils.CookieUtil;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.service.SysService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.sdblt.utils.sha1.SHA1Encrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * 
 * <br>
 * <b>功能：</b>CustomerController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Controller
public class MainController extends BaseController {

	@Inject
	private SysService sysService;

	@RequestMapping("/r/ttt")
	@ResponseBody
	public JsonResult ttt() {
		return jsonResult();
	}

	/**
	 * 
	 * <li>描述:跳转主界面</li>
	 * <li>方法名称:index</li>
	 * <li>参数:@param response
	 * <li>参数:@param request
	 * <li>参数:@return</li>
	 * <li>返回类型:String</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	@RequestMapping("main")
	public ModelAndView main() {
		return forword("main", null);
	}

	/**
	 * 
	 * <li>描述:跳转登录界面</li>
	 * <li>方法名称:index</li>
	 * <li>参数:@param response
	 * <li>参数:@param request
	 * <li>参数:@return</li>
	 * <li>返回类型:String</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	@RequestMapping("index")
	public ModelAndView index() {
		String redirectURL = request.getParameter("redirectURL");
		// 验证cookie 中token 登录
		Message message = CookieUtil.validate(request);// 0 失败 1 成功

		if (null == message) {
			return forword("login", null);
		} else {
			String code = message.getCode();
			String msg = message.getMessage();
			if ("200".equals(code)) {
				return redirect("main", null);// 登录成功，跳转到登录成功界面
			} else {
				Map<String, Object> context = new HashMap<>();
				context.put("errorMsg", msg);
				context.put("redirectURL", redirectURL);
				return forword("login", context);
			}
		}

	}

	/**
	 * 
	 * @Description 跳转登陆界面
	 * @return
	 * @author sen
	 * @Date 2017年2月15日 下午4:43:55
	 */
	@RequestMapping("login")
	public ModelAndView login() {
		// String redirectURL = request.getParameter("redirectURL");
		String errorMsg = request.getParameter("errorMsg");

		Map<String, Object> context = new HashMap<>();
		context.put("errorMsg", errorMsg);
		// context.put("redirectURL", redirectURL);
		return forword("login", context);
	}

	@RequestMapping("loginModal")
	public String loginModal() {
		return "/modal/loginModal";
	}

	/**
	 * 
	 * <li>描述:用户登录</li>
	 * <li>方法名称:check</li>
	 * <li>参数:@param response
	 * <li>参数:@param request
	 * <li>参数:@return</li>
	 * <li>返回类型:String</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	@RequestMapping("login/check")
	@ResponseBody
	public JsonResult check(HttpServletResponse response, HttpServletRequest request) {
		// 获取登录名
		String username = StringUtils.toString(request.getParameter("username"));
		// 密码
		String password = StringUtils.toString(request.getParameter("password"));
		String autoLogin = StringUtils.toString(request.getParameter("autologin"));

		Message message = new Message();
		// 根据登录名 获取用户信息
		SysUser user = sysService.getUserByUsername(username);
		if (null != user) {
			String pwd = user.getPassword();
			String salt = user.getSalt();
			password = SHA1Encrypt.encryptPassword(password, salt);
			if (password.equals(pwd)) {
				CookieUser cookieUser = new CookieUser(username, user.getSecurityToken());
				String key = SHA1Encrypt.encryptPassword(username, user.getSecurityToken());

				// 存入cookie
				CookieUtil.setCookie(response, cookieUser, autoLogin);
				// 用户验证信息 放入缓存
				String cookieKey = CookieUtil.COOKIE_CACHE + username;
				cookieUser.setStatus(user.getStatus());// 状态 1 正常 2 冻结 3 注销
				CacheUtils.put(CacheEmun.USER_CACHE, cookieKey, cookieUser);
				// 获取用户信息，放入缓存
				UserCache userCache = sysService.getUserInfo(username, user.getSecurityToken());
				CacheUtils.put(CacheEmun.USER_CACHE, key, userCache);
				return jsonResult();
			} else {
				message.setCode("-1");
				message.setMessage("密码错误");
			}
		} else {
			message.setCode("-1");
			message.setMessage("用户不存在");
		}
		return jsonResult(null, -1, message.getMessage());
	}

	/**
	 * 
	 * <li>描述:退出登录界面</li>
	 * <li>方法名称:index</li>
	 * <li>参数:@param response
	 * <li>参数:@param request
	 * <li>参数:@return</li>
	 * <li>返回类型:String</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	@RequestMapping("exit")
	public ModelAndView exit(HttpServletResponse response, HttpServletRequest request) {

		// 验证cookie 中token 登录
		CookieUtil.remove(response);// 0 失败 1 成功
		return redirect("index", null);// 登录成功，跳转到登录成功界面

	}

	/**
	 * @Description 根据code 获取字典信息
	 * @return
	 * @author sen
	 * @Date 2017年3月23日 上午8:45:22
	 */
	@RequestMapping("/getDict")
	@ResponseBody
	public JsonResult getDictListByCode() {

		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String dictProp = paramObj.getString("dictProp");// 产品分类ID

			if (StringUtils.isNull(dictProp)) {
				return jsonResult(null, 10002, "字典code必填");
			}

			List<DictCache> dictList = CacheManagerUtil.getDictListByCode(dictProp);

			return jsonResult(dictList);

		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

}
