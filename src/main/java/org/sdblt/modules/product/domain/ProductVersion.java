package org.sdblt.modules.product.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.sql.Clob;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.utils.UUIDUtil;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName ProductVersion
 * @Description 产品版本
 * @author sen
 * @Date 2017年3月24日 下午6:05:09
 * @version 1.0.0
 */
@Entity
@Table(name = "t_vm_product_version")
public class ProductVersion extends BaseDomain {

	@Id
	private String id;// null
	private String versionNum;// 版本号
	@Column(name = "pro_id")
	private String proId;// 产品ID

	private String preposeVersionId;// 前置版本ID
	private int orderNum;// 排序号
	private String versionDescription;// 版本说明
	private String versionType;// 类型 1全量 2 增量
	@JSONField(format = "yyyy-MM-dd")
	private Date releaseTime;// 发布时间
	private String releaseUser;// 发布人
	private String status;// 状态 1保存2 发布
	private Date createTime;// 创建时间
	private String createUser;// 创建人
	private Date updateTime;// 更新时间
	private String updateUser;// 更新人

	// 版本升级文件
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "version_id", referencedColumnName = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	List<ProductVersionFile> proVersionFileList;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersionNum() {
		return this.versionNum;
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

	public List<ProductVersionFile> getProVersionFileList() {
		return proVersionFileList;
	}

	public void setProVersionFileList(List<ProductVersionFile> proVersionFileList) {
		this.proVersionFileList = proVersionFileList;
	}

	public String getPreposeVersionId() {
		return this.preposeVersionId;
	}

	public void setPreposeVersionId(String preposeVersionId) {
		this.preposeVersionId = preposeVersionId;
	}

	public String getVersionDescription() {
		return this.versionDescription;
	}

	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}

	public String getVersionType() {
		return this.versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public Date getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReleaseUser() {
		return this.releaseUser;
	}

	public String getReleaseUserName() {
		return CacheManagerUtil.getUserNameById(releaseUser);
	}

	public void setReleaseUser(String releaseUser) {
		this.releaseUser = releaseUser;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public int getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
}
