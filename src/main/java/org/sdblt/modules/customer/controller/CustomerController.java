package org.sdblt.modules.customer.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.modules.common.utils.SysUtil;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.customer.domain.Customer;
import org.sdblt.modules.customer.dto.CustomerListDto;
import org.sdblt.modules.customer.service.CustomerService;
import org.sdblt.modules.proxy.dto.ProxyDto;
import org.sdblt.modules.proxy.service.ProxyService;
import org.sdblt.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("${restPath}/customer")
public class CustomerController extends BaseController {

	// 客户service
	@Inject
	private CustomerService customerService;

	/**
	 * 跳转到客户列表页面
	 * 
	 * @Description (TODO)
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月28日 上午9:43:36
	 */
	@RequestMapping("forwordList")
	public ModelAndView forwordList() {
		return forword("/modules/customer/customerList");
	}

	/**
	 * 跳转客户编辑页面
	 * 
	 * @Description (TODO)
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月28日 上午9:45:20
	 */
	@RequestMapping("forwordEdit")
	public ModelAndView forwordEdit() {
		return forword("/modules/customer/customerEdit");
	}

	@RequestMapping("forwordShow")
	public ModelAndView forwordShow() {
		return forword("/modules/customer/customerShow");
	}

	/**
	 * 获取客户列表
	 * 
	 * @Description (TODO)
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月28日 下午1:45:35
	 */
	@RequestMapping("getList")
	@ResponseBody
	public JsonResult getList() {
		String param = getParam(request);
		CustomerListDto customerParam = JSON.parseObject(param, CustomerListDto.class);
		if (customerParam == null) {
			customerParam = new CustomerListDto();
		}
		PageModel page = customerParam.getPage();
		if (page == null) {
			page = new PageModel();
		}
		customerService.getList(customerParam, page);
		return jsonResult(page);
	}

	/**
	 * 客户保存
	 * 
	 * @Description (TODO)
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月28日 下午1:52:50
	 */
	@RequestMapping("save")
	@ResponseBody
	public JsonResult save() {
		String param = getParam(request);
		Customer customerParam = JSON.parseObject(param, Customer.class);
		if (customerParam != null) {
			UserCache userCache = SysUtil.sysUser(request, response);
			Date nowDate = new Date();
			customerParam.setCreateUser(userCache.getUserId());
			customerParam.setCreateTime(nowDate);
			customerParam.setUpdateUser(userCache.getUserId());
			customerParam.setUpdateTime(nowDate);
			customerParam.setStatus("1");
			customerService.saveCustomer(customerParam);
			return jsonResult();
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 展示客户详情
	 * 
	 * @Description (TODO)
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月28日 下午1:57:01
	 */
	@RequestMapping("show")
	@ResponseBody
	public JsonResult show() {
		String param = getParam(request);
		JSONObject paramObj = JSONObject.parseObject(param);

		if (paramObj != null) {
			String id = paramObj.getString("id");
			if (StringUtils.isNull(id)) {
				return jsonResult(null, 10002, "用户Id必填");
			}
			Customer customer = customerService.get(id);
			return jsonResult(customer);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
	}

	/**
	 * 删除客户记录
	 * 
	 * @Description (TODO)
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月28日 下午2:02:52
	 */
	@RequestMapping("delete")
	@ResponseBody
	public JsonResult delete() {
		String param = getParam(request);
		JSONObject paramObj = JSON.parseObject(param);
		if (paramObj != null) {
			List ids = paramObj.getJSONArray("ids");
			customerService.batchUpdateForDel(ids);
		} else {
			return jsonResult(null, 10001, "参数错误");
		}
		return jsonResult();
	}
	/**
	 * 获取客户下拉列表
	 * @Description (TODO)
	 * @return
	 * @authorliuxingx
	 * @Date 2017年4月7日 上午11:24:25
	 */
	@RequestMapping("getCustomerDropDownList")
	@ResponseBody
	public JsonResult getCustomerDropDownList() {
		String param = getParam(request);
		CustomerListDto customerList = JSON.parseObject(param, CustomerListDto.class);
		if (customerList == null) {
			customerList = new CustomerListDto();
		}
		List<CustomerListDto> customerListDto = customerService.getCustomerDropDownList(customerList);
		return jsonResult(customerListDto);
	}
	
	
}
