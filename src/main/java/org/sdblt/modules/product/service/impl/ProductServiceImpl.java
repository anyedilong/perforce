package org.sdblt.modules.product.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.product.dao.ProductDao;
import org.sdblt.modules.product.domain.Product;
import org.sdblt.modules.product.domain.ProductDevice;
import org.sdblt.modules.product.dto.ProductListDto;
import org.sdblt.modules.product.service.ProductService;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;
import org.springframework.transaction.annotation.Transactional;

@Named
public class ProductServiceImpl extends BaseServiceImpl<ProductDao, Product> implements ProductService {

	@Override
	public void getList(ProductListDto productParam, PageModel page) {
		dao.getList(productParam, page);
	}

	/**
	 * 保存
	 */
	@Override
	@Transactional
	public void savePruduct(Product product) {
		// 验证产品是否可以修改 状态 1 草稿 2 发布 3 撤销发布 4 删除
		if (StringUtils.isNull(product.getStatus())) {
			product.setStatus("1");
		}

		// 验证产品是否存在
		if (StringUtils.isNull(product.getId()) || !dao.exists(product.getId())) {
			dao.save(product);
		} else {
			// 产品修改
			int count = dao.updateProduct(product);
			if (count <= 0) {
				throw new CommonException(20001,"产品修改失败，请刷新列表后重试");
			}
		}
		
		//根据产品ID 删除
		dao.deleteDeviceByProid(product.getId());
		
		//批量保存
		List<ProductDevice> proDeviceList = product.getProDeviceList();
		if(null != proDeviceList && proDeviceList.size() > 0){
			for (ProductDevice productDevice : proDeviceList) {
				productDevice.setId(UUIDUtil.getUUID());
				productDevice.setProId(product.getId());
			}
			dao.batchSaveProDevice(proDeviceList);
		}
		

	}

	/**
	 * 产品发布
	 */
	@Override
	@Transactional
	public void release(String id, String releaseUser, Date releaseTime) {
		
		Product  product =  dao.get(id);
		// 验证产品是否存在
		if (null == product) {
			throw new CommonException(20001,"产品不存在，请刷新列表后操作");
		}
		// 产品发布
		int count = dao.release(id, releaseUser, releaseTime);
		if (count <= 0) {
			throw new CommonException(20002,"产品发布失败，请刷新列表后重试");
		}

	}

	/**
	 * 撤销发布
	 */
	@Override
	@Transactional
	public void revokeRelease(String id, String updateUser, Date updateDate) {
		Product  product =  dao.get(id);
		// 验证产品是否存在
		if (null == product) {
			throw new CommonException(20001,"产品不存在，请刷新列表后操作");
		}
		// 产品撤销发布
		int count = dao.revokeRelease(id, updateUser, updateDate);
		if (count <= 0) {
			throw new CommonException(20003,"产品撤销失败，请刷新列表后重试");
		}
	}

	/**
	 * 删除
	 */
	@Override
	@Transactional
	public void updateForDelete(String id, String updateUser, Date updateDate) {
		// 验证产品是否存在
		if (!dao.exists(id)) {
			throw new CommonException(20001,"产品不存在，请刷新列表后操作");
		}
		// 产品删除
		int count = dao.updateForDelete(id, updateUser, updateDate);
		if (count <= 0) {
			throw new CommonException(20004,"产品删除失败，请刷新列表后重试");
		}
	}

	/**
	 * 修改库存
	 */
	@Override
	@Transactional
	public void updateStork(String id, int proStork) {
		// 验证产品是否存在
		if (!dao.exists(id)) {
			throw new CommonException(20001,"产品不存在，请刷新列表后操作");
		}
		//修改产品库存
		dao.updateStork(id, proStork);
	}

	@Override
	public List<ProductListDto> getProductListByType(String proType) {
		return dao.getProductListByType(proType);
	}

}
