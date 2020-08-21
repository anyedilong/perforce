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
		
		//初始化设备类型 字典数据
		 initDeviceTypeData();
		
		//初始化数据校验
		initValidate();
		//初始化产品分类树
		initProTypeTree();
		setTimeout('$("#downProTypeTree").downZtree()', 100);
		//初始化下拉框
		initProSlt();
		
		//初始化数据
	    var id = $.util.getUrlParam('id');
	    if(!$.util.isNull(id)){
	        initData(id);
	    }
	    $("#proDeviceBody").on("click","td[data-type=deviceType]",function(){
	        initDeviceTypeSel($(this).find("select[name=deviceType]"));
	    }).on("change","td[data-type=deviceType]",function(){
	    	var deviceTypeEle = $(this).find("select[name=deviceType]");
	    	var deviceModelEle = $(this).parent().find("select[name=deviceId]");
	    	initDeviceModelSel(deviceModelEle,deviceTypeEle);
	    });
		//
	$("#productSlt").chosen().change(function(){
			initProDevice();
		});
	})
	

	//初始化设备类型  字典数据
	function initDeviceTypeData(){
	getDictList("dictDeviceType",function(data){
		deviceTypeData = data;
		});
	}
	
	 //初始化数据校验
    function initValidate(){
    	var e = "<i class='fa fa-times-circle'></i> ";
        $("#saveForm").validate({
            rules: {
            	proTypeName: "required",
            	productSlt: "required",
            	proNum: "required"
                },
            messages: {
            	proTypeName: e + "请选择产品分类",
            	productSlt: e + "请选择产品",
            	proNum: e + "请输入库存编号"
                }
        });
    }
	
	//初始化产品分类树
	function initProTypeTree() {
		var initOrgUrl = $.util.basePath + '/r/productType/getTree';

		$.util.ajax({
			url : initOrgUrl,
			async : false, // 是否同步
			success : function(data) {
				if ($.util.isNull(data)) {
					data = [];
				}
				data.push({
					id : "0",
					parentId : "-1",
					typeName : "产品分类",
					open : true
				});
				var setting = {
					data : {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "parentId"
						},
						key : {
							//url: "xUrl"
							name : "typeName"
						}
					},
					callback : {
						onClick : function(event, treeId, treeNode) {
							$("#saveForm input[name=proType]").val(treeNode.id);
							$("#saveForm input[name=proTypeName]").val(treeNode.typeName);
							$("#downProTypeTree .selTree").addClass("hd");
							initProSlt();
						}
					}
				};
				proTypeTree = $.fn.zTree.init($("#proTypeTree"),setting, data);
				proTypeTree.expandAll(true);
			}
		});
	}
	
	//初始化产品下拉框
	function initProSlt(editFlg){
		var optionHtml = [];
		//获取产品类型
		var proType = $("#saveForm input[name=proType]").val();
		var proSltUrl = $.util.basePath + '/r/product/getProductSlt';
		if(!$.util.isNull(proType)){
			$.util.ajax({
				url : proSltUrl,
				data :{"proType":proType},
				async : false, // 是否同步
				success : function(data) {
					if (!$.util.isNull(data)) {
						for (var i = 0; i < data.length; i++) {
							var item = data[i];
							optionHtml.push(' <option value="'+item.id+'">' + item.proName+ '</option> ');
						}
					}
				}
			});
		}
		$("#productSlt").html(optionHtml.join(''));
		$("#productSlt").trigger("chosen:updated");
		if(!editFlg){
			initProDevice();
		}
	}
	
    //初始化数据
	function initData(id){
	    //查询数据，绑定到元素
	    var showUrl = $.util.basePath + '/r/proStock/show';
	    var json = {'id':id}
	    $.util.ajax({
	        url:showUrl,
	        data:json,
	        success:function(data){
	            bindData('saveForm',data);
	            $("#saveForm .show-hidden").addClass("hd");
	            
	            //初始化   所属产品分类
	            var proType = data.product.proType;
	            if(!$.util.isNull(proType)){
	                var node = proTypeTree.getNodesByParam("id", proType, null);
	                if(!$.util.isNull(node)){
	                    proTypeTree.selectNode(node[0]);
	                    $("#saveForm input[name=proType]").val(node[0].id);
	                    $("#saveForm input[name=proTypeName]").val(node[0].typeName);
	                    initProSlt(true);
	                    initProDevice(true,data.proStockDeviceList);
	                }
	            }
	            
	            //附属设备
	            var proDeviceArray = data.proDeviceList;
	            if(!$.util.isNull(proDeviceArray)){
	            	 for(var i = 0; i < proDeviceArray.length;i++){
	            		 var proDevice = proDeviceArray[i];
	            		 if(!$.util.isNull(proDevice)){
	            			 addSubDevice(proDevice);
	            		 }
	            	 }
	            }
	            
	        }
	    });
	    
	}
	
	//初始化产品设备
	function initProDevice(editFlg,proDeviceList){
		//获取产品ID
		if(!editFlg){
			var proId = $("#productSlt").val();
			var proShowUrl = $.util.basePath + '/r/product/show';
			if(!$.util.isNull(proId)){
				$.util.ajax({
					url : proShowUrl,
					data :{"id":proId},
					async : false, // 是否同步
					success : function(data) {
						if (!$.util.isNull(data)) {
			                proDeviceList = data.proDeviceList;
			                addDeviceList = data.proDeviceList;
			                addDeviceFlg = false;//是否初始化添加设备
						}
					}
				});
			}
		}
		$("#proDeviceBody").html('');
		//附属设备
       /*  if(!$.util.isNull(proDeviceList) && proDeviceList.length > 0){
        	for(var i = 0; i < proDeviceList.length;i++){
        		var proDevice = proDeviceList[i];
        		addProStockdeviceRow(proDevice,editFlg);
        	}
        }else{
	        $("#proDeviceBody").html('<tr class="no-records-found"><td colspan="6">没有找到匹配的记录</td></tr>');
        } */
		
	}
	
	//添加一行产品设备
	function addProStockdeviceRow(proDevice,editFlg){
		
		var htmlDevice = [];
		var device = proDevice.device;
        if(!$.util.isNull(device)){
            //数量
            var count = 1;
            if(!editFlg){
                count = $.util.isNull(proDevice.count)?"":proDevice.count;
            }
            
            for(var j = 0 ; j < count ; j ++){
                
                //设备ID
                var deviceId = $.util.isNull(proDevice.deviceId)?"":proDevice.deviceId;
                htmlDevice.push(' <tr data-id="'+deviceId+'">')
                htmlDevice.push('    <input name="deviceId" value="'+deviceId+'" type="hidden">');
                //附属设备
                var subFlg = $.util.isNull(proDevice.subFlg)?"":proDevice.subFlg;
                htmlDevice.push('    <input name="subFlg" value="'+subFlg+'" type="hidden">');
                //必要设备
                var requiredFlg = $.util.isNull(proDevice.requiredFlg)?"":proDevice.requiredFlg;
                htmlDevice.push('    <input name="requiredFlg" value="'+requiredFlg+'" type="hidden">');
                //设备类型
                var deviceType = $.util.isNull(proDevice.deviceType)?"":proDevice.deviceType;
                htmlDevice.push('    <input name="deviceType" value="'+deviceType+'" type="hidden">');
                
                
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
                htmlDevice.push(' <td> ')
                htmlDevice.push('    <input name="deviceNum" style="width:150px;" value="'+deviceNum+'" class="form-control" type="text">');
                htmlDevice.push(' </td>')
                //附属设备
                var subFlg = "1"==proDevice.subFlg?"是":"否";
                htmlDevice.push(' <td>'+subFlg+'</td>');
                //必要设备
                var requiredFlg = "1"==proDevice.requiredFlg?"是":"否";
                htmlDevice.push(' <td>'+requiredFlg+'</td>');
                //删除操作
                htmlDevice.push('     <td>')
                if(proDevice.requiredFlg != '1'){
                    htmlDevice.push('         <button class="btn btn-default " type="button" onclick="deleteProDevice(this)"><i class="fa fa-remove"></i></button> ')
                }
                htmlDevice.push('     </td>')
                
                htmlDevice.push(' </tr>')
            }
        }
        $("#proDeviceBody tr.no-records-found").remove();
        $("#proDeviceBody").append(htmlDevice.join(''));
	}
	
	//删除一行 产品设备
	function deleteProDevice(btnEle){
		$(btnEle).parent().parent().remove();
	}
	
	//保存
	//保存form表单
	function saveForm(){
		var param = {"proStockDeviceList":[]};
		
		//获取设备信息
		$("#proDeviceBody tr").each(function(){
			//设备ID
    		var deviceId = $(this).find("input[name=deviceId]").val();
    		//设备类型
    		var deviceType = $(this).find("input[name=deviceType]").val();
    		//附属设备
    		var subFlg = $(this).find("input[name=subFlg]").val();
    		//必要设备
    		var requiredFlg = $(this).find("input[name=requiredFlg]").val();
    		//设备编号  deviceNum
    		var deviceNum = $(this).find("input[name=deviceNum]").val();
    		
    		var itemParam = {
   				"deviceId":deviceId,
   				"deviceType":deviceType,
   				"subFlg":subFlg,
   				"requiredFlg":requiredFlg,
   				"deviceNum":deviceNum
   			};
    		param.proStockDeviceList.push(itemParam);
		});
		
	 	var saveUrl = '/r/proStock/fitProStock';
	    save(saveUrl,'saveForm',function(){
	        parent.refreshTable();
	        parent.closeDialog(menuId);
	    },param);
	}
	
	//初始化设备类型  下拉框
	function initDeviceTypeSel(selEle,initDeviceType) {
		//获取下拉框选中值
		var selValue = initDeviceType;
		if($.util.isNull(initDeviceType)){
			selValue = $(selEle).val();
		}

		if (!$.util.isNull(deviceTypeData)) {
			var optionHtml = [];
			for (var i = 0; i < deviceTypeData.length; i++) {
				var item = deviceTypeData[i];
				var value = item.value;

				if (selValue == value) {
					optionHtml.push(' <option selected value="'+value+'">'+ item.text + '</option> ');
				} else if ($("#proDeviceBody select[name=deviceType][data-value="+ value + "]").length <= 0) {
					optionHtml.push(' <option value="'+value+'">' + item.text+ '</option> ');
				}
			}
		}
		$(selEle).html(optionHtml.join(''));
		$(selEle).trigger("chosen:updated");
		selValue = $(selEle).val();

		$(selEle).attr("data-value", selValue);
	}

	var addDeviceFlg = false;//是否初始化添加设备
	var addDeviceList;//产品设备集合
	
	//打开添加设备弹出框
	function openDeviceDialog(){
		initAddDevice();
		//初始化 可以添加的附属设备
		$("#addProDeviceBody tr").removeClass("hd").addClass("add-device");
		
		$("#proDeviceBody tr").each(function(){
			var deviceId = $(this).attr("data-id");
			if(!$.util.isNull(deviceId)){
				$("#addProDeviceBody tr.add-device[data-id="+deviceId+"]:first").addClass("hd").removeClass("add-device");
			}
		});
		
		var html = $("#addProDevice").html();
		//打开弹出框
		$.dialog({
	        'title':"添加设备",
	        'content':html,
	        'lock':true
	    });
		
	}
	
	//初始化添加产品设备
	function initAddDevice(){
		if(!addDeviceFlg){
			if($.util.isNull(addDeviceList)){
				var proId = $("#productSlt").val();
	            var proShowUrl = $.util.basePath + '/r/product/show';
	            if(!$.util.isNull(proId)){
	                $.util.ajax({
	                    url : proShowUrl,
	                    data :{"id":proId},
	                    async : false, // 是否同步
	                    success : function(data) {
	                        if (!$.util.isNull(data)) {
	                            addDeviceList = data.proDeviceList;
	                        }
	                    }
	                });
	            }
			}
			
			//附属设备
	        var htmlDevice = [];
	        if(!$.util.isNull(addDeviceList) && addDeviceList.length > 0){
	            for(var i = 0; i < addDeviceList.length;i++){
	                var proDevice = addDeviceList[i];
	                var device = proDevice.device;
	                if(!$.util.isNull(device)){
	                    //数量
	                    var count = $.util.isNull(proDevice.count)?"":proDevice.count;
	                    
	                    for(var j = 0 ; j < count ; j ++){
	                        //设备ID
	                        var deviceId = $.util.isNull(proDevice.deviceId)?"":proDevice.deviceId;
	                        htmlDevice.push(' <tr class="add-device" data-id="'+deviceId+'">')
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
	                        //附属设备
	                        var subFlg = "1"==proDevice.subFlg?"是":"否";
	                        htmlDevice.push(' <td>'+subFlg+'</td>');
	                        //必要设备
	                        var requiredFlg = "1"==proDevice.requiredFlg?"是":"否";
	                        htmlDevice.push(' <td>'+requiredFlg+'</td>');
	                        //删除操作
	                        htmlDevice.push('     <td>')
                            htmlDevice.push('         <button class="btn btn-default " type="button" onclick="addProDeviceRow(this)"><i class="fa fa-plus"></i></button> ')
	                        htmlDevice.push('     </td>')
	                        
	                        htmlDevice.push(' </tr>')
	                    }
	                }
	            }
	        }else{
	            htmlDevice.push('<tr class="no-records-found"><td colspan="5">没有找到匹配的记录</td></tr>');
	        }
	        $("#addProDeviceBody").html(htmlDevice.join(''));
		}
		addDeviceFlg = true;
	}
	
	//添加库存设备
	function addProDeviceRow(btnEle){
		//获取设备编号
		var deviceId = $(btnEle).parent().parent().attr("data-id");
		$(btnEle).parent().parent().addClass("hd").removeClass("add-device");;
		//获取device
		 if(!$.util.isNull(addDeviceList) && addDeviceList.length > 0){
             for(var i = 0; i < addDeviceList.length;i++){
                 var proDevice = addDeviceList[i];
                 var _deviceId = $.util.isNull(proDevice.deviceId)?"":proDevice.deviceId;
                 if(_deviceId == deviceId){
                	 addProStockdeviceRow(proDevice,true);
                	 return false;
                 }
             }
		 }
		
	}
