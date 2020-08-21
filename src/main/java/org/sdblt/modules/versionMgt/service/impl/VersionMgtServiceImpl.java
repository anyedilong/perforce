package org.sdblt.modules.versionMgt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.product.domain.ProductDevice;
import org.sdblt.modules.product.domain.ProductVersionFile;
import org.sdblt.modules.versionMgt.dao.VersionMgtDao;
import org.sdblt.modules.versionMgt.domain.ProducTreeInfo;
import org.sdblt.modules.versionMgt.domain.VersionMgt;
import org.sdblt.modules.versionMgt.dto.VersionMgtDto;
import org.sdblt.modules.versionMgt.service.VersionMgtService;
import org.sdblt.utils.UUIDUtil;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional(readOnly = true)
public class VersionMgtServiceImpl extends BaseServiceImpl<VersionMgtDao, VersionMgt> implements VersionMgtService {

	/**
	 * 列表查询
	 */
	@Override
	public void queryVersionMgtList(VersionMgtDto versionMgtDto, PageModel page) {
		dao.queryVersionMgtList(versionMgtDto, page);
	}

	/**
	 * 保存
	 */
	@Override
	@Transactional
	public Map saveVersionMgt(VersionMgt versionMgt) {

		Map msgMap = new HashMap();
		int code = 0;
		String msg = "SUCCESS";
		msgMap.put("code", code);
		msgMap.put("msg", msg);
		dao.save(versionMgt);

		//删除原有附件信息
		dao.deleteFileByVersionId(versionMgt.getId());
		
		// 批量附件信息
		List<ProductVersionFile> prodVersFileList = versionMgt.getProdVersFileList();
		if (null != prodVersFileList && prodVersFileList.size() > 0) {
			for (ProductVersionFile productVersionFile : prodVersFileList) {
				productVersionFile.setId(UUIDUtil.getUUID());
				productVersionFile.setFileId(productVersionFile.getFileId());
				productVersionFile.setVersionId(versionMgt.getId());
				productVersionFile.setStatus("1");
			}
			dao.batchSavefileList(prodVersFileList);
		}
		return msgMap;
	}

	/**
	 * 删除
	 */
	@Override
	@Transactional
	public void updateForDelete(String id, String updateUser, Date updateDate) {
		// 验证产品是否存在
		if (!dao.exists(id)) {
			throw new CommonException(20001, "版本信息不存在，请刷新列表后操作");
		}
		// 产品删除
		int count = dao.updateForDelete(id, updateUser, updateDate);
		if (count <= 0) {
			throw new CommonException(20001, "版本信息删除失败，请刷新列表后操作");
		}
	}

	/**
	 * 
	 * @Description 获取产品树信息
	 * @return
	 * @author josen
	 * @Date 2017年3月29日 上午11:31:00
	 */
	public List<ProducTreeInfo> getProinfo() {
		List<ProducTreeInfo> productList = new ArrayList();
		productList = dao.getProinfo();
		return productList;
	}

	public VersionMgt getPrepVersId(String proId) {
		VersionMgt vm = dao.getPrepVersId(proId);
		return vm;
	}

	public void delFileById(String fileId, String versionId) {
		// 产品删除
		int count = dao.delFileById(fileId, versionId);
		// if (count <= 0) {
		// throw new CommonException(20001,"版本信息删除失败，请刷新列表后操作");
		// }
	}

}
