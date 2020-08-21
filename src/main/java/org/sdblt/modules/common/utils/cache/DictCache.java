package org.sdblt.modules.common.utils.cache;

import java.io.Serializable;

import org.sdblt.utils.StringUtils;

/**
 * @ClassName DictCache
 * @Description 字典缓存
 * @author sen
 * @Date 2017年3月17日 下午4:46:49
 * @version 1.0.0
 */
public class DictCache implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;// 字典明细ID
	private String code;// 字典code
	private String dictId;// 字典ID
	private String text;// 字典明细 内容
	private String value;// 字典明细 值
	private int orderNum;// 排序号
	private String status;// 状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

		DictCache dict = (DictCache) obj;

		// 如果ID相同
		if (!StringUtils.isNull(getId()) && getId().equals(dict.getId())
				|| (!StringUtils.isNull(getCode()) && (getCode().equals(dict.getCode()))
						&& (!StringUtils.isNull(getValue()) && getValue().equals(dict.getValue())))) {
			return true;
		}

		return false;
	}

	public DictCache() {
	}

	public DictCache(String id) {
		this.id = id;
	}

}
