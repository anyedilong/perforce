package org.sdblt.modules.product.service;

import java.util.Date;
import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.product.domain.Product;
import org.sdblt.modules.product.dto.ProductListDto;

public interface ProductService extends BaseService<Product>{

	/**
	 * 
	 * @Description 获取产品列表
	 * @param productParam
	 * @param page
	 * @author sen
	 * @Date 2017年3月15日 下午4:26:14
	 */
	void getList(ProductListDto productParam, PageModel page);

	void savePruduct(Product product);

	/**
	 * @Description 产品发布
	 * @param id
	 * @author sen
	 * @param updateDate 
	 * @param updateUser 
	 * @Date 2017年3月21日 下午4:54:09
	 */
	void release(String id, String updateUser, Date updateDate);

	/**
	 * @Description 撤销发布
	 * @param id
	 * @author sen
	 * @Date 2017年3月21日 下午4:59:51
	 */
	void revokeRelease(String id,String updateUser, Date updateDate);

	/**
	 * @Description 删除
	 * @param id
	 * @author sen
	 * @Date 2017年3月21日 下午5:08:18
	 */
	void updateForDelete(String id,String updateUser, Date updateDate);

	/**
	 * @Description 修改库存
	 * @param id
	 * @param proStork
	 * @author sen
	 * @Date 2017年3月22日 下午5:31:36
	 */
	void updateStork(String id, int proStork);

	/**
	 * 
	 * @Description 根据产品类型获取产品列表
	 * @param proType
	 * @return
	 * @authorliuxingx
	 * @Date 2017年3月31日 下午3:54:27
	 */
	List<ProductListDto> getProductListByType(String proType);

}
