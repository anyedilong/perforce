<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    
    $(function(){
        //初始化数据
        var id = $.util.getUrlParam('id');
        if(!$.util.isNull(id)){
            initData(id);
        }
    });

    //初始化数据
    function initData(id){
        //查询数据，绑定到元素
        var showUrl = $.util.basePath + '/r/product/show';
        var json = {'id':id}
        $.util.ajax({
            url:showUrl,
            data:json,
            success:function(data){
                bindShowData(data);
                if(!$.util.isNull(data)){
	                //附属设备
                	var htmlDevice = [];
	                var proDeviceList = data.proDeviceList;
	                if(!$.util.isNull(proDeviceList) && proDeviceList.length > 0){
	                	for(var i = 0; i < proDeviceList.length;i++){
	                		var proDevice = proDeviceList[i];
		                	var device = proDevice.device;
		                	if(!$.util.isNull(device)){
			                	htmlDevice.push(' <tr>')
			                	//设备类型
			                	var deviceTypeName = $.util.isNull(proDevice.deviceTypeName)?"":proDevice.deviceTypeName;
			                	htmlDevice.push(' <td>'+deviceTypeName+'</td>');
			                	//设备名称
			                	var deviceName = $.util.isNull(device.deviceName)?"":device.deviceName;
			                	htmlDevice.push(' <td>'+deviceName+'</td>');
			                	//设备厂家
			                	var deviceMaker = $.util.isNull(device.deviceMaker)?"":device.deviceMaker;
			                	htmlDevice.push(' <td>'+deviceMaker+'</td>');
			                	//设备型号
			                	var deviceModel = $.util.isNull(device.deviceModel)?"":device.deviceModel;
			                	htmlDevice.push(' <td>'+deviceModel+'</td>');
			                	//数量
			                	var count = $.util.isNull(proDevice.count)?"":proDevice.count;
			                	htmlDevice.push(' <td>'+count+'</td>');
			                	//附属设备
			                	var subFlg = "1"==proDevice.subFlg?"是":"否";
			                	htmlDevice.push(' <td>'+subFlg+'</td>');
			                	//必要设备
			                	var requiredFlg = "1"==proDevice.requiredFlg?"是":"否";
			                	htmlDevice.push(' <td>'+requiredFlg+'</td>');
			                	htmlDevice.push(' </tr>')
		                	}
	                	}
	                }else{
	                	htmlDevice.push('<tr class="no-records-found"><td colspan="7">没有找到匹配的记录</td></tr>');
	                }
	                $("#proDeviceBody").html(htmlDevice.join(''));
	                
	                //产品版本
	                var proVersionList = data.proVersionList;
	                var htmlVersion = [];
	                if(!$.util.isNull(proVersionList) && proVersionList.length > 0){
	                	for(var i = 0; i < proVersionList.length;i++){
	                		htmlVersion.push(' <tr>')
	                		var proVersion = proVersionList[i];
		                	//版本号
		                	var versionNum = $.util.isNull(proVersion.versionNum)?"":proVersion.versionNum;
		                	htmlVersion.push(' <td>'+versionNum+'</td>');
		                	//版本说明
		                	var versionDescription = $.util.isNull(proVersion.versionDescription)?"":proVersion.versionDescription;
		                	htmlVersion.push(' <td>'+versionDescription+'</td>');
		                	//版本类型
		                	var versionType = "1"==proVersion.versionType?"全量":"增量";
		                	htmlVersion.push(' <td>'+versionType+'</td>');
		                	//发布人
		                	var releaseUser = $.util.isNull(proVersion.releaseUserName)?"":proVersion.releaseUserName;
		                	htmlVersion.push(' <td>'+releaseUser+'</td>');
		                	//发布时间
		                	var releaseTime = $.util.isNull(proVersion.releaseTime)?"":proVersion.releaseTime;
		                	htmlVersion.push(' <td>'+releaseTime+'</td>');
		                	//附件
		                	var versionFile = "无";
		                	htmlVersion.push(' <td>'+versionFile+'</td>');
		                	
		                	htmlVersion.push(' </tr>')
	                	}
                    }else{
                    	htmlVersion.push('<tr class="no-records-found"><td colspan="6">没有找到匹配的记录</td></tr>');
                    }
	                $("#proVersionBody").html(htmlVersion.join(''));
                }
            }
        });
        
    }
    
