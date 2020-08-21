package org.sdblt.modules.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.Menu;
import org.sdblt.modules.system.domain.SysOrg;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.dto.RoleDto;
import org.sdblt.modules.system.dto.UserDto;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.modules.system.service.UserService;
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
 * <b>功能：</b>MenuController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Controller
@RequestMapping("${restPath}/user")
public class UserController extends BaseController {

	@Inject
	private UserService userService;

	/**
	 * @Description 跳转界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午9:54:34
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/system/user/userList");
	}

	@RequestMapping("forwordListShow")
	public ModelAndView forwordListShow() {
		return forword("/modules/system/usershow/userListShow");
	}

	@RequestMapping("forwordShow")
	public ModelAndView forwordShow() {
		return forword("/modules/system/usershow/userShow");
	}
	/*@RequestMapping("forwordMain")
	public ModelAndView forwordMain() {
		return forword("/main");
	}*/
	/**
	 * @Description 列表数据
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:14:59
	 */
	@RequestMapping("getListShow")
	@ResponseBody
	public JsonResult getListShow() {
		String param = getParam(request);

		UserListDto userParam = JSON.parseObject(param, UserListDto.class);

		if (userParam == null) {
			userParam = new UserListDto();
		}
		PageModel page = userParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		userService.queryUserListShow(userParam, page);

		return jsonResult(page);
	}
	/**
	 * @Description 列表数据
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:14:59
	 */
	@RequestMapping("getUserDto")
	@ResponseBody
	public JsonResult getUserDto() {
		UserCache user = SysUtil.sysUser(request, response);
		List<RoleDto> list = userService.getUserDto(user.getUserId());
		UserDto userDto = new UserDto();
		userDto.setUserName(user.getUserName());
		StringBuffer sb = new StringBuffer();
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					sb.append("  " + list.get(i).getName());
				} else {
					sb.append("  " + list.get(i).getName() + "、");
				}
			}
		}
		
		userDto.setName(sb.toString());

		return jsonResult(userDto);
	}

	/**
	 * @Description 列表数据
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:14:59
	 */
	@RequestMapping("getUserInfo")
	@ResponseBody
	public JsonResult getUserInfo() {
		UserCache user = SysUtil.sysUser(request, response);
		List<RoleDto> list = userService.getUserDto(user.getUserId());
		return jsonResult(list.get(0));
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

		UserListDto userParam = JSON.parseObject(param, UserListDto.class);

		if (userParam == null) {
			userParam = new UserListDto();
		}
		PageModel page = userParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		if (StringUtils.isNull(userParam.getOrgId())) {
			userParam.setOrgId(SysUtil.sysUser(request, response).getOrgId());
		}
		userService.queryUserList(userParam, page);

		return jsonResult(page);
	}

	/**
	 * @Description 跳转编辑界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:13:41
	 */
	@RequestMapping("forwordEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/system/user/userEdit");
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

		SysUser userParam = JSON.parseObject(param, SysUser.class);

		if (userParam != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();

			userParam.setCreateUser(user.getUserId());
			userParam.setCreateTime(nowDate);
			userParam.setUpdateUser(user.getUserId());
			userParam.setUpdateTime(nowDate);

			Map msgMap = userService.saveUser(userParam);

			return jsonResult(null, StringUtils.toInteger(msgMap.get("code")), StringUtils.toString(msgMap.get("msg")));

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * @Description 验证用户名是否存在
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 下午4:55:31
	 */
	@RequestMapping("existUsername")
	@ResponseBody
	public JsonResult existUsername() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String username = paramObj.getString("username");//

			if (StringUtils.isNull(username)) {
				return jsonResult(null, 10002, "用户名必填");
			}

			boolean bl = userService.existUsername(username);

			return jsonResult(bl);
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

			SysUser user = userService.get(id);

			return jsonResult(user);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	// 跳转详情

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

			userService.batchUpdateForDel(ids);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

		return jsonResult();
	}

	/**
	 * @Description 得到系统用户下拉框
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月30日 上午8:40:29
	 */
	@RequestMapping("getSysUserDropDownList")
	@ResponseBody
	public JsonResult getSysUserDropDownList(){
		String param = getParam(request);
		SysUser sysUserParam = JSON.parseObject(param,SysUser.class);
		if(sysUserParam != null){
			sysUserParam = new SysUser();
		}
		List<SysUser> sysUsers = userService.getSysUserDropDownList(sysUserParam);
		return jsonResult(sysUsers);
	}
}
