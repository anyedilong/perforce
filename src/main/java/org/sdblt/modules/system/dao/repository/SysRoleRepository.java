package org.sdblt.modules.system.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.sdblt.modules.system.domain.SysRole;

/**
 * 
 * <br>
 * <b>功能：</b>SysRoleRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface SysRoleRepository extends JpaRepository<SysRole, String> {

	@Modifying
	@Transactional
	@Query("update SysRole  set status='3' where id in :ids")
	void batchUpdateForDel(@Param("ids")List ids);
}
