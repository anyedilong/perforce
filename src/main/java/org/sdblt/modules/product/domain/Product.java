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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.domain.BaseOrgDomain;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.system.domain.SysUserRole;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName ProductDomain
 * @Description 产品实体类
 * @author sen
 * @Date 2017年3月15日 上午10:12:25
 * @version 1.0.0
 */
@Entity
@Table(name = "t_vm_product")
public class Product extends BaseDomain {

	@Id
	private String id;

	/**
	 * 产品名称
	 */
	private String proName;
	/**
	 * 产品编码
	 */
	private String proCode;
	/**
	 * 产品分类ID 产品表
	 */
	@Column(name = "pro_type")
	private String proType;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
	@JoinColumn(name = "pro_type", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private ProductType productType;// 产品分类

	/**
	 * 产品立项时间
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date proApprovalTime;
	/**
	 * 产品定型时间
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date proFinalTime;
	/**
	 * 产品投产时间
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date proPutTime;
	/**
	 * 产品软件质保时间（月）
	 */
	private int proSoftwareWarranty;
	/**
	 * 产品说明
	 */
	private String proDescription;
	/**
	 * 产品功能
	 */
	private String proFunction;
	/**
	 * 产品简介
	 */
	private String proIntroducte;
	/**
	 * 产品库存
	 */
	@Column(updatable = false) // 更新操作忽略
	private int proStork;
	/**
	 * 最新版本
	 */
	@Column(updatable = false) // 更新操作忽略
	private String proNewVersion;
	/**
	 * 最新全量版本
	 */
	@Column(updatable = false) // 更新操作忽略
	private String proNewFullVersion;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 状态 1 草稿 2 发布 3 撤销发布 4 删除
	 */
	@Column(updatable = false) // 更新操作忽略
	private String status;
	/**
	 * 发布时间
	 */
	@Column(updatable = false) // 更新操作忽略
	@JSONField(format = "yyyy-MM-dd")
	private Date releaseTime;
	/**
	 * 发布人
	 */
	@Column(updatable = false) // 更新操作忽略
	private String releaseUser;
	/**
	 * 创建时间
	 */
	@Column(updatable = false) // 更新操作忽略
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;
	/**
	 * 创建人
	 */
	@Column(updatable = false) // 更新操作忽略
	private String createUser;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;

	// 级联操作 若使用全部的方法可以使用all
	// @OneToMany(cascade = { CascadeType.REFRESH,
	// CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})

	// fetch的默认值是：如果是得到many的一方 默认的是延迟加载
	// mappedBy表示关系被维护端，是在TeamUser类总的哪一个属性维护的
	@OneToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_id", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	@OrderBy(value="deviceType")
	private List<ProductDevice> proDeviceList;// 产品设备

	// 产品版本
	@OneToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_id", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	@OrderBy(value="orderNum desc")
	private List<ProductVersion> proVersionList;// 产品版本

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProType() {
		return proType;
	}

	public String getProTypeName(){
		return CacheManagerUtil.getProTypeNameById(proType);
	}
	
	public void setProType(String proType) {
		this.proType = proType;
	}

	public Date getProApprovalTime() {
		return proApprovalTime;
	}

	public void setProApprovalTime(Date proApprovalTime) {
		this.proApprovalTime = proApprovalTime;
	}

	public Date getProFinalTime() {
		return proFinalTime;
	}

	public void setProFinalTime(Date proFinalTime) {
		this.proFinalTime = proFinalTime;
	}

	public Date getProPutTime() {
		return proPutTime;
	}

	public void setProPutTime(Date proPutTime) {
		this.proPutTime = proPutTime;
	}

	public int getProSoftwareWarranty() {
		return proSoftwareWarranty;
	}

	public void setProSoftwareWarranty(int proSoftwareWarranty) {
		this.proSoftwareWarranty = proSoftwareWarranty;
	}

	public String getProDescription() {
		return proDescription;
	}

	public void setProDescription(String proDescription) {
		this.proDescription = proDescription;
	}

	public String getProFunction() {
		return proFunction;
	}

	public void setProFunction(String proFunction) {
		this.proFunction = proFunction;
	}

	public String getProIntroducte() {
		return proIntroducte;
	}

	public void setProIntroducte(String proIntroducte) {
		this.proIntroducte = proIntroducte;
	}

	public int getProStork() {
		return proStork;
	}

	public void setProStork(int proStork) {
		this.proStork = proStork;
	}

	public String getProNewVersion() {
		return proNewVersion;
	}

	public void setProNewVersion(String proNewVersion) {
		this.proNewVersion = proNewVersion;
	}

	public String getProNewFullVersion() {
		return proNewFullVersion;
	}

	public void setProNewFullVersion(String proNewFullVersion) {
		this.proNewFullVersion = proNewFullVersion;
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
	
	public String getStatusName() {
		if("1".equals(status)){
			return "待发布";
		}else if("2".equals(status)){
			return "已发布";
		}else if("3".equals(status)){
			return "撤销发布";
		}
		return "";
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
	
	public String getCreateUserName() {
		return CacheManagerUtil.getUserNameById(createUser);
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
	
	public String getUpdateUserName() {
		return CacheManagerUtil.getUserNameById(updateUser);
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReleaseUser() {
		return releaseUser;
	}
	
	public String getReleaseUserName() {
		return CacheManagerUtil.getUserNameById(releaseUser);
	}
	

	public void setReleaseUser(String releaseUser) {
		this.releaseUser = releaseUser;
	}

	public List<ProductDevice> getProDeviceList() {
		return proDeviceList;
	}

	public void setProDeviceList(List<ProductDevice> proDeviceList) {
		this.proDeviceList = proDeviceList;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public List<ProductVersion> getProVersionList() {
		return proVersionList;
	}

	public void setProVersionList(List<ProductVersion> proVersionList) {
		this.proVersionList = proVersionList;
	}

}
