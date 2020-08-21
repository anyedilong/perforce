package org.sdblt.modules.system.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.Device;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.dto.DeviceListDto;
import org.sdblt.modules.system.service.DeviceService;
import org.sdblt.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("${restPath}/device")
public class DeviceController extends BaseController {

	// 设备service
	@Inject
	private DeviceService deviceService;

	/**
	 * 
	 * @Description 跳转设备列表页面
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月22日 上午9:35:13
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/system/device/deviceList");
	}

	/**
	 * 
	 * @Description 跳转设备详情页面
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月23日 上午9:29:54
	 */
	@RequestMapping("forwordShow")
	public ModelAndView forwordShow() {
		return forword("/modules/system/device/deviceShow");
	}

	/**
	 * 
	 * @Description 跳转设备编辑页面
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月23日 上午9:30:11
	 */
	@RequestMapping("forwordEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/system/device/deviceEdit");
	}

	/**
	 * 
	 * @Description 保存设备信息
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月22日 下午5:24:09
	 */
	@RequestMapping("save")
	@ResponseBody
	public JsonResult save() {
		String param = getParam(request);
		Device deviceParam = JSON.parseObject(param, Device.class);
		if (deviceParam != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();
			deviceParam.setCreateUser(user.getUserId());
			deviceParam.setCreateTime(nowDate);
			deviceParam.setUpdateUser(user.getUserId());
			deviceParam.setUpdateTime(nowDate);
			deviceParam.setStatus("1");
			deviceService.saveDevice(deviceParam);
			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 查询设备列表
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月22日 上午9:46:06
	 */
	@RequestMapping("getList")
	@ResponseBody
	public JsonResult getList() {
		String param = getParam(request);
		DeviceListDto deviceParam = JSON.parseObject(param, DeviceListDto.class);
		if (deviceParam == null) {
			deviceParam = new DeviceListDto();
		}
		PageModel page = deviceParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		deviceService.queryDeviceList(deviceParam, page);
		return jsonResult(page);
	}

	/**
	 * 
	 * @Description 根据设备类型 获取设备信息
	 * @return
	 * @author sen
	 * @Date 2017年3月23日 下午2:06:57
	 */
	@RequestMapping("getListByType")
	@ResponseBody
	public JsonResult getListByType() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);
		if (paramObj != null) {
			String deviceType = paramObj.getString("deviceType");
			if (StringUtils.isNull(deviceType)) {
				return jsonResult(null, 10002, "设备类型必填");
			}

			List<DeviceListDto> deviceList = deviceService.getListByType(deviceType);
			return jsonResult(deviceList);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 查看详情
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月22日 下午3:00:26
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
			Device device = deviceService.get(id);
			return jsonResult(device);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 删除设备信息(非物理删除)
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月23日 上午9:30:31
	 */
	@RequestMapping("delete")
	@ResponseBody
	public JsonResult delete() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);
		if (paramObj != null) {
			List ids = paramObj.getJSONArray("ids");
			deviceService.batchUpdateForDel(ids);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
		return jsonResult();
	}
}
