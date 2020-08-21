package org.sdblt.modules.system.service;

import java.util.List;

import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.system.domain.FileDomain;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * <br>
 * <b>功能：</b>FileUploadService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
public interface FileUploadService extends BaseService<FileDomain> {

	/**
	 * @Description 单文件上传
	 * @param multipartFile
	 * @author sen
	 * @return 
	 * @Date 2017年3月25日 下午1:38:35
	 */
	public FileDomain uploadFile(MultipartFile multipartFile,String storagePath);
	
	/**
	 * @Description 多文件上传
	 * @param multipartFileArray
	 * @author sen
	 * @return 
	 * @Date 2017年3月25日 下午1:38:52
	 */
	public List<FileDomain> uploadFileArray(List<MultipartFile> multipartFileArray,String storagePath);
	
}