</script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>产品详情</h5>
                    </div>
                    <div class="ibox-content show-content">
                        <div class="well" style="padding-bottom: 0;">
                             <div class="row">
                                <div class="col-sm">
                                    <div class="ibox float-e-margins">
                                        <div class="ibox-title">
                                            <h5>产品信息</h5>
                                        </div>
                                        <div class="ibox-content">
					                        <div class="row show-row show-text">
					                            <label class="col-sm-3 control-label font-l">产品名称：</label>
					                            <div class="col-sm-8">
					                                <lable class="form-control-static show-data" data-name="proName"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品编码：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proCode"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品分类：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="productType.typeName"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">立项时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proApprovalTime"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">定型时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proFinalTime"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">投产时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proPutTime"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">软件质保时间（月）：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proSoftwareWarranty"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品说明：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proDescription"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品功能：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proFunction"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品简介：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proIntroducte"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品库存：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proStork"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">备注：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="remarks"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">状态：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="statusName"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">发布人：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="releaseUserName"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">发布时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="releaseTime"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">创建人：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="createUserName"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">创建时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="createTime"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">更新人：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="updateUserName"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">更新时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="updateTime"></lable>
					                            </div>
					                        </div>
				                        </div>
			                        </div>
		                        </div>
	                        </div>
                        </div>
                            
                        <div class="well" style="padding-bottom: 0;">
                             <div class="row">
                                <div class="col-sm">
                                    <div class="ibox float-e-margins">
                                        <div class="ibox-title">
                                            <h5>附属设备</h5>
                                        </div>
                                        <div class="ibox-content">
                                            <table id="proDevice" class="table table-hover table-striped" style="border: 1px solid #e4eaec;">
                                                 <thead>
                                                     <tr>
                                                         <th style="width: 140px;"><div class="th-inner">设备类型</div></th>
                                                         <th><div class="th-inner">设备名称</div></th>
                                                         <th><div class="th-inner">设备厂家</div></th>
                                                         <th><div class="th-inner">设备型号</div></th>
                                                         <th style="width: 100px;"><div class="th-inner">数量</div></th>
                                                         <th style="width: 85px;"><div class="th-inner">附属设备</div></th>
                                                         <th style="width: 85px;"><div class="th-inner">必要设备</div></th>
                                                     </tr>
                                                </thead>
                                                <tbody id="proDeviceBody">
                                                    
                                                </tbody>
                                             </table>
                                        </div>
                                    </div>
                                </div>
                            </div>             
                         </div>
                         
                        <div class="well" style="padding-bottom: 0;">
                             <div class="row">
                                <div class="col-sm">
                                    <div class="ibox float-e-margins">
                                        <div class="ibox-title">
                                            <h5>产品版本</h5>
                                        </div>
                                        <div class="ibox-content">
                                            <table id="proVersion" class="table table-hover table-striped" style="border: 1px solid #e4eaec;">
                                                 <thead>
                                                     <tr>
                                                         <th><div class="th-inner">版本号</div></th>
                                                         <th><div class="th-inner">版本说明</div></th>
                                                         <th><div class="th-inner">版本类型</div></th>
                                                         <th><div class="th-inner">发布人</div></th>
                                                         <th><div class="th-inner">发布时间</div></th>
                                                         <th><div class="th-inner">附件</div></th>
                                                     </tr>
                                                </thead>
                                                <tbody id="proVersionBody">
                                                    
                                                </tbody>
                                             </table>
                                        </div>
                                    </div>
                                </div>
                            </div>             
                         </div>
                         
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>