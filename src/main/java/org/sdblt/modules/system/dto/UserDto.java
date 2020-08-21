package org.sdblt.modules.system.dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable{
	private String UserName;//用户名称
	private String name;// 角色名称
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	
}
