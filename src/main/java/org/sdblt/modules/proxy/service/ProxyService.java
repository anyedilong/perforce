package org.sdblt.modules.proxy.service;


import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.proxy.domain.Proxy;
import org.sdblt.modules.proxy.dto.ProxyDto;

public interface ProxyService extends BaseService<Proxy>{

	void queryProxyList(ProxyDto proxyParam, PageModel page);

	void saveProxy(Proxy proxyParam);

	void batchUpdateForDel(List ids);

	List<ProxyDto> getProxyDropDownList(ProxyDto proxyParam);

}
