package org.sdblt.modules.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.system.service.RoleService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;
import org.sdblt.modules.system.dao.RoleDao;
import org.sdblt.modules.system.domain.SysRole;
import org.sdblt.modules.system.domain.SysRoleMenu;
import org.sdblt.modules.system.dto.RoleListDto;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * <br>
 * <b>功能：</b>SysRoleService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
@Named
@Transactional(readOnly = true)
public class RoleServiceImpl extends BaseServiceImpl<RoleDao,SysRole> implements RoleService {

	@Override
	public void queryRoleList(RoleListDto roleParam, PageModel page) {
		dao.queryRoleList(roleParam,page);
	}

	@Override
	public void batchUpdateForDel(List ids) {
		dao.batchUpdateForDel(ids);
	}

	/**
	 * 授权
	 */
	@Override
	@Transactional
	public void authorize(String roleId, List menuIds) {
		//验证角色是否存在
		if(!dao.exists(roleId)){
			throw new CommonException(20001,"授权角色无效");
		}
		
		//批量删除角色
		dao.batchDelMenuByRole(roleId);
		
		List<SysRoleMenu> roleMenuList = new ArrayList<>();
		for (Object menuId : menuIds) {
			SysRoleMenu roleMenu = new SysRoleMenu();
			roleMenu.setMenuId(StringUtils.toString(menuId));
			roleMenu.setRoleId(roleId);
			roleMenu.setId(UUIDUtil.getUUID());
			roleMenuList.add(roleMenu);
		}
		dao.batchSaveRoleMenu(roleMenuList);
	}

	@Override
	public List<String> showAuthorize(String roleId) {
		return dao.showAuthorize(roleId);
	}

	@Override
	public List<RoleListDto> getAllList(RoleListDto roleParam) {
		return dao.getAllList(roleParam);
	}

}
