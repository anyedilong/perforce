package org.sdblt.modules.product.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sdblt.modules.common.domain.BaseDomain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName UpgradeLog
 * @Description 日志的实体
 * @authorliuxingx
 * @Date 2017年4月7日 下午1:10:57
 * @version 1.0.0
 */
@Entity
@Table(name = "t_vm_upgrade_log")
public class UpgradeLog extends BaseDomain{
	   /**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	    @Id
		private String id;        // NVARCHAR2(36)        not null,
		private String proStockId;      //产品库存ID
		private String status;             //'状态 1 成功  2 失败';
		@JSONField(format = "yyyy-MM-dd")
		@Column(updatable = false)
	    private Date upgradeTime;       //'升级时间'
	    private String upgradeBeforeVersion; //'升级前版本ID';
	    private String upgradeErrorLog;  //'升级错误日志';
	    private String upgradeVersionId; //'升级版本ID';
	    private String remarks;//'备注';
		public String getProStockId() {
			return proStockId;
		}
		public void setProStockId(String proStockId) {
			this.proStockId = proStockId;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Date getUpgradeTime() {
			return upgradeTime;
		}
		public void setUpgradeTime(Date upgradeTime) {
			this.upgradeTime = upgradeTime;
		}
		public String getUpgradeBeforeVersion() {
			return upgradeBeforeVersion;
		}
		public void setUpgradeBeforeVersion(String upgradeBeforeVersion) {
			this.upgradeBeforeVersion = upgradeBeforeVersion;
		}
		public String getUpgradeErrorLog() {
			return upgradeErrorLog;
		}
		public void setUpgradeErrorLog(String upgradeErrorLog) {
			this.upgradeErrorLog = upgradeErrorLog;
		}
		public String getUpgradeVersionId() {
			return upgradeVersionId;
		}
		public void setUpgradeVersionId(String upgradeVersionId) {
			this.upgradeVersionId = upgradeVersionId;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
}
