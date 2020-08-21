package org.sdblt.modules.system.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.sdblt.modules.system.domain.Dict;

/**
 * 
 * <br>
 * <b>功能：</b>DictRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
public interface DictRepository extends JpaRepository<Dict, String> {

	@Modifying
	@Transactional
	@Query("update Dict d set d.status='3' where d.id=:id")
	void updateForDel(@Param("id") String id);

	@Query("select t.code from Dict t where t.id=:id")
	String findCodeById(@Param("id")String id);

}
