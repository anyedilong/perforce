package org.sdblt.modules.versionMgt.dao.repository;

import org.sdblt.modules.versionMgt.domain.VersionMgt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionMgtRepository extends JpaRepository<VersionMgt, String>  {
	

}
