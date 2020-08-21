package org.sdblt.modules.test.dao.repository;

import org.sdblt.modules.test.domain.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 
 * @ClassName TestRepository
 * @Description TODO
 * @author sen
 * @Date 2016年12月12日 下午1:55:38
 * @version 1.0.0
 */
public interface TestRepository extends JpaRepository<Test, String> {
	
	Test findByUserName(String userName);
	
	@Modifying()
	@Query("update Test t set t.userName = ?1 where t.id = ?2 ")
	void updateName(String userName,String id);

	@Query("from Test t where t.userName=:name")
	Test findTestByUserName(@Param("name") String name);

	@Query("from Test t where t.userName like :name")
	Page<Test> queryJpaPageList(@Param("name")String name, Pageable pageable);

	
}
