package org.sdblt.modules.test.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.test.dao.repository.TestRepository;
import org.sdblt.modules.test.domain.Test;
import org.sdblt.modules.test.dto.TestDto;
import org.springframework.data.domain.Page;

@Named
public class TestDao extends BaseDao<TestRepository, Test> {

	// 分页
	public void findPage(PageModel page) {
		Page<Test> rs = (Page<Test>) repository.findAll(page.getPageable());
		page.setPageData(rs);
	}

	public Test findByUserName(String userName) {
		return repository.findByUserName(userName);
	}

	public Test findTestByUserName(String userName) {
		return repository.findTestByUserName(userName);
	}

	public TestDto queryByUsername(String userName) {
		String sql = " select * from sys_test where user_name like CONCAT(:list.0.userName,'%') ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		TestDto test = new TestDto();
		Test t1 = new Test();
		t1.setUserName(userName);

		List<Test> list = new ArrayList<Test>();
		list.add(t1);
		
		test.setList(list);
		
		
		paramMap.put("userName", userName);
		TestDto t = queryOne(sql, test, TestDto.class);

		return t;
	}

	public List<TestDto> queryListByUsername(String userName) {
		String sql = " select * from sys_test where user_name like CONCAT(:userName,'%')  order by user_code ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", userName);
		return queryList(sql, paramMap, TestDto.class);
	}

	public void queryPageList(PageModel page) {
		String sql = " select * from sys_test order by user_code ";

		queryPageList(sql, null, page, TestDto.class);
	}

	public void queryJpaPageList(PageModel page) {
		Page<Test> rs = (Page<Test>) repository.queryJpaPageList("%张三%",page.getPageable());
		page.setPageData(rs);
	}

	public void updateName(Test t) {
		String sql = " update sys_test t set t.user_name = :userName where t.id = :id ";
		execute(sql, t);
		//repository.updateName(t.getUserName(),t.getId());
	}

}
