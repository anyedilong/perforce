package org.sdblt.modules.common.utils;

import java.io.Serializable;
import java.util.Date;

import org.sdblt.utils.StringUtils;

public class CookieUser implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public CookieUser() {

	}

	public CookieUser(String username, String securityToken) {
		this.username = username;
		this.securityToken = securityToken;
	}

	private String username;
	private String securityToken;//
	private String status;// 状态 1 正常 2 冻结 3 注销

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
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

		CookieUser cookieUser = (CookieUser) obj;
		if (StringUtils.toString(getUsername()).equals(cookieUser.getUsername())
				&& StringUtils.toString(getSecurityToken()).equals(cookieUser.getSecurityToken())) {
			return true;
		}

		return false;
	}

}
