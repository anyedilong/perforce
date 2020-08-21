package org.sdblt.modules.versionMgt.domain;

import org.sdblt.utils.StringUtils;

public class ProducTreeInfo {

	private String id;// null
	private String typeName;// 产品分类名称
	private String parentId;// 上级产品分类ID
	private String code;// 编码
	private String orderNum;// 排序号
	private String isParent;// 是否是产品，产品类型，true；产品：false；

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrderNum() {
		return StringUtils.toString(orderNum);
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

}
