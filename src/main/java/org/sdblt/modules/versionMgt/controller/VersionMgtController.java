package org.sdblt.modules.versionMgt.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.FileDomain;
import org.sdblt.modules.system.service.FileUploadService;
import org.sdblt.modules.versionMgt.domain.ProducTreeInfo;
import org.sdblt.modules.versionMgt.domain.VersionMgt;
import org.sdblt.modules.versionMgt.dto.VersionMgtDto;
import org.sdblt.modules.versionMgt.service.VersionMgtService;
import org.sdblt.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("${restPath}/vmm")
public class VersionMgtController extends BaseController {

	@Inject
	private VersionMgtService versionMgtService;

	@Inject
	private FileUploadService fileUploadService;

	/**
	 * 
	 * @Description (TODO)
	 * @return
	 * @author josen
	 * @Date 2017年3月25日 上午10:02:51
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/versionMgt/versionMgtList");
	}

	/**
	 * @Description 列表数据
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public JsonResult getList() {
		String param = getParam(request);

		VersionMgtDto versionMgtDto = JSON.parseObject(param, VersionMgtDto.class);
		PageModel page = versionMgtDto.getPage();

		versionMgtService.queryVersionMgtList(versionMgtDto, page);

		return jsonResult(page);
	}

	/**
	 * 
	 * @Description 打开编辑页面
	 * @return
	 * @author josen
	 * @Date 2017年3月22日 上午10:18:39
	 */
	@RequestMapping("forwordEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/versionMgt/versionMgtEdit");
	}

	/**
	 * 
	 * @Description 保存
	 * @return
	 * @author josen
	 * @Date 2017年3月24日 下午2:45:43
	 */

	@RequestMapping("save")
	@ResponseBody
	public JsonResult save() {
		String param = getParam(request);

		VersionMgt versionMgt = JSON.parseObject(param, VersionMgt.class);

		if (versionMgt != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();

			versionMgt.setReleaseUser(user.getUserId());
			versionMgt.setCreateUser(user.getUserId());
			versionMgt.setCreateTime(nowDate);
			versionMgt.setUpdateUser(user.getUserId());
			versionMgt.setUpdateTime(nowDate);
			versionMgt.setReleaseTime(nowDate);

			Map msgMap = versionMgtService.saveVersionMgt(versionMgt);

			return jsonResult(null, StringUtils.toInteger(msgMap.get("code")), StringUtils.toString(msgMap.get("msg")));

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description (TODO)
	 * @return
	 * @author josen
	 * @Date 2017年3月27日 上午8:43:28
	 */
	@RequestMapping("show")
	@ResponseBody
	public JsonResult show() {
		try {
			String param = getParam(request);
			JSONObject paramObj = JSON.parseObject(param);

			if (paramObj != null) {
				String id = paramObj.getString("id");// 产品分类ID

				if (StringUtils.isNull(id)) {
					return jsonResult(null, 10002, "版本id必填");
				}

				VersionMgt vm = versionMgtService.get(id);

				return jsonResult(vm);

			} else {
				return jsonResult(null, 10001, "参数错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 90001, e.getMessage());
		}
	}

	/**
	 * 
	 * @Description 删除
	 * @return
	 * @author joosen
	 * @Date 2017年3月28日 下午5:16:46
	 */
	@RequestMapping("delete")
	@ResponseBody
	public JsonResult delete() {
		try {

			String param = getParam(request);
			JSONObject paramObj = JSON.parseObject(param);

			if (paramObj != null) {

				String id = paramObj.getString("id");// 产品分类ID

				if (StringUtils.isNull(id)) {
					return jsonResult(null, 10002, "产品ID必填");
				}

				UserCache user = SysUtil.sysUser(request, response);
				Date nowDate = new Date();
				versionMgtService.updateForDelete(id, user.getUserId(), nowDate);
				return jsonResult();
			} else {
				return jsonResult(null, 10001, "参数错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 90001, e.getMessage());
		}
	}

	/**
	 * 
	 * @Description 产品树
	 * @return
	 * @author josen
	 * @Date 2017年3月28日 下午8:50:43
	 */
	@RequestMapping("getTree")
	@ResponseBody
	public JsonResult getTree() {
		try {
			List<ProducTreeInfo> prodList = versionMgtService.getProinfo();

			if (null != prodList) {
				prodList.sort(Comparator.comparing(ProducTreeInfo::getOrderNum));
			}
			// 根据产品类型添加产品
			//
			return jsonResult(prodList);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult(null, 90001, e.getMessage());
		}
	}

	/**
	 * 
	 * @Description 文件上传
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @author Administrator
	 * @Date 2017年3月31日 上午11:52:32
	 */

	@RequestMapping("upload")
	@ResponseBody
	public String upload2(HttpServletRequest request, HttpServletResponse response) {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		JSONObject jsonObj = new JSONObject();
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			List<MultipartFile> multipartFileArray = new ArrayList<>();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					multipartFileArray.add(file);
				}
			}
			String storagePath = "/versionMgt";
			List resultInfo = fileUploadService.uploadFileArray(multipartFileArray, storagePath);

			jsonObj.put("status", "success");
			jsonObj.put("fileInfo", resultInfo);

		} else {
			jsonObj.put("status", "error");
			jsonObj.put("fileInfo", "文件为空");
		}
		return jsonObj.toJSONString();
	}

	/**
	 * 
	 * @Description (TODO)
	 * @return
	 * @author josen
	 * @Date 2017年3月31日 下午5:01:38
	 */
	@RequestMapping("forwordShow")
	public ModelAndView forwordShow() {
		return forword("/modules/versionMgt/versionMgtShow");
	}

	/**
	 * 
	 * @Description 根据产品id获取前置版本信息（已经发布的最后一个版本）
	 * @return
	 * @author josen
	 * @Date 2017年4月1日 下午4:51:46
	 */
	@RequestMapping("getPrepVersInfo")
	@ResponseBody
	public JsonResult getPrepVersInfo() {

		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String proId = paramObj.getString("proId");

			VersionMgt vm = new VersionMgt();

			vm = versionMgtService.getPrepVersId(proId);

			return jsonResult(vm);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 根据附件id删除附件
	 * @return
	 * @author josen
	 * @Date 2017年4月6日 下午2:30:44
	 */
	@RequestMapping("delFileById")
	@ResponseBody
	public JsonResult delFileById() {

		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String fileId = paramObj.getString("fileId");
			String versionId = paramObj.getString("versionId");
			versionMgtService.delFileById(fileId, versionId);

			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

}
