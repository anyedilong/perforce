package org.sdblt.modules.system.service;

import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.system.domain.Device;
import org.sdblt.modules.system.dto.DeviceListDto;

public interface DeviceService extends BaseService<Device>{

	void queryDeviceList(DeviceListDto deviceParam, PageModel page);

	void saveDevice(Device deviceParam);

	void batchUpdateForDel(List ids);

	/**
	 * @Description 根据设备类型，获取设备列表
	 * @param deviceType
	 * @return
	 * @author sen
	 * @Date 2017年3月23日 下午2:10:01
	 */
	List<DeviceListDto> getListByType(String deviceType);

}