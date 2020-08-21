package org.sdblt.modules.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.system.dao.UserDao;
import org.sdblt.modules.system.domain.Menu;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.domain.SysUserOrg;
import org.sdblt.modules.system.domain.SysUserRole;
import org.sdblt.modules.system.dto.RoleDto;
import org.sdblt.modules.system.dto.UserDto;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.modules.system.service.UserService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.sdblt.utils.sha1.SHA1Encrypt;
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
public class UserServiceImpl extends BaseServiceImpl<UserDao, SysUser> implements UserService {

	@Override
	public void queryUserList(UserListDto userParam, PageModel page) {
		dao.queryUserList(userParam,page);
	}
	
	@Override
	public void queryUserListShow(UserListDto userParam, PageModel page) {
		dao.queryUserListShow(userParam,page);
	}

	@Override
	@Transactional
	public void batchUpdateForDel(List ids) {
		dao.batchUpdateForDel(ids);
	}

	@Override
	@Transactional
	public Map saveUser(SysUser user) {
		boolean existBl = false;
		// 验证菜单是否存在
		String userId = user.getId();
		if (!StringUtils.isNull(userId)) {

			SysUser oldUser = dao.get(userId);
			if (null != oldUser) {
				existBl = true;
			}
		}

		Map msgMap = new HashMap();

		String msg = "SUCCESS";
		int code = 0;
		
		if (!existBl) {
			
			//验证用户名是否存在
			if(dao.existUsername(user.getUsername())){
				msgMap.put("code", 20001);
				msgMap.put("msg", "用户名"+user.getUsername()+"已存在");
				return msgMap;
			}
			//初始化密码
			String password = user.getPassword();
			if(StringUtils.isNull(password)){
				password = "111111";
			}
			String salt = UUIDUtil.getUUID();// 盐值
			String securityToken = UUIDUtil.getUUID();// 授权码
			password = SHA1Encrypt.encryptPassword(password, salt);
			
			user.setPassword(password);
			user.setSalt(salt);
			user.setSecurityToken(securityToken);
			user.setStatus("1");
		}
		dao.save(user);
		
		//删除用户所属角色
		dao.deleteUserRoleByUser(user.getId());
		
		//保存用户角色
		String roleids = user.getRoleIds();
		if(!StringUtils.isNull(roleids)){
			String[] roleIdArray = roleids.split(",");
			List<SysUserRole> userRoleList = new ArrayList<>();
			for (String roleId : roleIdArray) {
				SysUserRole userRole = new SysUserRole();
				userRole.setId(UUIDUtil.getUUID());
				userRole.setUserId(user.getId());
				userRole.setRoleId(roleId);
				userRoleList.add(userRole);
			}
			dao.batchSaveUserRole(userRoleList);
		}
		
		
		//管辖机构
		dao.deleteMrgOrgByUser(user.getId());
		String mrgOrgIds = user.getMrgOrgIds();
		if(StringUtils.isNull(mrgOrgIds)){
			mrgOrgIds = user.getOrgId();
		}
		String[] mrgOrgIdArray = mrgOrgIds.split(",");
		List<SysUserOrg> mrgOrgList = new ArrayList<>();
		for (String mrgOrgId : mrgOrgIdArray) {
			SysUserOrg marOrg = new SysUserOrg();
			marOrg.setId(UUIDUtil.getUUID());
			marOrg.setUserId(user.getId());
			marOrg.setOrgId(mrgOrgId);
			mrgOrgList.add(marOrg);
		}
		dao.batchSaveMrgOrg(mrgOrgList);
		
		CacheUtils.delete(CacheEmun.SYS_CACHE, CacheUtils.SYS_USER);

		msgMap.put("code", code);
		msgMap.put("msg", msg);
		return msgMap;
	}

	@Override
	public boolean existUsername(String username) {
		return dao.existUsername(username);
	}

	@Override
	public List<SysUser> getSysUserDropDownList(SysUser sysUserParam) {
		return dao.getSysUserDropDownList(sysUserParam);
	}
	@Override
	public List<RoleDto> getUserDto(String userId){
		return dao.getUserDto(userId);
	}
}
