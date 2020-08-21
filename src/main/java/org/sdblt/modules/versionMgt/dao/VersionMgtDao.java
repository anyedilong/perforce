package org.sdblt.modules.versionMgt.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sdblt.common.page.PageModel;
import org.sdblt.modules.common.repository.BaseDao;
import org.sdblt.modules.product.dao.repository.ProductVersionFileRepository;
import org.sdblt.modules.product.domain.ProductDevice;
import org.sdblt.modules.product.domain.ProductVersionFile;
import org.sdblt.modules.versionMgt.dao.repository.VersionMgtRepository;
import org.sdblt.modules.versionMgt.domain.ProducTreeInfo;
import org.sdblt.modules.versionMgt.domain.VersionMgt;
import org.sdblt.modules.versionMgt.dto.VersionMgtDto;
import org.sdblt.utils.StringUtils;

/**
 * 
 * @ClassName VersionMgtDao
 * @Description TODO
 * @author josen
 * @Date 2017年3月21日 下午3:23:15
 * @version 1.0.0
 */

@Named
public class VersionMgtDao extends BaseDao<VersionMgtRepository, VersionMgt> {

	@Inject
	private VersionMgtRepository versionMgtRepository;
	
	@Inject
	private ProductVersionFileRepository productVersionFileRepository;

	/**
	 * 
	 * @Description 列表查询
	 * @param productDto
	 * @param page
	 * @author Administrator
	 * @Date 2017年3月21日 下午3:23:37
	 */
	public void queryVersionMgtList(VersionMgtDto vmDto, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.ID,T.VERSION_NUM,T.PRO_ID,T.PREPOSE_VERSION_ID,V.VERSION_NUM AS PREPOSEVERSIONNUM,T.VERSION_DESCRIPTION,T.VERSION_TYPE,T.RELEASE_TIME,T.RELEASE_USER,T.STATUS,T.CREATE_TIME,T.CREATE_USER,T.UPDATE_TIME,T.UPDATE_USER,U.USERNAME AS RELEASEUSERNAME,P.PRO_NAME,P.PRO_CODE ");
		sql.append("  FROM T_VM_PRODUCT_VERSION T");
		sql.append("  LEFT JOIN SYS_USER U ON  T.RELEASE_USER = U.ID");
		sql.append("  LEFT JOIN T_VM_PRODUCT P ON T.PRO_ID = P.ID");
		sql.append("  LEFT JOIN T_VM_PRODUCT_VERSION V ON T.PREPOSE_VERSION_ID = V.ID");
		sql.append("  WHERE 1=1 AND T.STATUS != 4");

		if (!StringUtils.isNull(vmDto.getProName())) {//产品名称
			sql.append(" and p.pro_name like  CONCAT('%',CONCAT(:proName,'%')) ");
		}
		if (!StringUtils.isNull(vmDto.getProCode())) {//产品编码
			sql.append(" and p.pro_code like  CONCAT('%',CONCAT(:proCode,'%')) ");
		}
		if (!StringUtils.isNull(vmDto.getStatus())) {//版本状态
			sql.append(" and p.status =:status) ");
		}
		if (!StringUtils.isNull(vmDto.getProId())) {//版本id
			sql.append(" and p.proId =:proId) ");
		}
		
		sql.append(" ORDER BY T.UPDATE_TIME DESC");
		queryPageList(sql.toString(), vmDto, page, VersionMgtDto.class);
	}

	/**
	 * 
	 * @Description 删除
	 * @param id
	 * @param updateUser
	 * @param updateDate
	 * @return
	 * @author josen
	 * @Date 2017年3月28日 下午5:22:20
	 */
	public int updateForDelete(String id, String updateUser, Date updateDate) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", id);
		paramMap.put("UPDATEUSER", updateUser);
		paramMap.put("UPDATETIME", updateDate);

		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE T_VM_PRODUCT_VERSION T ").append(" SET T.STATUS = '4', ")
				.append(" T.UPDATE_TIME=:UPDATETIME, ").append(" T.UPDATE_USER=:UPDATEUSER ")
				.append(" WHERE T.ID = :ID ").append(" AND (T.STATUS = '1') ");

		return execute(sql.toString(), paramMap);
	}
	
	

	/**
	 * 
	 */
	public List<ProducTreeInfo> getProinfo(){
		String sql ="SELECT * from ((SELECT T.ID AS ID ,T.TYPE_NAME AS TYPE_NAME,T.PARENT_ID AS PARENT_ID,T.CODE AS CODE,TO_CHAR(T.ORDER_NUM) AS ORDER_NUM,'true' AS ISPARENT  FROM T_VM_PRODUCT_TYPE T WHERE T.STATUS <> 3 AND T.PARENT_ID  IS NOT NULL )UNION(SELECT P.ID AS ID,P.PRO_NAME AS TYPE_NAME,P.PRO_TYPE AS PARENT_ID,P.PRO_CODE AS CODE,'1' AS ORDER_NUM,'false' AS ISPARENT  FROM T_VM_PRODUCT P WHERE P.STATUS<>4 AND P.PRO_TYPE  IS NOT NULL))";
		return queryList(sql, null, ProducTreeInfo.class);
	}
	
	
	/**
	 * 
	 * @Description 根据产品id获取最新版本信息
	 * @return
	 * @author JOSEN
	 * @Date 2017年4月5日 上午10:36:40
	 */
	public VersionMgt getPrepVersId(String proId){
		
		VersionMgt vm = new VersionMgt();
		String sql ="SELECT * FROM (SELECT  T.* FROM T_VM_PRODUCT_VERSION  T WHERE   T.PRO_ID='"+proId+"'  ORDER BY T.ORDER_NUM DESC) WHERE ROWNUM=1";
		
		List<VersionMgt> list = queryList(sql, null, VersionMgt.class);
		if(list!=null){
			 vm = list.get(0);
		}
		return vm;
	}
	
	/**
	 * 
	 * @Description 批量保存附件
	 * @param 
	 * @author josen
	 * @Date 2017年4月5日 下午5:00:16
	 */
	public void batchSavefileList(List<ProductVersionFile> prodVersFileList) {
		productVersionFileRepository.save(prodVersFileList);
	}
	
	/**
	 * 
	 * @Description 根据附件id删除附件,修改版本附件表状态
	 * @param fileid
	 * @return
	 * @author Administrator
	 * @Date 2017年4月6日 下午2:49:50
	 */
	public int delFileById(String fileId,String versionId) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("FILEID", fileId);
		paramMap.put("VERSIONID", versionId);

		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE T_VM_PRO_VERSION_FILE T ").append(" SET T.STATUS = '0'")
				.append(" WHERE T.VERSION_ID = :VERSIONID").append(" AND T.FILE_ID = :FILEID");

		return execute(sql.toString(), paramMap);
	}

	public void deleteFileByVersionId(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("versionId", id);
		String sql = " delete from t_vm_pro_version_file t where t.version_id=:versionId ";
		
		execute(sql, paramMap);
	}
	

}
