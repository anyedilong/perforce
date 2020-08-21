package org.sdblt.modules.product.dao.repository;

import java.util.List;

import org.sdblt.modules.product.domain.Product;
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
 * @ClassName ProductRepository
 * @Description 产品 jpa配置类
 * @author sen
 * @Date 2017年3月15日 上午10:27:15
 * @version 1.0.0
 */
public interface ProductRepository extends JpaRepository<Product, String> {


}
