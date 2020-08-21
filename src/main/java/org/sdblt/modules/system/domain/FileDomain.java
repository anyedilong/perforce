package org.sdblt.modules.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Clob;
import org.sdblt.modules.common.domain.BaseDomain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName FileUpload
 * @Description 文件上传
 * @author sen
 * @Date 2017年3月25日 上午11:56:55
 * @version 1.0.0
 */
@Entity
@Table(name = "tf_file_upload")
public class FileDomain extends BaseDomain {

	@Id
	@Column(name = "id")
	private String id;// ID
	private String filePath;// 文件路径
	private String serverUrl;// 服务器地址
	private Date uploadTime;// 上传时间
	private String fileName;// 上传名

	private Long fileSize;// 文件大小

	@Transient
	private String uploadUrl;// 下载路径

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getServerUrl() {
		return this.serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

}
