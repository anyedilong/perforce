package org.sdblt.modules.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.modules.common.repository.QueryDao;
import org.sdblt.modules.common.utils.cache.AreaCache;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.common.utils.cache.OrgCache;
import org.sdblt.modules.common.utils.cache.ProductTypeCache;
import org.sdblt.modules.common.utils.cache.SysUserCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.dao.repository.SysUserRepository;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.utils.StringUtils;

@Named
public class SysDao extends QueryDao {

	@Inject
	private SysUserRepository userRepository;

	public SysUser getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public UserCache getUserInfo(String username, String securityToken) {
		
		// 参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("securityToken", securityToken);
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id userId, u.org_id orgId,u.username userName, u.status status ")
		.append("   from sys_user u ")
		.append("  where u.security_token=:securityToken ")
		.append("    and u.username=:username ");
		
		return queryOne(sql.toString(), paramMap, UserCache.class);
	}

	/**
	 * @Description 查询用户操作权限菜单 
	 * @param userId
	 * @return
	 * @author sen
	 * @Date 2017年3月8日 下午3:13:08
	 */
	public List<String> getMenuByUser(String userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct m.id  ")
		.append("   from sys_user_role ur ")
		.append("   join sys_role r on ur.role_id=r.id and r.status='1' ")
		.append("  join sys_role_menu rm on rm.role_id=r.id ")
		.append("   join sys_menu m on m.status='1' and m.id=rm.menu_id ")
		.append("  where ur.user_id=:userId ");
		
		return queryList(sql.toString(), paramMap, String.class);
	}

	public List<OrgCache> getOrgList(String orgId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select o.id            id, ")
			.append("       o.name          name, ")
			.append("       o.org_code      orgCode, ")
			.append("       o.sort_name     sortName, ")
			.append("       o.org_level     orgLevel, ")
			.append("       o.org_type      orgType, ")
			.append("       o.parent_id     parentId, ")
			.append("       o.parent_id_all parentIdAll, ")
			.append("       o.status        status ")
			.append("  from sys_org o  where o.status ='1' ")
			.append("   and (o.parent_id_all like CONCAT('%',CONCAT(:orgId,'%')) or o.id=:orgId) ");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgId", orgId);
		
		return queryList(sql.toString(), paramMap, OrgCache.class);
	}

	public List<String> getMrgOrgIds(String userId) {
		String sql = "select org_id from sys_user_org where user_id=:userId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		
		return queryList(sql, paramMap, String.class);
	}

	public List<DictCache> getDictCache() {
		
		StringBuffer sql = new StringBuffer();		
		sql.append(" select ds.id id, ds.dict_id dictId, ds.text text, ds.value value,ds.order_num orderNum,ds.status, d.code ")
			.append("   from sys_dict_sub ds ")
			.append("   join sys_dict d on d.id = ds.dict_id where ds.status !=3 and d.status != 3 ");
		
		return queryList(sql.toString(), null, DictCache.class);
	}

	public List<ProductTypeCache> getPruductType() {
		StringBuffer sql = new StringBuffer();
		sql.append( " select pt.id, ")
			.append( "        pt.type_name typeName, ")
			.append( "        pt.parent_id parentId, ")
			.append( "        pt.code, ")
			.append( "        pt.order_num orderNum ")
			.append( "   from t_vm_product_type pt where pt.status != 3 ")
			.append( "   order by pt.order_num ");
		
		return queryList(sql.toString(), null, ProductTypeCache.class);
	}

	public List<SysUserCache> getUserCache() {
		String sql = " select u.id, u.username, u.name from sys_user u ";
		
		return queryList(sql, null, SysUserCache.class);
	}

	public List<AreaCache> getAreaCache() {
		StringBuffer sql = new StringBuffer();
		sql.append( " select a.area_name areaName, a.area_code areaCode, a.area_level areaLevel ")
			.append("   from sys_area a ")
			.append("  where a.area_level <= 3 ");

		return queryList(sql.toString(), null, AreaCache.class);
	}

	public List<AreaCache> getAreaByParentCode(String parenCode, int level) {
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("code", parenCode);
		String parentId = queryOne("select id from sys_area where area_code=:code", paramMap1, String.class);
		if(StringUtils.isNull(parentId)){
			return null;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentId", parentId);
		paramMap.put("level", level);
		
		StringBuffer sql = new StringBuffer();
		sql.append( " select a.area_name areaName, a.area_code areaCode, a.area_level areaLevel ")
			.append("   from sys_area a ")
			.append("  where a.area_level=:level and a.parent_id=:parentId  ");

		return queryList(sql.toString(), paramMap, AreaCache.class);
	}

	/**
	 * @Description 根据code获取行政区划名称
	 * @param code
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 下午4:04:18
	 */
	public String getAreaNameByCode(String code) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", code);
		
		return queryOne("select area_name from sys_area where area_code=:code", paramMap, String.class);
	}

}
