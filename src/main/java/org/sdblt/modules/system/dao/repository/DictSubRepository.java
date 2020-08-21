package org.sdblt.modules.system.dao.repository;

import java.util.List;

import org.sdblt.modules.system.domain.DictSub;
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
 * <b>功能：</b>DictSubRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface DictSubRepository extends JpaRepository<DictSub, String> {

	
	@Query("from DictSub d where d.status!=3 ")
	Page<DictSub> findByDictId(@Param("dictId")String dictId, Pageable pageable);

	@Modifying
	@Transactional
	@Query("update DictSub ds set ds.status='3' where ds.id in :ids")
	void batchUpdateForDel(@Param("ids")List ids);
	
}
