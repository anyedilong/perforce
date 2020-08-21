package org.sdblt.modules.common.utils.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 
 * @ClassName Menu
 * @Description 菜单
 * @author sen
 * @Date 2017年2月20日 上午11:06:00
 * @version 1.0.0
 */
public class OrgCache implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;// ID

	private String name;// 机构名称
	private String orgCode;// 机构编码
	private String sortName;// 机构简称
	private int orgLevel;// 机构等级

	private String orgType;// 类型 1机构 2 部门 3 岗位
	private String parentId;// 上级机构
	private String parentIdAll;// 上级全部机构

	private String orderNum;// 排序号

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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public int getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(int orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIdAll() {
		return parentIdAll;
	}

	public void setParentIdAll(String parentIdAll) {
		this.parentIdAll = parentIdAll;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

}
