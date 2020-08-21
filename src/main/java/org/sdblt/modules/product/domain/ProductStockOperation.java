package org.sdblt.modules.product.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;


import org.sdblt.modules.common.domain.BaseDomain;

import org.sdblt.modules.common.utils.CacheManagerUtil;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName ProductStockOperation
 * @Description '产品库存操作历史'
 * @authorliuxingx
 * @Date 2017年3月31日 上午11:54:46
 * @version 1.0.0
 */
@Entity
@Table(name = "t_vm_product_stock_operation")
public class ProductStockOperation extends BaseDomain{
	/**
	 * @Field @serialVersionUID : TODO·
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name="pro_stock_id")
	private String proStockId;//'产品库存ID'
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "pro_stock_id", referencedColumnName = "id", insertable = false, updatable = false)
//	@NotFound(action = NotFoundAction.IGNORE)
//	private ProductStock productStock;//产品库存
	// 产品状态
	// 10 登记 12 登记完成
	// 20 测试 21 测试失败 23 测试成功
	// 30 入库成功
	// 40 出库代理商 43代理商退货入库 42 代理商退货重新测试 41 代理商退货重新登记
	// 50 出库客户 54退货代理商 53 退货商家 52 客户退货 重新测试 51 客户退货重新登记
	// 60 激活
	private String operationType;
	


	private String remarks;//'备注';
	
	@Column(name="operation_user",updatable=false)
	private String operationUser;//'操作人'
	
	@JSONField(format ="yyyy-MM-dd hh:mm:ss")
	private Date operationTime;//'操作时间'
	
	private String createUser;//'创建人'
	
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;//'创建时间'
	

	
	@Transient
	private String idList;
	
	
	
	

	public String getIdList() {
		return idList;
	}
	public void setIdList(String idList) {
		this.idList = idList;
	}
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
//	public ProductStock getProductStock() {
//		return productStock;
//	}
//	public void setProductStock(ProductStock productStock) {
//		this.productStock = productStock;
//	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	public String getOperationName(){
		return CacheManagerUtil.getTextByValue("dictProductStatus",operationType);
		
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOperationUser() {
		return operationUser;
	}
	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}
	public String  getUsername(){
		return CacheManagerUtil.getUserNameById(operationUser);
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
