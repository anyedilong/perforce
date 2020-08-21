package org.sdblt.modules.common.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.domain.BaseDomain;
import org.sdblt.utils.DateUtils;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

public class BaseDao<R extends JpaRepository, B extends BaseDomain> extends QueryDao {
	
	/** JpaRepository操作类 */
	@Inject
	protected R repository;
	


	/**
	 * 获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	public B get(String id) {
		return (B) repository.getOne(id);
	}

	/**
	 * 查询列表数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<B> findList() {
		return repository.findAll();
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
		Page<B> rs = (Page<B>) repository.findAll(page.getPageable());
		page.setPageData(rs);
	}

	/**
	 * 数据是否存在
	 */
	public boolean exists(String id) {
		return repository.exists(id);
	}

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param siteinfo
	 */
	@Transactional(readOnly = false)
	public void save(B entity) {
		if(StringUtils.isNull(entity.getId())){
			entity.setId(UUIDUtil.getUUID());
		}
		
		repository.save(entity);
		
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		repository.delete(id);
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(B entity) {
		repository.delete(entity);
	}


}
