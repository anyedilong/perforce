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
 * <br>
 * <b>功能：</b>SysRoleEntity<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseDomain {

	@Id
	private String id;// ID
	private String name;// 角色名称
	@Column(updatable = false)
	private String orgId;// 机构ID
	private String remarks;// 备注
	@Column(updatable = false)
	private String status;// 状态 1 正常 2冻结 3 删除
	@Column(updatable = false)
	private Date createTime;// 创建时间
	@Column(updatable = false)
	private String createUser;// 创建人
	private Date updateTime;// 更新时间
	private String updateUser;// 更新人

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

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
}
