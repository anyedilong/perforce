package org.sdblt.modules.versionMgt.dto;

import java.util.Date;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.domain.BaseDomain;

import com.alibaba.fastjson.annotation.JSONField;

public class VersionMgtDto extends BaseDomain {
		
	private String id;
	
	private String versionNum;         //产品版本
	
	private String proId;              //产品ID
	
	private String proName;  //产品名称
	
	private String proCode;  //产品编码
	
	private String preposeVersionId;   //前置版本ID
	
	private String preposeVersionNum;  //前置版本名称
	
	private String versionDescription; //版本说明
	
	private String versionType;        //类型 1全量 2 增量
	
	@JSONField(format ="yyyy-MM-dd")
	private Date releaseTime;        //发布时间
	
	private String releaseUser;        //发布人
	
	private String releaseUserName;        //发布人姓名
	
	private String status;             //状态 1保存2 发布
	
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;           //创建时间
	
	private String createUser;         //创建人
	
	@JSONField(format = "yyyy-MM-dd")
	private Date updateTime;           //更新时间
	
	private String updateUser;         //更新人
	
	private PageModel page;// 分页

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getPreposeVersionId() {
		return preposeVersionId;
	}

	public void setPreposeVersionId(String preposeVersionId) {
		this.preposeVersionId = preposeVersionId;
	}

	public String getVersionDescription() {
		return versionDescription;
	}

	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReleaseUser() {
		return releaseUser;
	}

	public void setReleaseUser(String releaseUser) {
		this.releaseUser = releaseUser;
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

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getPreposeVersionNum() {
		return preposeVersionNum;
	}

	public void setPreposeVersionNum(String preposeVersionNum) {
		this.preposeVersionNum = preposeVersionNum;
	}

	public String getReleaseUserName() {
		return releaseUserName;
	}

	public void setReleaseUserName(String releaseUserName) {
		this.releaseUserName = releaseUserName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	
	
	

}
