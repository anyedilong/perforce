package org.sdblt.modules.system.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.system.dao.repository.DictRepository;
import org.sdblt.modules.system.dao.repository.DictSubRepository;
import org.sdblt.modules.system.domain.Dict;
import org.sdblt.modules.system.domain.DictSub;
import org.sdblt.modules.system.dto.DictTree;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;

/**
 * 
 * <br>
 * <b>功能：</b>DictDao<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@Named
public class DictDao extends BaseDao<DictRepository, Dict> {

	@Inject
	private DictSubRepository dictSubRepository;

	public void querySubList(DictSub dictSubParam, PageModel page) {

		StringBuffer sql = new StringBuffer();
		sql.append(" select ds.id,ds.text,ds.value,ds.status,ds.order_num orderNum,ds.remarks from sys_dict_sub ds where 1=1 ");
		sql.append(" and ds.dict_id=:dictId and ds.status!=3 ");

		if (!StringUtils.isNull(dictSubParam.getText())) {
			sql.append(" and ds.text  like CONCAT('%',CONCAT(:text,'%')) ");
		}

		if (!StringUtils.isNull(dictSubParam.getValue())) {
			sql.append(" and ds.value  like CONCAT('%',CONCAT(:value,'%')) ");
		}

		sql.append(" order by ds.order_num ");

		queryPageList(sql.toString(), dictSubParam, page, DictSub.class);
	}

	public void saveSub(DictSub dictSub) {
		if (StringUtils.isNull(dictSub.getId())) {
			dictSub.setId(UUIDUtil.getUUID());
		}
		dictSubRepository.save(dictSub);
	}

	public List<DictTree> findListTree() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select d.id, d.name, d.code, d.remarks, d.status,d.parent_id parentId from sys_dict d where 1=1 and d.status!=3 ");

		sql.append(" order by d.create_time ");

		return queryList(sql.toString(), null, DictTree.class);
	}

	public DictSub getSub(String id) {
		return dictSubRepository.findOne(id);
	}

	public void batchSubUpdateForDel(List ids) {
		dictSubRepository.batchUpdateForDel(ids);
	}

	public void updateForDel(String id) {
		repository.updateForDel(id);
	}

	public String getCodeById(String dictId) {
		return repository.findCodeById(dictId);
	}

}
