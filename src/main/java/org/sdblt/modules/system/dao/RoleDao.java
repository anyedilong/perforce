package org.sdblt.modules.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.system.dao.repository.SysRoleMenuRepository;
import org.sdblt.modules.system.dao.repository.SysRoleRepository;
import org.sdblt.modules.system.domain.SysRole;
import org.sdblt.modules.system.domain.SysRoleMenu;
import org.sdblt.modules.system.dto.RoleListDto;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.utils.StringUtils;
/**
 * 
 * <br>
 * <b>功能：</b>SysRoleDao<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
@Named
public class RoleDao extends BaseDao<SysRoleRepository, SysRole> {
	
	@Inject
	private SysRoleMenuRepository roleMenuRepository;

	public void queryRoleList(RoleListDto roleParam, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.id id, r.name name, r.remarks remarks, r.status status ")
			.append(" from sys_role r ")
			.append(" where r.status !='3' ");
//			.append(" where r.org_id = :orgId and r.status !='3' ");
		if(!StringUtils.isNull(roleParam.getName())){
			sql.append("    and r.name like CONCAT('%',CONCAT(:name,'%')) ");
		}
		
		sql.append(" order by r.create_time desc ");
		
		queryPageList(sql.toString(), roleParam, page, RoleListDto.class);
	}

	public void batchUpdateForDel(List ids) {
		repository.batchUpdateForDel(ids);
	}

	/**
	 * 
	 * @Description 批量删除角色授权信息
	 * @param roleId
	 * @author sen
	 * @Date 2017年3月7日 下午2:42:06
	 */
	public void batchDelMenuByRole(String roleId) {
		
		roleMenuRepository.deleteByRoleId(roleId);
	}

	/**
	 * 
	 * @Description 批量保存授权信息
	 * @param roleMenuList
	 * @author sen
	 * @Date 2017年3月7日 下午2:48:11
	 */
	public void batchSaveRoleMenu(List<SysRoleMenu> roleMenuList) {
		roleMenuRepository.save(roleMenuList);
	}

	public List<String> showAuthorize(String roleId) {
		String sql = " select menu_id from sys_role_menu where role_id=:roleId ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		return queryList(sql, paramMap, String.class);
	}

	public List<RoleListDto> getAllList(RoleListDto roleParam) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.id id, r.name name, r.remarks remarks, r.status status ")
			.append(" from sys_role r ")
			.append(" where r.status !='3' ");
//			.append(" where r.org_id = :orgId and r.status !='3' ");
		sql.append(" order by r.create_time desc ");
		return queryList(sql.toString(), roleParam, RoleListDto.class);
	}

}
