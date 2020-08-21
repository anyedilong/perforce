package org.sdblt.modules.system.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.domain.BaseRoleDomain;

/**
 * 
 * <br>
 * <b>功能：</b>SysUserRoleEntity<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Entity
@Table(name = "sys_user_role")
public class SysUserRole extends BaseDomain {

	@Id
	private String id;// ID

	@Column(name = "role_id")
	private String roleId;// 角色ID

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private BaseRoleDomain role;// 机构

	@Column(name = "user_id")
	private String userId;// 用户ID

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BaseRoleDomain getRole() {
		return role;
	}

	public void setRole(BaseRoleDomain role) {
		this.role = role;
	}

}
