package org.sdblt.modules.system.domain;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.domain.BaseOrgDomain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName SysUser
 * @Description 系统用户
 * @author sen
 * @Date 2016年11月23日 下午4:29:21
 * @version 1.0.0
 */
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseDomain {
	@Id
	private String id;
	@Column(updatable = false)
	private String username;// 用户名
	private String name;// 姓名
	@Column(updatable = false,name="org_id")
	private String orgId;// 机构ID

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
	@JoinColumn(name = "org_id",  insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private BaseOrgDomain org;// 机构

	@Column(updatable = false)
	@JSONField(serialize=false)
	private String password;// 密码
	@Column(updatable = false)
	@JSONField(serialize=false)
	private String securityToken;// 授权码
	@Column(updatable = false)
	@JSONField(serialize=false)
	private String salt;// 盐值
	@Column(updatable = false)
	private String status;// 状态 1 正常 2 冻结 3 注销

	private String remarks;// 备注

	@Column(updatable = false)
	private Date createTime;// 创建时间
	@Column(updatable = false)
	private String createUser;// 创建人
	private Date updateTime;// 更新时间
	private String updateUser;// 更新人

	// 级联操作 若使用全部的方法可以使用all
	// @OneToMany(cascade = { CascadeType.REFRESH,
	// CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})

	// fetch的默认值是：如果是得到many的一方 默认的是延迟加载
	// mappedBy表示关系被维护端，是在TeamUser类总的哪一个属性维护的
	@OneToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<SysUserRole> userRoleList;// 角色

	@OneToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<SysUserOrg> mrgOrgList;// 管辖机构
	

	@Transient
	private String roleIds;//
	@Transient
	private String mrgOrgIds;// 管辖机构

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<SysUserRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<SysUserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getMrgOrgIds() {
		return mrgOrgIds;
	}

	public void setMrgOrgIds(String mrgOrgIds) {
		this.mrgOrgIds = mrgOrgIds;
	}

	public List<SysUserOrg> getMrgOrgList() {
		return mrgOrgList;
	}

	public void setMrgOrgList(List<SysUserOrg> mrgOrgList) {
		this.mrgOrgList = mrgOrgList;
	}

	public BaseOrgDomain getOrg() {
		return org;
	}

	public void setOrg(BaseOrgDomain org) {
		this.org = org;
	}
	

}
