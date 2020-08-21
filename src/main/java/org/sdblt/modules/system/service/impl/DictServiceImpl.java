package org.sdblt.modules.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.common.spring.InstanceFactory;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.common.utils.CacheManagerUtil;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.system.dao.DictDao;
import org.sdblt.modules.system.domain.Dict;
import org.sdblt.modules.system.domain.DictSub;
import org.sdblt.modules.system.dto.DictTree;
import org.sdblt.modules.system.service.DictService;
import org.sdblt.modules.system.service.SysService;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName DictServiceImpl
 * @Description 字典服务类
 * @author sen
 * @Date 2017年3月20日 上午8:54:19
 * @version 1.0.0
 */
@Named
@Transactional(readOnly = true)
public class DictServiceImpl extends BaseServiceImpl<DictDao, Dict> implements DictService {

	@Override
	public void getSubList(DictSub dictSubParam, PageModel page) {
		dao.querySubList(dictSubParam, page);
	}

	@Override
	@Transactional
	public void save(Dict dict) {
		dao.save(dict);

		// 字典缓存
		List<DictCache> dictAllList = InstanceFactory.getInstance(SysService.class).initDictCache();
		if (null != dictAllList) {
			List<DictCache> dictList = dictAllList.stream()
					.filter(filterDict -> dict.getId().equals(filterDict.getDictId())).collect(Collectors.toList());

			if (null != dictList) {
				for (DictCache dictCache : dictList) {
					dictCache.setCode(dict.getCode());
				}
				CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.SYS_DICT, dictAllList);
			}
		}
	}

	@Override
	@Transactional
	public void saveSub(DictSub dictSub) {
		dao.saveSub(dictSub);

		String code = dao.getCodeById(dictSub.getDictId());
		dictSub.setCode(code);
		// 将数据保存到缓存
		CacheManagerUtil.saveDict(dictSub);
	}

	@Override
	public List<DictTree> findListTree() {
		return dao.findListTree();
	}

	@Override
	public DictSub getSub(String id) {
		return dao.getSub(id);
	}

	@Override
	@Transactional
	public void batchSubUpdateForDel(List ids) {
		dao.batchSubUpdateForDel(ids);

		List<DictCache> dictAllList = InstanceFactory.getInstance(SysService.class).initDictCache();
		if (null != dictAllList) {
			List<DictCache> dictList = dictAllList.stream()
					.filter(filterDict -> ids.stream().filter(id -> id.equals(filterDict.getId())).count() <= 0)
					.collect(Collectors.toList());

			CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.SYS_DICT, dictList);

		}
	}

	@Override
	@Transactional
	public void updateForDel(String id) {
		dao.updateForDel(id);

		List<DictCache> dictAllList = InstanceFactory.getInstance(SysService.class).initDictCache();
		if (null != dictAllList) {
			List<DictCache> dictList = dictAllList.stream().filter(filterDict -> !id.equals(filterDict.getDictId()))
					.collect(Collectors.toList());

			CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.SYS_DICT, dictList);

		}

	}

}
