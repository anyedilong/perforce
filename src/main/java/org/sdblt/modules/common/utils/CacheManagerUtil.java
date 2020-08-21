package org.sdblt.modules.common.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sdblt.common.spring.InstanceFactory;
import org.sdblt.modules.common.utils.cache.AreaCache;
import org.sdblt.modules.common.utils.cache.DictCache;
import org.sdblt.modules.common.utils.cache.ProductTypeCache;
import org.sdblt.modules.common.utils.cache.SysUserCache;
import org.sdblt.modules.system.domain.DictSub;
import org.sdblt.modules.system.service.SysService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.cache.CacheEmun;
import org.sdblt.utils.cache.CacheUtils;
import org.sdblt.utils.properties.PropertiesUtil;

public class CacheManagerUtil {

	/**
	 * @Description 根据 字典值和字典code 获取字典明细
	 * @param propDict
	 *            配置文件字典code
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 下午5:05:55
	 */
	public static List<DictCache> getDictListByCode(String propDict) {
		
		if(StringUtils.isNull(propDict)){
			return null;
		}

		String dictCode = PropertiesUtil.getDict(propDict);

		List<DictCache> dictAllList = InstanceFactory.getInstance(SysService.class).initDictCache();

		if (null == dictAllList) {
			return null;
		}

		// 字典 过滤 排序

		List<DictCache> dictList = dictAllList.stream().filter(dict -> dictCode.equals(dict.getCode()))
				.sorted(Comparator.comparing(DictCache::getOrderNum)).collect(Collectors.toList());

		return dictList;

	}

	/**
	 * @Description 根据字典ID 获取字典明细列表
	 * @param id
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 下午5:17:06
	 */
	public static DictCache getDictById(String id) {

		if(StringUtils.isNull(id)){
			return null;
		}
		
		List<DictCache> dictAllList = InstanceFactory.getInstance(SysService.class).initDictCache();

		if (null == dictAllList) {
			return null;
		}

		// 字典 过滤 排序
		DictCache dictQuery = new DictCache(id);
		int index = dictAllList.indexOf(dictQuery);
		if (index >= 0) {
			return dictAllList.get(index);
		}

		return null;
	}

	/**
	 * 
	 * @Description
	 * @param id
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 下午5:55:08
	 */
	public static void saveDict(DictSub dictSub) {
		
		if(null == dictSub){
			return ;
		}

		List<DictCache> dictAllList = InstanceFactory.getInstance(SysService.class).initDictCache();

		if (null == dictAllList) {
			dictAllList = new ArrayList<>();
		}

		String id = dictSub.getId();

		// 字典 过滤 排序
		DictCache dict = null;
		// 字典 过滤 排序
		DictCache dictQuery = new DictCache(id);
		int index = dictAllList.indexOf(dictQuery);
		if (index >= 0) {
			dict = dictAllList.get(index);
		}

		if (null == dict) {
			dict = new DictCache();
			dict.setId(dictSub.getId());
			dict.setCode(dictSub.getCode());
			dict.setDictId(dictSub.getDictId());
			dict.setText(dictSub.getText());
			dict.setValue(dictSub.getValue());
			dict.setOrderNum(dictSub.getOrderNum());
			dict.setStatus(dictSub.getStatus());
			dictAllList.add(dict);
		} else {
			dict.setText(dictSub.getText());
			dict.setValue(dictSub.getValue());
			dict.setOrderNum(dictSub.getOrderNum());
			dict.setStatus(dictSub.getStatus());
		}

		CacheUtils.put(CacheEmun.SYS_CACHE, CacheUtils.SYS_DICT, dictAllList);

	}

	/**
	 * @Description 根据value 获取文本 多个value
	 * @param propDict
	 * @param values
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 下午5:24:33
	 */
	public static String getTextByValues(String propDict, String values) {
		
		if(StringUtils.isNull(propDict) || StringUtils.isNull(values)){
			return "";
		}
		
		String dictCode = PropertiesUtil.getDict(propDict);
		// Stream<String> valueStream = Stream.of(values.split(","));
		String[] valueArray = values.split(",");

		List<DictCache> dictAllList = InstanceFactory.getInstance(SysService.class).initDictCache();

		if (null == dictAllList) {
			return null;
		}

		// 字典 过滤 排序
		String rs = "";
		List<DictCache> dictList = dictAllList.stream().filter(dict -> dictCode.equals(dict.getCode()))
				.collect(Collectors.toList());

		if (null != dictList && dictList.size() > 0) {
			for (String value : valueArray) {
				// 字典 过滤 排序
				DictCache dict = null;
				// 字典 过滤 排序
				DictCache dictQuery = new DictCache();
				dictQuery.setCode(dictCode);
				dictQuery.setValue(value);

				int index = dictAllList.indexOf(dictQuery);
				if (index >= 0) {
					dict = dictAllList.get(index);
					if (null != dict && !StringUtils.isNull(dict.getText())) {
						rs += dict.getText() + ",";
					}
				}
			}
		}

		if (rs.length() > 0) {
			rs = rs.substring(0, rs.length() - 1);
		}

		// List<String> textList = dictAllList.stream()
		// .filter(dict -> dictCode.equals(dict.getCode())
		// && (valueStream.filter(value ->
		// value.equals(dict.getValue())).count() > 0))
		// .sorted(Comparator.comparing(DictCache::getOrderNum)).map(DictCache::getText)
		// .collect(Collectors.toList());

		return rs;
	}

