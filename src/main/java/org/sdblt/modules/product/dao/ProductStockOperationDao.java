package org.sdblt.modules.product.dao;

import javax.inject.Named;

import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.product.dao.repository.ProductStockOperationRepository;
import org.sdblt.modules.product.domain.ProductStockOperation;

@Named
public class ProductStockOperationDao extends BaseDao<ProductStockOperationRepository,ProductStockOperation>{

}
