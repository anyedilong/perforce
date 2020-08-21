package org.sdblt.modules.product.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.common.utils.cache.UserCache;
import org.sdblt.modules.product.dao.repository.ProductStockDeviceRepository;
import org.sdblt.modules.product.dao.repository.ProductStockOperationRepository;
import org.sdblt.modules.product.dao.repository.ProductStockRepository;
import org.sdblt.modules.product.dao.repository.UpgradeLogRepository;
import org.sdblt.modules.product.domain.ProductStock;
import org.sdblt.modules.product.domain.ProductStockDevice;
import org.sdblt.modules.product.domain.ProductStockOperation;
import org.sdblt.modules.product.domain.UpgradeLog;
import org.sdblt.modules.product.dto.ProductStockListDto;
import org.sdblt.utils.StringUtils;
import org.sdblt.utils.UUIDUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName ProductDao
 * @Description 产品数据操作层
 * @author sen
 * @Date 2017年3月15日 上午10:29:16
 * @version 1.0.0
 */
@Named
public class ProductStockDao extends BaseDao<ProductStockRepository, ProductStock>{

	@Inject
	private ProductStockDeviceRepository stockDeviceRepository;
	
	@Inject
	private UpgradeLogRepository logRepository; 
	
	@Inject
	private ProductStockOperationRepository psoRepository;
	
