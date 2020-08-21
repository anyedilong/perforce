package org.sdblt.modules.system.dao.repository;

import org.sdblt.modules.system.domain.FileDomain;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * <br>
 * <b>功能：</b>FileUploadRepository<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
public interface FileUploadRepository extends JpaRepository<FileDomain, String> {

}
