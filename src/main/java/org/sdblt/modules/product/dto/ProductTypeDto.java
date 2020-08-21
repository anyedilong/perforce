package org.sdblt.modules.product.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.sdblt.common.page.PageModel;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName ProductTypeDto
 * @Description 产品类型
 * @author sen
 * @Date 2017年3月20日 下午2:44:23
 * @version 1.0.0
 */
public class ProductTypeDto implements Serializable {

	private String id;// null
	private String typeName;// 产品分类名称
	private String parentId;// 上级产品分类ID
	private String code;// 编码
	private String remarks;// 备注
	private String status;// 状态
	private String orderNum;// 排序号

	private PageModel page;// 分页

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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

}
