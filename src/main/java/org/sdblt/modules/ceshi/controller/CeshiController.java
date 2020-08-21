package org.sdblt.modules.ceshi.controller;

import javax.inject.Inject;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.message.JsonResult;
import org.sdblt.modules.ceshi.service.CeshiService;
import org.sdblt.modules.common.controller.BaseController;
import org.sdblt.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("${tablePath}/ceshi")
public class CeshiController extends BaseController {

	@Inject
	private CeshiService ceshiService;

	/**
	 * 
	 * @Description (TODO)
	 * @param param
	 * @return
	 * @author wangpenghui
	 * @Date 2017年4月6日 上午11:17:10
	 */
	@RequestMapping("product")
	@ResponseBody
	public JsonResult updateproduct(String param) {
		try{
			JSONObject paramobj = JSONObject.parseObject(param);
			if (paramobj != null) {
				String proNum = paramobj.getString("proNum");//
				String machineCode = paramobj.getString("machineCode");//
				if (StringUtils.isNull(proNum) || StringUtils.isNull(machineCode)) {
					return jsonResult(null, 1002, "产品编号和机器码不能为空");
				}
				ceshiService.updateproduct(proNum, machineCode);
				return jsonResult();
			} else {
				return jsonResult(null, 10001, "参数错误");
			}
		}catch(CommonException e){
			e.printStackTrace();
			return jsonResult(null,e.getCode(),e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return jsonResult(null,90001,e.getMessage());
		}
		
	}

}
