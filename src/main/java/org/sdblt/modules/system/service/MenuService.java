package org.sdblt.modules.system.service;

import java.util.List;
import java.util.Map;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.system.domain.Menu;

/**
 * 
 * <br>
 * <b>功能：</b>MenuService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface MenuService extends BaseService<Menu>{

	/**
	 * 
	 * @Description 分页查询下级菜单  功能  
	 * @param paramMenu
	 * @param page
	 * @return
	 * @author sen
	 * @Date 2017年2月22日 上午10:45:48
	 */
	void queryMenuList(String parentId, String menuName, PageModel page);

	/**
	 * 
	 * @Description 保存菜单
	 * @author sen
	 * @param menu 
	 * @return 
	 * @Date 2017年2月24日 下午2:24:56
	 */
	Map saveMenuAndHandle(Menu menu);

	/**
	 * 
	 * @Description 获取菜单树
	 * @return
	 * @author sen
	 * @Date 2017年2月25日 下午2:29:42
	 */
	List<Menu> getMenuTree();

	/**
	 * 
	 * @Description 批量删除
	 * @param ids
	 * @author sen
	 * @Date 2017年3月1日 下午4:30:08
	 */
	void batchUpdateForDel(List ids);

}
