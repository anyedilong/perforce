package org.sdblt.modules.common.dto;

import java.io.InputStream;

public class BaseFileDto {

	private String fileName;// 文件名
	private String md5;// 文件唯一标识
	private InputStream fileInputStream;// 文件输入流

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

}
