package org.sdblt.modules.proxy.dto;

import java.util.Date;

import javax.persistence.Transient;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.utils.CacheManagerUtil;

import com.alibaba.fastjson.annotation.JSONField;

public class ProxyDto {
	private String id;
	private String name;//代理商名称
	private String phoneNum;//代理商手机号
	private String type;//代理类型
	@Transient
	private String dictProxyType;//代理类型字典值
	private String merchantsUser;//招商用户
	@JSONField(format="yyyy-MM-dd")
	private Date merchantsTime;//招商时间
	private PageModel page;// 分页
	private String status;//状态
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMerchantsUser() {
		return merchantsUser;
	}
	public void setMerchantsUser(String merchantsUser) {
		this.merchantsUser = merchantsUser;
	}
	public Date getMerchantsTime() {
		return merchantsTime;
	}
	public void setMerchantsTime(Date merchantsTime) {
		this.merchantsTime = merchantsTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDictProxyType() {
		return CacheManagerUtil.getTextByValue("dictProxyType", type);
	}
	public PageModel getPage() {
		return page;
	}
	public void setPage(PageModel page) {
		this.page = page;
	}
	
	
	
}
