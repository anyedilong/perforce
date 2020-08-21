package org.sdblt.modules.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.OrgCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.SysOrg;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.dto.OrgListDto;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.modules.system.service.OrgService;
import org.sdblt.utils.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <br>
 * <b>功能：</b>SysOrgController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@RestController
@RequestMapping("${restPath}/org")
public class OrgController extends BaseController {

	@Inject
	private OrgService orgService;

	/**
	 * @Description 跳转界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午9:54:34
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/system/org/orgList");
	}

	/**
	 * @Description 跳转编辑界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:13:41
	 */
	@RequestMapping("forwordOrgEdit")
	public ModelAndView forwordOrgEdit() {
		return forword("/modules/system/org/orgEdit");
	}

	@RequestMapping("forwordDeptEdit")
	public ModelAndView forwordDeptEdit() {
		return forword("/modules/system/org/deptEdit");
	}

	@RequestMapping("forwordJobEdit")
	public ModelAndView forwordJobEdit() {
		return forword("/modules/system/org/jobEdit");
	}

	// 保存数据
	@RequestMapping("saveOrg")
	@ResponseBody
	public JsonResult saveOrg() {
		String param = getParam(request);

		SysOrg orgParam = JSON.parseObject(param, SysOrg.class);

		if (orgParam != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();
			// 类型 1机构 2 部门 3 岗位
			orgParam.setOrgType("1");

			orgParam.setCreateUser(user.getUserId());
			orgParam.setCreateTime(nowDate);
			orgParam.setUpdateUser(user.getUserId());
			orgParam.setUpdateTime(nowDate);

			Map msgMap = orgService.saveOrg(orgParam);

			return jsonResult(null, StringUtils.toInteger(msgMap.get("code")), StringUtils.toString(msgMap.get("msg")));

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	@RequestMapping("saveDept")
	@ResponseBody
	public JsonResult saveDept() {
		String param = getParam(request);

		SysOrg orgParam = JSON.parseObject(param, SysOrg.class);

		if (orgParam != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();
			// 类型 1机构 2 部门 3 岗位
			orgParam.setOrgType("2");

			orgParam.setCreateUser(user.getUserId());
			orgParam.setCreateTime(nowDate);
			orgParam.setUpdateUser(user.getUserId());
			orgParam.setUpdateTime(nowDate);

			Map msgMap = orgService.saveOrg(orgParam);

			return jsonResult(null, StringUtils.toInteger(msgMap.get("code")), StringUtils.toString(msgMap.get("msg")));

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	@RequestMapping("saveJob")
	@ResponseBody
	public JsonResult saveJob() {
		String param = getParam(request);

		SysOrg orgParam = JSON.parseObject(param, SysOrg.class);

		if (orgParam != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();
			// 类型 1机构 2 部门 3 岗位
			orgParam.setOrgType("3");

			orgParam.setCreateUser(user.getUserId());
			orgParam.setCreateTime(nowDate);
			orgParam.setUpdateUser(user.getUserId());
			orgParam.setUpdateTime(nowDate);

			Map msgMap = orgService.saveOrg(orgParam);

			return jsonResult(null, StringUtils.toInteger(msgMap.get("code")), StringUtils.toString(msgMap.get("msg")));

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
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

		OrgListDto orgParam = JSON.parseObject(param, OrgListDto.class);

		if (orgParam == null) {
			orgParam = new OrgListDto();
		}
		PageModel page = orgParam.getPage();
		if (page == null) {
			page = new PageModel();
		}

		if (StringUtils.isNull(orgParam.getOrgId())) {
			orgParam.setOrgId("0");
		}

		orgService.queryOrgList(orgParam, page);

		return jsonResult(page);
	}

	/**
	 * @Description 获取机构树
	 * @return
	 * @author sen
	 * @Date 2017年3月3日 下午2:16:52
	 */
	@RequestMapping("getOrgTree")
	@ResponseBody
	public JsonResult getOrgTree() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		// 1只统计 组织机构 2 统计到部门 默认统计到岗位
		String type = paramObj.getString("type");//
		UserCache user = SysUtil.sysUser(request, response);
		List<SysOrg> orgList = orgService.getOrgTree(type, user.getOrgId());
		return jsonResult(orgList);
	}

	@RequestMapping("getUserOrgTree")
	@ResponseBody
	public JsonResult getUserOrgTree() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		// 1只统计 组织机构 2 统计到部门 默认统计到岗位
		String type = paramObj.getString("type");//

		UserCache user = SysUtil.sysUser(request, response);
		List<OrgCache> orgTree = user.getOrgTree();
		if(orgTree == null ){
			return jsonResult(null);
		}
		List<OrgCache> orgTree1 = null;
		if ("1".equals(type)) {
			orgTree1 = orgTree.stream().filter(org -> "1".equals(org.getOrgType())).collect(Collectors.toList());
		} else if ("2".equals(type)) {
			orgTree1 = orgTree.stream().filter(org -> "1".equals(org.getOrgType()) || "2".equals(org.getOrgType()))
					.collect(Collectors.toList());
		} else {
			orgTree1 = orgTree;
		}

		// List<SysOrg> orgList = orgService.getOrgTree(type);
		return jsonResult(orgTree1);
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

			orgService.batchUpdateForDel(ids);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

		return jsonResult();
	}

	// 查看数据
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
				return jsonResult(null, 10002, "机构ID必填");
			}

			SysOrg org = orgService.get(id);

			return jsonResult(org);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	public JsonResult demo(String param) {

		return jsonResult();
	}

}