	/**
	 * @Description 登记
	 * @param proStock
	 * @author liuxingx
	 * @Date 2017年3月31日 下午5:34:08
	 */
	public void fitProStock(ProductStock proStock,UserCache user) {
		Date Time = new Date();
		proStock.setFitTime(Time);
		
			if(StringUtils.isNull(proStock.getId())){
				save(proStock);
				//删除库存设备信息
				deleteStockDeviceById(proStock.getId());
				// TODO 加入操作日志
				UpgradeLog uL = new UpgradeLog();
				uL.setProStockId(proStock.getId());
				uL.setStatus("1");
				Date nowDate = new Date();
				uL.setUpgradeTime(nowDate);
				saveLog(uL);
				
				//TODO 操作历史
				ProductStockOperation pso=new ProductStockOperation();
				pso.setProStockId(proStock.getId());
				pso.setOperationType(proStock.getStatus());
				Date time=new Date();
				pso.setCreateTime(time);
				pso.setCreateUser(user.getUserId());
				pso.setOperationUser(user.getUserId());
				pso.setOperationTime(time);
				saveOperation(pso);
			}else{
				save(proStock);
				//删除库存设备信息
				deleteStockDeviceById(proStock.getId());
			}
			
		//新增库存设备
		List<ProductStockDevice> proStockDeviceList = proStock.getProStockDeviceList();
		if(null != proStockDeviceList && proStockDeviceList.size() > 0){
			for (ProductStockDevice stockDevice : proStockDeviceList) {
				stockDevice.setId(UUIDUtil.getUUID());
				stockDevice.setProStockId(proStock.getId());
			}
			batchSaveProStockDevice(proStockDeviceList);
		}
		
	}
	/**
	 * 
	 * @Description 出库登记
	 * @param proStock
	 * @authorliuxingx
	 * @Date 2017年4月11日 上午10:35:05
	 */
	public int updateProStock(ProductStock productStock) {
		
		// TODO 加入操作日志
				UpgradeLog uL = new UpgradeLog();
				uL.setProStockId(productStock.getId());
				uL.setStatus("1");
				Date nowDate = new Date();
				uL.setUpgradeTime(nowDate);
				saveLog(uL);
				
				//TODO 操作历史
				ProductStockOperation pso=new ProductStockOperation();
				pso.setProStockId(productStock.getId());
				pso.setOperationType(productStock.getStatus());
				Date time=new Date();
				pso.setOperationUser(productStock.getOutGoing());
				pso.setOperationTime(time);
				pso.setCreateTime(time);
				saveOperation(pso);
				StringBuffer sql = new StringBuffer();
				sql.append(" update t_vm_product_stock ps set  ");
		
		if (!StringUtils.isNull(productStock.getOutProxyUser())) {
			productStock.setOutProxyUser(pso.getId());
			sql.append(" ps.out_proxy_user=:outProxyUser, ");
			sql.append(" ps.out_proxy_operation=:outProxyOperation, ");
		}
		if (!StringUtils.isNull(productStock.getOutProxyTime())) {
			sql.append(" ps.out_proxy_time=:outProxyTime, ");
		}
		if (!StringUtils.isNull(productStock.getOutCustomerUser())) {
			productStock.setOutCustomerOperation(pso.getId());
			sql.append(" ps.out_customer_user=:outCustomerUser, ");
			sql.append(" ps.out_customer_operation=:outCustomerOperation, ");
		}
		if (!StringUtils.isNull(productStock.getOutCustomerTime())) {
			sql.append(" ps.out_customer_time=:outCustomerTime, ");
		}
		if (!StringUtils.isNull(productStock.getOutGoing())) {
			sql.append(" ps.out_going=:outGoing, ");
		}
		if (!StringUtils.isNull(productStock.getOutCustomerAgent())) {
			sql.append(" ps.out_customer_agent=:outCustomerAgent, ");
		}
		if (!StringUtils.isNull(productStock.getOutProxyAgent())) {
			sql.append(" ps.out_proxy_agent=:outProxyAgent, ");
		}
		sql.append(" ps.status=:status");
		sql.append(" where ps.id=:id ");
		
			sql.append(" and (ps.status = '30' or ps.status = '40' or ps.status = '50') ");
			sql.append(" and (ps.status !=:status) ");
		int count=execute(sql.toString(), productStock);
		if (count == 1) {
			// 修该库存
			String updateProSql = " update t_vm_product p set p.pro_stork = p.pro_stork-1 where p.id=:proId ";
			Map<String, Object> updateProParamMap = new HashMap<>();
			updateProParamMap.put("proId", productStock.getProId());
			execute(updateProSql, updateProParamMap);
			return 1;
		} else {
			throw new CommonException(-1, "");
		}
	}
	/**
	 * @Description 保存日志
	 * @authorliuxingx
	 * @Date 2017年4月10日 上午9:33:22
	 */
	private void saveLog(UpgradeLog entity) {
		if(StringUtils.isNull(entity.getId())){
			entity.setId(UUIDUtil.getUUID());
		}
		logRepository.save(entity);
	}
	/**
	 * 
	 * @Description 保存操作历史
	 * @param entity
	 * @author yixiaoli
	 * @Date 2017年4月12日 上午9:23:59
	 */
	@Transactional(readOnly = false)
	public void saveOperation(ProductStockOperation entity) {
		if(StringUtils.isNull(entity.getId())){
			entity.setId(UUIDUtil.getUUID());
		}
		
		psoRepository.save(entity);
	}
	/**
	 * 
	 * @Description 删除库存设备
	 * @param id
	 * @author liuxingx
	 * @Date 2017年3月31日 下午5:28:51
	 */
	private void deleteStockDeviceById(String id) {
		stockDeviceRepository.deleteByProId(id);
	}
	
