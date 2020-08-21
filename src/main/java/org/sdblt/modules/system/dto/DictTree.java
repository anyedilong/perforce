package org.sdblt.modules.system.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.sdblt.common.page.PageModel;

/**
 * 
 * @ClassName DictListDto
 * @Description 字典列表DTO
 * @author sen
 * @Date 2017年2月20日 上午11:16:43
 * @version 1.0.0
 */
public class DictTree implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;// 字典名称
	private String parentId;// 上级
	private String code;// 字典code
	private String status;// 状态 1 正常 2冻结 3 删除

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDictName() {
		String statusName = "";
		if ("1".equals(status)) {
			statusName = "正常";
		} else if ("2".equals(status)) {
			statusName = "冻结";
		} else if ("3".equals(status)) {
			statusName = "删除";
		}
		String dictName = String.format("%s[%s][%s]", name, code, statusName);
		return dictName;
	}

}
