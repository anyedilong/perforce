package org.sdblt.modules.product.service;

import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.product.domain.ProductStock;
import org.sdblt.modules.product.domain.ProductStockOperation;
import org.sdblt.modules.product.dto.ProductStockListDto;

public interface ProductStockService extends BaseService<ProductStock> {

	/**
	 * @Description 产品登记
	 * @param proStockParam
	 * @authorliuxingx
	 * @Date 2017年3月31日 下午5:12:48
	 */
	void fitProStock(ProductStock proStockParam, UserCache user);

	/**
	 * 
	 * @Description (TODO)
	 * @param customerParam
	 * @param page
	 * @author liuxingx
	 * @Date 2017年4月1日 下午3:26:37
	 */
	void getList(ProductStockListDto productStockDto, PageModel page);

	void getTestList(ProductStockListDto productStockDto, PageModel page);

	void getProxyList(ProductStockListDto productStockDto, PageModel page);

	void getOutList(ProductStockListDto productStockDto, PageModel page);

	void getLists(ProductStockListDto productStockDto, PageModel page);

	/**
	 * 
	 * @Description 入库产品显示
	 * @param productStockDto
	 * @param page
	 * @authorliuxingx
	 * @Date 2017年4月13日 上午8:45:06
	 */
	public void getInList(ProductStockListDto productStockDto, PageModel page);

	/**
	 * 出库
	 * 
	 * @Description (TODO)
	 * @param proStock
	 * @return
	 * @authorliuxingx
	 * @Date 2017年4月10日 下午3:26:07
	 */
	public int outProStock(ProductStock proStock, UserCache user);

	/**
	 * @Description 入库
	 * @param ids
	 * @param userId
	 * @authorliuxingx
	 * @Date 2017年4月12日 下午4:43:25
	 */
	int inProStock(List ids, String userId);

	void batchUpdateForDel(List ids);

	int testProduct(List ids, String userId);

	int errorProduct(List ids, String userId);
}