	 public void  getLists(ProductStockListDto productStockDto, PageModel page){
				StringBuffer sql = new StringBuffer();
				sql.append("select p.pro_name proName,ps.out_going,ps.fit_operation, ")
				.append("         pt.type_name proType, ")
				.append("         ps.pro_num proNum, ")
				.append("    ps.id,p.pro_stork, ")
				.append("   ps.status,ps.pro_id proId, ")
				.append(" pt.code,pt.type_name,")
				.append("   ps.fit_user, ")
				.append("   ps.fit_time, ")
				.append("        (select listagg(to_char(psd.device_num) || ';' || ")
				.append("    to_char(psd.device_type), ")
				.append("    ',') WITHIN GROUP(ORDER BY psd.device_type) ")
				.append("           from t_vm_product_stock_device psd ")
				.append("          where psd.pro_stock_id = ps.id) proStockDevices ")
				.append("   from t_vm_product_stock ps ")
				.append("   join t_vm_product p ")
				.append("     on p.id = ps.pro_id ")
				.append("   join t_vm_product_type pt ")
				.append("     on p.pro_type = pt.id ");
				sql.append(" where 1=1 and exists (select id from t_vm_product_stock_device psd where psd.pro_stock_id=ps.id");
			if(!StringUtils.isNull(productStockDto.getDeviceCode())){
				sql.append("and psd.device_num like CONCAT('%',CONCAT(:deviceCode,'%'))");
			}

			if(!StringUtils.isNull(productStockDto.getDeviceType())){
				sql.append(" and psd.device_type like CONCAT('%',CONCAT(:deviceType,'%'))");
			}
			    sql.append(")");
			if(!StringUtils.isNull(productStockDto.getStatus())){
				sql.append(" and ps.status like CONCAT('%',CONCAT(:status,'%'))");
			}
			queryPageList(null, sql, page, null);
				queryPageList(sql.toString(), productStockDto, page, ProductStockListDto.class);
				
			
	 }
	
	/**
	 * 
	 * @Description 查询
	 * @param productStockDto
	 * @param page
	 * @author liuxingx
	 * @Date 2017年4月1日 下午3:30:17
	 */
	public void getList(ProductStockListDto productStockDto, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select p.pro_name proName,ps.out_going, ")
		.append("         pt.type_name proType, ")
		.append("         ps.pro_num proNum, ")
		.append("    ps.id,p.pro_stork, ")
		.append("   ps.status,ps.pro_id proId, ")
		.append("  pt.code,pt.type_name,")
		.append("   ps.fit_time,u.name, ")
		.append("        (select listagg(to_char(psd.device_num) || ';' || ")
		.append("    to_char(psd.device_type), ")
		.append("    ',') WITHIN GROUP(ORDER BY psd.device_type) ")
		.append("           from t_vm_product_stock_device psd ")
		.append("          where psd.pro_stock_id = ps.id) proStockDevices ")
		.append("   from t_vm_product_stock ps ")
		.append("   left join sys_user u on ps.fit_user=u.id ")
		.append("   join t_vm_product p ")
		.append("     on p.id = ps.pro_id ")
		.append("   join t_vm_product_type pt ")
		.append("     on p.pro_type = pt.id ");
		sql.append(" where (ps.status='10' or ps.status like '%1') and exists (select id from t_vm_product_stock_device psd where psd.pro_stock_id=ps.id");
		sql.append(" and ps.pro_num like CONCAT('%',CONCAT(:proNum,'%'))");

	if(!StringUtils.isNull(productStockDto.getProId())){
		sql.append(" and ps.pro_id = :proId");
	}
	    sql.append(") order by fit_time");
//	if(!StringUtils.isNull(productStockDto.getStatus())){
//		sql.append(" and ps.status like CONCAT('%',CONCAT(:status,'%'))");
//	}
		queryPageList(sql.toString(), productStockDto, page, ProductStockListDto.class);
		
	}
	/**
	 * 入库列表显示
	 * @Description (TODO)
	 * @param productStockDto
	 * @param page
	 * @authorliuxingx
	 * @Date 2017年4月13日 上午8:40:47
	 */
	public void getInList(ProductStockListDto productStockDto, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.pro_name proName,ps.out_going,ps.test_user, ")
		.append("         pt.type_name proType, ")
		.append("         ps.pro_num proNum, ")
		.append("    ps.id,p.pro_stork, ")
		.append("   ps.status,ps.pro_id proId, ")
		.append(" pt.code,pt.type_name,")
		.append("   ps.fit_user, ")
		.append("   ps.fit_time, ")
		.append("        (select listagg(to_char(psd.device_num) || ';' || ")
		.append("    to_char(psd.device_type), ")
		.append("    ',') WITHIN GROUP(ORDER BY psd.device_type) ")
		.append("           from t_vm_product_stock_device psd ")
		.append("          where psd.pro_stock_id = ps.id) proStockDevices ")
		.append("   from t_vm_product_stock ps ")
		.append("   join t_vm_product p ")
		.append("     on p.id = ps.pro_id ")
		.append("   join t_vm_product_type pt ")
		.append("     on p.pro_type = pt.id ");
		sql.append(" where ps.status='20' and exists (select id from t_vm_product_stock_device psd where psd.pro_stock_id=ps.id");
		if(!StringUtils.isNull(productStockDto.getDeviceCode())){
			sql.append("and psd.device_num like CONCAT('%',CONCAT(:deviceCode,'%'))");
		}

		if(!StringUtils.isNull(productStockDto.getDeviceType())){
			sql.append(" and psd.device_type like CONCAT('%',CONCAT(:deviceType,'%'))");
		}
		    sql.append(")  order by  ps.test_time desc ");
	if(!StringUtils.isNull(productStockDto.getStatus())){
		sql.append(" and ps.status like CONCAT('%',CONCAT(:status,'%'))");
	}
		queryPageList(sql.toString(), productStockDto, page, ProductStockListDto.class);
		
	}
	/**
	 * @Description 批量保存库存设备
	 * @param proStockDeviceList
	 * @author liuxingx
	 * @Date 2017年3月31日 下午5:32:52
	 */
	private void batchSaveProStockDevice(List<ProductStockDevice> proStockDeviceList) {
		stockDeviceRepository.save(proStockDeviceList);
	}
	
