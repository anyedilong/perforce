package org.sdblt.modules.system.dao.repository;

import java.util.List;

import org.sdblt.modules.system.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * <br>
 * <b>功能：</b>MenuRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface MenuRepository extends JpaRepository<Menu, String> {

	@Query("from Menu m where m.parentId=:parentId and m.name like :name and m.status !='3' order by m.orderNum ")
	Page<Menu> findByParentIdAndName(@Param("parentId")String parentId, @Param("name")String name, Pageable page);

	@Modifying
	@Transactional
	@Query("update Menu m set m.status='3' where m.id in :ids")
	void batchUpdateForDel(@Param("ids")List ids);

}
