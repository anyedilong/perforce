package org.sdblt.modules.customer.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.proxy.domain.Proxy;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.utils.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "t_vm_customer")
public class Customer extends BaseDomain {
	@Id
	private String id;
	private String name;// 客户名称
	private String phoneNum;// 客户手机号
	private String province;// 省
	private String city;// 市
	private String county;// 县区
	private String town;// 镇
	private String village;// 村
	private String source;// 数据来源 1代理商客户 2 直接客户
	private String address;// 地址
	private String remarks;// 备注
	@Column(name="proxy_id")
	private String proxyId;// 代理商id
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "proxy_id", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Proxy proxy;//代理商
	@Column(name="sell_user")
	private String sellUser;// 销售商id
	private String status;// 状态 1 正常 2冻结 3 删除
	@Column(updatable = false)
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;// 创建时间
	@Column(updatable = false)
	private String createUser;// 创建人
	@JSONField(format = "yyyy-MM-dd")
	private Date updateTime;// 创建时间
	private String updateUser;// 更新人

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceName() {
		return CacheManagerUtil.getAreaNameByCode(getProvince());
	}

	public String getCity() {
		return city;
	}

	public String getCityName() {
		return CacheManagerUtil.getAreaNameByCode(getCity());
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountyName() {
		return CacheManagerUtil.getAreaNameByCode(getCounty());
	}

	public String getTown() {
		return town;
	}
	
	public String getTownName(){
		return CacheManagerUtil.getAreaNameByCode(getTown());
	}
	
	public void setTown(String town) {
		this.town = town;
	}

	public String getVillage() {
		return village;
	}

	public String getVillageName(){
		return CacheManagerUtil.getAreaNameByCode(getVillage());
	}
	
	public void setVillage(String village) {
		this.village = village;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProxyId() {
		return proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}


	public Proxy getProxy() {
		return proxy;
	}

	public String getSellUser() {
		return sellUser;
	}

	public void setSellUser(String sellUser) {
		this.sellUser = sellUser;
	}

	/**
	 *
	 * @Description  获取销售商名称
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月30日 上午8:57:36
	 */
	public String getSellUserName() {
		return CacheManagerUtil.getUserNameById(getSellUser());
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getSourceName() {
		if ("1".equals(getSource())) {
			return "代理商客户";
		} else if ("2".equals(getSource())) {
			return "直接客户";
		} else {
			return "";
		}

	}

	public String getStatusName() {
		if ("1".equals(getStatus())) {
			return "正常";
		} else if ("2".equals(getStatus())) {
			return "冻结";
		} else if ("3".equals(getStatus())) {
			return "已删除";
		} else {
			return "";
		}
	}

}
