package org.sdblt.modules.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.common.utils.cache.ProductTypeCache;
import org.sdblt.modules.product.service.ProductTypeService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.sdblt.modules.product.dao.ProductTypeDao;
import org.sdblt.modules.product.domain.ProductType;
import org.sdblt.modules.product.dto.ProductStockDeviceListDto;
import org.sdblt.modules.product.dto.ProductTypeDto;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * <br>
 * <b>功能：</b>ProductTypeService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
@Named
@Transactional(readOnly = true)
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductTypeDao,ProductType> implements ProductTypeService {

	@Override
	public void getList(ProductTypeDto productTypeParam, PageModel page) {
		if(StringUtils.isNull(productTypeParam.getParentId())){
			productTypeParam.setParentId("0");
		}
		dao.getList(productTypeParam,page);
	}

	@Override
	@Transactional
	public void saveProType(ProductType productType) {
		productType.setStatus("1");
		
		//上级ID
		if(StringUtils.isNull(productType.getId()) || !dao.exists(productType.getId())){
			
			String parentId = productType.getParentId();
			String parentIdAll = "";
			if(StringUtils.isNull(parentId)||"0".equals(parentId)){
				parentId = "0";
				parentIdAll = "0";
			}else{
				//获取上级ID
				ProductType proTypeParent = dao.get(parentId);
				
				if(null != proTypeParent){
					parentId = proTypeParent.getId();
					parentIdAll = proTypeParent.getParentIdAll()+","+parentId;
				}else{
					throw new CommonException(20001,"上级产品分类错误");
				}
			}
			productType.setParentId(parentId);
			productType.setParentIdAll(parentIdAll);
		}
		dao.save(productType);
	
		//放入缓存
		List<ProductTypeCache> productTypeList = CacheManagerUtil.getProTypeList();
		if (null == productTypeList) {
			productTypeList = new ArrayList<>();
		}
		String proTypeId = productType.getId();
		
		//过滤
		ProductTypeCache proTypeQuery = new ProductTypeCache(proTypeId);
		int index = productTypeList.indexOf(proTypeQuery);
		if (index >= 0) {
			ProductTypeCache proTypeCache = productTypeList.get(index);
			proTypeCache.setTypeName(productType.getTypeName());// 产品分类名称
			proTypeCache.setCode(productType.getCode());
			proTypeCache.setOrderNum(productType.getOrderNum());
		}else{
			ProductTypeCache proTypeCache = new ProductTypeCache();
			proTypeCache.setId(productType.getId());
			proTypeCache.setTypeName(productType.getTypeName());// 产品分类名称
			proTypeCache.setParentId(productType.getParentId());
			proTypeCache.setCode(productType.getCode());
			proTypeCache.setOrderNum(productType.getOrderNum());
			productTypeList.add( proTypeCache);
		}
		CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.PRODUCT_TYPE, productTypeList);

	}

	@Override
	@Transactional
	public void batchUpdateForDel(List ids) {
		dao.batchUpdateForDel(ids);
		
		//缓存中删除
		List<ProductTypeCache> productTypeList = CacheManagerUtil.getProTypeList();
		if (null != productTypeList) {
			List<ProductTypeCache> productTypeNewList = productTypeList.stream()
					.filter(filterProType -> ids.stream().filter(id -> id.equals(filterProType.getId())).count() <= 0)
					.collect(Collectors.toList());

			CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.PRODUCT_TYPE, productTypeNewList);

		}
		
	}


}
