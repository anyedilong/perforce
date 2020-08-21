package org.sdblt.modules.system.domain;

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
import org.sdblt.modules.common.domain.BaseOrgDomain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * <br>
 * <b>功能：</b>SysUserOrgEntity<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Entity
@Table(name = "sys_user_org")
public class SysUserOrg extends BaseDomain {

	@Id
	private String id;// ID
	@Column(name = "org_id")
	private String orgId;// 机构ID
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
	@JoinColumn(name = "org_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private BaseOrgDomain org;// 机构

	@Column(name = "user_id")
	private String userId;// 用户ID

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BaseOrgDomain getOrg() {
		return org;
	}

	public void setOrg(BaseOrgDomain org) {
		this.org = org;
	}

}
