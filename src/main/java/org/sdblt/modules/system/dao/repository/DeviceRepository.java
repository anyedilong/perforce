package org.sdblt.modules.system.dao.repository;

import java.util.List;

import org.sdblt.modules.product.domain.Product;
import org.sdblt.modules.system.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceRepository extends JpaRepository<Device, String>{

	@Modifying
	@Transactional
	@Query(value ="update t_vm_device t set t.status='3' where t.id in :ids",nativeQuery = true)
	void batchUpdateForDel(@Param("ids")List ids);

}
