package org.sdblt.modules.product.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.utils.CacheManagerUtil;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName ProductListDto
 * @Description 产品列表
 * @author sen
 * @Date 2017年2月20日 上午11:16:43
 * @version 1.0.0
 */
public class ProductListDto implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 产品名称
	 */
	private String proName;
	/**
	 * 产品编码
	 */
	
	private String deviceNum;// '设备编号'
	
	private String proCode;
	/**
	 * 产品分类ID 产品表
	 */
	private String proType;
	/**
	 * 产品立项时间
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date proApprovalTime;
	/**
	 * 产品定型时间
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date proFinalTime;
	/**
	 * 产品投产时间
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date proPutTime;
	/**
	 * 产品软件质保时间（月）
	 */
	private int proSoftwareWarranty;
	/**
	 * 产品库存
	 */
	private int proStork;
	/**
	 * 最新版本
	 */
	private String proNewVersion;
	/**
	 * 最新全量版本
	 */
	private String proNewFullVersion;

	/**
	 * 状态 1 草稿 2 发布 3 撤销 4 删除
	 */
	private String status;
	/**
	 * 发布时间
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date releaseTime;
	/**
	 * 发布人
	 */
	private String releaseUser;
	/**
	 * 创建时间
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 更新时间
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;

	private PageModel page;
	
	private String deviceType;
	private String typeName;// 产品分类名称
	private String code;// 编码
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public Date getProApprovalTime() {
		return proApprovalTime;
	}

	public void setProApprovalTime(Date proApprovalTime) {
		this.proApprovalTime = proApprovalTime;
	}

	public Date getProFinalTime() {
		return proFinalTime;
	}

	public void setProFinalTime(Date proFinalTime) {
		this.proFinalTime = proFinalTime;
	}

	public Date getProPutTime() {
		return proPutTime;
	}

	public void setProPutTime(Date proPutTime) {
		this.proPutTime = proPutTime;
	}

	public int getProSoftwareWarranty() {
		return proSoftwareWarranty;
	}

	public void setProSoftwareWarranty(int proSoftwareWarranty) {
		this.proSoftwareWarranty = proSoftwareWarranty;
	}

	public int getProStork() {
		return proStork;
	}

	public void setProStork(int proStork) {
		this.proStork = proStork;
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

	public String getCreateUserName() {
		return CacheManagerUtil.getUserNameById(createUser);
	}
	
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	public String getProNewVersion() {
		return proNewVersion;
	}

	public void setProNewVersion(String proNewVersion) {
		this.proNewVersion = proNewVersion;
	}

	public String getProNewFullVersion() {
		return proNewFullVersion;
	}

	public void setProNewFullVersion(String proNewFullVersion) {
		this.proNewFullVersion = proNewFullVersion;
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
	
	public String getReleaseUserName() {
		return CacheManagerUtil.getUserNameById(releaseUser);
	}
	
	public String getProTypeName(){
		return CacheManagerUtil.getProTypeNameById(proType);
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserName() {
		return CacheManagerUtil.getUserNameById(updateUser);
	}
	
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}
