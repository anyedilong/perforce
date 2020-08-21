package org.sdblt.modules.common.service;

import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.product.dto.ProductStockDeviceListDto;

public interface BaseService<T>  {
	public T get(String id) ;

	/**
	 * 查询列表数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> findList() ;

	/**
	 * 查询分页数据
	 * 
	 * @param page
	 *            分页对象
	 * @param entity
	 * @return
	 */
	public void findPage(PageModel page);

	/**
	 * 数据是否存在
	 */
	public boolean exists(String id);

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param siteinfo
	 */
	public void save(T entity);

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	public void delete(String id);

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	
}
