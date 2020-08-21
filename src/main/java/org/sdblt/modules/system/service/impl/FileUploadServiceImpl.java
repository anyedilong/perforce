package org.sdblt.modules.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.system.dao.FileUploadDao;
import org.sdblt.modules.system.domain.FileDomain;
import org.sdblt.modules.system.service.FileUploadService;
import org.sdblt.utils.FileUtils;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;
import org.sdblt.utils.properties.PropertiesUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * <br>
 * <b>功能：</b>FileUploadService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Named
@Transactional(readOnly = true)
public class FileUploadServiceImpl extends BaseServiceImpl<FileUploadDao, FileDomain> implements FileUploadService {
	
	/**
	 * @Description 单文件上传
	 * @param multipartFile
	 * @param storagePath  文件存放路径
	 * @author sen
	 * @Date 2017年3月25日 下午1:40:27
	 */
	@Override
	@Transactional
	public FileDomain uploadFile(MultipartFile multipartFile,String storagePath) {
		if(StringUtils.isNull(storagePath)){
			storagePath = "";
		}
		if(null != multipartFile){
			FileDomain file = new FileDomain();
			//文件名
			String fileName = multipartFile.getOriginalFilename();
			//后缀名
			String fileExtensions =fileName.substring(fileName.lastIndexOf("."));
			//文件存放根路径
			String fileBasePath = PropertiesUtil.getSys("FILE_BASE_PATH");
			//服务器地址
			String serviceUrl = PropertiesUtil.getSys("FILE_SERVER_URL");
			
			//文件路径
			String filePath = storagePath +"\\"
					+System.currentTimeMillis()+UUIDUtil.getUUID()+fileExtensions ;
			//文件存放磁盘路径
			String fileFullPath =fileBasePath +filePath;
			filePath = filePath.replaceAll("\\\\", "/");
			
			file.setFilePath(filePath);
			file.setFileName(fileName);
			file.setServerUrl(serviceUrl);
			file.setUploadTime(new Date());
			file.setFileSize(multipartFile.getSize());
			
			try {
				// 文件上传
				InputStream fileInputStream = multipartFile.getInputStream();
				File uploadFile = new File(fileFullPath);
				uploadFile.getParentFile().mkdirs();
				if (uploadFile.exists())
					uploadFile.createNewFile();
				multipartFile.transferTo(uploadFile);
				//FileUtils.uploadFile(fileInputStream, fileFullPath);
				//URL路径
				dao.save(file);
			} catch (IOException e) {
				e.printStackTrace();
				throw new CommonException(20001,"文件上传失败");
			}
			
			
			return file;
		}
		return null;
	}

	/**
	 * @Description 多文件上传
	 * @param multipartFileArray
	 * @author sen
	 * @Date 2017年3月25日 下午1:40:42
	 */
	@Override
	@Transactional
	public List<FileDomain> uploadFileArray(List<MultipartFile> multipartFileArray,String storagePath) {
		if(null != multipartFileArray && multipartFileArray.size() > 0){
			List<FileDomain> fileList = new ArrayList<>();
			for (MultipartFile multipartFile : multipartFileArray) {
				FileDomain file = uploadFile(multipartFile,storagePath);
				if(null != file){
					fileList.add(file);
				}
			}
			return fileList;
		}
		return null;
	}

}
