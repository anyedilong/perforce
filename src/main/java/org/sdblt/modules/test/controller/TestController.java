package org.sdblt.modules.test.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.message.JsonResult;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.controller.BaseRestController;
import org.sdblt.modules.test.domain.Test;
import org.sdblt.modules.test.dto.TestDto;
import org.sdblt.modules.test.service.TestService;
import org.sdblt.utils.validate.ValidateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("test")
public class TestController extends BaseRestController {

	@Inject
	private TestService service;

	public final static String TEST_CONTROLLER = "";

	/**
	 * 
	 * @Description 检验
	 * @return
	 * @author sen
	 * @Date 2016年12月8日 上午10:26:00
	 */
	@RequestMapping("validateTest")
	public JsonResult validateTest(String param) {
		// 参数转换
		TestDto test = JSON.parseObject(param, TestDto.class);

		//
		ValidateUtil.vldObjCamelLowerCase(test, new String[] { "add", "*" }, response);

		return jsonResult();
	}

	@RequestMapping("page")
	public JsonResult getDict(String param) {

		PageModel page = new PageModel(0, 5);
		service.findPage(page);
		return jsonResult(page);
	}

	@RequestMapping("save")
	public JsonResult save(String param) {
		//
		Test t = new Test();
		t.setAddress("地址");
		t.setCreateTime(new Date());
		t.setUserName("张三");

		service.save(t);

		return jsonResult();

	}

	@RequestMapping("update")
	public JsonResult update(String param) {

		Test t = service.get("1");
		t.setCreateTime(new Date());

		service.save(t);

		return jsonResult();

	}

	@RequestMapping("delete")
	public JsonResult delete(String param) {

		service.delete("5");

		return jsonResult();

	}

	@RequestMapping("updateName")
	public JsonResult updateName(String param) {

		Test t = new Test();
		t.setId("1");
		t.setUserName("测试11111");

		service.updateName(t);

		return jsonResult();

	}

	/**
	 * 
	 * @Description 利用jpa特性
	 * @param param
	 * @return
	 * @author sen
	 * @Date 2016年11月22日 下午4:43:45
	 */
	@RequestMapping("findByUserName")
	public JsonResult findByUserName(String param) {

		Test t = service.findByUserName("张三4");

		return jsonResult(t);

	}

	/**
	 * 
	 * @Description sql
	 * @param param
	 * @return
	 * @author sen
	 * @Date 2016年11月22日 下午4:44:40
	 */
	@RequestMapping("findByUserName1")
	public JsonResult findByUserName1(String param) {

		Test t = service.findUserByUserName("张三4");

		return jsonResult(t);

	}

	/**
	 * 
	 * @Description 自定义查询
	 * @param param
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 上午9:19:59
	 */
	@RequestMapping("queryByUsername")
	public JsonResult queryByUsername() {

		TestDto t = service.queryByUsername("张三");

		return jsonResult(t);

	}

	/**
	 * 
	 * @Description 自定义查询
	 * @param param
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 上午9:19:59
	 */
	@RequestMapping("queryListByUsername")
	public JsonResult queryListByUsername() {

		List<TestDto> rs = service.queryListByUsername("张三");

		return jsonResult(rs);

	}

	/**
	 * 
	 * @Description 自定义查询
	 * @param param
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 上午9:19:59
	 */
	@RequestMapping("queryPageList")
	public JsonResult queryPageList() {

		PageModel page = new PageModel(2, 4);

		service.queryPageList(page);

		return jsonResult(page);

	}

	/**
	 * 
	 * @Description JPA 分页查询
	 * @param param
	 * @return
	 * @author sen
	 * @Date 2016年11月23日 上午9:19:59
	 */
	@RequestMapping("queryJpaPageList")
	public JsonResult queryJpaPageList() {

		PageModel page = new PageModel(2, 4);

		service.queryJpaPageList(page);

		return jsonResult(page);

	}

}
