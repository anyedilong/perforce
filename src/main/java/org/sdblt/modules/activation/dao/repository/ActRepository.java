package org.sdblt.modules.activation.dao.repository;


import org.sdblt.modules.product.domain.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public  interface ActRepository  extends  JpaRepository<ProductStock,String> {
	
	@Modifying
	@Query(value="update t_vm_product_stock a set a.status='60' where a.pro_num=:proNum and a.machine_code=:machineCode and a.status='50' ",nativeQuery=true)
	int updateact(@Param("proNum")String proNum,@Param("machineCode")String machineCode);
	
	@Modifying
	@Query(value="update t_vm_product_stock a set a.current_version=:versionNum where a.pro_num=:proNum",nativeQuery=true)
	@Transactional
    void updateversionid(@Param("versionNum")String versionNum,@Param("proNum")String proNum);
}
