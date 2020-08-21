package org.sdblt.modules.activation.service;

import java.util.List;

import org.sdblt.modules.product.domain.ProductVersion;
import org.sdblt.modules.system.domain.FileDomain;

public interface ActService {
	
	
	  boolean updateact(String proNum,String machineCode,String code);
	  
	  List<FileDomain> queryfile(String versionId);
	   void  updateversion(String versionNum,String proNum);
	   List<ProductVersion> queryversion(String proNum,String machineCode);

}
