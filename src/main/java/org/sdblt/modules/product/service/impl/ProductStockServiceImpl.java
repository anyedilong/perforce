package org.sdblt.modules.product.service.impl;


import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.service.impl.BaseServiceImpl;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.product.dao.ProductDao;
import org.sdblt.modules.product.dao.ProductStockDao;
import org.sdblt.modules.product.domain.ProductStock;
import org.sdblt.modules.product.domain.ProductStockOperation;
import org.sdblt.modules.product.dto.ProductStockListDto;
import org.sdblt.modules.product.service.ProductStockService;
import org.sdblt.utils.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Named
public class ProductStockServiceImpl extends BaseServiceImpl<ProductStockDao, ProductStock>
		implements ProductStockService {

	/**
	 * 产品登记
	 */
	@Override
	@Transactional
	public void fitProStock(ProductStock proStock,UserCache user) {
		proStock.setStatus("10");
		if(!StringUtils.isNull(proStock.getId())){
			//产品是否存在
			if(dao.exists(proStock.getId())){
				//产品如果存在  验证产品的状态
				ProductStock proStockOld = dao.get(proStock.getId());
				String status = proStockOld.getStatus();
				if(status.indexOf("1") < 0){
					throw new CommonException(20001, "该库存不允许修改");
				}
			}
		}
		
		//修改库存信息
		dao.fitProStock(proStock,user);
		
	}
	/**
	 * 出库登记
	 * @Description (TODO)
	 * @param proStock
	 * @authorliuxingx
	 * @Date 2017年4月10日 下午3:25:35
	 */
	@Override
	@Transactional
	public int outProStock(ProductStock proStock, UserCache user) {
		String idList = proStock.getIdList();
		Date nowDate = new Date();
		int successCount = 0;
		if (idList != null) {
			String[] ids = idList.split(",");
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					//数据存在
					ProductStock stock = dao.get(ids[i]);
					if(null != stock){
						//数据可以入库?
						String status = stock.getStatus();
						if (proStock.getOutProxyUser() != null) {
							proStock.setId(ids[i]);
							proStock.setOutGoing(user.getUserId());
							proStock.setOutProxyAgent(user.getUserId());
							proStock.setOutProxyTime(nowDate);
							proStock.setOutProxyOperation(user.getUserId());
							proStock.setStatus("40");
							if("30".equals(status)||"50".equals(status)){
								//数据入库
								try{
									int count = dao.updateProStock(proStock);
									successCount+= count;
								}catch (Exception e) {
									
								}
								
							}
						}
						if (proStock.getOutCustomerUser() != null) {
							proStock.setId(ids[i]);
							proStock.setOutCustomerAgent(user.getUserId());
							proStock.setOutGoing(user.getUserId());
							proStock.setOutCustomerTime(nowDate);
							proStock.setOutCustomerOperation(user.getUserId());
							proStock.setStatus("50");
							if("30".equals(status)||"40".equals(status)){
								//数据入库
								try{
									int count = dao.updateProStock(proStock);
									successCount+= count;
								}catch (Exception e) {
									
								}
								
							}
						}
						
					}	
				}

			}
		}
		return successCount;

	}
	/**
	 * 产品登记的查询
	 */
	@Override
	public void getList(ProductStockListDto productStockDto, PageModel page) {
		
		dao.getList(productStockDto, page);
		
	}
	
	@Override
//	@Transactional(readOnly=true)
	@Transactional(propagation=Propagation.SUPPORTS)
	public int inProStock(List ids, String userId) {
		int successCount = 0;
		ProductDao pd = new ProductDao();
		Date nowDate = new Date();
		if (ids != null && ids.size() > 0 ) {
			for (Object idObj : ids) {
				String id = StringUtils.toString(idObj);
				if(!StringUtils.isNull(id)){
					
					//数据存在
					ProductStock stock = dao.get(id);
					if(null != stock){
						//数据可以入库?
						String status = stock.getStatus();
						if("20".equals(status)){
							//数据入库
							try{
								int count = dao.inProStock(id,userId,nowDate,stock.getProId());
								successCount += count;
							}catch (Exception e) {
								
							}
							
						}
						
					}
				}
			}
		}
		return successCount;
		
	}
	/**
	 * 产品入库显示
	 */
	@Override
	public void getInList(ProductStockListDto productStockDto, PageModel page) {
		dao.getInList(productStockDto, page);
		
	}

	
	/**
	 * 删除
	 */
	@Override
	public void batchUpdateForDel(List ids) {
		dao.batchUpdateForDel(ids);
		
	}
	
	/**
	 * 产品测试
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public int testProduct(List ids, String userId) {
		
		int successCount = 0;
		
		Date nowDate = new Date();
		if(null != ids || ids.size() > 0){
			for (Object idObj : ids) {
				String id = StringUtils.toString(idObj);
				if(!StringUtils.isNull(id)){
					try{
						int count = dao.testProductSuccess(id,userId,nowDate,"20");
						successCount += count;
					}catch(Exception e){
						
					}
					
				}
			}
		}
		return successCount;
	}
	/**
	 * 产品测试查询
	 */
	@Override
	public void getTestList(ProductStockListDto productStockDto, PageModel page) {
		dao.getTestList(productStockDto, page);
		
	}
	/**
	 * 产品测试失败
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public int errorProduct(List ids, String userId) {
		int errorCount=0;
		Date nowDate = new Date();
		if(null != ids || ids.size() > 0){
			for (Object idObj : ids) {
				String id = StringUtils.toString(idObj);
				if(!StringUtils.isNull(id)){
					try{
						int count = dao.testProductSuccess(id,userId,nowDate,"21");
						errorCount += count;
					}catch(Exception e){
						
					}
					
				}
			}
		}
		return errorCount;
	}
	@Override
	public void getProxyList(ProductStockListDto productStockDto, PageModel page) {
		dao.getProxyList(productStockDto, page);
		
	}
	@Override
	public void getOutList(ProductStockListDto productStockDto, PageModel page) {
		dao.getOutList(productStockDto, page);
		
	}
	@Override
	public void getLists(ProductStockListDto productStockDto, PageModel page) {
	
		dao.getList(productStockDto, page);
	}


}
