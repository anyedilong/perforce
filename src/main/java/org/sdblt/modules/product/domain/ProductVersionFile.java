package org.sdblt.modules.product.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Clob;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.system.domain.FileDomain;
import org.sdblt.utils.UUIDUtil;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName ProductVersionFile
 * @Description 版本升级文件
 * @author sen
 * @Date 2017年3月25日 上午11:13:48
 * @version 1.0.0
 */
@Entity
@Table(name = "t_vm_pro_version_file")
public class ProductVersionFile extends BaseDomain {

	@Id
	private String id = UUIDUtil.getUUID();// null
	@Column(name = "version_id")
	private String versionId;// 产品版本
	@Column(name = "file_id")
	private String fileId;// 文件

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
	@JoinColumn(name = "file_id", referencedColumnName = "id", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private FileDomain fileDomain;

	private String status;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public FileDomain getFileDomain() {
		return fileDomain;
	}

	public void setFileDomain(FileDomain fileDomain) {
		this.fileDomain = fileDomain;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
