package org.sdblt.modules.product.dto;

import org.sdblt.common.page.PageModel;

/**
 * 
 * @ClassName UpgradeLogDto
 * @Description 升级日志
 * @author liuxingx
 * @Date 2017年4月7日 下午1:35:13
 * @version 1.0.0
 */
public class UpgradeLogDto {
	private PageModel page;// 分页
	private String id;

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	} 
}
