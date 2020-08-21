package org.sdblt.modules.proxy.dao;

import java.util.List;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.proxy.dao.repository.ProxyRepository;
import org.sdblt.modules.proxy.domain.Proxy;
import org.sdblt.modules.proxy.dto.ProxyDto;
import org.sdblt.utils.StringUtils;

@Named
public class ProxyDao extends BaseDao<ProxyRepository,Proxy>{

	public void queryProxyList(ProxyDto proxyParam, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id,t.name,t.phone_num,t.type,t.merchants_user,t.merchants_time,t.status ");
		sql.append(" from T_VM_PROXY t ");
		sql.append(" where 1=1 and t.status='1' ");
		if(!StringUtils.isNull(proxyParam.getName())){
			sql.append(" and t.name like CONCAT('%',CONCAT(:name,'%')) ");
		}
		if(!StringUtils.isNull(proxyParam.getPhoneNum())){
			sql.append(" and t.phone_num like CONCAT('%',CONCAT(:phoneNum,'%')) ");
		}
		if(!StringUtils.isNull(proxyParam.getMerchantsUser())){
			sql.append(" and t.merchants_user like CONCAT('%',CONCAT(:merchantsUser,'%')) ");
		}
		if(!StringUtils.isNull(proxyParam.getType())){
			sql.append(" and t.type = :type ");
		}
		queryPageList(sql.toString(), proxyParam, page, ProxyDto.class);
	}

	public void batchUpdateForDel(List ids) {
		repository.batchUpdateForDel(ids);
	}

	public List<ProxyDto> getProxyDropDownList(ProxyDto proxyParam) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id,t.name from t_vm_proxy t where t.status = '1' ");
		return queryList(sql.toString(),proxyParam,ProxyDto.class);
	}

}
