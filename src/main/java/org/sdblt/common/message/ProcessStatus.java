package org.sdblt.common.message;

import java.io.Serializable;

/**
 * 
 * @ClassName ProcessStatus
 * @Description 处理结果返回状态
 * @author sen
 * @Date 2016年11月21日 下午3:21:51
 * @version 1.0.0
 */
public class ProcessStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8397325882760657573L;

	private int retCode;

	private String retMsg;

	public ProcessStatus() {

	}

	public ProcessStatus(int retCode, String retMsg) {
		this.retCode = retCode;
		this.retMsg = retMsg;
	}

	/**
	 * 返回处理正常或错误编码
	 * 
	 * @return int
	 */
	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	/**
	 * 处理结果描述
	 * 
	 * @return String
	 */
	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public final static ProcessStatus COMMON_SUCCESS = new ProcessStatus(0, "已处理");
	public final static ProcessStatus COMMON_ERROR = new ProcessStatus(-1, "未知错误");

	public final static ProcessStatus ILLEGAL_REQUEST = new ProcessStatus(1001, "非法请求");
	public final static ProcessStatus REQUEST_TIMEOUT = new ProcessStatus(1002, "请求超时");
	public final static ProcessStatus FREEZE_ERROR = new ProcessStatus(1004, "用户已冻结");
	public final static ProcessStatus AUTH_ERROR = new ProcessStatus(1003, "身份认证错误");
	public final static ProcessStatus NOT_AUTH = new ProcessStatus(1005, "未对该请求授权");

	public static final ProcessStatus DEVICE_AUTH_ERROR = new ProcessStatus(2001, "设备授权信息错误");

	public static final ProcessStatus DEVICE_NOT_FIND = new ProcessStatus(2002, "设备不存在");

	public static final ProcessStatus DEVICE_FREEZE = new ProcessStatus(2003, "设备冻结，联系客服解冻");

	public static final ProcessStatus DEVICE_NOTBIND = new ProcessStatus(2004, "设备未入网注册");

	public static final ProcessStatus DEVICE_NOTACTIVATE = new ProcessStatus(2005, "设备未激活，需激活后使用");

	public final static ProcessStatus DATA_VALIDATE = new ProcessStatus(10000, "数据校验错误");
}
