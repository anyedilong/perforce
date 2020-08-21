package org.sdblt.modules.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.spring.InstanceFactory;
import org.sdblt.modules.common.dto.Message;
import org.sdblt.modules.common.utils.CookieUser;
import org.sdblt.modules.common.utils.CookieUtil;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.service.SysService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.sdblt.utils.cache.EhCacheEmun;
import org.sdblt.utils.cache.EhCacheUtils;
import org.sdblt.utils.sha1.SHA1Encrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.sf.ehcache.CacheManager;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Controller
@RequestMapping("cache")
public class CacheController extends BaseController {

	@Inject
	private SysService sysService;

	/**
	 * @Description 跳转缓存管理界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午9:54:34
	 */
	@RequestMapping("forwordSysCache")
	public ModelAndView forwordList() {
		return forword("/modules/system/cache/sysCache");
	}

	/**
	 * @Description 更新系统缓存
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午2:25:19
	 */
	@ResponseBody
	@RequestMapping("updateSysCache")
	public JsonResult updateSysCache() {
		// 初始化用户缓存
		CacheUtils.delete(CacheEmun.SYS_CACHE, CacheUtils.SYS_USER);
		sysService.initDictCache();
		// 初始化字典缓存
		CacheUtils.delete(CacheEmun.SYS_CACHE, CacheUtils.SYS_DICT);
		sysService.initDictCache();
		// 初始化产品类型
		CacheUtils.delete(CacheEmun.SYS_CACHE, CacheUtils.PRODUCT_TYPE);
		CacheUtils.delete(CacheEmun.SYS_CACHE, CacheUtils.SYS_AREA);
		CacheUtils.delete(CacheEmun.SYS_CACHE, CacheUtils.ACT_CODE);
		sysService.initProductType();

		return jsonResult();
	}

	/**
	 * @Description 更新用户缓存
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午2:25:19
	 */
	@ResponseBody
	@RequestMapping("updateUserCache")
	public JsonResult updateUserCache() {
		// 初始化用户缓存
		CacheUtils.delete(CacheEmun.SYS_CACHE, CacheUtils.SYS_USER);
		sysService.initDictCache();
		return jsonResult();
	}

	/**
	 * @Description 更新字典缓存
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午2:25:19
	 */
	@ResponseBody
	@RequestMapping("updateDictCache")
	public JsonResult updateDictCache() {
		// 初始化字典缓存
		CacheUtils.delete(CacheEmun.SYS_CACHE, CacheUtils.SYS_DICT);
		sysService.initDictCache();
		return jsonResult();
	}

	/**
	 * @Description 更新产品类型
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午2:25:19
	 */
	@ResponseBody
	@RequestMapping("updateProTypeCache")
	public JsonResult updateProTypeCache() {
		// 初始化产品类型
		CacheUtils.delete(CacheEmun.SYS_CACHE, CacheUtils.PRODUCT_TYPE);
		sysService.initProductType();
		return jsonResult();
	}

}
