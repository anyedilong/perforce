package org.sdblt.modules.product.dto;

import java.io.Serializable;

public class ProductStockDeviceDto implements Serializable{
	private String devieNum;//'设备编号'
	private String deviceType;//'设备类型';
	public String getDevieNum() {
		return devieNum;
	}
	public void setDevieNum(String devieNum) {
		this.devieNum = devieNum;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

}
