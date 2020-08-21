<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
var deviceTypeData;//设备类型数据

$(function(){
	
	//初始化数据
    var id = $.util.getUrlParam('id');
    if(!$.util.isNull(id)){
        initData(id);
    }
   
})




//初始化数据
function initData(id){
	
    //查询数据，绑定到元素
    var showUrl = $.util.basePath + '/r/proStock/show';
    var json = {'id':id}
    $.util.ajax({
        url:showUrl,
        data:json,
        success:function(data){
        	   bindShowData(data);
            
            //初始化   所属产品分类
            var proType = data.product.proType;
            if(!$.util.isNull(proType)){
              initProDevice(true,data.proStockDeviceList);               
            }  
             initoperation(data.productStockOperation);
        }
    });
    
}

// //初始化产品历史
function initoperation(productStockOperation){
	  var htmlDevice =[];
	  if(!$.util.isNull(productStockOperation) && productStockOperation.length >0 ){
		 for(var i=0;i<productStockOperation.length;i++){
			 var operation=productStockOperation[i];
				htmlDevice.push(' <tr>');
				
            	//操作类型
            	var operationName = $.util.isNull(operation.operationName)?"":operation.operationName;
            	htmlDevice.push(' <td>'+operationName+'</td>');
            	//操作人
            	var operationUser= $.util.isNull(operation.username)?"":operation.username;
            	//操作时间
            	var operationTime = $.util.isNull(operation.operationTime)?"":operation.operationTime;
         
                htmlDevice.push(' <td>'+operationUser+'</td>');
           
            	htmlDevice.push(' <td>'+operationTime+'</td>');
            
            	htmlDevice.push(' </tr>');
		 }
	}else{
		htmlDevice.push('<tr class="no-records-found"><td colspan="5">没有找到匹配的记录</td></tr>');
	}
	  $("#operation").html(htmlDevice.join(''));
	 
}

//初始化产品设备
function initProDevice(editFlg,proDeviceList){
	
	//附属设备
	var htmlDevice = [];
    if(!$.util.isNull(proDeviceList) && proDeviceList.length > 0){
    	for(var i = 0; i < proDeviceList.length;i++){
    		var proDevice = proDeviceList[i];
        	var device = proDevice.device;
        	if(!$.util.isNull(device)){
            	//数量
            	var count = 1;
            	if(!editFlg){
            		count = $.util.isNull(proDevice.count)?"":proDevice.count;
            	}
            	
            	for(var j = 0 ; j < count ; j ++){
            		htmlDevice.push(' <tr>')
            		
            		//设备ID
            		var deviceId = $.util.isNull(proDevice.deviceId)?"":proDevice.deviceId;
            		htmlDevice.push(' 	 <input name="deviceId" value="'+deviceId+'" type="hidden">');
            		//附属设备
            		var subFlg = $.util.isNull(proDevice.subFlg)?"":proDevice.subFlg;
            		htmlDevice.push(' 	 <input name="subFlg" value="'+subFlg+'" type="hidden">');
            		//必要设备
            		var requiredFlg = $.util.isNull(proDevice.requiredFlg)?"":proDevice.requiredFlg;
            		htmlDevice.push(' 	 <input name="requiredFlg" value="'+requiredFlg+'" type="hidden">');
            		//设备类型
            		var deviceType = $.util.isNull(proDevice.deviceType)?"":proDevice.deviceType;
            		htmlDevice.push(' 	 <input name="deviceType" value="'+deviceType+'" type="hidden">');
            		
                	//设备类型
                	var deviceTypeName = $.util.isNull(proDevice.deviceTypeName)?"":proDevice.deviceTypeName;
                	htmlDevice.push(' <td>'+deviceTypeName+'</td>');
                	//设备名称
                	var deviceName = $.util.isNull(device.deviceName)?"":device.deviceName;
                	//设备厂家
                	var deviceMaker = $.util.isNull(device.deviceMaker)?"":device.deviceMaker;
                	//设备型号
                	var deviceModel = $.util.isNull(device.deviceModel)?"":device.deviceModel;
                	
                	htmlDevice.push(' <td>'+deviceName+"["+deviceMaker+"]"+"["+deviceModel+"]"+'</td>');
                	//设备编号
                	var deviceNum = $.util.isNull(proDevice.deviceNum)?"":proDevice.deviceNum;
                    htmlDevice.push(' <td>'+deviceNum+'</td>');
                	//附属设备
                	var subFlg = "1"==proDevice.subFlg?"是":"否";
                	htmlDevice.push(' <td>'+subFlg+'</td>');
                	//必要设备
                	var requiredFlg = "1"==proDevice.requiredFlg?"是":"否";
                	htmlDevice.push(' <td>'+requiredFlg+'</td>');
                
                	htmlDevice.push(' </tr>')
            	}
        	}
    	}
    }else{
    	htmlDevice.push('<tr class="no-records-found"><td colspan="5">没有找到匹配的记录</td></tr>');
    }
    $("#proDeviceBody").html(htmlDevice.join(''));
	
}

   </script>
   
   <body>
   		<div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>登记详情</h5>
                    </div>
                    <div class="ibox-content show-content">
                          <div class="well" style="padding-bottom: 0;">
                             <div class="row">
                                <div class="col-sm">
                                    <div class="ibox float-e-margins">
                                        <div class="ibox-content">
					                        <div class="row show-row show-text">
					                            <label class="col-sm-3 control-label font-l">所属产品分类：</label>
					                            <div class="col-sm-8">
					                                <lable class="form-control-static show-data" data-name="product.productType.typeName"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">所属产品：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="product.proName"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">库存编号：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proNum"></lable>
					                            </div>
					                        </div>
					                           <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品装配人：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="fitUser"></lable>
					                            </div>
					                        </div>
					                          <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品装配时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="fitTime"></lable>
					                            </div>
					                        </div>
					                              <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品测试人：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="testName"></lable>
					                            </div>
					                        </div>
					                          <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品测试时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="testTime"></lable>
					                            </div>
					                        </div>
					                              <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品入库人：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="storageName"></lable>
					                            </div>
					                        </div>
					                             <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品入库时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="storageTime"></lable>
					                            </div>
					                        </div>
					                         <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">出库代理商：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="proxy.name"></lable>
					                            </div>
					                        </div>
					                           <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">出库代理商时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="outProxyTime"></lable>
					                            </div>
					                        </div>
					                         <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">出库客户：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="customer.name"></lable>
					                            </div>
					                        </div>
					                         <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">出库客户时间：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="outCustomerTime"></lable>
					                            </div>
					                        </div>
					                            <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">产品当前版本：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="currentVersion"></lable>
					                            </div>
					                        </div>
					                        <div class="row show-row">
					                            <label class="col-sm-3 control-label font-l">备注：</label>
					                            <div class="col-sm-8 show-text">
					                                <lable class="form-control-static show-data" data-name="remarks"></lable>
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
                                                         <th><div class="th-inner" >设备编号</div></th>
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
					                        <h5>登记历史</h5>
					                    </div>
					                    <div class="ibox-content">
                                            <table id="proDevic" class="table table-hover table-striped" style="border: 1px solid #e4eaec;">
                                                 <thead>
                                                     <tr>
                                                          <th style="width: 140px;"><div class="th-inner">操作类型</div></th>
                                                         <th style="width: 85px;"><div class="th-inner">操作人</div></th>
                                                         <th style="width: 85px;"><div class="th-inner">操作时间</div></th>
                                                     </tr>
                                                 </thead>
                                                <tbody id="operation">
                                                       	                                                    
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