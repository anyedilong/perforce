package org.sdblt.modules.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.Dict;
import org.sdblt.modules.system.domain.DictSub;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.dto.DictTree;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.modules.system.service.DictService;
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
 * <b>功能：</b>DictController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@RestController
@RequestMapping("${restPath}/dict")
public class DictController extends BaseController {

	@Inject
	private DictService service;

	/**
	 * @Description 跳转界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午9:54:34
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/system/dict/dictList");
	}

	/**
	 * @Description 获取字典树
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 上午9:13:13
	 */
	@RequestMapping("getDictTree")
	@ResponseBody
	public JsonResult getDictTree() {
		List<DictTree> list = service.findListTree();

		return jsonResult(list);
	}

	/**
	 * @Description 打开字典修改界面
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 上午9:22:49
	 */
	@RequestMapping("forwordEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/system/dict/dictEdit");
	}

	/**
	 * @Description 打开字典明细修改界面
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 上午9:22:41
	 */
	@RequestMapping("forwordSubEdit")
	public ModelAndView forwordSubEdit() {
		return forword("/modules/system/dict/dictSubEdit");
	}

	/**
	 * @Description 列表数据
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:14:59
	 */
	@RequestMapping("getSubList")
	@ResponseBody
	public JsonResult getSubList() {
		String param = getParam(request);

		DictSub dictSubParam = JSON.parseObject(param, DictSub.class);

		if (dictSubParam == null) {
			dictSubParam = new DictSub();
		}
		PageModel page = dictSubParam.getPage();
		if (page == null) {
			page = new PageModel();
		}

		service.getSubList(dictSubParam, page);

		return jsonResult(page);
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

		Dict dict = JSON.parseObject(param, Dict.class);

		if (dict != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();

			dict.setCreateUser(user.getUserId());
			dict.setCreateTime(nowDate);
			dict.setUpdateUser(user.getUserId());
			dict.setUpdateTime(nowDate);

			service.save(dict);

			return jsonResult(dict.getId());

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	@RequestMapping("saveSub")
	@ResponseBody
	public JsonResult saveSub() {
		String param = getParam(request);

		DictSub dictSub = JSON.parseObject(param, DictSub.class);

		if (dictSub != null) {
			service.saveSub(dictSub);
			return jsonResult();

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * @Description 查看字典信息
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 上午9:46:11
	 */
	@RequestMapping("show")
	@ResponseBody
	public JsonResult show() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String id = paramObj.getString("id");//

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "字典ID必填");
			}

			Dict dict = service.get(id);

			return jsonResult(dict);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * @Description 查看字典子项信息
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 上午9:46:20
	 */
	@RequestMapping("showSub")
	@ResponseBody
	public JsonResult showSub() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String id = paramObj.getString("id");//

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "字典明细ID必填");
			}

			DictSub dictSub = service.getSub(id);

			return jsonResult(dictSub);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * @Description 删除字典明细
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 上午9:46:20
	 */
	@RequestMapping("delete")
	@ResponseBody
	public JsonResult delete() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {
			String id = paramObj.getString("id");

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "字典ID必填");
			}

			service.updateForDel(id);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

		return jsonResult();

	}

	/**
	 * @Description 删除字典明细
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 上午9:46:20
	 */
	@RequestMapping("deleteSub")
	@ResponseBody
	public JsonResult deleteSub() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {
			List ids = paramObj.getJSONArray("ids");

			service.batchSubUpdateForDel(ids);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

		return jsonResult();

	}

	public JsonResult demo(String param) {

		return jsonResult();
	}

}
