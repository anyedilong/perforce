package org.sdblt.modules.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.product.dao.repository.ProductRepository;
import org.sdblt.modules.product.domain.Product;
import org.sdblt.modules.system.dao.repository.DeviceRepository;
import org.sdblt.modules.system.domain.Device;
import org.sdblt.modules.system.dto.DeviceListDto;
import org.sdblt.utils.StringUtils;

import oracle.net.aso.d;

@Named
public class DeviceDao extends BaseDao<DeviceRepository, Device>{

	public void queryDeviceList(DeviceListDto deviceParam, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id,t.device_name, ");
		sql.append(" t.device_code,t.device_model,t.device_maker, ");
		sql.append(" t.device_warranty,t.device_type,t.status ");
		sql.append(" from t_vm_device t where 1=1 and t.status='1' ");
		if(!StringUtils.isNull(deviceParam.getDeviceName())){
			sql.append(" and t.device_name like CONCAT('%',CONCAT(:deviceName,'%'))");
		}
		if(!StringUtils.isNull(deviceParam.getDeviceCode())){
			sql.append(" and t.device_code like CONCAT('%',CONCAT(:deviceCode,'%'))");
		}
		if(!StringUtils.isNull(deviceParam.getDeviceMaker())){
			sql.append(" and t.device_maker like CONCAT('%',CONCAT(:deviceMaker,'%'))");
		}
		if(!StringUtils.isNull(deviceParam.getDeviceType())){
			sql.append(" and t.device_type = :deviceType");
		}
		
		sql.append(" order by t.create_time desc ");
		
		queryPageList(sql.toString(), deviceParam, page, DeviceListDto.class);
	}

	public void batchUpdateForDel(List ids) {
		repository.batchUpdateForDel(ids);
	}

	public List<DeviceListDto> getListByType(String deviceType) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id,t.device_name, ");
		sql.append(" t.device_code,t.device_model,t.device_maker, ");
		sql.append(" t.device_warranty,t.device_type,t.status ");
		sql.append(" from t_vm_device t where 1=1 and t.status='1' ");
		sql.append(" and t.device_type=:deviceType");
		
		sql.append(" order by t.create_time desc ");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceType", deviceType);
		
		return queryList(sql.toString(), paramMap, DeviceListDto.class);
	}

}
