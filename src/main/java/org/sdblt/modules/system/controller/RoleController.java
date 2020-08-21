package org.sdblt.modules.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.SysRole;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.dto.RoleListDto;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.modules.system.service.RoleService;
import org.sdblt.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <br>
 * <b>功能：</b>SysRoleController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Controller
@RequestMapping("${restPath}/role")
public class RoleController extends BaseController {

	@Inject
	private RoleService roleService;

	/**
	 * @Description 跳转界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午9:54:34
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/system/role/roleList");
	}

	/**
	 * @Description 列表数据
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:14:59
	 */
	@RequestMapping("getList")
	@ResponseBody
	public JsonResult getList() {

		String param = getParam(request);
		RoleListDto roleParam = JSON.parseObject(param, RoleListDto.class);
		roleParam.setOrgId(SysUtil.sysUser(request, response).getOrgId());

		if (roleParam == null) {
			roleParam = new RoleListDto();
		}
		PageModel page = roleParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		roleService.queryRoleList(roleParam, page);
		return jsonResult(page);
	}

	@RequestMapping("getAllList")
	@ResponseBody
	public JsonResult getAllList() {

		String param = getParam(request);
		RoleListDto roleParam = new RoleListDto();
		roleParam.setOrgId(SysUtil.sysUser(request, response).getOrgId());

		List<RoleListDto> list = roleService.getAllList(roleParam);
		return jsonResult(list);
	}

	/**
	 * @Description 跳转编辑界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:13:41
	 */
	@RequestMapping("forwordEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/system/role/roleEdit");
	}

	/**
	 * @Description 保存数据
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:34:23
	 */
	@RequestMapping("save")
	@ResponseBody
	public JsonResult save() {
		String param = getParam(request);
		SysRole roleParam = JSON.parseObject(param, SysRole.class);

		if (roleParam != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();
			roleParam.setOrgId(user.getOrgId());
			roleParam.setCreateUser(user.getUserId());
			roleParam.setCreateTime(nowDate);
			roleParam.setUpdateUser(user.getUserId());
			roleParam.setUpdateTime(nowDate);
			roleParam.setStatus("1");
			roleService.save(roleParam);
			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 查看
	 * @return
	 * @author sen
	 * @Date 2017年2月24日 下午4:56:01
	 */
	@RequestMapping("show")
	@ResponseBody
	public JsonResult show() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String id = paramObj.getString("id");//

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "用户ID必填");
			}

			SysRole role = roleService.get(id);
			return jsonResult(role);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * @Description 删除
	 * @return
	 * @author sen
	 * @Date 2017年3月1日 下午3:45:42
	 */
	@RequestMapping("delete")
	@ResponseBody
	public JsonResult delete() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {
			List ids = paramObj.getJSONArray("ids");

			roleService.batchUpdateForDel(ids);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

		return jsonResult();
	}

	/**
	 * @Description 跳转角色授权
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:13:41
	 */
	@RequestMapping("forwordAuthorize")
	public ModelAndView forwordAuthorize() {
		String id = request.getParameter("id");
		SysRole role = roleService.get(id);
		Map<String, Object> map = new HashMap<>();
		map.put("roleName", role != null ? role.getName() : "");
		return forword("/modules/system/role/authorizeEdit", map);
	}

	/**
	 * @Description 角色授权
	 * @return
	 * @author sen
	 * @Date 2017年3月7日 下午2:57:05
	 */
	@RequestMapping("authorize")
	@ResponseBody
	public JsonResult authorize() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {
			// 授权功能
			List menuIds = paramObj.getJSONArray("menuIds");
			// 角色ID
			String roleId = paramObj.getString("id");

			roleService.authorize(roleId, menuIds);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

		return jsonResult();
	}

	/**
	 * @Description 查询角色授权信息
	 * @return
	 * @author sen
	 * @Date 2017年3月7日 下午2:57:40
	 */
	@RequestMapping("showAuthorize")
	@ResponseBody
	public JsonResult showAuthorize() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {
			// 角色ID
			String roleId = paramObj.getString("id");

			List<String> roleMenuList = roleService.showAuthorize(roleId);
			return jsonResult(roleMenuList);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	public JsonResult demo(String param) {

		return jsonResult();
	}

}
