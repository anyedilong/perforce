package org.sdblt.modules.product.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.sdblt.modules.product.domain.ProductDevice;

/**
 * 
 * <br>
 * <b>功能：</b>ProductDeviceRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface ProductDeviceRepository extends JpaRepository<ProductDevice, String> {

	@Query(value = "delete from t_vm_product_device where pro_id=?1 ", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteByProId(String proId);
	
}
