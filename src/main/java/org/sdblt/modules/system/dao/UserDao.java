package org.sdblt.modules.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.system.dao.repository.SysUserOrgRepository;
import org.sdblt.modules.system.dao.repository.SysUserRepository;
import org.sdblt.modules.system.dao.repository.SysUserRoleRepository;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.domain.SysUserOrg;
import org.sdblt.modules.system.domain.SysUserRole;
import org.sdblt.modules.system.dto.RoleDto;
import org.sdblt.modules.system.dto.UserDto;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.modules.test.dto.TestDto;
import org.sdblt.utils.StringUtils;

/**
 * 
 * <br>
 * <b>功能：</b>MenuDao<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Named
public class UserDao extends BaseDao<SysUserRepository, SysUser> {
	
	@Inject
	private SysUserRoleRepository userRoleRepository;
	
	@Inject
	private SysUserOrgRepository userOrgRepository;
	

	public void queryUserListShow(UserListDto userParam, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id id, ")
			.append("        u.username username, ")
			.append("        u.name name, ")
			.append("        u.status status, ")
			.append("        (select listagg(to_char(r.name), ',') WITHIN GROUP(ORDER BY r.create_time) ")
			.append("           from sys_user_role ur ")
			.append("           join sys_role r ")
			.append("             on r.id = ur.role_id ")
			.append("          where ur.user_id = u.id ")
			.append("            and r.status != '3' ")
			.append("          group by ur.user_id) roleNames ")
			.append("   from sys_user u   ")
			.append("   join sys_org o on o.id=u.org_id   ")
			.append("   where 1=1  ");
		if(!StringUtils.isNull(userParam.getOrgId())){
			if("1".equals(userParam.getIncludeSubFlg())){
				sql.append(" and (o.parent_id_all like CONCAT('%',CONCAT(:orgId,'%')) or o.id=:orgId) ");
			}else{
				sql.append(" and o.id=:orgId ");
			}
		}
		if(!StringUtils.isNull(userParam.getUsername())){
			sql.append(" and u.username  like CONCAT('%',CONCAT(:username,'%')) ");
		}
		if(!StringUtils.isNull(userParam.getName())){
			sql.append(" and u.name like CONCAT('%',CONCAT(:name,'%'))  ");
		}
		
		sql.append(" order by u.create_time desc ");
		
		queryPageList(sql.toString(), userParam, page, UserListDto.class);
	}
	
	public void queryUserList(UserListDto userParam, PageModel page) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id id, ")
			.append("        u.username username, ")
			.append("        u.name name, ")
			.append("        u.status status, ")
			.append("        (select listagg(to_char(r.name), ',') WITHIN GROUP(ORDER BY r.create_time) ")
			.append("           from sys_user_role ur ")
			.append("           join sys_role r ")
			.append("             on r.id = ur.role_id ")
			.append("          where ur.user_id = u.id ")
			.append("            and r.status != '3' ")
			.append("          group by ur.user_id) roleNames ")
			.append("   from sys_user u   ")
			.append("   join sys_org o on o.id=u.org_id   ")
			.append("   where 1=1  ");
		if("1".equals(userParam.getIncludeSubFlg())){
			sql.append(" and (o.parent_id_all like CONCAT('%',CONCAT(:orgId,'%')) or o.id=:orgId) ");
		}else{
			sql.append(" and o.id=:orgId ");
		}
		if(!StringUtils.isNull(userParam.getUsername())){
			sql.append(" and u.username  like CONCAT('%',CONCAT(:username,'%')) ");
		}
		if(!StringUtils.isNull(userParam.getName())){
			sql.append(" and u.name like CONCAT('%',CONCAT(:name,'%'))  ");
		}
		
		sql.append(" order by u.create_time desc ");
		
		queryPageList(sql.toString(), userParam, page, UserListDto.class);
	}

	public void batchUpdateForDel(List ids) {
		repository.batchUpdateForDel(ids);
	}

	public boolean existUsername(String username) {
		int count = repository.getUsernameCount(username);
		
		if(count > 0){
			return true;
		}
		return false;
	}

	public void batchSaveUserRole(List<SysUserRole> userRoleList) {
		userRoleRepository.save(userRoleList);
	}

	public void deleteUserRoleByUser(String userId) {
		
		userRoleRepository.deleteUserRoleByUser(userId);
		
	}

	public void batchSaveMrgOrg(List<SysUserOrg> mrgOrgList) {
		userOrgRepository.save(mrgOrgList);
	}

	public void deleteMrgOrgByUser(String userId) {
		userOrgRepository.deleteMrgOrgByUser(userId);
	}

	public List<SysUser> getSysUserDropDownList(SysUser sysUserParam) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id,u.name from sys_user u where u.status='1' ");
		return queryList(sql.toString(), sysUserParam, SysUser.class);
	}
	public List<RoleDto> getUserDto(String userId){
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		sql.append("  select r.name from sys_role r left join ");
		sql.append(" sys_user_role ur on ur.role_id = r.id   ");
		sql.append(" left join sys_user u on u.id = ur.user_id  ");
		sql.append(" where u.id=:userId and u.status='1' ");
		return queryList(sql.toString(), paramMap, RoleDto.class);
		
	}
}
