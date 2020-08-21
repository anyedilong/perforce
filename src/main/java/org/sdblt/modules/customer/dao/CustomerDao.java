package org.sdblt.modules.customer.dao;

import java.util.List;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.customer.dao.repository.CustomerRepository;
import org.sdblt.modules.customer.domain.Customer;
import org.sdblt.modules.customer.dto.CustomerListDto;
import org.sdblt.modules.proxy.dao.repository.ProxyRepository;
import org.sdblt.modules.proxy.domain.Proxy;
import org.sdblt.modules.proxy.dto.ProxyDto;
import org.sdblt.utils.StringUtils;

@Named
public class CustomerDao extends BaseDao<CustomerRepository,Customer>{

	public void getList(CustomerListDto customerParam, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id,t.name,t.phone_num,t.source,t.status  ");
		sql.append(" from t_vm_customer t ");
		sql.append(" where 1=1 and t.status='1' ");
		if(!StringUtils.isNull(customerParam.getName())){
			sql.append(" and t.name like CONCAT('%',CONCAT(:name,'%')) ");
		}
		if(!StringUtils.isNull(customerParam.getPhoneNum())){
			sql.append(" and t.phone_num like CONCAT('%',CONCAT(:phoneNum,'%')) ");
		}
		if(!StringUtils.isNull(customerParam.getSource())){
			sql.append(" and t.source = :source ");
		}
		queryPageList(sql.toString(), customerParam, page, CustomerListDto.class);
		System.out.println("sql"+sql);
	}

	public void batchUpdateForDel(List ids) {
		repository.batchUpdateForDel(ids);
	}
	public List<CustomerListDto> getCustomerDropDownList(CustomerListDto customerList) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id,t.name from t_vm_customer t where t.status = '1' ");
		return queryList(sql.toString(),customerList,CustomerListDto.class);
	}
	
}
