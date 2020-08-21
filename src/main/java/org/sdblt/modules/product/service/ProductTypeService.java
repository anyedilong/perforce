package org.sdblt.modules.product.service;

import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.product.domain.ProductType;
import org.sdblt.modules.product.dto.ProductTypeDto;

/**
 * 
 * <br>
 * <b>功能：</b>ProductTypeService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface ProductTypeService extends BaseService<ProductType>{

	/**
	 * @Description 获取列表
	 * @param productTypeParam
	 * @param page
	 * @author sen
	 * @Date 2017年3月20日 下午2:54:54
	 */
	void getList(ProductTypeDto productTypeParam, PageModel page);

	void saveProType(ProductType productType);

	void batchUpdateForDel(List ids);

}
