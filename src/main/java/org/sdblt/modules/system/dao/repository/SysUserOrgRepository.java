package org.sdblt.modules.system.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.sdblt.modules.system.domain.SysUserOrg;

/**
 * 
 * <br>
 * <b>功能：</b>SysUserOrgRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface SysUserOrgRepository extends JpaRepository<SysUserOrg, String> {

	@Query(value = "delete from sys_user_org where user_id=?1 ", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteMrgOrgByUser(String userId);
	
}
