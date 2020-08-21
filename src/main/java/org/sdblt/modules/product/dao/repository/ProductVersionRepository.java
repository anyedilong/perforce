package org.sdblt.modules.product.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.sdblt.modules.product.domain.ProductVersion;

/**
 * 
 * <br>
 * <b>功能：</b>ProductVersionRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface ProductVersionRepository extends JpaRepository<ProductVersion, String> {
	
}
