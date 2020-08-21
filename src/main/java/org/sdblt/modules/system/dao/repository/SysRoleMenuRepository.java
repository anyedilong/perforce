package org.sdblt.modules.system.dao.repository;

import org.sdblt.modules.system.domain.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * <br>
 * <b>功能：</b>SysRoleMenuRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, String> {

	@Query(value = "delete from sys_role_menu where role_id=?1 ", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteByRoleId(String roleId);

}
