package org.sdblt.modules.system.service.impl;

import java.util.List;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.product.dao.ProductDao;
import org.sdblt.modules.product.domain.Product;
import org.sdblt.modules.product.service.ProductService;
import org.sdblt.modules.system.dao.DeviceDao;
import org.sdblt.modules.system.domain.Device;
import org.sdblt.modules.system.dto.DeviceListDto;
import org.sdblt.modules.system.service.DeviceService;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional(readOnly = true)
public class DeviceServiceImpl extends BaseServiceImpl<DeviceDao, Device> implements DeviceService{

	@Override
	public void queryDeviceList(DeviceListDto deviceParam, PageModel page) {
		// TODO Auto-generated method stub
		dao.queryDeviceList(deviceParam,page);
	}

	@Override
	@Transactional
	public void saveDevice(Device deviceParam) {
		dao.save(deviceParam);
	}

	@Override
	@Transactional
	public void batchUpdateForDel(List ids) {
		// TODO Auto-generated method stub
		dao.batchUpdateForDel(ids);
	}

	@Override
	public List<DeviceListDto> getListByType(String deviceType) {
		return dao.getListByType(deviceType);
	}

}
