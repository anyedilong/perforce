package org.sdblt.modules.system.dao.repository;

import java.util.Date;
import java.util.List;

import org.sdblt.modules.system.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * <br>
 * <b>功能：</b>TeamRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
public interface SysUserRepository extends JpaRepository<SysUser, String> {

	/**
	 * 
	 * @Description 根据登录名获取用户信息
	 * @param username
	 * @return
	 * @author sen
	 * @Date 2017年2月15日 上午10:34:55
	 */
	SysUser findByUsername(String username);
	
	@Modifying
	@Transactional
	@Query("update SysUser u set u.status='3' where u.id in :ids")
	void batchUpdateForDel(@Param("ids")List ids);

	
	@Query(value = " select count(1) from SysUser u where u.status!='3' and u.username=:username ")
	int getUsernameCount(@Param("username") String username);
}
