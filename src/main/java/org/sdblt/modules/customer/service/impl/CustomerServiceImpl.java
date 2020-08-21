package org.sdblt.modules.customer.service.impl;

import java.util.List;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.customer.dao.CustomerDao;
import org.sdblt.modules.customer.domain.Customer;
import org.sdblt.modules.customer.dto.CustomerListDto;
import org.sdblt.modules.customer.service.CustomerService;
import org.sdblt.modules.proxy.dto.ProxyDto;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional(readOnly = true)
public class CustomerServiceImpl extends BaseServiceImpl<CustomerDao, Customer> implements CustomerService{

	@Override
	public void getList(CustomerListDto customerParam, PageModel page) {
		dao.getList(customerParam,page);
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customerParam) {
		dao.save(customerParam);
	}

	@Override
	public void batchUpdateForDel(List ids) {
		dao.batchUpdateForDel(ids);
	}

	@Override
	public List<CustomerListDto> getCustomerDropDownList(CustomerListDto customerList) {
		return dao.getCustomerDropDownList(customerList);
	}

}
