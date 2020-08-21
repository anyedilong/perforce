package org.sdblt.modules.common.utils.cache;

import java.io.Serializable;

import org.sdblt.utils.StringUtils;

/**
 * @ClassName SysUserCache
 * @Description 系统用户缓存
 * @author sen
 * @Date 2017年3月17日 下午4:46:49
 * @version 1.0.0
 */
public class SysUserCache implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String username;// 用户名
	private String name;// 姓名

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object obj){
		
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		SysUserCache userCache = (SysUserCache) obj;
		
		// 如果ID相同
		if (!StringUtils.isNull(getId()) && getId().equals(userCache.getId())) {
			return true;
		}
		
		return false;
	}
	
}
