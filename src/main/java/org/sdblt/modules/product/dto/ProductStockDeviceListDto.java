package org.sdblt.modules.product.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.utils.CacheManagerUtil;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName 设备和库存明细DTO
 * @Description TODO
 * @authorliuxingx
 * @Date 2017年4月1日 下午3:32:13
 * @version 1.0.0
 */
public class ProductStockDeviceListDto {
	private String id;
	private String deviceNum;// '设备编号'
	private String deviceType;// '设备类型';
	@JSONField(format = "yyyy-MM-dd")
	private Date fitTime;// '产品装配时间'
	private String fitUser;// '产品装配人'
	// 产品状态
	// 10 登记 12 登记完成
	// 20 测试 21 测试失败 23 测试成功
	// 30 入库成功
	// 40 出库代理商 43代理商退货入库 42 代理商退货重新测试 41 代理商退货重新登记
	// 50 出库客户 54退货代理商 53 退货商家 52 客户退货 重新测试 51 客户退货重新登记
	// 60 激活
	private String status;
	private String dictStatus;// 状态字典值
	private String proStockId;// '产品库存ID'
	private PageModel page;// 分页
	/**
	 * 产品分类ID 产品表
	 */
	private String proType;

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
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

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	public String getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Date getFitTime() {
		return fitTime;
	}

	public void setFitTime(Date fitTime) {
		this.fitTime = fitTime;
	}

	public String getFitUser() {
		return fitUser;
	}

	public void setFitUser(String fitUser) {
		this.fitUser = fitUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
