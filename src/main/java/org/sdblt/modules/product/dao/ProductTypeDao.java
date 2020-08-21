package org.sdblt.modules.product.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.product.dao.repository.ProductTypeRepository;
import org.sdblt.modules.product.domain.ProductType;
import org.sdblt.modules.product.dto.ProductListDto;
import org.sdblt.modules.product.dto.ProductTypeDto;
import org.sdblt.utils.StringUtils;
/**
 * 
 * <br>
 * <b>功能：</b>ProductTypeDao<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
@Named
public class ProductTypeDao extends BaseDao<ProductTypeRepository, ProductType> {
     
	@Inject
	private  ProductTypeRepository productTypeRepository;
	/**
	 * @Description 分页查询项目分类
	 * @param productTypeParam
	 * @param page
	 * @author sen
	 * @Date 2017年3月20日 下午3:00:28
	 */
	public void getList(ProductTypeDto productTypeParam, PageModel page) {
		StringBuffer sql = new StringBuffer();
		
		sql.append( " select pt.id, ")
			.append( "        pt.type_name typeName, ")
			.append( "        pt.parent_id parentId, ")
			.append( "        pt.code, ")
			.append( "        pt.remarks, ")
			.append( "        pt.status, ")
			.append( "        pt.order_num orderNum ")
			.append( "   from t_vm_product_type pt ");
		sql.append("   where 1=1 and pt.status != 3 ");
		sql.append("   and pt.parent_id = :parentId ");
		
		if(!StringUtils.isNull(productTypeParam.getTypeName())){
			sql.append( " and pt.type_name like  CONCAT('%',CONCAT(:typeName,'%')) ");
		}
		if(!StringUtils.isNull(productTypeParam.getCode())){
			sql.append( " and pt.code like  CONCAT('%',CONCAT(:code,'%')) ");
		}
		sql.append( "   order by pt.order_num ");
		
		queryPageList(sql.toString(), productTypeParam, page, ProductTypeDto.class);
	}

	public void batchUpdateForDel(List ids) {
		repository.batchUpdateForDel(ids);
	}
	
}