	/**
	 * @Description 根据value 获取文本
	 * @param propDict
	 * @param value
	 * @return
	 * @author sen
	 * @Date 2017年3月17日 下午5:24:33
	 */
	public static String getTextByValue(String propDict, String value) {
		
		if(StringUtils.isNull(propDict) || StringUtils.isNull(value)){
			return "";
		}

		String dictCode = PropertiesUtil.getDict(propDict);

		List<DictCache> dictAllList = InstanceFactory.getInstance(SysService.class).initDictCache();

		if (null == dictAllList) {
			return null;
		}

		// 字典 过滤 排序
		DictCache dictQuery = new DictCache();
		dictQuery.setCode(dictCode);
		dictQuery.setValue(value);

		int index = dictAllList.indexOf(dictQuery);
		if (index >= 0) {
			return dictAllList.get(index).getText();
		}

		return "";
	}

	/**
	 * @Description 获取产品类型
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午1:36:39
	 */
	public static List<ProductTypeCache> getProTypeList() {
		List<ProductTypeCache> productTypeList = InstanceFactory.getInstance(SysService.class).initProductType();
		return productTypeList;
	}

	/**
	 * @Description 根据ID 获取产品类型
	 * @param proTypeId
	 * @return
	 * @author sen
	 * @Date 2017年3月21日 下午1:40:13
	 */
	public static String getProTypeNameById(String proTypeId) {
		
		if(StringUtils.isNull(proTypeId)){
			return "";
		}
		
		String rs = "";
		List<ProductTypeCache> productTypeList = getProTypeList();
		if (null != productTypeList && productTypeList.size() > 0) {
			// 字典 过滤 排序
			ProductTypeCache proTypeQuery = new ProductTypeCache(proTypeId);
			int index = productTypeList.indexOf(proTypeQuery);
			if (index >= 0) {
				rs = productTypeList.get(index).getTypeName();
			}
		}
		return rs;
	}

	/**
	 * @Description 根据用户ID 获取用户姓名
	 * @param userId
	 * @return
	 * @author sen
	 * @Date 2017年3月23日 下午7:40:43
	 */
	public static String getUserNameById(String userId) {
		
		if(StringUtils.isNull(userId)){
			return "";
		}
		
		String rs = "";
		List<SysUserCache> userList = InstanceFactory.getInstance(SysService.class).initUserCache();

		if (null != userList && userList.size() > 0) {
			// 字典 过滤 排序
			SysUserCache userCache = new SysUserCache();
			userCache.setId(userId);
			int index = userList.indexOf(userCache);
			if (index >= 0) {
				rs = userList.get(index).getName();
			}
		}
		return rs;
	}

	/**
	 * @Description 根据上级编码和等级获取行政区划列表
	 * @param parentId
	 * @param level
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 下午2:57:17
	 */
	public static List<AreaCache> getAreaList(String parentCode, int level) {
		List<AreaCache> areaAllList = InstanceFactory.getInstance(SysService.class).initAreaCache();

		// 判断数据存在
		if (null != areaAllList && areaAllList.size() > 0) {
			// 存放结果
			List<AreaCache> areaList = new ArrayList<>();
			for (AreaCache areaCache : areaAllList) {
				String areaCode = areaCache.getAreaCode();

				int areaLevel = areaCache.getAreaLevel();
				if (areaLevel == level && areaCode.indexOf(parentCode) == 0) {
					areaList.add(areaCache);
				}
			}
			areaList.sort(Comparator.comparing(AreaCache::getAreaCode));
			return areaList;
		}
		return null;
	}
	
	/**
	 * @Description 根据code获取name
	 * @param parentId
	 * @param level
	 * @return
	 * @author yuanwentian
	 * @Date 2017年3月24日 下午2:57:17
	 */
	public static String getAreaNameByCode(String code) {
		if(StringUtils.isNull(code)){
			return "";
		}
		String areaName = "";
		if(code.length() > 6){
			//数据库取
			areaName = InstanceFactory.getInstance(SysService.class).getAreaNameByCode(code);
		}else{
			//缓存中取
			List<AreaCache> areaList = InstanceFactory.getInstance(SysService.class).initAreaCache();
			if(areaList != null){
				// 字典 过滤 排序
				AreaCache areaQuery = new AreaCache();
				areaQuery.setAreaCode(code);

				int index = areaList.indexOf(areaQuery);
				if (index >= 0) {
					areaName = areaList.get(index).getAreaName();
				}
			}
		}
		
		return areaName;
	}

}
