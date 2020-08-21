package org.sdblt.modules.proxy.dao.repository;

import java.util.List;

import org.sdblt.modules.proxy.domain.Proxy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProxyRepository extends JpaRepository<Proxy,String>{

	@Modifying
	@Transactional
	@Query(value ="update t_vm_proxy t set t.status='3' where t.id in :ids",nativeQuery = true)
	void batchUpdateForDel(@Param("ids")List ids);

}
