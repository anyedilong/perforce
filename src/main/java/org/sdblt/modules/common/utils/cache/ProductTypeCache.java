package org.sdblt.modules.common.utils.cache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Clob;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.utils.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName ProductTypeCache
 * @Description 产品分类缓存
 * @author sen
 * @Date 2017年3月20日 下午1:50:03
 * @version 1.0.0
 */
public class ProductTypeCache implements Serializable {

	private String id;// null
	private String typeName;// 产品分类名称
	private String parentId;// 上级产品分类ID
	private String code;// 编码
	private String orderNum;// 排序号

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
		if(StringUtils.isNull(orderNum)){
			return "0";
		}
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public ProductTypeCache(){}
	
	public ProductTypeCache(String id){
		this.id = id;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		ProductTypeCache proType = (ProductTypeCache) obj;

		// 如果ID相同
		if (!StringUtils.isNull(getId()) && getId().equals(proType.getId())) {
			return true;
		}

		return false;
	}

}
