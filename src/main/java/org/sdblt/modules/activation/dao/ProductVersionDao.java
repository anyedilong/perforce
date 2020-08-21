package org.sdblt.modules.activation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.exception.CommonException;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.product.dao.repository.ProductVersionRepository;
import org.sdblt.modules.product.domain.ProductVersion;
import org.sdblt.utils.StringUtils;

@Named
public class ProductVersionDao extends BaseDao<ProductVersionRepository, ProductVersion> {

	@Inject
	private ProductVersionRepository productVersionRepository;
    
	
	
	public boolean getid(String versionNum){
		Map<String,String>  map=new HashMap<String,String>();
		map.put("versionNum",versionNum);
		StringBuffer sql=new StringBuffer();
		sql.append("select a.id from t_vm_product_version a ");
		sql.append(" where 1=1 and a.id=:versionNum");
		  String id=queryOne(sql.toString(),map,String.class);
		  if(!StringUtils.isNull(id)){
			  return true;
		  }
		return false;
	}
	public List<ProductVersion> queryversion(String proNum, String machineCode) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("proNum", proNum);
		paramMap.put("machineCode", machineCode);
		StringBuffer queryVersionSql = new StringBuffer();
		// 获取当前版本
		queryVersionSql.append("select a.current_version from  t_vm_product_stock a");
		queryVersionSql.append(" where 1=1 and a.status in('10','20','30','60') ");
		queryVersionSql.append(" and a.pro_num=:proNum and a.machine_code=:machineCode");
		String currentVersion = queryOne(queryVersionSql.toString(), paramMap, String.class);
		StringBuffer sql = new StringBuffer();
		if (StringUtils.isNull(currentVersion)) {
			Map<String, String> paramMaps = new HashMap<String, String>();
			paramMaps.put("proNum", proNum);
			paramMaps.put("machineCode", machineCode);
			sql.append("select b.id,b.version_num versionNum");
			sql.append("  from t_vm_product_stock a");
			sql.append("  left join t_vm_product_version b");
			sql.append("   on a.pro_id = b.pro_id");
			sql.append(" where 1=1 and a.status in('10','20','30','60') and b.status='2'");
			sql.append(" and a.pro_num=:proNum and a.machine_code=:machineCode order by  b.order_num");
			List<ProductVersion> list= queryList(sql.toString(), paramMaps, ProductVersion.class);
			 return list;
		} else {
			Map<String, String> paramMaps = new HashMap<String, String>();
			paramMaps.put("proNum", proNum);
			paramMaps.put("machineCode", machineCode);
			Map<String,String>  paramObject=new HashMap<String,String>();
			paramObject.put("currentVersion", currentVersion);
			//根据版本ID  查询排序号
			StringBuffer queryordernumsql=new StringBuffer();
			queryordernumsql.append("select  a.order_num from  t_vm_product_version a");
			queryordernumsql.append(" where 1=1 and  a.id=:currentVersion");
			String orderNum=queryOne(queryordernumsql.toString(), paramObject,String.class);
			if(StringUtils.isNull(orderNum)){
				     throw new CommonException(20001,"当前版本不存在");
			}else{
				paramMaps.put("orderNum", orderNum);
//				   sql.append("select c.id,c.version_num versionNum from t_vm_product_version c where  c.order_num>(select b.order_num");
//				   sql.append("    from t_vm_product_stock a");
//				   sql.append("   left join t_vm_product_version b");
//				   sql.append("    on a.pro_id = b.pro_id ");
//				   sql.append("  where 1=1 and a.status='60' and b.status='2'");
//				   sql.append(" and a.pro_num=:proNum and a.machine_code=:machineCode ) order by  c.order_num");

				sql.append("select b.id,b.version_num versionNum");
				sql.append("  from t_vm_product_stock a");
				sql.append("  left join t_vm_product_version b");
				sql.append("   on a.pro_id = b.pro_id");
				sql.append(" where 1=1 and a.status in('10','20','30','60') and b.status='2'");
				sql.append(" and a.pro_num=:proNum and a.machine_code=:machineCode and b.order_num>:orderNum order by  b.order_num");
				List<ProductVersion> list= queryList(sql.toString(), paramMaps, ProductVersion.class);
				 return list;
			}

		}
	}

}
