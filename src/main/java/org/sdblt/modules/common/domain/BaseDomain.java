package org.sdblt.modules.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

@Inheritance(strategy = InheritanceType.JOINED) // 选择继承策略
public class BaseDomain implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
