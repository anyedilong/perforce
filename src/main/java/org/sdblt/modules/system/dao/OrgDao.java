package org.sdblt.modules.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.system.dao.repository.SysOrgRepository;
import org.sdblt.modules.system.domain.SysOrg;
import org.sdblt.modules.system.dto.OrgListDto;
import org.sdblt.utils.StringUtils;
/**
 * 
 * <br>
 * <b>功能：</b>SysOrgDao<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
@Named
public class OrgDao extends BaseDao<SysOrgRepository, SysOrg> {

	public void queryOrgList(OrgListDto orgParam, PageModel page) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select o.id            id, ")
			.append("       o.name          name, ")
			.append("       o.org_code      orgCode, ")
			.append("       o.sort_name     sortName, ")
			.append("       o.org_level     orgLevel, ")
			.append("       o.org_type      orgType, ")
			.append("       o.parent_id     parentId, ")
			.append("       o.parent_id_all parentIdAll, ")
			.append("       o.order_num orderNum, ")
			.append("       o.status        status ")
			.append("  from sys_org o  where o.status !='3' ")
			.append("   and o.parent_id = :orgId ");
		if(!StringUtils.isNull(orgParam.getName())){
			sql.append("   and o.name like CONCAT('%',CONCAT(:name,'%'))  ");
		}
		
		sql.append(" order by o.order_num desc ");

		queryPageList(sql.toString(), orgParam, page, OrgListDto.class);
	}
	
	public void batchUpdateForDel(List ids) {
		repository.batchUpdateForDel(ids);
	}

	
	public List<SysOrg> getOrgTree(String type, String orgId) {
		//1只统计 组织机构  2 统计到部门   默认统计到岗位
		StringBuffer sql = new StringBuffer();
		sql.append(" select o.id            id, ")
		   .append("        o.name          name, ")
		   .append("        o.org_code      orgCode, ")
		   .append("        o.sort_name     sortName, ")
		   .append("        o.org_level     orgLevel, ")
		   .append("        o.org_type      orgType, ")
		   .append("        o.parent_id     parentId, ")
		   .append("        o.parent_id_all parentIdAll ")
		   .append("   from sys_org o ")
		   .append("  where 1 = 1 and o.status='1' ");
		   if("1".equals(type)){
			   sql.append(" and o.org_type='1' ");
		   }else  if("2".equals(type)){
			   sql.append(" and (o.org_type='1' or o.org_type='2') ");
		   }
		   //sql.append(" and (o.parent_id_all like CONCAT('%',CONCAT(:orgId,'%')) or o.id=:orgId) ");
			   
		sql.append("  start with o.parent_id = '0' and status='1' ")
		   .append(" connect by prior id = o.parent_id ")
		   .append("  order SIBLINGS BY o.order_num ");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//paramMap.put("orgId", orgId);
		
		return queryList(sql.toString(), paramMap, SysOrg.class);
	}

	public String getMaxOrderByParent(String parentId) {
		String sql = " select max(order_num) from sys_org where parent_id=:parentId ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentId", parentId);
		return queryOne(sql, paramMap, String.class);
	}	
	
}
