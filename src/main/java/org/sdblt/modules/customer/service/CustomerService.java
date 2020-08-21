package org.sdblt.modules.customer.service;

import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.customer.domain.Customer;
import org.sdblt.modules.customer.dto.CustomerListDto;
import org.sdblt.modules.proxy.dto.ProxyDto;

public interface CustomerService extends BaseService<Customer>{

	void getList(CustomerListDto customerParam, PageModel page);

	void saveCustomer(Customer customerParam);

	void batchUpdateForDel(List ids);
	
	List<CustomerListDto> getCustomerDropDownList(CustomerListDto customerList);
}
