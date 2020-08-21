package org.sdblt.modules.product.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.product.dao.repository.ProductDeviceRepository;
import org.sdblt.modules.product.dao.repository.ProductRepository;
import org.sdblt.modules.product.domain.Product;
import org.sdblt.modules.product.domain.ProductDevice;
import org.sdblt.modules.product.dto.ProductListDto;
import org.sdblt.modules.system.dto.UserListDto;
import org.sdblt.utils.StringUtils;

/**
 * @ClassName ProductDao
 * @Description 产品数据操作层
 * @author sen
 * @Date 2017年3月15日 上午10:29:16
 * @version 1.0.0
 */
@Named
public class ProductDao extends BaseDao<ProductRepository, Product>{

	@Inject
	private ProductDeviceRepository proDeviceRepository;
	
	public void getList(ProductListDto productParam, PageModel page) {
		StringBuffer sql = new StringBuffer();
		
		sql.append( " select p.id, ")
			.append("        p.pro_name proName, ")
			.append("        p.pro_code proCode, ")
			.append("        p.pro_type proType, ")
			.append("        p.pro_software_warranty proSoftwareWarranty, ")
			.append("        p.pro_stork proStork, ")
			.append("        p.pro_new_version proNewVersion, ")
			.append("        p.pro_new_full_version proNewFullVersion, ")
			.append("        p.status status, ")
			.append("        p.release_time releaseTime, ")
			.append("        p.release_user releaseUser, ")
			.append("        p.create_time createTime, ")
			.append("        p.create_user createUser, ")
			.append("        p.update_time updateTime, ")
			.append("        p.update_user updateUser ")
			.append("   from t_vm_product p  ")
			.append("   join t_vm_product_type pt on  p.pro_type=pt.id ")
			.append("   where 1=1 and p.status != 4  ");

		if(!StringUtils.isNull(productParam.getProName())){
			sql.append( " and p.pro_name like  CONCAT('%',CONCAT(:proName,'%')) ");
		}
		if(!StringUtils.isNull(productParam.getProCode())){
			sql.append( " and p.pro_code like  CONCAT('%',CONCAT(:proCode,'%')) ");
		}
		if(!StringUtils.isNull(productParam.getProType())){
			sql.append( " and  pt.parent_id_all like CONCAT('%',CONCAT(:proType,'%')) ");
		}
		if(!StringUtils.isNull(productParam.getStatus())){
			sql.append( " and  p.status=:status ");
		}
		
		sql.append( " order by p.create_time desc ");
		
		queryPageList(sql.toString(), productParam, page, ProductListDto.class);
	}
	
	/**
	 * @Description 修改产品
	 * @param product
	 * @return
	 * @author sen
	 * @Date 2017年3月22日 上午8:57:30
	 */
	public int updateProduct(Product product) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update t_vm_product p ")
			.append("    set p.pro_name=:proName, ")
			.append("        p.pro_code=:proCode, ")
			.append("        p.pro_type=:proType, ")
			.append("        p.pro_approval_time=:proApprovalTime, ")
			.append("        p.pro_final_time=:proFinalTime, ")
			.append("        p.pro_put_time=:proPutTime, ")
			.append("        p.pro_software_warranty=:proSoftwareWarranty, ")
			.append("        p.pro_description=:proDescription, ")
			.append("        p.pro_function=:proFunction, ")
			.append("        p.pro_introducte=:proIntroducte, ")
			.append("        p.remarks=:remarks, ")
			.append("        p.update_time=:updateTime, ")
			.append("        p.update_user=:updateUser ")
			.append("  where p.id = :id ")
			.append("    and (p.status = '1' or p.status = '3') ");
		
		List<String> dateFieldList = new ArrayList<>();
		dateFieldList.add("proApprovalTime");
		dateFieldList.add("proFinalTime");
		dateFieldList.add("proPutTime");
		
		return execute(sql.toString(), product,dateFieldList);
	}

	/**
	 * @Description 产品发布
	 * @param id
	 * @param updateUser
	 * @param updateDate
	 * @return
	 * @author sen
	 * @Date 2017年3月22日 上午9:19:55
	 */
	public int release(String id,String releaseUser, Date releaseTime) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("releaseUser", releaseUser);
		paramMap.put("releaseTime", releaseTime);
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update t_vm_product p ")
			.append("    set p.status = '2', ")
			.append("        p.release_time=:releaseTime, ")
			.append("        p.release_user=:releaseUser ")
			.append("  where p.id = :id ")
			.append("    and (p.status = '1' or p.status = '3') ");
		
		return execute(sql.toString(), paramMap);
	}

	/**
	 * @Description 撤销发布
	 * @param id
	 * @param updateUser
	 * @param updateDate
	 * @return
	 * @author sen
	 * @Date 2017年3月22日 上午9:27:51
	 */
	public int revokeRelease(String id, String updateUser, Date updateDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("updateUser", updateUser);
		paramMap.put("updateTime", updateDate);
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update t_vm_product p ")
			.append("    set p.status = '3', ")
			.append("        p.update_time=:updateTime, ")
			.append("        p.update_user=:updateUser ")
			.append("  where p.id = :id ")
			.append("    and (p.status = '2') ");
		
		return execute(sql.toString(), paramMap);
	}

	/**
	 * @Description 删除
	 * @param id
	 * @param updateUser
	 * @param updateDate
	 * @return
	 * @author sen
	 * @Date 2017年3月22日 上午9:34:02
	 */
	public int updateForDelete(String id, String updateUser, Date updateDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("updateUser", updateUser);
		paramMap.put("updateTime", updateDate);
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update t_vm_product p ")
			.append("    set p.status = '4', ")
			.append("        p.update_time=:updateTime, ")
			.append("        p.update_user=:updateUser ")
			.append("  where p.id = :id ")
			.append("    and (p.status = '1') ");
		
		return execute(sql.toString(), paramMap);
	}

	/**
	 * @Description 修改库存
	 * @param id
	 * @param proStork
	 * @author sen
	 * @Date 2017年3月22日 下午5:35:37
	 */
	public void updateStork(String id, int proStork) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("proStork", proStork);
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update t_vm_product p ")
			.append("    set p.pro_stork=:proStork ")
			.append("  where p.id = :id ");
	
		execute(sql.toString(), paramMap);
	}
	/**
	 * 
	 * @Description 
	 * @param id
	 * @authorliuxingx
	 * @Date 2017年4月11日 上午11:35:28
	 */
	/*public void updateProductStork(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append(" update t_vm_product p ")
			.append("    set p.pro_stork= p.pro_stork-1 ")
			.append("  where p.id = :id ");
	System.out.println(sql+"-----------");
		execute(sql.toString(), paramMap);
	}*/
	
	public void batchSaveProDevice(List<ProductDevice> proDeviceList) {
		proDeviceRepository.save(proDeviceList);
	}

	/**
	 * @Description 删除产品设备
	 * @param proId
	 * @author sen
	 * @Date 2017年3月23日 下午3:21:39
	 */
	public void deleteDeviceByProid(String proId) {
		proDeviceRepository.deleteByProId(proId);
	}

	public List<ProductListDto> getProductListByType(String proType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proType", proType);
		
		String sql = "select p.id,p.pro_name proName from t_vm_product p where p.pro_type = :proType and p.status=2 ";
		
		return queryList(sql, paramMap, ProductListDto.class);
	}

}
