package org.sdblt.modules.activation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.modules.activation.dao.repository.ActRepository;
import org.sdblt.modules.common.repository.BaseDao;

import org.sdblt.modules.product.dao.repository.ProductStockDeviceRepository;

import org.sdblt.modules.product.dao.repository.ProductStockRepository;
import org.sdblt.modules.product.domain.ProductStock;
import org.sdblt.modules.product.domain.ProductStockDevice;

import org.sdblt.modules.system.domain.Device;
import org.sdblt.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;


@Named
public class ActDao extends BaseDao<ActRepository, ProductStock> {

	@Inject
	private ActRepository actRepository;
	
	@Inject
	private ProductStockRepository stockRepository;
	@Inject
	private ProductStockDeviceRepository stockDeviceRepository;
	
	
	public ProductStock  queryid(String proNum,String machineCode){
		Map<String,String> paramMap=new HashMap<String,String>();
		    paramMap.put("proNum",proNum);
		    paramMap.put("machineCode",machineCode);
		   StringBuffer sql=new StringBuffer();
		   sql.append("select a.id from t_vm_product_stock a");
		   sql.append(" where 1=1 and a.pro_num=:proNum and a.machine_code=:machineCode");
		   return queryOne(sql.toString(), paramMap,ProductStock.class);
	}
   
    
	@Transactional
	public boolean updateact(String proNum, String machineCode) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("proNum", proNum);
		paramMap.put("machineCode", machineCode);

		//根据ID查询库存状态
		String status = getStatusByDevice(proNum, machineCode);
		if(StringUtils.isNull(status)){
			//
			throw new CommonException(20001, "设备不存在");
		}else{
			if("60".equals(status)){
				throw new CommonException(20003, "设备已经激活，不需要重复激活");
			}else if(!"50".equals(status)){
				throw new CommonException(20002, "设备未出库，不允许激活");
			}else{
				//修改设备 保质期时间
				ProductStock productStock = stockRepository.getByDevice(proNum, machineCode);
				List<ProductStockDevice> proStockDeviceList = productStock.getProStockDeviceList();
				if(null != proStockDeviceList && proStockDeviceList.size() > 0){
					for (ProductStockDevice productStockDevice : proStockDeviceList) {
						String stockDeviceId = productStockDevice.getId();
						
						int deviceWarranty = 0;
						Device device = productStockDevice.getDevice();
						if(null != device){
							deviceWarranty = device.getDeviceWarranty();
						}
						//修改保质期
						stockDeviceRepository.updateWarranty(stockDeviceId,deviceWarranty);
						
					}
				}
				
				
				int count = actRepository.updateact(proNum, machineCode);
				if(count > 0){
					return true;
				}
			}
		}
		return false;
	}
   
	@Transactional
	public void updateversionid(String versionNum, String proNum) {
		actRepository.updateversionid(versionNum, proNum);
	}
	
	/**
	 * @Description 根据设备查询状态
	 * @param proNum
	 * @param machineCode
	 * @return
	 * @author sen
	 * @Date 2017年4月14日 上午11:26:48
	 */
	public String getStatusByDevice(String proNum, String machineCode){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("proNum", proNum);
		paramMap.put("machineCode", machineCode);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.status ");
		sql.append(" from t_vm_product_stock a");
		sql.append("    where 1=1 ");
		sql.append(" and a.pro_num=:proNum and a.machine_code=:machineCode");
		return queryOne(sql.toString(), paramMap, String.class);
	}

}
