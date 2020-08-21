package org.sdblt.modules.product.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.utils.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName ProductStockListDto
 * @Description 产品库存列表DTO
 * @author liuxingx
 * @Date 2017年3月31日 下午3:11:28
 * @version 1.0.0
 */
public class ProductStockListDto implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;

	/**
	 * 产品分类ID 产品表
	 */
	private String proType;
	/**
	 * 产品名称
	 */
	private String proName;

	private String proNum;// '产品编号';

	private String proStockDevices;// 库存设备 设备编号;设备类型,设备编号;设备类型

	private String status;// 状态

	private PageModel page;// 分页
	
	private String outGoing;//出库人
	
	private String storageUser;// '产品入库人
	
	private String deviceCode;//设备编号
	
	private String deviceModel;//设备型号
	
	private String deviceType;//设备类型
	
	@Column(name = "pro_id")
	private String proId;// '产品ID';
	
	private String fitUser;// '产品装配人'
	@JSONField(format = "yyyy-MM-dd")
	private Date fitTime;// '产品装配时间'
	private String testUser;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTestUser() {
		return testUser;
	}
	public String  getStorageName(){
		return CacheManagerUtil.getUserNameById(storageUser);
	}

	public String getStorageUser() {
		return storageUser;
	}
	public void setStorageUser(String storageUser) {
		this.storageUser = storageUser;
	}
	public String  getTestName(){
		return CacheManagerUtil.getUserNameById(testUser);
	}
	public void setTestUser(String testUser) {
		this.testUser = testUser;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getOutGoing() {
		return outGoing;
	}

	public void setOutGoing(String outGoing) {
		this.outGoing = outGoing;
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

	private String typeName;

	public String getFitUser() {
		return fitUser;
	}

	public void setFitUser(String fitUser) {
		this.fitUser = fitUser;
	}

	public Date getFitTime() {
		return fitTime;
	}

	public void setFitTime(Date fitTime) {
		this.fitTime = fitTime;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	public String getProNum() {
		return proNum;
	}

	public void setProNum(String proNum) {
		this.proNum = proNum;
	}

	public String getProStockDevices() {
		String stckDeviceRs = "";
		// 处理库存设备数据
		if (!StringUtils.isNull(proStockDevices)) {
			String[] deviceArray = proStockDevices.split(",");
			for (String deviceItem : deviceArray) {
				if (!StringUtils.isNull(deviceItem)) {
					String[] device = deviceItem.split(";");
					if (device.length >= 2) {
						stckDeviceRs += String.format("%s(%s),",
								CacheManagerUtil.getTextByValue("dictDeviceType", StringUtils.toString(device[0])),
								StringUtils.toString(device[1]));
					}
				}
			}

			if (stckDeviceRs.length() > 0) {
				stckDeviceRs = stckDeviceRs.substring(0, stckDeviceRs.length() - 1);
			}
		}

		return stckDeviceRs;
	}

	public void setProStockDevices(String proStockDevices) {
		this.proStockDevices = proStockDevices;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDictStatus() {
		return CacheManagerUtil.getTextByValue("dictProductStatus", status);
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

}
