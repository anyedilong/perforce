package org.sdblt.modules.customer.dto;

import java.io.Serializable;

import org.sdblt.common.page.PageModel;

public class CustomerListDto implements Serializable{

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -1791754346433435487L;

	private String id;
	private String name;//客户姓名
	private String phoneNum;//客户手机号
	private String source;//数据来源  1代理商客户  2 直接客户
	private String status;//状态 1 正常 2冻结 3 删除
	private PageModel page;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public PageModel getPage() {
		return page;
	}
	public void setPage(PageModel page) {
		this.page = page;
	}
	
	
}
