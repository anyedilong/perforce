package org.sdblt.modules.product.dao.repository;


import org.sdblt.modules.product.domain.ProductStockOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockOperationRepository extends JpaRepository<ProductStockOperation, String>{
	
}
