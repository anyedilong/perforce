package org.sdblt.modules.test.service.impl;

import java.util.List;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.test.dao.TestDao;
import org.sdblt.modules.test.domain.Test;
import org.sdblt.modules.test.dto.TestDto;
import org.sdblt.modules.test.service.TestService;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional(readOnly = true)
public class TestServiceImpl extends BaseServiceImpl<TestDao, Test> implements TestService{
	
	public void findPage(PageModel page) {
		dao.findPage(page);
	}

	@Override
	public Test findByUserName(String userName) {
		return dao.findByUserName(userName);
	}

	@Override
	public Test findUserByUserName(String userName) {
		return dao.findTestByUserName(userName);
	}

	@Override
	public TestDto queryByUsername(String userName) {
		return dao.queryByUsername(userName);
	}

	@Override
	public List<TestDto> queryListByUsername(String userName) {
		return dao.queryListByUsername(userName);
	}

	@Override
	public void queryPageList(PageModel page) {
		dao.queryPageList(page);
	}

	@Override
	public void queryJpaPageList(PageModel page) {
		dao.queryJpaPageList(page);
	}

	@Override
	@Transactional
	public void updateName(Test t) {
		dao.updateName(t);
	}

}
