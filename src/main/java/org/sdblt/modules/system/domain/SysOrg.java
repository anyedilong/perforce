package org.sdblt.modules.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Clob;
import org.sdblt.modules.common.domain.BaseDomain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName SysOrg
 * @Description 机构
 * @author sen
 * @Date 2017年3月3日 上午10:47:59
 * @version 1.0.0
 */
@Entity
@Table(name = "sys_org")
public class SysOrg extends BaseDomain {

	@Id
	private String id;// ID

	private String name;// 机构名称
	private String orgCode;// 机构编码
	private String sortName;// 机构简称
	private int orgLevel;// 机构等级
	
	@Column(updatable = false)
	private String orgType;// 类型 1机构 2 部门 3 岗位
	@Column(updatable = false)
	private String parentId;// 上级机构
	@Column(updatable = false)
	private String parentIdAll;// 上级全部机构
	private String remarks;// 备注
	@Column(updatable = false)
	private String status;// 状态 1 正常 2冻结 3 删除
	@Column(updatable = false)
	private Date createTime;// 创建时间
	@Column(updatable = false)
	private String createUser;// 创建人
	
	private Date updateTime;// 更新时间
	private String updateUser;// 更新人

	private String orderNum;// 排序号

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public int getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(int orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIdAll() {
		return this.parentIdAll;
	}

	public void setParentIdAll(String parentIdAll) {
		this.parentIdAll = parentIdAll;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

}
