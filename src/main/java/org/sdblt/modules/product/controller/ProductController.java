package org.sdblt.modules.product.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.product.domain.Product;
import org.sdblt.modules.product.dto.ProductListDto;
import org.sdblt.modules.product.service.ProductService;
import org.sdblt.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("${restPath}/product")
public class ProductController extends BaseController {

	// 产品service
	@Inject
	private ProductService productService;

	/**
	 * @Description 跳转到详情界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午9:54:34
	 */
	@RequestMapping("forwordShow")
	public ModelAndView forwordShow() {
		return forword("/modules/product_mg/product/productShow");
	}

	/**
	 * @Description 跳转登记列表界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午9:54:34
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/product_mg/product/productList");
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

		ProductListDto productParam = JSON.parseObject(param, ProductListDto.class);

		if (productParam == null) {
			productParam = new ProductListDto();
		}
		PageModel page = productParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		productService.getList(productParam, page);

		return jsonResult(page);
	}
	
	/**
	 * 
	 * @Description 根据产品分类，获取产品集合
	 * @return
	 * @authorliuxingx
	 * @Date 2017年3月31日 下午3:51:05
	 */
	@RequestMapping("getProductSlt")
	@ResponseBody
	public JsonResult getProductSlt() {
		String param = getParam(request);
		
		ProductListDto productParam = JSON.parseObject(param, ProductListDto.class);
		
		if (productParam != null) {
			String proType = productParam.getProType();
			if (StringUtils.isNull(proType)) {
				return jsonResult(null, 10002, "产品类型必填");
			}
			
			//根据产品类型获取产品列表
			List<ProductListDto> productList =productService.getProductListByType(proType);
			
			return jsonResult(productList);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
		
	}

	/**
	 * @Description 跳转编辑界面
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 上午10:13:41
	 */
	@RequestMapping("forwordEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/product_mg/product/productEdit");
	}

	/**
	 * @Description 保存
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午3:10:12
	 */
	@RequestMapping("save")
	@ResponseBody
	public JsonResult save() {
		String param = getParam(request);
		Product product = JSON.parseObject(param, Product.class);

		if (product != null) {
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();

			product.setCreateUser(user.getUserId());
			product.setCreateTime(nowDate);
			product.setUpdateUser(user.getUserId());
			product.setUpdateTime(nowDate);

			productService.savePruduct(product);

			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * @Description 查询详情
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午3:14:49
	 */
	@RequestMapping("show")
	@ResponseBody
	public JsonResult show() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String id = paramObj.getString("id");// 产品分类ID

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "产品ID必填");
			}

			Product product = productService.get(id);

			return jsonResult(product);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * @Description 产品发布
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午4:53:05
	 */
	@RequestMapping("release")
	@ResponseBody
	public JsonResult release() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String id = paramObj.getString("id");// 产品分类ID

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "产品ID必填");
			}
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();

			productService.release(id, user.getUserId(), nowDate);
			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * @Description 撤销发布
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午4:53:05
	 */
	@RequestMapping("revokeRelease")
	@ResponseBody
	public JsonResult revokeRelease() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String id = paramObj.getString("id");// 产品分类ID

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "产品ID必填");
			}
			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();

			productService.revokeRelease(id, user.getUserId(), nowDate);
			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * @Description 删除
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午5:00:22
	 */
	@RequestMapping("delete")
	@ResponseBody
	public JsonResult delete() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String id = paramObj.getString("id");// 产品分类ID

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "产品ID必填");
			}

			UserCache user = SysUtil.sysUser(request, response);
			Date nowDate = new Date();
			productService.updateForDelete(id, user.getUserId(), nowDate);
			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * @Description 修改库存
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午5:00:22
	 */
	@RequestMapping("updateStork")
	@ResponseBody
	public JsonResult updateStork() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {

			String id = paramObj.getString("id");// 产品分类ID

			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "产品ID必填");
			}
			int proStork = paramObj.getIntValue("proStork");

			productService.updateStork(id, proStork);
			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}
}
