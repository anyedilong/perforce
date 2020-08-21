package org.sdblt.modules.system.service;

import java.util.List;
import java.util.Map;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.system.domain.SysOrg;
import org.sdblt.modules.system.dto.OrgListDto;

/**
 * 
 * <br>
 * <b>功能：</b>SysOrgService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface OrgService extends BaseService<SysOrg>{

	/**
	 * 
	 * @Description 查询机构列表
	 * @param orgParam
	 * @param page
	 * @author sen
	 * @Date 2017年3月3日 上午11:20:09
	 */
	void queryOrgList(OrgListDto orgParam, PageModel page);

	/**
	 * 
	 * @Description 批量删除
	 * @param ids
	 * @author sen
	 * @Date 2017年3月3日 下午1:30:23
	 */
	void batchUpdateForDel(List ids);

	/**
	 * @Description 获取机构树
	 * @param type
	 * @return
	 * @author sen
	 * @param string 
	 * @Date 2017年3月3日 下午2:32:11
	 */
	List<SysOrg> getOrgTree(String type, String orgId);

	/**
	 * @Description 保存组织机构
	 * @param orgParam
	 * @return
	 * @author sen
	 * @Date 2017年3月3日 下午4:31:16
	 */
	Map saveOrg(SysOrg orgParam);

}
