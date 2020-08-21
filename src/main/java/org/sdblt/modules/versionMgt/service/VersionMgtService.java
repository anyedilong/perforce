package org.sdblt.modules.versionMgt.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.versionMgt.domain.ProducTreeInfo;
import org.sdblt.modules.versionMgt.domain.VersionMgt;
import org.sdblt.modules.versionMgt.dto.VersionMgtDto;

public interface VersionMgtService extends BaseService<VersionMgt>{
	
	void queryVersionMgtList(VersionMgtDto versionMgtDto, PageModel page);
	
	Map saveVersionMgt(VersionMgt vm);
	
	/**
	 * 
	 * @Description 获取产品树信息
	 * @return
	 * @author josen
	 * @Date 2017年3月29日 上午11:31:00
	 */
	List<ProducTreeInfo>  getProinfo();
	
	
	/**
	 * 
	 * @Description 删除
	 * @param id
	 * @param updateUser
	 * @param updateDate
	 * @author josen
	 * @Date 2017年3月28日 下午5:18:26
	 */
	void updateForDelete(String id,String updateUser, Date updateDate);
	
	
	/**
	 *  
	 * @Description (TODO)根据产品id获取已发布的最后版本id,用户设置前置版本信息和序列号
	 * @param proId
	 * @return
	 * @author josen
	 * @Date 2017年4月5日 上午10:33:33
	 */

	VersionMgt getPrepVersId(String proId);
	
	/**
	 * 
	 * @Description 根据单个附件的id删除附件信息
	 * @return
	 * @author josen
	 * @Date 2017年4月6日 下午2:32:43
	 */
	void delFileById(String fileId,String versionId);


}
