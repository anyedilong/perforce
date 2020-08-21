package org.sdblt.modules.proxy.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.utils.CacheManagerUtil;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName Proxy
 * @Description 代理商实体类
 * @author yuanwentian
 * @Date 2017年3月24日 上午8:59:31
 * @version 1.0.0
 */
@Entity
@Table(name="t_vm_proxy")
public class Proxy extends BaseDomain{
	
	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -1265735768800074001L;
	@Id
	private String id;
	private String name;//代理商名称
	private String phoneNum;//代理商手机号
	private String type;//代理类型 0 全国  1 省  2 市 3县
	@Transient
	private String dictProxyType;//代理类型字典值
	private String province;//省
	private String city;//市
	private String county;//县
	private String address;//地址
	private String merchantsUser;//招商用户
	@JSONField(format="yyyy-MM-dd")
	private Date merchantsTime;//招商时间
	private String remarks;//备注
	@Column(updatable=false)
	private String status;//状态 1 正常 2冻结 3 删除
	@JSONField(format="yyyy-MM-dd")
	private Date createTime;//创建时间
	@Column(updatable=false)
	private String createUser;//创建人
	@JSONField(format="yyyy-MM-dd")
	private Date updateTime;//更新时间
	private String updateUser;//更新人
	@Override
	public String getId() {
		return id;
	}
	@Override
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

	public String getDictProxyType() {
		return CacheManagerUtil.getTextByValue("dictProxyType", type);
	}
	public String getProvince() {
		return province;
	}
	public String getProvinceName() {
		return CacheManagerUtil.getAreaNameByCode(getProvince());
	}
	public void setProvince(String province) {
		this.province = province;
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
	public String getCountyName() {
		return CacheManagerUtil.getAreaNameByCode(getCounty());
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getStatusName(){
		if("1".equals(getStatus())){
			return "正常";
		}else if("2".equals(getStatus())){
			return "冻结";
		}else if("3".equals(getStatus())){
			return "删除";
		}else{
			return "状态异常";
		}
	}
	
}
