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
 * <b>功能：</b>SysRoleMenuEntity<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Entity
@Table(name = "sys_role_menu")
public class SysRoleMenu extends BaseDomain {

	@Id
	private String id;// ID
	private String roleId;// 角色ID
	private String menuId;// 菜单ID

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

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
