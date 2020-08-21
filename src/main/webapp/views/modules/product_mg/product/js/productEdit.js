var proTypeTree;
    
    var deviceTypeData;//设备类型数据

$(function(){

    //初始化设备类型  字典数据
    initDeviceTypeData();
    
    
	initValidate();
	//初始化产品分类树
	initProTypeTree();
	setTimeout('$("#downProTypeTree").downZtree()', 100);
	
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
    
});

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
        	proName: "required",
        	proCode: "required",
        	proTypeName: "required"
            },
        messages: {
        	proName: e + "请输入产品名称",
        	proCode: e + "请输入产品编码",
        	proTypeName: e + "请选择产品分类"
            }
    });
}

//初始化产品分类树
function initProTypeTree(){
    var initOrgUrl =  $.util.basePath + '/r/productType/getTree';
    
    $.util.ajax({
        url:initOrgUrl,
        async : false, // 是否同步
        success:function(data){
            if($.util.isNull(data)){
                data = [];
            }
            data.push({id:"0",parentId:"-1",typeName:"产品分类",open:true});
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parentId"
                    },
                    key:{
                        //url: "xUrl"
                    	name: "typeName"
                    }
                },
                callback : {
                    onClick:function(event, treeId, treeNode){
                    	 $("#saveForm input[name=proType]").val(treeNode.id);
                         $("#saveForm input[name=proTypeName]").val(treeNode.typeName);
                         $("#downProTypeTree .selTree").addClass("hd");
                    }
                }
            };

            proTypeTree = $.fn.zTree.init($("#proTypeTree"), setting, data);
                proTypeTree.expandAll(true); 
            }
        });
    }
   
    
    //初始化数据
