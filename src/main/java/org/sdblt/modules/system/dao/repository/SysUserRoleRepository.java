package org.sdblt.modules.system.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.sdblt.modules.system.domain.SysUserRole;

/**
 * 
 * <br>
 * <b>功能：</b>SysUserRoleRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, String> {

	@Query(value = "delete from sys_user_role where user_id=?1 ", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteUserRoleByUser(String userId);
	
}
