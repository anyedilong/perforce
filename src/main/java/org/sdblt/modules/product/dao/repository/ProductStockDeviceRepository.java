package org.sdblt.modules.product.dao.repository;

import org.sdblt.modules.product.domain.ProductStockDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @ClassName ProductStockRepository
 * @Description 产品 jpa配置类
 * @author sen
 * @Date 2017年3月15日 上午10:27:15
 * @version 1.0.0
 */
public interface ProductStockDeviceRepository extends JpaRepository<ProductStockDevice, String> {

	@Query(value = "delete from t_vm_product_stock_device where pro_stock_id=?1 ", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteByProId(String stockId);

	@Modifying()
	@Query("update ProductStockDevice t set t.deviceWarranty = ?2 where t.id = ?1 ")
	void updateWarranty(String stockDeviceId, int deviceWarranty);
}
