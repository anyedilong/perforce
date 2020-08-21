package org.sdblt.modules.proxy.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.proxy.domain.Proxy;
import org.sdblt.modules.proxy.dto.ProxyDto;
import org.sdblt.modules.proxy.service.ProxyService;
import org.sdblt.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import oracle.net.aso.p;

@Controller
@RequestMapping("${restPath}/proxy")
public class ProxyController extends BaseController {
	// 代理商service
	@Inject
	private ProxyService proxyService;

	/**
	 * 
	 * @Description 跳转设备列表页面
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月22日 上午9:35:13
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/proxy/proxyList");
	}

	/**
	 * 
	 * @Description 跳转到设备编辑页面
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 上午11:39:48
	 */
	@RequestMapping("forwordEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/proxy/proxyEdit");
	}

	@RequestMapping("forwordShow")
	public ModelAndView forwordShow() {
		return forword("/modules/proxy/proxyShow");
	}

	/**
	 * 
	 * @Description 获取代理商列表
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 上午10:12:16
	 */
	@RequestMapping("getList")
	@ResponseBody
	public JsonResult getList() {
		String param = getParam(request);
		ProxyDto proxyParam = JSON.parseObject(param, ProxyDto.class);
		if (proxyParam == null) {
			proxyParam = new ProxyDto();
		}
		PageModel page = proxyParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		proxyService.queryProxyList(proxyParam, page);
		return jsonResult(page);
	}

	/**
	 * 
	 * @Description 保存代理商信息
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 上午11:26:56
	 */
	@RequestMapping("save")
	@ResponseBody
	public JsonResult save() {
		String param = getParam(request);
		Proxy proxyParam = JSON.parseObject(param, Proxy.class);
		if (proxyParam != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();
			proxyParam.setCreateUser(user.getUserId());
			proxyParam.setCreateTime(nowDate);
			proxyParam.setUpdateUser(user.getUserId());
			proxyParam.setUpdateTime(nowDate);
			proxyParam.setStatus("1");
			proxyService.saveProxy(proxyParam);
			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 查看代理商详情
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 上午11:37:20
	 */
	@RequestMapping("show")
	@ResponseBody
	public JsonResult show() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);
		if (paramObj != null) {
			String id = paramObj.getString("id");
			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "用户Id必填");
			}
			Proxy proxy = proxyService.get(id);
			return jsonResult(proxy);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 删除代理商信息
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 下午1:46:56
	 */
	@RequestMapping("delete")
	@ResponseBody
	public JsonResult delete() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);
		if (paramObj != null) {
			List ids = paramObj.getJSONArray("ids");
			proxyService.batchUpdateForDel(ids);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
		return jsonResult();
	}

	/**
	 * 获取代理商下拉列表
	 * @Description (TODO)
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月29日 下午4:15:12
	 */
	@RequestMapping("getProxyDropDownList")
	@ResponseBody
	public JsonResult getProxyDropDownList() {
		String param = getParam(request);
		ProxyDto proxyParam = JSON.parseObject(param, ProxyDto.class);
		if (proxyParam == null) {
			proxyParam = new ProxyDto();
		}
		List<ProxyDto> proxyDtos = proxyService.getProxyDropDownList(proxyParam);
		return jsonResult(proxyDtos);
	}
}
