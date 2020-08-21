package org.sdblt.modules.test.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.test.domain.Test;
import org.sdblt.utils.validate.Validate;

public class TestDto extends BaseDomain implements Comparable<TestDto> {

	@Validate(name={"update","delete"},required=true)
	private String id;
	
	@Validate(name={"add"},required=true)
	private String userName;
	@Validate(required=true)
	private String address;
	private String userCode;
	private int userAge;
	private Date createTime;

	private Map<String, Object> paramMap;

	
	private List<Test> list;
	private Test test;
	
	//查询条件
	private String whereStr;
	
	
	public List<Test> getList() {
		return list;
	}

	public void setList(List<Test> list) {
		this.list = list;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
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

	@Override
	public int compareTo(TestDto o) {
		return 0;
	}

}