package org.sdblt.modules.product.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Clob;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.system.domain.Device;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName ProductDevice
 * @Description 产品设备
 * @author sen
 * @Date 2017年3月23日 下午3:10:00
 * @version 1.0.0
 */
@Entity
@Table(name = "t_vm_product_device")
public class ProductDevice extends BaseDomain {

	@Id
	private String id;// null
	@Column(name = "pro_id")
	private String proId;// 产品ID
	@Column(name = "device_id")
	private String deviceId;// 设备ID

	// 设备
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
	@JoinColumn(name = "device_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Device device;

	private String deviceType;// 设备类型
	private String subFlg;// 附属设备标识
	private String requiredFlg;// 是否为必要设备标识
	private int count;// 数量

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProId() {
		return this.proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return this.deviceType;
	}
	
	public String getDeviceTypeName() {
		return CacheManagerUtil.getTextByValue("dictDeviceType",deviceType);
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSubFlg() {
		return this.subFlg;
	}

	public void setSubFlg(String subFlg) {
		this.subFlg = subFlg;
	}

	public String getRequiredFlg() {
		return this.requiredFlg;
	}

	public void setRequiredFlg(String requiredFlg) {
		this.requiredFlg = requiredFlg;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
