package org.sdblt.modules.system.service;

import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.system.domain.Dict;
import org.sdblt.modules.system.domain.DictSub;
import org.sdblt.modules.system.dto.DictTree;

/**
 * 
 * <br>
 * <b>功能：</b>DictService<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */ 
public interface DictService extends BaseService<Dict>{

	void getSubList(DictSub dictSubParam, PageModel page);

	void saveSub(DictSub dictSub);

	List<DictTree> findListTree();

	DictSub getSub(String id);

	void batchSubUpdateForDel(List ids);

	void updateForDel(String id);

}
