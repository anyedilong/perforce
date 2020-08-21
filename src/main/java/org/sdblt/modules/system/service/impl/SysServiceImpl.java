package org.sdblt.modules.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.modules.common.utils.cache.AreaCache;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.common.utils.cache.MenuCache;
import org.sdblt.modules.common.utils.cache.OrgCache;
import org.sdblt.modules.common.utils.cache.ProductTypeCache;
import org.sdblt.modules.common.utils.cache.SysUserCache;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.system.dao.MenuDao;
import org.sdblt.modules.system.dao.SysDao;
import org.sdblt.modules.system.domain.SysUser;
import org.sdblt.modules.system.service.SysService;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional(readOnly = true)
public class SysServiceImpl implements SysService {

	@Inject
	private SysDao dao;
	@Inject
	private MenuDao menuDao;

	@Override
	public SysUser getUserByUsername(String username) {
		return dao.getUserByUsername(username);
	}

	@Override
	public UserCache getUserInfo(String username, String securityToken) {

		UserCache user = dao.getUserInfo(username, securityToken);

		if (null != user) {
			// 获取 用户 所有菜单和操作
			List<MenuCache> userMenuAndHandleList = null;

			// 如果为admin 则为所有
			String superAdmin = "admin";

			if (superAdmin.equals(username)) {
				userMenuAndHandleList = menuDao.getAllMenuAndHandleList();
			} else {
				List<MenuCache> menuAndHandleList = menuDao.getAllMenuAndHandleList();

				// 获取用户授权信息
				List<String> userMenuIdArray = dao.getMenuByUser(user.getUserId());

				List<MenuCache> userMenuList = menuAndHandleList
						.stream().filter(
								menu -> userMenuIdArray.indexOf(menu.getId()) >=0)
						.collect(Collectors.toList());
				
				userMenuAndHandleList = menuAndHandleList.stream().filter(menu -> userMenuList.stream()
						.filter(menu1 -> menu.getId().equals(menu1.getParentId()) || menu.getId().equals(menu1.getId())
								|| menu.getParentIdAll().indexOf(menu1.getId()) >= 0)
						.count() > 0).collect(Collectors.toList());
				
			}

			List<MenuCache> menuNav = new ArrayList<>();// 菜单导航
			Map<String, MenuCache> menuMap = new HashMap<>();// 菜单map
			Map<String, List<MenuCache>> handleMap = new HashMap<>();

			if (null != userMenuAndHandleList && userMenuAndHandleList.size() > 0) {
				for (MenuCache menuCache : userMenuAndHandleList) {
					if (null != menuCache) {
						if (menuCache.getMenuLevel() == 1) {
							menuNav.add(menuCache);
							menuMap.put(menuCache.getId(), menuCache);
						} else {
							// 如果存在
							if (menuMap.containsKey(menuCache.getParentId())) {
								menuMap.get(menuCache.getParentId()).getChildren().add(menuCache);
								menuMap.put(menuCache.getId(), menuCache);
							}

							if ("2".equals(menuCache.getType())) {
								// 如果为操作
								List<MenuCache> handleList = handleMap.get(menuCache.getParentId());
								if (null == handleList) {
									handleList = new ArrayList<>();
									handleMap.put(menuCache.getParentId(), handleList);
								}
								handleList.add(menuCache);
							} else {
							}
						}
					}
				}
			}

			//机构tree
			List<OrgCache> orgAllList = dao.getOrgList(user.getOrgId());
			//用户管辖机构
			List<String> mrgOrgIdArray1 = null;
			List<OrgCache> orgTree = null;
			
			if (superAdmin.equals(username)) {
				mrgOrgIdArray1 = new ArrayList<>();
				mrgOrgIdArray1.add("0");
				orgTree = orgAllList;
			}else{
				mrgOrgIdArray1 = dao.getMrgOrgIds(user.getUserId());
				List<String> mrgOrgIdArray = mrgOrgIdArray1;

				List<OrgCache> userOrgList = orgAllList.stream()
						.filter(org -> mrgOrgIdArray.indexOf(org.getId()) >= 0)
						.collect(Collectors.toList());

				orgTree = orgAllList.stream().filter(org -> userOrgList.stream()
						.filter(userMrgorg -> org.getId().equals(userMrgorg.getParentId()) || org.getId().equals(userMrgorg.getId())
								|| org.getParentIdAll().indexOf(userMrgorg.getId()) >= 0)
						.count() > 0).collect(Collectors.toList());
			}
			if(null != orgTree){
				List<String> mrgOrgList = orgTree.stream().map(OrgCache::getId).collect(Collectors.toList());
				user.setMrgOrgList(mrgOrgList);
			}
			
			user.setOrgTree(orgTree);
			user.setMenuNav(menuNav);
			user.setMenuMap(menuMap);
			user.setHandleMap(handleMap);

		}

		return user;
	}

	@Override
	public void initData() {
		
		//初始化缓存信息
		initSysCacheData();
	}

	/**
	 * @Description 初始化缓存信息
	 * @author sen
	 * @Date 2017年3月17日 下午4:33:18
	 */
	private void initSysCacheData() {
		//初始化行政区划
		initAreaCache();
		//初始化用户缓存
		initUserCache();
		//初始化字典缓存
		initDictCache();
		//初始化产品类型
		initProductType();
	}
	
	/**
	 * 
	 * @Description 初始化行政区划缓存
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 下午2:49:33
	 */
	public List<AreaCache> initAreaCache() {
		List<AreaCache> areaList = CacheUtils.getArray(CacheEmun.SYS_CACHE, CacheUtils.SYS_AREA, AreaCache.class);
		// 如果数据库为空，数据库查询
		if (null == areaList) {
			areaList = dao.getAreaCache();
			CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.SYS_AREA, areaList);
		}
		return areaList;
	}

	/**
	 * @Description 初始化用户缓存
	 * @author sen
	 * @Date 2017年3月17日 下午4:38:44
	 */
	public List<SysUserCache> initUserCache() {
		List<SysUserCache> userList = CacheUtils.getArray(CacheEmun.SYS_CACHE, CacheUtils.SYS_USER, SysUserCache.class);
		//如果数据库为空，数据库查询
		if(null == userList){
			userList = dao.getUserCache();
			CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.SYS_USER, userList);
		}
		
		return userList;
	}
	
	/**
	 * @Description 初始化字典缓存
	 * @author sen
	 * @Date 2017年3月17日 下午4:38:44
	 */
	public List<DictCache> initDictCache() {
		List<DictCache> dictList = CacheUtils.getArray(CacheEmun.SYS_CACHE, CacheUtils.SYS_DICT, DictCache.class);
		//如果数据库为空，数据库查询
		if(null == dictList){
			dictList = dao.getDictCache();
			CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.SYS_DICT, dictList);
		}
		
		return dictList;
	}

	/**
	 * 初始化产品类型
	 */
	public List<ProductTypeCache> initProductType() {

		List<ProductTypeCache> productTypeList = CacheUtils.getArray(CacheEmun.SYS_CACHE, CacheUtils.PRODUCT_TYPE, ProductTypeCache.class);
		
		if(null == productTypeList){
			productTypeList = dao.getPruductType();
			CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.PRODUCT_TYPE, productTypeList);
		}
		return productTypeList;
	}

	@Override
	public List<AreaCache> getAreaByParentCode(String parenCode, int level) {
		return dao.getAreaByParentCode(parenCode,level);
	}

	@Override
	public String getAreaNameByCode(String code) {
		return dao.getAreaNameByCode(code);
	}

}
