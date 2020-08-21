<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    
    var iconDialog;
    
    $(function(){
    	
    	initValidate();
    	setTimeout('$("#downOrgTree").downZtree()', 100);
    	
    	//初始化设备类型拉下框
    	initDeviceType();
    	
    	//初始化数据
        var id = $.util.getUrlParam('id');
        if(!$.util.isNull(id)){
            initData(id);
        }
    	
        //是否为查看状态
        var type =  $.util.getUrlParam('type');
        if(type=='show'){
            
        }
    	
    });
    
	//初始化设备类型拉下框
    function initDeviceType(){
    	getDictList("dictDeviceType",function(data){
    		var optionHtml = [];
    		//下拉框数据绑定
    		if(!$.util.isNull(data)){
    			for(var i = 0; i < data.length;i++){
    				 var item = data[i];
    				 optionHtml.push(' <option value="'+item.value+'">'+item.text+'</option> ');
    			}
    		}
    		$("#deviceTypeSel").html(optionHtml.join(''));
    		$("#deviceTypeSel").trigger("chosen:updated"); 
    	});
	}
    
    //初始化数据校验
    function initValidate(){
    	var e = "<i class='fa fa-times-circle'></i> ";
        $("#saveForm").validate({
            rules: {
            	deviceName: "required",
            	makerPhone:"digits"
            },
            messages: {
            	deviceName: e + "请输入设备名称",
            	makerPhone: e + "手机号码格式错误"
            }
        });
    }
    
    //保存form表单
    function saveForm(){
    	
    	var saveUrl = '/r/device/save';
    	save(saveUrl,'saveForm',function(){
            parent.refreshTable();
            parent.closeDialog(menuId);
        });
    }
    
    //初始化数据
    function initData(id){
        //查询数据，绑定到元素
        var showUrl = $.util.basePath + '/r/device/show';
        var json = {'id':id}
        $.util.ajax({
            url:showUrl,
            data:json,
            success:function(data){
            	bindData('saveForm',data);
            	$("#deviceTypeSel").trigger("chosen:updated"); 
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
                        <h5>保存设备信息</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">设备名称：</label>
                                <div class="col-sm-8">
                                    <input id="deviceName" name="deviceName" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">设备类型：</label>
                                <div class="col-sm-8">
                                	<select id="deviceTypeSel" name="deviceType" data-placeholder="选择设备类型..." style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">设备型号：</label>
                                <div class="col-sm-8">
                                    <input id="deviceModel" name="deviceModel" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">设备编码：</label>
                                <div class="col-sm-8">
                                    <input id="deviceCode" name="deviceCode" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">设备厂家：</label>
                                <div class="col-sm-8">
                                    <input id="deviceMaker" name="deviceMaker" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">质保期(月)：</label>
                                <div class="col-sm-8">
                                    <input id="deviceWarranty" name="deviceWarranty" class="form-control" type="number" min="0">
                                </div>
                            </div>
                           <div class="form-group">
                                <label class="col-sm-3 control-label">厂家联系方式：</label>
                                <div class="col-sm-8">
                                    <input id="makerPhone" name="makerPhone" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">厂家地址：</label>
                                <div class="col-sm-8">
                                    <input id="makerAddress" name="makerAddress" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea name="remarks" class="form-control" ></textarea>
                                </div>
                            </div>
                            
                            
                        </form>
                    </div>
		            <div class="ibox-content text-center">
		                <button class="btn btn-info" type="button" onclick="saveForm()"><i class="fa fa-save"></i> 保存</button>
		            </div>
                </div>
            </div>
        </div>
    </div>
	</body>
</html>