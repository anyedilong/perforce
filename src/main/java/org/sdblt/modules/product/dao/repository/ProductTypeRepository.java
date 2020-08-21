package org.sdblt.modules.product.dao.repository;

import java.util.List;

import org.sdblt.modules.product.domain.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * <br>
 * <b>功能：</b>ProductTypeRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface ProductTypeRepository extends JpaRepository<ProductType, String> {

	@Modifying
	@Transactional
	@Query("update ProductType pt set pt.status='3' where pt.id in :ids")
	void batchUpdateForDel(@Param("ids")List ids);
	
}
