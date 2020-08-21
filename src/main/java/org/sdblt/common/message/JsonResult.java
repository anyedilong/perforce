package org.sdblt.common.message;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAnyElement;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * 
 * @ClassName JsonResult
 * @Description 系统消息
 * @author sen
 * @Date 2016年11月21日 上午9:48:53
 * @version 1.0.0
 */
public class JsonResult extends ResponseStatus {

	private Object data;// 结果集
	private ProcessStatus result;// 处理状态

	public JsonResult() {
		this.result = ProcessStatus.COMMON_SUCCESS;
		this.data = null;
	}

	public JsonResult(Object data) {
		this.result = ProcessStatus.COMMON_SUCCESS;
		this.data = data;
	}

	public JsonResult(Object data, ProcessStatus result) {
		Assert.notNull(result, "Result must not be null!");
		this.setResult(result);
		this.data = data;
	}

	public JsonResult(Object data, int retCode, String retMsg) {
		ProcessStatus result = new ProcessStatus(retCode, retMsg);
		this.setResult(result);
		this.data = data;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.setCode(responseStatus.getCode());
		this.setMessage(responseStatus.getMessage());
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ProcessStatus getResult() {
		return result;
	}

	public void setResult(ProcessStatus result) {
		this.result = result;
	}

	public String toJsonString() {
		
		SerializeConfig config = new SerializeConfig(); // 生产环境中，config要做singleton处理，要不然会存在性能问题
		config.propertyNamingStrategy = com.alibaba.fastjson.PropertyNamingStrategy.SnakeCase;
		String rsStr = JSON.toJSONString(this, config);
		return rsStr;
	}

}