package org.sdblt.modules.product.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.system.domain.Device;

/**
 * 
 * @ClassName ProductStockDevice
 * @Description 产品库存设备信息实体类
 * @authorliuxingx
 * @Date 2017年3月31日 上午11:42:20
 * @version 1.0.0
 */
@Entity
@Table(name = "t_vm_product_stock_device")
public class ProductStockDevice extends BaseDomain {
	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Column(name = "pro_stock_id")
	private String proStockId;// '产品库存ID'
	private String deviceNum;// '设备编号';
	@Column(name = "device_id")
	private String deviceId;// '设备ID';
	// 设备
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
	@JoinColumn(name = "device_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Device device;

	private String subFlg;// '附属设备标识';
	private String requiredFlg;// '是否为必要设备标识';
	private int deviceWarranty;// '保质期（月）';
	private String deviceType;// '设备类型';

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProStockId() {
		return proStockId;
	}

	public void setProStockId(String proStockId) {
		this.proStockId = proStockId;
	}

	public String getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSubFlg() {
		return subFlg;
	}

	public void setSubFlg(String subFlg) {
		this.subFlg = subFlg;
	}

	public String getRequiredFlg() {
		return requiredFlg;
	}

	public void setRequiredFlg(String requiredFlg) {
		this.requiredFlg = requiredFlg;
	}

	public int getDeviceWarranty() {
		return deviceWarranty;
	}

	public void setDeviceWarranty(int deviceWarranty) {
		this.deviceWarranty = deviceWarranty;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getDeviceTypeName() {
		return CacheManagerUtil.getTextByValue("dictDeviceType", deviceType);
	}
}
