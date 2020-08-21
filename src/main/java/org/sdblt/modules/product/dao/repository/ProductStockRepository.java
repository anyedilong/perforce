package org.sdblt.modules.product.dao.repository;

import java.util.List;

import org.sdblt.modules.product.domain.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @ClassName ProductStockRepository
 * @Description 产品 jpa配置类
 * @author sen
 * @Date 2017年3月15日 上午10:27:15
 * @version 1.0.0
 */
public interface ProductStockRepository extends JpaRepository<ProductStock, String> {
	@Modifying
	@Transactional
	@Query(value ="update t_vm_product_stock t set t.status='70' where t.status='10' and t.id in :ids",nativeQuery = true)
	void batchUpdateForDel(@Param("ids")List ids);

	@Query("from ProductStock ps where ps.proNum=?1 and machineCode=?2 ")
	ProductStock getByDevice(String proNum, String machineCode);
	
	@Modifying
	@Query(value="update t_vm_product_stock a set a.machine_code=:machineCode where a.pro_num=:proNum and a.status='10'  ",nativeQuery=true)
	@Transactional
	void updateproduct(@Param("proNum")String proNum,@Param("machineCode")String machineCode );

}