function initData(id){
    //查询数据，绑定到元素
    var showUrl = $.util.basePath + '/r/product/show';
    var json = {'id':id}
    $.util.ajax({
        url:showUrl,
        data:json,
        success:function(data){
            bindData('saveForm',data);
            $("#saveForm .show-hidden").addClass("hd");
            
            //初始化   所属产品分类
            var proType = data.proType;
            if(!$.util.isNull(proType)){
                var node = proTypeTree.getNodesByParam("id", proType, null);
                if(!$.util.isNull(node)){
                    proTypeTree.selectNode(node[0]);
                    $("#saveForm input[name=proType]").val(node[0].id);
                    $("#saveForm input[name=proTypeName]").val(node[0].typeName);
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

//保存form表单
function saveForm(){
	var param = {"proDeviceList":[]};
	
	//获取设备信息
	$("#proDeviceBody tr").each(function(){
		
		var deviceId = $(this).find("select[name=deviceId]").val();
		var deviceType = $(this).find("select[name=deviceType]").val();
		var count = $(this).find("input[name=count]").val();
		var requiredFlg = $(this).find("input[name=requiredFlg]:checked").val();
		if(requiredFlg != 1){
			requiredFlg = 0;
		}
		var subFlg = $(this).find("input[name=subFlg]:checked").val();
		if(subFlg != 1){
			subFlg = 0;
		}
		
		var itemParam = {
			"deviceId":deviceId,
			"deviceType":deviceType,
			"count":count,
			"requiredFlg":requiredFlg,
			"subFlg":subFlg
		};
		
		param.proDeviceList.push(itemParam);
	});
	
    var saveUrl = '/r/product/save';
    save(saveUrl,'saveForm',function(){
        parent.refreshTable();
        parent.closeDialog(menuId);
    },param);
}

//添加附属设备
function addSubDevice(proDevice){
    var deviceType = "";
	var deviceId = "";
	var subFlg = "";
	var requiredFlg = "";
	var count = "";
	
	//初始化信息
	if(!$.util.isNull(proDevice)){
		deviceType = proDevice.deviceType;
		deviceId = proDevice.deviceId;
		subFlg = proDevice.subFlg;
		requiredFlg = proDevice.requiredFlg;
		count = proDevice.count;
	}

	var htmlModel = [];
	htmlModel.push(' <tr data-row="">')
	// 设备类型
	htmlModel.push('     <td data-type="deviceType">')
	htmlModel.push('          <select name="deviceType" data-placeholder="选择设备类型..." style="width:120px;" class="chosen-select-deselect" tabindex="12">')
	htmlModel.push('          </select>')
	htmlModel.push('     </td>')
	// 设备型号
	htmlModel.push('     <td data-type="deviceModel">')
	htmlModel.push('          <select name="deviceId" data-placeholder="选择设备型号..." style="width:350px;" class="chosen-select-deselect" tabindex="12">')
	htmlModel.push('          </select>')
	htmlModel.push('     </td>')
	// 数量
	htmlModel.push('     <td data-type="count">')
	htmlModel.push('         <input name="count" style="width:80px;" value="1" class="form-control" type="number">')
	htmlModel.push('     </td>')
	//附属设备 
	htmlModel.push('     <td data-type="subFlg">')
	if(subFlg == 1){
	    htmlModel.push('         <input type="checkbox" name="subFlg" value="1" checked="checked" class="i-checks">')
	}else{
	    htmlModel.push('         <input type="checkbox" name="subFlg" value="1" class="i-checks">')
	}
	htmlModel.push('     </td>')
	// 必要设备 
	htmlModel.push('     <td data-type="requiredFlg">')
	if(requiredFlg == 1){
		htmlModel.push('         <input type="checkbox" name="requiredFlg" value="1" checked="checked" class="i-checks">')
	}else{
		htmlModel.push('         <input type="checkbox" name="requiredFlg" value="1" class="i-checks">')
	}
	htmlModel.push('     </td>')
	
	//删除操作
	htmlModel.push('     <td data-type="count">')
	htmlModel.push('         <button class="btn btn-default " type="button" onclick="deleteProDevice(this)"><i class="fa fa-remove"></i></button> ')
	htmlModel.push('     </td>')
	
	htmlModel.push(' </tr>')
	$("#proDeviceBody").append(htmlModel.join(''));

	//设置设备类型
	var deviceTypeEle = $("#proDeviceBody tr:last select[name=deviceType]");
	$(deviceTypeEle).chosen();
	initDeviceTypeSel(deviceTypeEle,deviceType);

	//设置设备型号
	var deviceModel = $("#proDeviceBody tr:last select[name=deviceId]");
	$(deviceModel).chosen();
	initDeviceModelSel(deviceModel, deviceTypeEle,deviceId);

	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	})
}

//初始化设备型号
function initDeviceModelSel(deviceModelEle, deviceTypeEle,initDeviceModel) {
	var deviceType = $(deviceTypeEle).val();
	var url = $.util.basePath + '/r/device/getListByType';
	$.util.ajax({
		url : url,
		data : {
			'deviceType' : deviceType
		},
		async : false, // 是否同步
		success : function(data) {
			var optionHtml = [];
			if (!$.util.isNull(data)) {
				for (var i = 0; i < data.length; i++) {
					var item = data[i];
					var deviceName = item.deviceName;
					var deviceModel = item.deviceModel;
					if ($.util.isNull(deviceModel)) {
						deviceModel = "";
					}
					var deviceMaker = item.deviceMaker;
					if ($.util.isNull(deviceMaker)) {
						deviceMaker = "";
					}
					var deviceId = item.id;
					var text = deviceName + "[" + deviceMaker + "]["+ deviceModel + "]";
					if(deviceId == initDeviceModel){
						optionHtml.push(' <option selected value="'+deviceId+'">' + text+ '</option> ');
					}else{
						optionHtml.push(' <option value="'+deviceId+'">' + text+ '</option> ');
					}
				}
			}
			$(deviceModelEle).html(optionHtml.join(''));
			$(deviceModelEle).trigger("chosen:updated");
		}
	});
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

//删除一行 产品设备
function deleteProDevice(btnEle){
	$(btnEle).parent().parent().remove();
}