</script>

</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>产品测试</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">所属产品分类：</label>
                                <div id="downProTypeTree" class="col-sm-8">
                                    <input id="proTypeName" name="proTypeName" class="form-control" data-placeholder="选择所属产品分类..." type="text">
                                    <input name="proType" class="form-control" type="hidden">
	                                <div class="selTree hd" style="">
	                                    <ul id="proTypeTree" class="ztree"></ul>
	                                </div>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">所属产品：</label>
                                <div class="col-sm-8">
                                    <select id="productSlt" name="proId" data-placeholder="选择产品..." style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">库存编号：</label>
                                <div class="col-sm-8">
                                    <input id="proNum" type="text" name="proNum" class="form-control" ></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea name="remarks" class="form-control" ></textarea>
                                </div>
                            </div>
                        </form>
                        <div class="well" style="padding-bottom: 0;">
                             <div class="row">
					            <div class="col-sm">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-title">
					                        <h5>附属设备</h5>
					                        <button class="btn btn-info" style="float: right;padding: 3px 10px;" type="button" onclick="openDeviceDialog()"><i class="fa fa-plus"></i> 添加附属设备</button>
					                    </div>
					                    <div class="ibox-content">
                                            <table id="proDevice" class="table table-hover table-striped" style="border: 1px solid #e4eaec;">
                                                 <thead>
                                                     <tr>
                                                          <th style="width: 140px;"><div class="th-inner">设备类型</div></th>
                                                         <th><div class="th-inner">设备名称</div></th>
                                                         <th><div class="th-inner">设备编号</div></th>
                                                         <th style="width: 85px;"><div class="th-inner">附属设备</div></th>
                                                         <th style="width: 85px;"><div class="th-inner">必要设备</div></th>
                                                         <th style="width: 50px;"><div class="th-inner">操作</div></th>
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
                    </div>
		            <div class="ibox-content text-center">
		                <button class="btn btn-info" type="button" onclick="saveForm()"><i class="fa fa-save"></i> 保存</button>
		            </div>
                </div>
            </div>
        </div>
    </div>

	<div id="addProDevice" class="hd">
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
				<div class="col-sm">
					<div class="ibox">
							<table class="table table-hover table-striped"
								style="border: 1px solid #e4eaec;">
								<thead>
									<tr>
										<th style="width: 140px;"><div class="th-inner">设备类型</div></th>
										<th><div class="th-inner">设备名称</div></th>
										<th style="width: 85px;"><div class="th-inner">附属设备</div></th>
										<th style="width: 85px;"><div class="th-inner">必要设备</div></th>
										<th style="width: 50px;"><div class="th-inner">操作</div></th>
									</tr>
								</thead>
								<tbody id="addProDeviceBody">

								</tbody>
							</table>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>