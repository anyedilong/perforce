package org.sdblt.modules.proxy.service.impl;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Transient;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.proxy.dao.ProxyDao;
import org.sdblt.modules.proxy.domain.Proxy;
import org.sdblt.modules.proxy.dto.ProxyDto;
import org.sdblt.modules.proxy.service.ProxyService;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional(readOnly = true)
public class ProxyServiceImpl extends BaseServiceImpl<ProxyDao,Proxy> implements ProxyService{

	@Override
	public void queryProxyList(ProxyDto proxyParam, PageModel page) {
		dao.queryProxyList(proxyParam,page);
	}

	@Override
	@Transactional
	public void saveProxy(Proxy proxyParam) {
		dao.save(proxyParam);
	}

	@Override
	@Transactional
	public void batchUpdateForDel(List ids) {
		dao.batchUpdateForDel(ids);
	}

	@Override
	public List<ProxyDto> getProxyDropDownList(ProxyDto proxyParam) {
		return dao.getProxyDropDownList(proxyParam);
	}

}
