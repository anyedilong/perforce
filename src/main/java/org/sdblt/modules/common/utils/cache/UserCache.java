package org.sdblt.modules.common.utils.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UserCache implements Serializable {

	/**
	 * @Fields:serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;

	private String userId;// 用户ID
	private String userName;// 用户名称
	private String status;// 状态 1 正常 2 冻结 3 注销

	private String orgId;// 机构ID

	private List<MenuCache> menuNav;// 菜单导航
	private Map<String, MenuCache> menuMap;// 菜单map
	private Map<String, List<MenuCache>> handleMap;// 操作map

	// 机构tree
	private List<OrgCache> orgTree;

	// 管辖机构ID集合
	private List<String> mrgOrgList;

	private OrgCache org;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MenuCache> getMenuNav() {
		return menuNav;
	}

	public void setMenuNav(List<MenuCache> menuNav) {
		this.menuNav = menuNav;
	}

	public Map<String, MenuCache> getMenuMap() {
		return menuMap;
	}

	public void setMenuMap(Map<String, MenuCache> menuMap) {
		this.menuMap = menuMap;
	}

	public Map<String, List<MenuCache>> getHandleMap() {
		return handleMap;
	}

	public void setHandleMap(Map<String, List<MenuCache>> handleMap) {
		this.handleMap = handleMap;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<OrgCache> getOrgTree() {
		return orgTree;
	}

	public void setOrgTree(List<OrgCache> orgTree) {
		this.orgTree = orgTree;
	}

	public List<String> getMrgOrgList() {
		return mrgOrgList;
	}

	public void setMrgOrgList(List<String> mrgOrgList) {
		this.mrgOrgList = mrgOrgList;
	}

	public OrgCache getOrg() {
		return org;
	}

	public void setOrg(OrgCache org) {
		this.org = org;
	}

}
