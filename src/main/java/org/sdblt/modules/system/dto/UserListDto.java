package org.sdblt.modules.system.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.sdblt.common.page.PageModel;

/**
 * 
 * @ClassName MenuTreeDto
 * @Description 查询菜单TREE 结果DTO
 * @author sen
 * @Date 2017年2月20日 上午11:16:43
 * @version 1.0.0
 */
public class UserListDto implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String username;// 用户名
	private String name;// 姓名
	private String status;// 状态 1 正常 2 冻结 3 注销
	private String orgId;// 机构ID

	private String roleNames;// 角色名称
	private String includeSubFlg;// 包含下级

	private PageModel page;// 分页

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getIncludeSubFlg() {
		return includeSubFlg;
	}

	public void setIncludeSubFlg(String includeSubFlg) {
		this.includeSubFlg = includeSubFlg;
	}

}
