package org.sdblt.modules.system.service;

import java.util.List;

import org.sdblt.modules.common.utils.cache.AreaCache;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.common.utils.cache.ProductTypeCache;
import org.sdblt.modules.common.utils.cache.SysUserCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.domain.SysUser;

public interface SysService {

	/**
	 * 
	 * @Description 根据登录名获取用户信息
	 * @param username
	 * @return
	 * @author sen
	 * @Date 2017年2月15日 上午10:34:55
	 */
	SysUser getUserByUsername(String username);

	/**
	 * 
	 * @Description 获取用户信息
	 * @param username
	 * @param securityToken
	 * @return
	 * @author sen
	 * @Date 2017年2月15日 下午2:03:17
	 */
	UserCache getUserInfo(String username, String securityToken);

	/**
	 * @Description 初始化数据
	 * @author sen
	 * @Date 2017年3月17日 下午4:31:18
	 */
	void initData();
	
	/**
	 * @Description 初始化行政区划缓存
	 * @author sen
	 * @Date 2017年3月17日 下午4:38:44
	 */
	public List<AreaCache> initAreaCache();
	
	/**
	 * @Description 初始化字典缓存
	 * @author sen
	 * @Date 2017年3月17日 下午4:38:44
	 */
	public List<DictCache> initDictCache();

	/**
	 * @Description 初始化项目类型
	 * @return
	 * @author sen
	 * @Date 2017年3月20日 下午3:25:13
	 */
	List<ProductTypeCache> initProductType();
	
	/**
	 * @Description 初始化用户
	 * @return
	 * @author sen
	 * @Date 2017年3月20日 下午3:25:13
	 */
	List<SysUserCache> initUserCache();
	
	/**
	 * @Description 获取  数据库行政区划 乡镇
	 * @param parenCode
	 * @param level
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 下午3:15:54
	 */
	List<AreaCache> getAreaByParentCode(String parenCode, int level);

	/**
	 * @Description 根据code获取行政区划名称
	 * @param code
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 下午4:04:18
	 */
	String getAreaNameByCode(String code);
}