	/**
	 * @Description 
	 * @param id
	 * @param userId
	 * @param nowDate
	 * @return
	 * @authorliuxingx
	 * @Date 2017年4月12日 下午4:50:59
	 */
	@Transactional
	public int inProStock(String id, String userId, Date nowDate, String proId) {
		String status = "30";
		// 存入历史
		ProductStockOperation pso = new ProductStockOperation();
		pso.setProStockId(id);
		pso.setOperationType(status);
		pso.setOperationUser(userId);
		pso.setOperationTime(nowDate);
		pso.setCreateTime(nowDate);
		saveOperation(pso);
		// 入库
		String inStockSql = " update t_vm_product_stock ps set ps.status=:status ,ps.storage_operation=:operationId ,ps.storage_user=:userId, ps.storage_time=:nowDate ";
		inStockSql += " where  ps.status='20' and ps.id=:id  ";
		Map<String, Object> inParamMap = new HashMap<>();
		inParamMap.put("id", id);
		inParamMap.put("userId", userId);
		inParamMap.put("nowDate", nowDate);
		inParamMap.put("operationId", pso.getId());
		inParamMap.put("status", status);

		int count = execute(inStockSql, inParamMap);
		if (count == 1) {
			// 修该库存
			String updateProSql = " update t_vm_product p set p.pro_stork = p.pro_stork+1 where p.id=:proId ";
			Map<String, Object> updateProParamMap = new HashMap<>();
			updateProParamMap.put("proId", proId);
			execute(updateProSql, updateProParamMap);
			return 1;
		} else {
			throw new CommonException(-1, "");
		}
	}

