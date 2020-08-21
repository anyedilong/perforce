package org.sdblt.modules.product.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.customer.domain.Customer;
import org.sdblt.modules.proxy.domain.Proxy;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName ProductStock
 * @Description '产品库存明细'
 * @authorliuxingx
 * @Date 2017年3月31日 上午11:10:11
 * @version 1.0.0
 */
@Entity
@Table(name = "t_vm_product_stock")
public class ProductStock extends BaseDomain {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name = "pro_id")
	private String proId;// '产品ID';
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "pro_id", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private StockProduct product;
	
	@Column(name="pro_num")
	private String proNum;// '产品编号';
	private String remarks;
	//	产品状态 
	//	10 登记  11 登记中
	//	20 测试 21 测试失败  
	//	30 入库成功 
	//	40 出库代理商  43代理商退货入库  42 代理商退货重新测试  41 代理商退货重新登记 
	//	50 出库客户  54退货代理商  53 退货商家  52 客户退货 重新测试  51 客户退货重新登记 
	//	60 激活
	//  70 删除
	@Column(updatable = false)
	private String status;
	private String fitOperation;// '产品装配操作ID
	private String fitUser;// '产品装配人'
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	private Date fitTime;// '产品装配时间'

	@Column(updatable = false)
	private String testOperation;// '产品测试操作ID'
	@Column(updatable = false)
	private String testUser;// '产品测试人'
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	
	private Date testTime;// 产品测试时间

	@Column(updatable = false)
	private String storageOperation;// '产品入库操作ID'
	@Column(updatable = false)
	private String storageUser;// '产品入库人
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	@Column(updatable = false)
	private Date storageTime;// 产品入库时间'

	@Column(updatable = false)
	private String outProxyOperation;// '出库代理商操作ID'
	@Column(name="out_proxy_user")
	private String outProxyUser;// 代理商id
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "out_proxy_user", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Proxy proxy;//代理商
	@Column(updatable = false)
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	private Date outProxyTime;// '出库代理商时间'

	@Column(updatable = false)
	private String outCustomerOperation;// '出库客户操作ID'
	@Column( name="out_customer_user",updatable = false)
	private String outCustomerUser;// '客户ID'
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	@Column(updatable = false)
	private Date outCustomerTime;// '出库客户时间'
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "out_customer_user", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Customer customer;//代理商

	@JSONField(format = "yyyy-MM-dd")
	@Column(updatable = false)
	private Date activationTime;// '激活时间'
	@Column( name="machine_code",updatable = false)
	private String machineCode;// '产品机器码'
	@Column(updatable = false)
	private String currentVersion;// '产品当前版本'
	@Column(updatable = false)
	private Date upgradeTime;// '升级时间'
	private String outGoing;//入库人
	private String outCustomerAgent;//
	private String salesManager;
	private String outProxyAgent;
	@OneToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_stock_id", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	@OrderBy(value = "deviceType")
	private List<ProductStockDevice> proStockDeviceList;// 库存设备
	
	@OneToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_stock_id", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	@OrderBy(value="operationTime DESC")
	private  List<ProductStockOperation>  productStockOperation;
	
	
	@Transient
	private String idList;
	
	
   
   

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<ProductStockOperation> getProductStockOperation() {
		return productStockOperation;
	}

	public void setProductStockOperation(List<ProductStockOperation> productStockOperation) {
		this.productStockOperation = productStockOperation;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getOutGoing() {
		return outGoing;
	}

	public void setOutGoing(String outGoing) {
		this.outGoing = outGoing;
	}

	public String getOutCustomerAgent() {
		return outCustomerAgent;
	}

	public void setOutCustomerAgent(String outCustomerAgent) {
		this.outCustomerAgent = outCustomerAgent;
	}

	public String getSalesManager() {
		return salesManager;
	}

	public void setSalesManager(String salesManager) {
		this.salesManager = salesManager;
	}

	public String getOutProxyAgent() {
		return outProxyAgent;
	}

	public void setOutProxyAgent(String outProxyAgent) {
		this.outProxyAgent = outProxyAgent;
	}

	public StockProduct getProduct() {
		return product;
	}

	public void setProduct(StockProduct product) {
		this.product = product;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFitOperation() {
		return fitOperation;
	}

	public void setFitOperation(String fitOperation) {
		this.fitOperation = fitOperation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getProNum() {
		return proNum;
	}

	public void setProNum(String proNum) {
		this.proNum = proNum;
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

	public String getFitUser() {
		return fitUser;
	}

	public void setFitUser(String fitUser) {
		this.fitUser = fitUser;
	}
	public String  getFitName(){
		return CacheManagerUtil.getUserNameById( fitUser);
	}


	public Date getFitTime() {
		return fitTime;
	}

	public void setFitTime(Date fitTime) {
		this.fitTime = fitTime;
	}

	public String getTestOperation() {
		return testOperation;
	}

	public void setTestOperation(String testOperation) {
		this.testOperation = testOperation;
	}

	public String getTestUser() {
		return testUser;
	}

	public void setTestUser(String testUser) {
		this.testUser = testUser;
	}
	public String  getTestName(){
		return CacheManagerUtil.getUserNameById(testUser);
	}

	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

	public String getStorageOperation() {
		return storageOperation;
	}

	public void setStorageOperation(String storageOperation) {
		this.storageOperation = storageOperation;
	}

	public String getStorageUser() {
		return storageUser;
	}

	public void setStorageUser(String storageUser) {
		this.storageUser = storageUser;
	}
	public String  getStorageName(){
		 return CacheManagerUtil.getUserNameById(storageUser);
	}

	public Date getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}

	public String getOutProxyOperation() {
		return outProxyOperation;
	}

	public void setOutProxyOperation(String outProxyOperation) {
		this.outProxyOperation = outProxyOperation;
	}

	public String getOutProxyUser() {
		return outProxyUser;
	}

	public void setOutProxyUser(String outProxyUser) {
		this.outProxyUser = outProxyUser;
	}

	public Date getOutProxyTime() {
		return outProxyTime;
	}

	public void setOutProxyTime(Date outProxyTime) {
		this.outProxyTime = outProxyTime;
	}

	public String getOutCustomerOperation() {
		return outCustomerOperation;
	}

	public void setOutCustomerOperation(String outCustomerOperation) {
		this.outCustomerOperation = outCustomerOperation;
	}

	public String getOutCustomerUser() {
		return outCustomerUser;
	}

	public void setOutCustomerUser(String outCustomerUser) {
		this.outCustomerUser = outCustomerUser;
	}

	public Date getOutCustomerTime() {
		return outCustomerTime;
	}

	public void setOutCustomerTime(Date outCustomerTime) {
		this.outCustomerTime = outCustomerTime;
	}

	public Date getActivationTime() {
		return activationTime;
	}

	public void setActivationTime(Date activationTime) {
		this.activationTime = activationTime;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public Date getUpgradeTime() {
		return upgradeTime;
	}

	public void setUpgradeTime(Date upgradeTime) {
		this.upgradeTime = upgradeTime;
	}

	public List<ProductStockDevice> getProStockDeviceList() {
		return proStockDeviceList;
	}

	public void setProStockDeviceList(List<ProductStockDevice> proStockDeviceList) {
		this.proStockDeviceList = proStockDeviceList;
	}

}
