package org.sdblt.modules.system.service;

import java.util.List;
import java.util.Map;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.system.domain.Menu;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.dto.RoleDto;
import org.sdblt.modules.system.dto.UserDto;
import org.sdblt.modules.system.dto.UserListDto;

/**
 * 
 * <br>
 * <b>功能：</b>MenuService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface UserService extends BaseService<SysUser>{

	/**
	 * @Description 查询用户列表
	 * @param userParam
	 * @param page
	 * @author sen
	 * @Date 2017年3月2日 上午10:04:13
	 */
	void queryUserList(UserListDto userParam, PageModel page);

	/**
	 * 
	 * @Description 批量删除
	 * @param ids
	 * @author sen
	 * @Date 2017年3月1日 下午4:30:08
	 */
	void batchUpdateForDel(List ids);

	/**
	 * 
	 * @Description 保存用户
	 * @param user
	 * @author sen
	 * @Date 2017年3月2日 下午4:44:17
	 */
	Map saveUser(SysUser user);

	/**
	 * @Description 用户名是否存在
	 * @param username
	 * @return
	 * @author sen
	 * @Date 2017年3月2日 下午4:58:08
	 */
	boolean existUsername(String username);

	void queryUserListShow(UserListDto userParam, PageModel page);

	List<SysUser> getSysUserDropDownList(SysUser sysUserParam);
	/**
	 * 获取用户名称和角色名称
	 * @Description (TODO)
	 * @param userId
	 * @return
	 * @authorliuxingx
	 * @Date 2017年5月25日 下午1:55:14
	 */
	public List<RoleDto> getUserDto(String userId);
}