	/**
	 * 
	 * @Description 批量删除
	 * @param ids
	 * @author yixiaoli
	 * @Date 2017年4月11日 上午9:42:30
	 */
	public void batchUpdateForDel(List ids) {
		
		repository.batchUpdateForDel(ids);
	}
	/**
	 * 
	 * @Description 测试查询
	 * @param productStockDto
	 * @param page
	 * @author yixiaoli
	 * @Date 2017年4月13日 上午8:36:25
	 */
	public void getTestList(ProductStockListDto productStockDto, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.pro_name proName,ps.out_going,ps.fit_operation, ")
		.append("         pt.type_name proType, ")
		.append("         ps.pro_num proNum, ")
		.append("    ps.id,p.pro_stork, ")
		.append("   ps.status,ps.pro_id proId, ")
		.append(" pt.code,pt.type_name,")
		.append("   ps.fit_user, ")
		.append("   ps.fit_time, ")
		.append("        (select listagg(to_char(psd.device_num) || ';' || ")
		.append("    to_char(psd.device_type), ")
		.append("    ',') WITHIN GROUP(ORDER BY psd.device_type) ")
		.append("           from t_vm_product_stock_device psd ")
		.append("          where psd.pro_stock_id = ps.id) proStockDevices ")
		.append("   from t_vm_product_stock ps ")
		.append("   join t_vm_product p ")
		.append("     on p.id = ps.pro_id ")
		.append("   join t_vm_product_type pt ")
		.append("     on p.pro_type = pt.id ");
		sql.append(" where ps.status='10' and ( ps.machine_code!='' or ps.machine_code is not null)  and exists (select id from t_vm_product_stock_device psd where psd.pro_stock_id=ps.id");
		if (!StringUtils.isNull(productStockDto.getDeviceCode())
				|| !StringUtils.isNull(productStockDto.getDeviceType())) {
			sql.append(" and exists (select id from t_vm_product_stock_device psd where psd.pro_stock_id=ps.id ");

			/**
			if (!StringUtils.isNull(productStockDto.getDeviceCode())) {
				sql.append("and psd.device_num like CONCAT('%',CONCAT(:deviceCode,'%'))");
			}

			if (!StringUtils.isNull(productStockDto.getDeviceType())) {
				sql.append(" and psd.device_type like CONCAT('%',CONCAT(:deviceType,'%'))");
			}
			
			 **/
			sql.append(") ");
		}
		
		if(!StringUtils.isNull(productStockDto.getProNum())){
			sql.append(" and ps.pro_num like CONCAT('%',CONCAT(:proNum,'%'))");
		}
		if(!StringUtils.isNull(productStockDto.getProId())){
			sql.append(" and ps.pro_id = :proId");
		}
		    sql.append(") order by fit_time");
		queryPageList(sql.toString(), productStockDto, page, ProductStockListDto.class);
		
	}
	 
