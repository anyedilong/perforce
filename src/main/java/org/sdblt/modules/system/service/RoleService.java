package org.sdblt.modules.system.service;

import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.system.domain.SysRole;
import org.sdblt.modules.system.dto.RoleListDto;

/**
 * 
 * <br>
 * <b>功能：</b>SysRoleService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface RoleService extends BaseService<SysRole>{

	void queryRoleList(RoleListDto roleParam, PageModel page);

	void batchUpdateForDel(List ids);

	/**
	 * 
	 * @Description 角色授权
	 * @param roleId
	 * @param menuIds
	 * @author sen
	 * @Date 2017年3月7日 下午2:15:51
	 */
	void authorize(String roleId, List menuIds);

	/**
	 * @Description 角色授权信息 
	 * @param roleId
	 * @return
	 * @author sen
	 * @Date 2017年3月7日 下午3:03:58
	 */
	List<String> showAuthorize(String roleId);

	/**
	 * @Description 查询所有角色
	 * @param roleParam
	 * @return
	 * @author sen
	 * @Date 2017年3月7日 下午4:02:32
	 */
	List<RoleListDto> getAllList(RoleListDto roleParam);

}
