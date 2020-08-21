package org.sdblt.modules.product.controller;

import java.util.List;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.product.domain.ProductStock;
import org.sdblt.modules.product.dto.ProductStockListDto;
import org.sdblt.modules.product.service.ProductStockService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.sf.ehcache.search.Direction;

/**
 * @ClassName ProductStockController
 * @Description 产品库存controller
 * @authorliuxingx
 * @Date 2017年3月31日 下午3:02:48
 * @version 1.0.0
 */
@Controller
@RequestMapping("${restPath}/proStock")
public class ProductStockController extends BaseController {

	@Inject
	private ProductStockService proStockService;

	/**
	 * 
	 * @Description 产品登记列表跳转
	 * @return
	 * @author liuxingx
	 * @Date 2017年3月31日 下午3:03:34
	 */
	@RequestMapping("forwordFitList")
	public ModelAndView forwordShow() {
		return forword("/modules/product_mg/proStock/fit/proStockList");
	}
	
	@RequestMapping("proStockList")
	public   ModelAndView  proStockList(){
		return forword("/modules/product_mg/proStock/proStockList");
	}
	@RequestMapping("forwordInList")
	public ModelAndView forwordInShow() {
		return forword("/modules/product_mg/proStock/In/proStockInList");
	}
	@RequestMapping("getFitList")
	@ResponseBody
	public JsonResult getList() {
		String param = getParam(request);

		ProductStockListDto proStockParam = JSON.parseObject(param, ProductStockListDto.class);

		if (proStockParam == null) {
			proStockParam = new ProductStockListDto();
		}
		PageModel page = proStockParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		proStockService.getList(proStockParam, page);
		return jsonResult(page);
	}
	
	@RequestMapping("showlist")
	@ResponseBody
	public JsonResult  showlist(){
		 List<DictCache>  list=CacheManagerUtil.getDictListByCode("dictProductStatus");
	
		return jsonResult(list);
		 
	}
	
	/**
	 * @Description 产品库存管理列表
	 * @return
	 * @author liuxingx
	 * @Date 2017年3月31日 下午3:11:58
	 */
	@RequestMapping("getFitLists")
	@ResponseBody
	public JsonResult getLists() {
		String param = getParam(request);

		ProductStockListDto proStockParam = JSON.parseObject(param, ProductStockListDto.class);

		if (proStockParam == null) {
			proStockParam = new ProductStockListDto();
		}
		PageModel page = proStockParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		proStockService.getLists(proStockParam, page);
		return jsonResult(page);
	}
	
