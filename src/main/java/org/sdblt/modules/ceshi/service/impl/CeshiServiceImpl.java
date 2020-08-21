package org.sdblt.modules.ceshi.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


import org.sdblt.modules.ceshi.dao.CeshiDao;
import org.sdblt.modules.ceshi.service.CeshiService;


@Named
public class CeshiServiceImpl implements CeshiService {

	 @Inject
	 private CeshiDao ceshiDao;
	
	@Override
	public boolean updateproduct(String proNum, String machineCode) {
	
	      return  ceshiDao.updateproduct(proNum, machineCode);
	}

}
