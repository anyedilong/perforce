package org.sdblt.modules.test.service;

import java.util.List;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.BaseService;
import org.sdblt.modules.test.domain.Test;
import org.sdblt.modules.test.dto.TestDto;

public interface TestService extends BaseService<Test>{
	public void findPage(PageModel page);

	public Test findByUserName(String string);

	public Test findUserByUserName(String string);

	public TestDto queryByUsername(String string);

	public List<TestDto> queryListByUsername(String string);

	public void queryPageList(PageModel page);

	public void queryJpaPageList(PageModel page);

	public void updateName(Test t);
}
