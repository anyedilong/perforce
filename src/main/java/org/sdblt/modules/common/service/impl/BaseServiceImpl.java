package org.sdblt.modules.common.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public abstract class BaseServiceImpl<R extends BaseDao, T extends BaseDomain> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 持久层对象
	 */
	@Inject
	protected R dao;

	/**
	 * 获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public T get(String id) {
		return (T) dao.get(id);
	}

	/**
	 * 查询列表数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> findList() {
		return dao.findList();
	}

	/**
	 * 查询分页数据
	 * 
	 * @param page
	 *            分页对象
	 * @param entity
	 * @return
	 */
	public void findPage(PageModel page) {
		dao.findPage(page);
	}

	/**
	 * 数据是否存在
	 */
	public boolean exists(String id) {
		return dao.exists(id);
	}

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param siteinfo
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (StringUtils.isNull(entity.getId())) {
			entity.setId(UUIDUtil.getUUID());
		}
		dao.save(entity);
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		dao.delete(id);
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

}
