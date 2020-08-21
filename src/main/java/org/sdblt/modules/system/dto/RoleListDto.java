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
public class RoleListDto implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;// 用户名
	private String remarks;// 备注
	private String status;// 状态 1 正常 2 冻结 3 注销

	private String orgId;

	private PageModel page;// 分页

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

}
