package org.sdblt.modules.common.utils.cache;

import java.io.Serializable;

import org.sdblt.utils.StringUtils;

/**
 * @ClassName DictCache
 * @Description 行政区划缓存
 * @author sen
 * @Date 2017年3月17日 下午4:46:49
 * @version 1.0.0
 */
public class AreaCache implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String areaName;// 行政区划名称
	private String areaCode;// 行政区划code
	private int areaLevel;// 行政区划等级

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(int areaLevel) {
		this.areaLevel = areaLevel;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		AreaCache area = (AreaCache) obj;

		// 如果ID相同
		if (!StringUtils.isNull(getAreaCode()) && getAreaCode().equals(area.getAreaCode())) {
			return true;
		}

		return false;
	}

}
