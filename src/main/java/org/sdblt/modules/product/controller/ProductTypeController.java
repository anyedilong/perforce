package org.sdblt.modules.product.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.common.utils.cache.ProductTypeCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.product.domain.ProductType;
import org.sdblt.modules.product.dto.ProductTypeDto;
import org.sdblt.modules.product.service.ProductTypeService;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.service.SysService;
import org.sdblt.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName ProductTypeController
 * @Description 版本类型
 * @author sen
 * @Date 2017年3月21日 上午11:07:13
 * @version 1.0.0
 */
@Controller
@RequestMapping("${restPath}/productType")
public class ProductTypeController extends BaseController {

	@Inject
	private ProductTypeService productTypeService;

	@Inject
	private SysService sysService;

	/**
	 * @Description 跳转登记列表界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午9:54:34
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/product_mg/productType/productTypeList");
	}

	/**
	 * @Description 跳转编辑界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:13:41
	 */
	@RequestMapping("forwordEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/product_mg/productType/productTypeEdit");
	}

	/**
	 * @Description 登记列表数据
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:14:59
	 */
	@RequestMapping("getList")
	@ResponseBody
	public JsonResult getList() {
		String param = getParam(request);

		ProductTypeDto productTypeParam = JSON.parseObject(param, ProductTypeDto.class);

		if (productTypeParam == null) {
			productTypeParam = new ProductTypeDto();
		}
		PageModel page = productTypeParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		productTypeService.getList(productTypeParam, page);

		return jsonResult(page);
	}

	/**
	 * @Description 查询产品分类树
	 * @return
	 * @author sen
	 * @Date 2017年3月20日 下午3:38:52
	 */
	@RequestMapping("getTree")
	@ResponseBody
	public JsonResult getTree() {
		List<ProductTypeCache> typeList = sysService.initProductType();

		if (null != typeList) {
			typeList.sort(Comparator.comparing(ProductTypeCache::getOrderNum));
		}

		return jsonResult(typeList);
	}

	/**
	 * 
	 * @Description 查询产品分类详情
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

			String id = paramObj.getString("id");// 产品分类ID

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "产品分类ID必填");
			}

			ProductType productType = productTypeService.get(id);

			return jsonResult(productType);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * @Description 保存产品分类
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:34:23
	 */
	@RequestMapping("save")
	@ResponseBody
	public JsonResult save() {
		String param = getParam(request);
		ProductType productType = JSON.parseObject(param, ProductType.class);

		if (productType != null) {
			productTypeService.saveProType(productType);

			return jsonResult();
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

			productTypeService.batchUpdateForDel(ids);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

		return jsonResult();
	}

}
