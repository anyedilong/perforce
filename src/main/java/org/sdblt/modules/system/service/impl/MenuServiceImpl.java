package org.sdblt.modules.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.system.service.MenuService;
import org.sdblt.utils.StringUtils;
import org.sdblt.modules.system.dao.MenuDao;
import org.sdblt.modules.system.domain.Menu;

import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * <br>
 * <b>功能：</b>MenuService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Named
@Transactional(readOnly = true)
public class MenuServiceImpl extends BaseServiceImpl<MenuDao, Menu> implements MenuService {

	@Override
	public void queryMenuList(String parentId, String menuName, PageModel page) {
		dao.queryMenuList(parentId, menuName, page);
	}

	@Override
	@Transactional
	public Map saveMenuAndHandle(Menu menu) {
		boolean existBl = false;
		// 验证菜单是否存在
		String menuId = menu.getId();
		if (!StringUtils.isNull(menuId)) {

			Menu oldMenu = dao.get(menuId);
			if (null != oldMenu) {
				existBl = true;
			}
		}

		Map msgMap = new HashMap();

		String msg = "SUCCESS";
		int code = 0;
		if (!existBl) {
			// 验证上级ID
			String parentId = menu.getParentId();
			int menuLevel = 1;
			String parentIdAll = "0";

			if (StringUtils.isNull(parentId)) {
				parentId = "0";
				menu.setParentId("0");
			} else {
				// 获取上级ID信息
				Menu parentMenu = dao.get(parentId);
				if (null == parentMenu) {
					msgMap.put("code", 20001);
					msgMap.put("msg", "上级菜单不存在");
					return msgMap;
				}
				// 验证菜单类型
				String parentMenuType = parentMenu.getType();
				if (!"1".equals(parentMenuType)) {
					msgMap.put("code", 20002);
					msgMap.put("msg", "只能以菜单为上级");
					return msgMap;
				}
				menuLevel = parentMenu.getMenuLevel() + 1;
				parentIdAll = parentMenu.getParentIdAll() +","+ parentMenu.getId();
			}
			menu.setMenuLevel(menuLevel);

			// 获取最大排序号
			if (StringUtils.isNull(menu.getOrderNum())) {
				String maxOrderNum = dao.getMaxOrderByParent(parentId);
				int maxOrder = 0;
				if (!StringUtils.isNull(maxOrderNum)) {
					maxOrder = StringUtils.toInteger(maxOrderNum);
				}
				if (maxOrder < 1000) {
					maxOrder = 1000;
				}
				maxOrder = (maxOrder / 10 + 1) * 10;

				menu.setOrderNum(maxOrder + "");
			}

			menu.setStatus("1");
			menu.setParentIdAll(parentIdAll);
		}

		dao.save(menu);

		msgMap.put("code", code);
		msgMap.put("msg", msg);
		return msgMap;
	}

	@Override
	public List<Menu> getMenuTree() {
		return dao.getMenuTree();
	}

	@Override
	@Transactional
	public void batchUpdateForDel(List ids) {
		dao.batchUpdateForDel(ids);
	}

}
