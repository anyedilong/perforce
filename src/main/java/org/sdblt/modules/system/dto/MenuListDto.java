package org.sdblt.modules.system.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName MenuTreeDto
 * @Description 查询菜单TREE 结果DTO
 * @author sen
 * @Date 2017年2月20日 上午11:16:43
 * @version 1.0.0
 */
public class MenuListDto implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;// ID
	private String type;// 类型 1菜单 2 操作
	private int menuLevel;// 等级
	private String url;// URL
	private String name;// 名称
	private String icon;// 图标
	private String openTyoe;// 窗口打开方式 1 普通 2打开新窗口 3 弹窗
	private String orderNum;// 排序号
	private String parentId;//

	private List<MenuListDto> children;// 下级数据

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOpenTyoe() {
		return openTyoe;
	}

	public void setOpenTyoe(String openTyoe) {
		this.openTyoe = openTyoe;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public List<MenuListDto> getChildren() {
		return children;
	}

	public void setChildren(List<MenuListDto> children) {
		this.children = children;
	}

	public int getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}


}
