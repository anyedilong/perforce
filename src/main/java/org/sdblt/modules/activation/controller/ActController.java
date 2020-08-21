package org.sdblt.modules.activation.controller;

import java.util.List;

import javax.inject.Inject;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.message.JsonResult;
import org.sdblt.modules.activation.dto.ActDto;
import org.sdblt.modules.activation.service.ActService;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.product.domain.ProductVersion;
import org.sdblt.modules.system.domain.FileDomain;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("${tablePath}/act")
public class ActController extends BaseController {

	@Inject
	private ActService actService;

	/**
	 * 获取激活码
	 * 
	 * @Description (TODO)
	 * @param param
	 * @return
	 * @author wangpenghui
	 * @Date 2017年4月12日 上午8:34:43
	 */
	@RequestMapping("actcode")
	@ResponseBody
	public JsonResult queryactcode(String param) {

		JSONObject paramobj = JSONObject.parseObject(param);
		if (paramobj != null) {
			String proNum = paramobj.getString("proNum");
			if (StringUtils.isNull(proNum)) {
				return jsonResult(null, 1003, "产品编号不能为空");
			}
			String uuid = CacheUtils.get(CacheEmun.COMM_CACHE, proNum, String.class);
			if (!StringUtils.isNull(uuid)) {
				return jsonResult(uuid);
			} else {
				uuid = UUIDUtil.getUUID();
				CacheUtils.put(CacheEmun.COMM_CACHE, proNum, uuid, 300);
				return jsonResult(uuid);
			}

		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * 改变激活状态
	 * 
	 * @Description (TODO)
	 * @param param
	 * @return
	 * @author wangpenghui
	 * @Date 2017年4月7日 上午9:11:29
	 */
	@RequestMapping("activa")
	@ResponseBody
	public JsonResult updateact(String param) {

		try {
			JSONObject paramobj = JSONObject.parseObject(param);
			if (paramobj != null) {
				String proNum = paramobj.getString("proNum");
				String machineCode = paramobj.getString("machineCode");
				String code = paramobj.getString("code");
				if (StringUtils.isNull(proNum) || StringUtils.isNull(machineCode) || StringUtils.isNull(code)) {
					return jsonResult(null, 1002, "产品编号激活码和机器码不能为空");
				}

				return jsonResult(actService.updateact(proNum, machineCode, code));
			} else {
				return jsonResult(null, 10001, "参数错误");
			}

		} catch (CommonException e) {
			e.printStackTrace();
			return jsonResult(null, e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 90001, e.getMessage());
		}

	}

	/**
	 * 获取版本列表
	 * 
	 * @Description (TODO)
	 * @param param
	 * @return
	 * @author wangpenghui
	 * @Date 2017年4月7日 上午9:11:23
	 */
	@RequestMapping("downversion")
	@ResponseBody
	public JsonResult downversion(String param) {
		try {

			JSONObject paramobj = JSONObject.parseObject(param);
			if (paramobj != null) {
				String proNum = paramobj.getString("proNum");
				String machineCode = paramobj.getString("machineCode");
				if (StringUtils.isNull(machineCode) || StringUtils.isNull(proNum)) {
					return jsonResult(null, 1003, "产品编号机器码和版本编号不能为空");
				}
				List<ProductVersion> list = actService.queryversion(proNum, machineCode);
				return jsonResult(list);
			} else {
				return jsonResult(null, 10001, "参数错误");
			}
		} catch (CommonException e) {
			e.printStackTrace();
			return jsonResult(null, e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 90001, e.getMessage());
		}
	}

	/**
	 * 下载版本文件
	 * 
	 * @Description (TODO)
	 * @param param
	 * @return
	 * @author wangpenghui
	 * @Date 2017年4月7日 上午9:11:19
	 */
	@RequestMapping("downact")
	@ResponseBody
	public JsonResult downact(String param) {
		try {

			JSONObject paramobj = JSONObject.parseObject(param);
			if (paramobj != null) {
				String versionId = paramobj.getString("versionId");
				if (StringUtils.isNull(versionId)) {
					return jsonResult(null, 1003, "版本ID不能为空");
				}
				List<FileDomain> fileUpload = actService.queryfile(versionId);
				return jsonResult(fileUpload);
			} else {
				return jsonResult(null, 10001, "参数错误");
			}
		} catch (CommonException e) {
			e.printStackTrace();
			return jsonResult(null, e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 90001, e.getMessage());
		}
	}

	/**
	 * 修改库存版本
	 * 
	 * @Description (TODO)
	 * @param param
	 * @return
	 * @author wangpenghui
	 * @Date 2017年4月7日 上午9:16:04
	 */
	@RequestMapping("updateversion")
	@ResponseBody
	public JsonResult updateversion(String param) {
		try {

			JSONObject paramObj = JSONObject.parseObject(param);
			if (paramObj != null) {
				String proNum = paramObj.getString("proNum");
				String versionNum = paramObj.getString("versionNum");
				if (StringUtils.isNull(versionNum) || StringUtils.isNull(proNum)) {
					return jsonResult(null, 1004, "版本号和产品编号不能为空");
				}
				actService.updateversion(versionNum, proNum);
				return jsonResult();
			} else {
				return jsonResult(null, 10001, "参数错误");
			}
		} catch (CommonException e) {
			e.printStackTrace();
			return jsonResult(null, e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 90001, e.getMessage());
		}
	}
}
