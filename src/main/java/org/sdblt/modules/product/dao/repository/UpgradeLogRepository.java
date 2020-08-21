package org.sdblt.modules.product.dao.repository;

import org.sdblt.modules.product.domain.UpgradeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpgradeLogRepository extends JpaRepository<UpgradeLog, String>{
	
}