	/**
	 * 产品入库显示列表
	 * @Description (TODO)
	 * @return
	 * @authorliuxingx
	 * @Date 2017年4月13日 上午8:48:29
	 */
	@RequestMapping("getInList")
	@ResponseBody
	public JsonResult getInList() {
		String param = getParam(request);
		
		ProductStockListDto proStockParam = JSON.parseObject(param, ProductStockListDto.class);
		
		if (proStockParam == null) {
			proStockParam = new ProductStockListDto();
		}
		PageModel page = proStockParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		 proStockService.getInList(proStockParam,page);
		return jsonResult(page);
	}
	/**
	 * @Description 产品登记跳转 SAVE
	 * @return
	 * @author liuxingx
	 * @Date 2017年3月31日 下午3:18:57
	 */
	@RequestMapping("forwordFitEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/product_mg/proStock/fit/proStockEdit");
	}

	/**
	 * 
	 * @Description 客户产品出库跳转
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月10日 上午11:20:46
	 */
	@RequestMapping("forwordStockEdit")
	public ModelAndView forwordEditOut() {
		return forword("/modules/product_mg/proStock/out/proStockCustomerEdit");
	}

	/**
	 * 
	 * @Description 代理商产品出库跳转
	 * @return
	 * @authorliuxingx
	 * @Date 2017年4月11日 上午9:35:04
	 */
	@RequestMapping("forwordStockProxyEdit")
	public ModelAndView forwordEditProxyOut() {
		return forword("/modules/product_mg/proStock/out/proStockProxyEdit");
	}
	/**
	 * @Description 产品登记
	 * @return
	 * @authorliuxingx
	 * @Date 2017年3月31日 下午4:56:31
	 */
	@RequestMapping("fitProStock")
	@ResponseBody
	public JsonResult fitProStock() {
		String param = getParam(request);
		ProductStock proStockParam = JSON.parseObject(param, ProductStock.class);
		if (proStockParam != null) {
			UserCache user = SysUtil.sysUser(request, response);
			proStockParam.setFitOperation(user.getUserId());
			proStockParam.setFitUser(user.getUserId());
			proStockService.fitProStock(proStockParam,user);
			return jsonResult();

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 产品入库
	 * @return
	 * @author liuxingx
	 * @Date 2017年4月12日 上午11:28:21
	 */
	@RequestMapping("InProStock")
	@ResponseBody
	public JsonResult InProStock() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {
			
			List ids = paramObj.getJSONArray("idList");
			UserCache user = SysUtil.sysUser(request, response);
			int successCount = proStockService.inProStock(ids,user.getUserId());
			return jsonResult(successCount);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}
	/**
	 * 
	 * @Description 产品出库
	 * @return
	 * @authorliuxingx
	 * @Date 2017年4月11日 下午2:58:39
	 */
	@RequestMapping("outProStock")
	@ResponseBody
	public JsonResult outProStock() {
		String param = getParam(request);
		ProductStock proStockParam = JSON.parseObject(param, ProductStock.class);
		if (proStockParam != null) {
			UserCache user = SysUtil.sysUser(request, response);
			int count =proStockService.outProStock(proStockParam, user);
			return jsonResult(count);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 
	 * @Description 产品库存详情跳转
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月1日 下午2:28:51
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
	  
			ProductStock proStock = proStockService.get(id);
	
			return jsonResult(proStock);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}

	}

	/**
	 * 
	 * @Description 跳转库存详情
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月1日 下午5:24:45
	 */
	@RequestMapping("forwordFitShow")
	public ModelAndView forwordFitShow() {
		return forword("/modules/product_mg/proStock/fit/proStockShow");
	}

	/**
	 * 
	 * @Description 代理商出库
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月10日 上午9:55:28
	 */
	@RequestMapping("forwordFitProxyList")
	public ModelAndView forwordShowProxy() {
		return forword("/modules/product_mg/proStock/out/proStockProxyList");
	}

	/**
	 * 
	 * @Description 客户出库
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月10日 上午11:11:21
	 */
	@RequestMapping("forwordFitCustomerList")
	public ModelAndView forwordShowCustomer() {
		return forword("/modules/product_mg/proStock/out/proStockList");
	}

	/**
	 * 
	 * @Description
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月1日 下午5:24:45
	 */
	@RequestMapping("forwordStockShow")
	public ModelAndView forwordOutShow() {
		return forword("/modules/product_mg/proStock/out/proStockShow");
	}

	/**
	 * 
	 * @Description 删除登记产品
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月11日 上午9:33:01
	 */
	@RequestMapping("delete")
	@ResponseBody
	public JsonResult delete() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);
		if (paramObj != null) {
			List ids = paramObj.getJSONArray("ids");
			proStockService.batchUpdateForDel(ids);

		} else {
			return jsonResult(null, 10001, "参数错误");
		}
		return jsonResult();
	}

	/**
	 * 
	 * @Description 产品测试
	 * @return
	 * @author yixiaoli 
	 * @Date 2017年4月11日 下午1:17:17
	 */
	@RequestMapping("forwordTestList")
	public ModelAndView forwordTestList() {
		return forword("/modules/product_mg/proStock/test/proStockList");
	}

	/**
	 * 
	 * @Description 测试跳转
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月11日 下午2:27:02
	 */
	@RequestMapping("testProStock")
	@ResponseBody
	public JsonResult testProStock() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {
			List ids = paramObj.getJSONArray("ids");
			UserCache user = SysUtil.sysUser(request, response);
			int count=proStockService.testProduct(ids,user.getUserId());
			return jsonResult(count);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}
	/**
	 * 
	 * @Description 测试查询
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月13日 上午8:40:42
	 */
	@RequestMapping("getTestList")
	@ResponseBody
	public JsonResult getTestList() {
		String param = getParam(request);

		ProductStockListDto proStockParam = JSON.parseObject(param, ProductStockListDto.class);

		if (proStockParam == null) {
			proStockParam = new ProductStockListDto();
		}
		PageModel page = proStockParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		proStockService.getTestList(proStockParam, page);
		return jsonResult(page);
	}
	/**
	 * 
	 * @Description 测试失败
	 * @return
	 * @author yixiaoli
	 * @Date 2017年4月13日 上午8:53:09
	 */
	@RequestMapping("errorProStock")
	@ResponseBody
	public JsonResult errorProStock() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);

		if (paramObj != null) {
			List ids = paramObj.getJSONArray("ids");
			UserCache user = SysUtil.sysUser(request, response);
			int count=proStockService.errorProduct(ids,user.getUserId());
			return jsonResult(count);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}
	@RequestMapping("getProxyList")
	@ResponseBody
	public JsonResult getProxyList() {
		String param = getParam(request);

		ProductStockListDto proStockParam = JSON.parseObject(param, ProductStockListDto.class);

		if (proStockParam == null) {
			proStockParam = new ProductStockListDto();
		}
		PageModel page = proStockParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		proStockService.getProxyList(proStockParam, page);
		return jsonResult(page);
	}
	@RequestMapping("getOutList")
	@ResponseBody
	public JsonResult getOutList() {
		String param = getParam(request);

		ProductStockListDto proStockParam = JSON.parseObject(param, ProductStockListDto.class);

		if (proStockParam == null) {
			proStockParam = new ProductStockListDto();
		}
		PageModel page = proStockParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		proStockService.getOutList(proStockParam, page);
		return jsonResult(page);
	}
}
