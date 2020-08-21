package org.sdblt.modules.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.system.service.OrgService;
import org.sdblt.utils.StringUtils;
import org.sdblt.modules.system.dao.OrgDao;
import org.sdblt.modules.system.domain.Menu;
import org.sdblt.modules.system.domain.SysOrg;
import org.sdblt.modules.system.dto.OrgListDto;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * <br>
 * <b>功能：</b>SysOrgService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
@Named
@Transactional(readOnly = true)
public class OrgServiceImpl extends BaseServiceImpl<OrgDao,SysOrg> implements OrgService {

	@Override
	public void queryOrgList(OrgListDto orgParam, PageModel page) {
		dao.queryOrgList(orgParam,page);
	}

	@Override
	public void batchUpdateForDel(List ids) {
		dao.batchUpdateForDel(ids);
	}

	@Override
	public List<SysOrg> getOrgTree(String type, String orgId) {
		return dao.getOrgTree(type,orgId);
	}

	@Override
	@Transactional
	public Map saveOrg(SysOrg org) {
		
		org.setStatus("1");
		
		boolean existBl = false;
		// 验证菜单是否存在
		String orgId = org.getId();
		if (!StringUtils.isNull(orgId)) {
			SysOrg oldOrg = dao.get(orgId);
			if (null != oldOrg) {
				existBl = true;
			}
		}
		
		Map msgMap = new HashMap();

		String msg = "SUCCESS";
		int code = 0;
		if (!existBl) {
			// 类型 1机构 2 部门 3 岗位
			String orgType = org.getOrgType();
			//上级机构
			String parentId = org.getParentId();
			int orgLevel = 1;
			String parentIdAll = "0";
			
			if(StringUtils.isNull(parentId)){
				parentId = "0";
				if(!"1".equals(orgType)){
					msgMap.put("code", 20001);
					msgMap.put("msg", "顶级只能添加机构");
					return msgMap;
				}
			}else{
				//获取上级ID信息
				SysOrg parentOrg = dao.get(parentId);
				if (null == parentOrg) {
					msgMap.put("code", 20002);
					msgMap.put("msg", "上级机构不存在");
					return msgMap;
				}
				//上级机构信息
				int parentLevel = parentOrg.getOrgLevel();
				String parentType = parentOrg.getOrgType();
				
				if("1".equals(orgType) && !"1".equals(parentType)){
					msgMap.put("code", 20003);
					msgMap.put("msg", "机构上级节点只能选择机构");
					return msgMap;
				}
				
				if("2".equals(orgType) && "3".equals(parentType)){
					msgMap.put("code", 20003);
					msgMap.put("msg", "部门上级节点不能选择岗位");
					return msgMap;
				}
				
				if("3".equals(orgType) && "3".equals(parentType)){
					msgMap.put("code", 20003);
					msgMap.put("msg", "岗位上级节点不能选择岗位");
					return msgMap;
				}
				
				//机构等级
				orgLevel = parentLevel + 1;
				parentIdAll = parentOrg.getParentIdAll()+","+parentId;
			}
			
			// 获取最大排序号
			if (StringUtils.isNull(org.getOrderNum())) {
				String maxOrderNum = dao.getMaxOrderByParent(parentId);
				int maxOrder = 0;
				if (!StringUtils.isNull(maxOrderNum)) {
					maxOrder = StringUtils.toInteger(maxOrderNum);
				}
				if (maxOrder < 1000) {
					maxOrder = 1000;
				}
				maxOrder = (maxOrder / 10 + 1) * 10;

				org.setOrderNum(maxOrder + "");
			}
			
			org.setParentId(parentId);
			org.setOrgLevel(orgLevel);
			org.setParentIdAll(parentIdAll);
		}
		
		dao.save(org);

		msgMap.put("code", code);
		msgMap.put("msg", msg);
		return msgMap;
	}

}
