package org.sdblt.modules.system.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.utils.CacheManagerUtil;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "t_vm_device")
public class Device extends BaseDomain{
	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 7559404505994793676L;
	@Id
	private String id;//主键
	private String deviceName;//设备名称
	private String deviceType;//设备类型(字典)
	@Transient
	private String dictDeviceType;//设备类型字典值
	private String deviceMaker;//设备厂家
	private String deviceModel;//设备型号
	private String deviceCode;//设备编码
	private int deviceWarranty;//质保期
	private String makerPhone;//厂家联系方式
	private String makerAddress;//厂家地址
	private String remarks;//备注
	private String status;//状态 1 正常 2冻结 3 删除
	@Column(updatable = false)
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;//创建时间
	@Column(updatable = false)
	private String createUser;//创建人
	@JSONField(format = "yyyy-MM-dd")
	private Date updateTime;//更新时间
	private String updateUser;//更新人
	
	
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
	public String getDictDeviceType() {
		return CacheManagerUtil.getTextByValue("dictDeviceType",deviceType);
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceMaker() {
		return deviceMaker;
	}
	public void setDeviceMaker(String deviceMaker) {
		this.deviceMaker = deviceMaker;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public int getDeviceWarranty() {
		return deviceWarranty;
	}
	public void setDeviceWarranty(int deviceWarranty) {
		this.deviceWarranty = deviceWarranty;
	}
	public String getMakerPhone() {
		return makerPhone;
	}
	public void setMakerPhone(String makerPhone) {
		this.makerPhone = makerPhone;
	}
	public String getMakerAddress() {
		return makerAddress;
	}
	public void setMakerAddress(String makerAddress) {
		this.makerAddress = makerAddress;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
}
