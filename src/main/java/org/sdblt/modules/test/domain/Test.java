package org.sdblt.modules.test.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sdblt.modules.common.domain.BaseDomain;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "sys_test")
public class Test extends BaseDomain {

	@Id
	private String id;//主键
	private String userName;
	private String address;
	private String userCode;
	private int userAge;
	@Column(updatable=false)
	@JSONField(format="yyyy-MM-dd")
	private Date createTime;
	private String teTestName;

	public String getTeTestName() {
		return teTestName;
	}

	public void setTeTestName(String teTestName) {
		this.teTestName = teTestName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

}