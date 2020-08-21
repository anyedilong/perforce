package org.sdblt.modules.product.dto;

import java.io.Serializable;

public class ProDuctStockOperationDto implements Serializable{
	private String remarks;//'备注';

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
