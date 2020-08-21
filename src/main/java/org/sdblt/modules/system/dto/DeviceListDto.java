package org.sdblt.modules.system.dto;

import java.io.Serializable;

import javax.persistence.Transient;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.utils.CacheManagerUtil;

public class DeviceListDto implements Serializable{

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 5926330456030154458L;
	
	private String id;
	private String deviceName;//设备名
	private String deviceCode;//设备编码
	private String deviceModel;//设备型号
	private String deviceMaker;//设备厂家
	private String deviceWarranty;//质保期
	private String deviceType;//设备类型(字典)
	@Transient
	private String dictDeviceType;//设备类型字典值
	private String status;//状态
	private PageModel page;// 分页
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceMaker() {
		return deviceMaker;
	}
	public void setDeviceMaker(String deviceMaker) {
		this.deviceMaker = deviceMaker;
	}
	public String getDeviceWarranty() {
		return deviceWarranty;
	}
	public void setDeviceWarranty(String deviceWarranty) {
		this.deviceWarranty = deviceWarranty;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDictDeviceType() {
		return CacheManagerUtil.getTextByValue("dictDeviceType",deviceType);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public PageModel getPage() {
		return page;
	}
	public void setPage(PageModel page) {
		this.page = page;
	}
	
	

}
