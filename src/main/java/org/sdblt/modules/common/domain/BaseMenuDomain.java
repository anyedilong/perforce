package org.sdblt.modules.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Clob;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.domain.BaseDomain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName Menu
 * @Description 菜单
 * @author sen
 * @Date 2017年2月20日 上午11:06:00
 * @version 1.0.0
 */
@Entity
@Table(name = "sys_menu")
public class BaseMenuDomain extends BaseDomain {

	@Id
	private String id;// ID
	private String name;// 名称

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

}
