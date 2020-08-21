package org.sdblt.modules.versionMgt.domain;

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
import org.hibernate.annotations.Where;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.product.domain.Product;
import org.sdblt.modules.product.domain.ProductVersion;
import org.sdblt.modules.product.domain.ProductVersionFile;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName VersionMgt
 * @Description TODO
 * @author josen
 * @Date 2017年3月21日 下午2:41:33
 * @version 1.0.0
 */


@Entity
@Table(name = "T_VM_PRODUCT_VERSION")
public class VersionMgt extends BaseDomain {
	
	@Id
	private String id;
	
	private String versionNum;         //产品版本
	@Column(name = "pro_id")
	private String proId;              //产品ID
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_id", referencedColumnName="id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Product  product;              //产品名称
	
	@Column(updatable = false)
	private String preposeVersionId;   //前置版本ID
	
	@Column(updatable = false)
	private String preposeVersionNum;   //前置版本号
	
	private String versionDescription; //版本说明
	
	@Column(updatable = false)
	private String versionType;        //类型 1全量 2 增量
	
	@Column(updatable = false)
	@JSONField(format="yyyy-MM-dd")
	private Date releaseTime;        //发布时间
	
	@Column(updatable = false)
	private String releaseUser;        //发布人
	
	private String status;             //状态 1保存2 发布
	
	@Column(updatable = false)
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;           //创建时间
	
	@Column(updatable = false)
	private String createUser;         //创建人
	
	@JSONField(format = "yyyy-MM-dd")
	private Date updateTime;           //更新时间
	
	private String updateUser;         //更新人
	
	
	@Column(updatable = false)
	private int orderNum;// 排序号
	
	
	// 附件
	@OneToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "version_id", referencedColumnName = "id", insertable = false, updatable = false)
	@Where(clause="STATUS='1'")
	@NotFound(action = NotFoundAction.IGNORE)
	private List<ProductVersionFile> prodVersFileList;//附件

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getPreposeVersionId() {
		return preposeVersionId;
	}

	public void setPreposeVersionId(String preposeVersionId) {
		this.preposeVersionId = preposeVersionId;
	}

	public String getVersionDescription() {
		return versionDescription;
	}

	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
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
		return CacheManagerUtil.getUserNameById(createUser);
	}

	public void setReleaseUser(String releaseUser) {
		this.releaseUser = releaseUser;
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
	
	public String getUpdateUserName() {
		return CacheManagerUtil.getUserNameById(updateUser);
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getPreposeVersionNum() {
		return preposeVersionNum;
	}

	public void setPreposeVersionNum(String preposeVersionNum) {
		this.preposeVersionNum = preposeVersionNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public List<ProductVersionFile> getProdVersFileList() {
		return prodVersFileList;
	}

	public void setProdVersFileList(List<ProductVersionFile> prodVersFileList) {
		this.prodVersFileList = prodVersFileList;
	}

	
	
	

}
