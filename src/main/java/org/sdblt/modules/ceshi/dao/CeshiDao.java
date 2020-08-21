package org.sdblt.modules.ceshi.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.product.dao.repository.ProductStockRepository;
import org.sdblt.modules.product.domain.ProductStock;
import org.springframework.transaction.annotation.Transactional;

@Named
public class CeshiDao extends BaseDao<ProductStockRepository,ProductStock> {
	
	@Inject
	private ProductStockRepository ceshiRepository;
	
	@Transactional
	public  boolean updateproduct(String proNum,String machineCode){
		Map<String,String>  paramMap=new HashMap<String,String>();
		paramMap.put("proNum",proNum);
	//	paramMap.put("machineCode",machineCode);
		StringBuffer sql=new StringBuffer();
		sql.append("select count(1) from t_vm_product_stock a ");
		sql.append(" where a.status='10' and a.pro_num=:proNum");
		int count=queryOne(sql.toString(),paramMap,Integer.class);
		if(count==1){
			ceshiRepository.updateproduct(proNum, machineCode);  
			return true;  
		}else{
			throw new CommonException(20001,"产品库存编号不存在");
			
		}
		
	}

}
