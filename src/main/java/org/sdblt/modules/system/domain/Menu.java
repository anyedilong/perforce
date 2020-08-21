package org.sdblt.modules.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Clob;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.domain.BaseDomain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName Menu
 * @Description 菜单
 * @author sen
 * @Date 2017年2月20日 上午11:06:00
 * @version 1.0.0
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseDomain {

	@Id
	private String id;// ID
	@Column(updatable = false)
	private String type;// 类型 1菜单 2 操作
	private String url;// URL
	private String name;// 名称
	@Column(updatable = false)
	private String parentId;// 上级ID
	@Column(updatable = false)
	private String parentIdAll;// 上级ID
	private String handleType;// 操作类型 自定义
	private String icon;// 图标
	@Column(updatable = false)
	private int menuLevel;// 等级
	private String openType;// 窗口打开方式 1 普通 2打开新窗口 3 弹窗
	private String orderNum;// 排序号
	@Column(updatable = false)
	private String status;// 状态 1 正常 2冻结 3 删除
	@Column(updatable = false)
	private Date createTime;// 创建时间
	@Column(updatable = false)
	private String createUser;// 创建人
	private Date updateTime;// 更新时间
	private String updateUser;// 更新人

	private String dialogFlg;// 是否为弹出框
	private String height;// 高度
	private String width;// 宽度
	private String title;// 标题
	private String rowHandleFlg;// 是否为行操作

	@Transient
	private PageModel page;// 分页

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getOpenTyoe() {
		return openType;
	}

	public void setOpenTyoe(String openType) {
		this.openType = openType;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
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

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getDialogFlg() {
		return dialogFlg;
	}

	public void setDialogFlg(String dialogFlg) {
		this.dialogFlg = dialogFlg;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRowHandleFlg() {
		return rowHandleFlg;
	}

	public void setRowHandleFlg(String rowHandleFlg) {
		this.rowHandleFlg = rowHandleFlg;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	public String getParentIdAll() {
		return parentIdAll;
	}

	public void setParentIdAll(String parentIdAll) {
		this.parentIdAll = parentIdAll;
	}

}
