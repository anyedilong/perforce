package org.sdblt.modules.system.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.MenuCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.Menu;
import org.sdblt.modules.system.dto.MenuListDto;
import org.sdblt.modules.system.service.MenuService;
import org.sdblt.utils.FileUtils;
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
@RequestMapping("${restPath}/menu")
public class MenuController extends BaseController {

	@Inject
	private MenuService menuService;

	/**
	 * 
	 * @Description 获取用户 在某菜单下的操作
	 * @return
	 * @author sen
	 * @Date 2017年2月27日 上午11:08:14
	 */
	@RequestMapping("getUserHandleByMenuId")
	@ResponseBody
	public JsonResult getUserHandleByMenuId() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String menuId = paramObj.getString("menuId");//

			if (StringUtils.isNull(menuId)) {
				return jsonResult(null, 10002, "菜单ID必填");
			}

			UserCache user = SysUtil.sysUser(request, response);
			Map<String, List<MenuCache>> handleMap = user.getHandleMap();

			return jsonResult(handleMap.get(menuId));

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 获取菜单树
	 * @return
	 * @author sen
	 * @Date 2017年2月20日 上午11:08:50
	 */
	@RequestMapping("getUserMenu")
	@ResponseBody
	public JsonResult getUserMenu() {

		UserCache user = SysUtil.sysUser(request, response);

		return jsonResult(user.getMenuNav());
	}

	/**
	 * 
	 * @Description 跳转到菜单列表界面
	 * @return
	 * @author sen
	 * @Date 2017年2月21日 上午10:33:01
	 */
	@RequestMapping("menuList")
	public ModelAndView menuList() {
		return forword("/modules/system/menu/menuList");
	}

	/**
	 * 
	 * @Description 获取用户tree
	 * @return
	 * @author sen
	 * @Date 2017年2月21日 上午10:34:53
	 */
	@RequestMapping("getUserTree")
	@ResponseBody
	public JsonResult getUserTree() {
		UserCache user = SysUtil.sysUser(request, response);

		return jsonResult(user.getMenuNav());
	}

	/**
	 * 
	 * @Description 获取菜单tree
	 * @return
	 * @author sen
	 * @Date 2017年2月21日 上午10:34:53
	 */
	@RequestMapping("getMenuTree")
	@ResponseBody
	public JsonResult getMenuTree() {
		List<Menu> menuList = menuService.getMenuTree();
		return jsonResult(menuList);
	}

	/**
	 * 
	 * @Description 获取机构列表
	 * @return
	 * @author sen
	 * @Date 2017年2月22日 上午10:35:10
	 */
	@RequestMapping("getMenuList")
	@ResponseBody
	public JsonResult getMenuList() {
		String param = getParam(request);
		Menu paramMenu = JSON.parseObject(param, Menu.class);
		if (paramMenu == null) {
			paramMenu = new Menu();
		}
		PageModel page = paramMenu.getPage();
		if (page == null) {
			page = new PageModel();
		}

		String parentId = paramMenu.getParentId();
		if (StringUtils.isNull(parentId)) {
			parentId = "0";
		}
		String menuName = StringUtils.toString(paramMenu.getName());

		menuService.queryMenuList(parentId, menuName, page);
		// menuService.findPage(page);

		return jsonResult(page);
	}

	/**
	 * @Description 跳转编辑菜单界面
	 * @return
	 * @author sen
	 * @Date 2017年2月23日 下午1:20:52
	 */
	@RequestMapping("editMenu")
	public ModelAndView editMenu() {
		return forword("/modules/system/menu/menuEdit");
	}

	/**
	 * @Description 跳转编辑操作界面
	 * @return
	 * @author sen
	 * @Date 2017年2月23日 下午1:21:00
	 */
	@RequestMapping("editHandle")
	public ModelAndView editHandle() {
		return forword("/modules/system/menu/handleEdit");
	}

	// 添加 菜单
	@RequestMapping("saveMenu")
	@ResponseBody
	public JsonResult saveMenu() {
		String param = getParam(request);
		Menu menu = JSON.parseObject(param, Menu.class);

		UserCache user = SysUtil.sysUser(request, response);
		Date nowDate = new Date();
		// 类型 1菜单 2 操作
		menu.setType("1");

		menu.setCreateUser(user.getUserId());
		menu.setCreateTime(nowDate);
		menu.setUpdateUser(user.getUserId());
		menu.setUpdateTime(nowDate);

		Map msgMap = menuService.saveMenuAndHandle(menu);

		return jsonResult(null, StringUtils.toInteger(msgMap.get("code")), StringUtils.toString(msgMap.get("msg")));
	}

	/**
	 * @Description 添加 操作
	 * @return
	 * @author sen
	 * @Date 2017年2月28日 上午9:47:14
	 */
	@RequestMapping("saveHandle")
	@ResponseBody
	public JsonResult saveHandle() {
		String param = getParam(request);
		Menu menu = JSON.parseObject(param, Menu.class);

		UserCache user = SysUtil.sysUser(request, response);
		Date nowDate = new Date();
		// 类型 1菜单 2 操作
		menu.setType("2");

		menu.setCreateUser(user.getUserId());
		menu.setCreateTime(nowDate);
		menu.setUpdateUser(user.getUserId());
		menu.setUpdateTime(nowDate);

		Map msgMap = menuService.saveMenuAndHandle(menu);

		return jsonResult(null, StringUtils.toInteger(msgMap.get("code")), StringUtils.toString(msgMap.get("msg")));
	}

	// 修改

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

			menuService.batchUpdateForDel(ids);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

		return jsonResult();
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
				return jsonResult(null, 10002, "菜单ID必填");
			}

			Menu menu = menuService.get(id);

			return jsonResult(menu);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * 
	 * @Description 跳转到 菜单图标界面
	 * @return
	 * @author sen
	 * @Date 2017年2月27日 下午3:20:03
	 */
	@RequestMapping("menuIcon")
	public ModelAndView menuIcon() {
		String path = request.getSession().getServletContext().getRealPath("");
		String iconPath = path + "static\\image\\menuIcon";

		List<String> fileNames = new ArrayList<>();

		File srcDir = new File(iconPath);
		if (srcDir.exists()) {
			// 列出源目录下的所有文件名和子目录名
			File[] files = srcDir.listFiles();

			for (int i = 0; i < files.length; i++) {
				// 如果是一个单个文件
				if (files[i].isFile()) {
					fileNames.add(files[i].getName());
				}
			}
		}

		Map<String, Object> context = new HashMap<>();
		context.put("iconList", fileNames);
		return forword("/modules/system/menu/menuIcon", context);
	}

	/**
	 * 
	 * @Description 跳转到 菜单图标界面
	 * @return
	 * @author sen
	 * @Date 2017年2月27日 下午3:20:03
	 */
	@RequestMapping("handleIcon")
	public ModelAndView handleIcon() {
		return forword("/modules/system/menu/handleIcon", null);
	}

	public JsonResult demo(String param) {

		return jsonResult();
	}

}
