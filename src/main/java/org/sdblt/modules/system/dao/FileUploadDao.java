package org.sdblt.modules.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.system.dao.repository.FileUploadRepository;
import org.sdblt.modules.system.domain.FileDomain;

/**
 * 
 * <br>
 * <b>功能：</b>FileUploadDao<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Named
public class FileUploadDao extends BaseDao<FileUploadRepository, FileDomain> {

	public List<FileDomain> queryfile(String versionId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("versionId", versionId);

		StringBuffer sql = new StringBuffer();
		sql.append("select CONCAT(d.server_url,d.file_path) uploadUrl,d.file_size fileSize ");
		sql.append("  from t_vm_product_version a");
		sql.append("   left join t_vm_pro_version_file c");
		sql.append("   on c.version_id=a.id");
		sql.append("   left join tf_file_upload d");
		sql.append("    on d.id=c.file_id");
		sql.append("  where 1 = 1");
		sql.append("   and a.status = '2'");
		sql.append("  and a.id=:versionId");
		List<FileDomain> fileUpload = queryList(sql.toString(), paramMap, FileDomain.class);
		return fileUpload;
	}

}
