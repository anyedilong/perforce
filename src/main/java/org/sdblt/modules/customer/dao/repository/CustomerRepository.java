package org.sdblt.modules.customer.dao.repository;

import java.util.List;

import org.sdblt.modules.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer,String>{

	@Modifying
	@Transactional
	@Query(value = "update t_vm_customer t set t.status='3' where t.id in :ids",nativeQuery = true)
	void batchUpdateForDel(@Param("ids")List ids);

}
