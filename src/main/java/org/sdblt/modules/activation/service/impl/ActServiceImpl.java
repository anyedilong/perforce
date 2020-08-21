package org.sdblt.modules.activation.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.modules.activation.dao.ActDao;

import org.sdblt.modules.activation.dao.ProductVersionDao;
import org.sdblt.modules.activation.dto.ActDto;
import org.sdblt.modules.activation.service.ActService;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.product.dao.ProductStockOperationDao;
import org.sdblt.modules.product.domain.ProductStock;
import org.sdblt.modules.product.domain.ProductStockOperation;
import org.sdblt.modules.product.domain.ProductVersion;
import org.sdblt.modules.system.dao.FileUploadDao;
import org.sdblt.modules.system.domain.FileDomain;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.springframework.transaction.annotation.Transactional;

@Named
public class ActServiceImpl implements ActService {
   
	@Inject
	private ActDao  actDao; 
	
	@Inject
	private FileUploadDao  fileUploadDao;
	
	@Inject
	private ProductVersionDao  productVersionDao; 
	@Inject
	private ProductStockOperationDao  productStockOperationDao; 
	
	

	
	
	@Override
	@Transactional
	public boolean updateact(String proNum, String machineCode,String code) {
		String actcode=CacheUtils.get(CacheEmun.COMM_CACHE,proNum,String.class);
		if(!code.equals(actcode)){
			 throw new CommonException(20001,"激活码不匹配或已过期");
		}else{
			boolean pd=actDao.updateact(proNum, machineCode);
			if(pd==true){
				    ProductStock productStock=actDao.queryid(proNum,machineCode);
				ProductStockOperation operation=new ProductStockOperation();
				operation.setOperationType("60");
				operation.setProStockId(productStock.getId());
				Date date=new Date();
				operation.setCreateTime(date);
				operation.setOperationTime(date);
				  productStockOperationDao.save(operation);
				  return true;
			}else{
				return false;
			}
		
		}
	
	}

	@Override
	public  List<FileDomain> queryfile(String versionId) {
		
		return fileUploadDao.queryfile(versionId);
	}

	@Override
	@Transactional
	public void updateversion(String versionNum,String proNum) {
		  boolean bd=productVersionDao.getid(versionNum);
		if(!bd){
			throw new CommonException(20002,"版本不存在不能进行更新");
		}else{
		actDao.updateversionid(versionNum,proNum);	
		}
	}

	@Override
	public List<ProductVersion> queryversion(String proNum, String machineCode) {
	              
		return productVersionDao.queryversion(proNum,machineCode);
	}

}