	/**
	 * 
	 * @Description 代理商出库
	 * @param productStockDto
	 * @param page
	 * @author yixiaoli
	 * @Date 2017年4月13日 上午9:19:36
	 */
	public void getProxyList(ProductStockListDto productStockDto, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.pro_name proName,ps.out_going,ps.storage_user, ")
		.append("         pt.type_name proType, ")
		.append("         ps.pro_num proNum, ")
		.append("    ps.id,p.pro_stork, ")
		.append("   ps.status,ps.pro_id proId, ")
		.append(" pt.code,pt.type_name,")
		.append("   ps.fit_user, ")
		.append("   ps.fit_time, ")
		.append("        (select listagg(to_char(psd.device_num) || ';' || ")
		.append("    to_char(psd.device_type), ")
		.append("    ',') WITHIN GROUP(ORDER BY psd.device_type) ")
		.append("           from t_vm_product_stock_device psd ")
		.append("          where psd.pro_stock_id = ps.id) proStockDevices ")
		.append("   from t_vm_product_stock ps ")
		.append("   join t_vm_product p ")
		.append("     on p.id = ps.pro_id ")
		.append("   join t_vm_product_type pt ")
		.append("     on p.pro_type = pt.id ");
		sql.append(" where ps.status='30' and exists (select id from t_vm_product_stock_device psd where psd.pro_stock_id=ps.id");
	if(!StringUtils.isNull(productStockDto.getDeviceCode())){
		sql.append("and psd.device_num like CONCAT('%',CONCAT(:deviceCode,'%'))");
	}

	if(!StringUtils.isNull(productStockDto.getDeviceType())){
		sql.append(" and psd.device_type like CONCAT('%',CONCAT(:deviceType,'%'))");
	}
	    sql.append(")  order by  ps.storage_time desc ");

		queryPageList(sql.toString(), productStockDto, page, ProductStockListDto.class);
		
	}
	/**
	 * 
	 * @Description 客户出库
	 * @param productStockDto
	 * @param page
	 * @author yixiaoli
	 * @Date 2017年4月13日 上午9:22:12
	 */
	public void getOutList(ProductStockListDto productStockDto, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.pro_name proName,ps.out_going,ps.storage_user, ")
			.append("         pt.type_name proType, ")
			.append("         ps.pro_num proNum, ")
			.append("    ps.id,p.pro_stork, ")
			.append("   ps.status,ps.pro_id proId, ")
			.append(" pt.code,pt.type_name,")
			.append("   ps.fit_user, ")
			.append("   ps.fit_time, ")
			.append("        (select listagg(to_char(psd.device_num) || ';' || ")
			.append("    to_char(psd.device_type), ")
			.append("    ',') WITHIN GROUP(ORDER BY psd.device_type) ")
			.append("           from t_vm_product_stock_device psd ")
			.append("          where psd.pro_stock_id = ps.id) proStockDevices ")
			.append("   from t_vm_product_stock ps ")
			.append("   join t_vm_product p ")
			.append("     on p.id = ps.pro_id ")
			.append("   join t_vm_product_type pt ")
			.append("     on p.pro_type = pt.id ");
		sql.append(" where (ps.status='30' or ps.status='40') ");

		if (!StringUtils.isNull(productStockDto.getDeviceCode())
				|| !StringUtils.isNull(productStockDto.getDeviceType())) {
			sql.append(" and exists (select id from t_vm_product_stock_device psd where psd.pro_stock_id=ps.id ");

			if (!StringUtils.isNull(productStockDto.getDeviceCode())) {
				sql.append("and psd.device_num like CONCAT('%',CONCAT(:deviceCode,'%'))");
			}

			if (!StringUtils.isNull(productStockDto.getDeviceType())) {
				sql.append(" and psd.device_type like CONCAT('%',CONCAT(:deviceType,'%'))");
			}

			sql.append(") ");
		}

		if (!StringUtils.isNull(productStockDto.getStatus())) {
			sql.append(" and ps.status=:status ");
		}
		sql.append(" order by  ps.storage_time desc ");
		queryPageList(sql.toString(), productStockDto, page, ProductStockListDto.class);

	}
	
	/**
	 * @Description 库存测试成功
	 * @param id  库存ID
	 * @param userId  登录用户
	 * @param nowDate 当前时间
	 * @author yixiaoli
	 * @Date 2017年4月14日 上午10:18:21
	 */
	@Transactional()
	public int testProductSuccess(String id, String userId, Date nowDate,String status) {
		final String successStatus = "20";
		final String errorStatus = "21";
		// 保存  操作
		ProductStockOperation pso = new ProductStockOperation();
		pso.setProStockId(id);
		pso.setOperationType(status);
		pso.setOperationUser(userId);
		pso.setOperationTime(nowDate);
		pso.setCreateTime(nowDate);
		saveOperation(pso);
		
		// 更新库存表
		StringBuffer sqlSb = new StringBuffer();
		Map paramMap = new HashMap<>();
		sqlSb.append(" update t_vm_product_stock ps set ps.status=:status ");
		paramMap.put("status", status);
		//时间  用户  操作
		if(successStatus.equals(status)){
			sqlSb.append(" ,ps.test_operation=:operationId,ps.test_user=:userId,ps.test_time=:nowTime ");
			paramMap.put("operationId", pso.getId());
			paramMap.put("userId", userId);
			paramMap.put("nowTime", nowDate);
		}
		if(errorStatus.equals(status)){
			sqlSb.append(" ,ps.test_operation=:operationId,ps.test_user=:userId,ps.test_time=:nowTime ");
			paramMap.put("operationId", pso.getId());
			paramMap.put("userId", userId);
			paramMap.put("nowTime", nowDate);
		}
		sqlSb.append(" where ps.status='10' and id=:id and ( ps.machine_code!='' or ps.machine_code is not null) ");
		paramMap.put("id", id);
		
		int count = execute(sqlSb.toString(), paramMap);
		if(count > 0){
			return count;
		}else{
			throw new CommonException(-1, "");
		}
		
	}
}
