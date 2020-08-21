package org.sdblt.modules.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.common.utils.cache.MenuCache;
import org.sdblt.modules.system.dao.repository.MenuRepository;
import org.sdblt.modules.system.domain.Menu;
import org.sdblt.modules.test.dto.TestDto;
import org.springframework.data.domain.Page;

/**
 * 
 * <br>
 * <b>功能：</b>MenuDao<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Named
public class MenuDao extends BaseDao<MenuRepository, Menu> {

	public void queryMenuList(String parentId, String menuName, PageModel page) {

		Page<Menu> rs = (Page<Menu>) repository.findByParentIdAndName(parentId, "%" + menuName + "%",
				page.getPageable());
		page.setPageData(rs);
	}

	public String getMaxOrderByParent(String parentId) {
		String sql = " select max(m.order_num) from sys_menu m where m.parent_id=:parentId ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentId", parentId);
		return queryOne(sql, paramMap, String.class);
	}

	public List<Menu> getMenuTree() {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select m.id         id, ")
			.append("        m.type       type, ")
			.append("        m.url       url, ")
			.append("        m.name       name, ")
			.append("        m.parent_id  parentId, ")
			.append("        m.handle_type  handleType, ")
			.append("        m.icon  icon, ")
			.append("        m.menu_level menuLevel, ")
			.append("        m.open_type openType, ")
			.append("        m.order_num orderNum, ")
			.append("        m.status status, ")
			.append("        m.dialog_flg       dialogFlg, ")
			.append("        m.height       height, ")
			.append("        m.width  width, ")
			.append("        m.title  title, ")
			.append("        m.row_handle_flg  rowHandleFlg ")
			
			.append("   from sys_menu m ")
			.append("  where m.type = '1' and m.status !='3' ")
			.append("  start with m.parent_id = '0'  and m.status !='3'  ")
			.append(" connect by prior m.id = m.parent_id ")
			.append("  order SIBLINGS BY m.order_num ");
		
		return queryList(sql.toString(), null, Menu.class);
	}

	public List<MenuCache> getAllMenuList() {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select m.id         id, ")
			.append("        m.type       type, ")
			.append("        m.url       url, ")
			.append("        m.name       name, ")
			.append("        m.parent_id  parentId, ")
			.append("        m.parent_id_all  parentIdAll, ")
			.append("        m.handle_type  handleType, ")
			.append("        m.icon  icon, ")
			.append("        m.menu_level menuLevel, ")
			.append("        m.open_type openType, ")
			.append("        m.order_num orderNum, ")
			.append("        m.status status, ")
			.append("        m.dialog_flg       dialogFlg, ")
			.append("        m.height       height, ")
			.append("        m.width  width, ")
			.append("        m.title  title, ")
			.append("        m.row_handle_flg  rowHandleFlg ")
			
			.append("   from sys_menu m ")
			.append("  where m.type = '1' and  m.status='1' ")
			.append("  start with m.parent_id = '0' and  m.status='1' ")
			.append(" connect by prior m.id = m.parent_id ")
			.append("  order SIBLINGS BY m.order_num ");
		
		return queryList(sql.toString(), null, MenuCache.class);
	}

	public List<MenuCache> getAllMenuAndHandleList() {

		StringBuffer sql = new StringBuffer();
		sql.append(" select m.id         id, ")
			.append("        m.type       type, ")
			.append("        m.url       url, ")
			.append("        m.name       name, ")
			.append("        m.parent_id  parentId, ")
			.append("        m.parent_id_all  parentIdAll, ")
			.append("        m.handle_type  handleType, ")
			.append("        m.icon  icon, ")
			.append("        m.menu_level menuLevel, ")
			.append("        m.open_type openType, ")
			.append("        m.order_num orderNum, ")
			.append("        m.status status, ")
			.append("        m.dialog_flg       dialogFlg, ")
			.append("        m.height       height, ")
			.append("        m.width  width, ")
			.append("        m.title  title, ")
			.append("        m.row_handle_flg  rowHandleFlg ")
			
			.append("   from sys_menu m ")
			.append("  where m.status='1' ")
			.append("  start with m.parent_id = '0' and m.status='1' ")
			.append(" connect by prior m.id = m.parent_id ")
			.append("  order SIBLINGS BY m.order_num ");
		
		return queryList(sql.toString(), null, MenuCache.class);
	}

	public void batchUpdateForDel(List ids) {
		repository.batchUpdateForDel(ids);
	}
}
