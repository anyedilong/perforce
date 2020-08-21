package org.sdblt.modules.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.common.spring.InstanceFactory;
import org.sdblt.modules.common.dto.Message;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.common.utils.CookieUser;
import org.sdblt.modules.common.utils.CookieUtil;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.AreaCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.modules.system.service.SysService;
import org.sdblt.modules.system.service.UserService;
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
@RequestMapping("")
public class SysController extends BaseController {

	@Inject
	private SysService sysService;

	/**
	 * @Description 列表数据
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:14:59
	 */
	@RequestMapping("getAreaList")
	@ResponseBody
	public JsonResult getAreaList() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String parenCode = paramObj.getString("code");//
			int level = paramObj.getIntValue("level");

			if (level != 1 && StringUtils.isNull(parenCode)) {
				return jsonResult();
			}

			if (level == 1) {
				parenCode = "";
			}

			List<AreaCache> areaList = null;
			if (level <= 3) {
				// 获取 缓存中行政区划 省市县
				areaList = CacheManagerUtil.getAreaList(parenCode, level);
			} else {
				// 获取 数据库行政区划 乡镇
				areaList = sysService.getAreaByParentCode(parenCode, level);
			}

			return jsonResult(areaList);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

}
