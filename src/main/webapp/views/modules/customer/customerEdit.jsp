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
    	//初始化代理商下拉列表
		initProxy();
    	//初始化销售用户
    	initSellUser();
        //初始化省
        initProvince();
    	//初始化数据
        var id = $.util.getUrlParam('id');
        if(!$.util.isNull(id)){
            initData(id);
        }
    	
        //是否为查看状态
        var type =  $.util.getUrlParam('type');
        if(type=='show'){
            
        }
        //下拉框change事件
        $("#provinceSel").change(function(){
        	initCity();
        });
        $("#citySel").change(function(){
        	initCounty();
        });
        $("#countySel").change(function(){
        	initTown();
        });
        $("#townSel").change(function(){
        	initVillage();
        });
    });
    //初始化销售用户
	function initSellUser(){
		var showUrl = $.util.basePath + '/r/user/getSysUserDropDownList';
        $.util.ajax({
            url:showUrl,
            data:null,
            success:function(data){
            	var optionHtml = [];
            	optionHtml.push(' <option value="">请选择</option> ');
        		//下拉框数据绑定
        		if(!$.util.isNull(data)){
        			for(var i = 0; i < data.length;i++){
        				 var item = data[i];
        				 optionHtml.push(' <option value="'+item.id+'">'+item.name+'</option> ');
        			}
        		}
        		$("#sellUserSel").html(optionHtml.join(''));
        		$("#sellUserSel").trigger("chosen:updated"); 
            }
        });  
  	}
    //初始化代理商下拉列表
	function initProxy(){
		var showUrl = $.util.basePath + '/r/proxy/getProxyDropDownList';
        $.util.ajax({
            url:showUrl,
            data:null,
            success:function(data){
            	var optionHtml = [];
            	optionHtml.push(' <option value="">请选择</option> ');
        		//下拉框数据绑定
        		if(!$.util.isNull(data)){
        			for(var i = 0; i < data.length;i++){
        				 var item = data[i];
        				 optionHtml.push(' <option value="'+item.id+'">'+item.name+'</option> ');
        			}
        		}
        		$("#proxyIdSel").html(optionHtml.join(''));
        		$("#proxyIdSel").trigger("chosen:updated"); 
            }
        });
  	}
    //初始化数据校验
    function initValidate(){
    	var e = "<i class='fa fa-times-circle'></i> ";
        $("#saveForm").validate({
            rules: {
            	name: "required",
            	phoneNum:"digits",
            },
            messages: {
            	name: e + "请输入客户姓名",
            	phoneNum: e + "手机号码格式错误",
            }
        });
    }
    
    //保存form表单
    function saveForm(){
    	var saveUrl = '/r/customer/save';
    	save(saveUrl,'saveForm',function(){
    	
            parent.refreshTable();
            parent.closeDialog(menuId);
        });
    }
    
    //初始化数据
    function initData(id){
    
        //查询数据，绑定到元素
        var showUrl = $.util.basePath + '/r/customer/show';
        var json = {'id':id}
        $.util.ajax({
            url:showUrl,
            data:json,
            success:function(data){
            	debugger;
            	bindData('saveForm',data);
            	//省
            	initProvince();
            	var province = data.province;
            	$("#provinceSel").val(province);
            	$("#provinceSel").trigger("chosen:updated"); 
            	
            	//市
            	initCity();
            	var city = data.city;
            	$("#citySel").val(city);
            	$("#citySel").trigger("chosen:updated"); 
            	
            	//县
            	initCounty()
            	var county = data.county;
            	$("#countySel").val(county);
            	$("#countySel").trigger("chosen:updated"); 
            	
            	//镇
            	initTown();
            	var town = data.town;
            	$("#townSel").val(town);
            	$("#townSel").trigger("chosen:updated"); 
            	
            	//村
            	initVillage();
            	var village = data.village;
            	$("#villageSel").val(village);
            	$("#villageSel").trigger("chosen:updated"); 
            }
        });
    }
    
    //初始化省
    function initProvince(){
    	var url = $.util.basePath + '/getAreaList';
    	var json = {'level':1}
    	$.util.ajax({
            url:url,
            data:json,
            async : false, // 是否同步
            success:function(data){
            	var optionHtml = [];
            	optionHtml.push(' <option value="">请选择</option> ');
        		//下拉框数据绑定
        		if(!$.util.isNull(data)){
        			for(var i = 0; i < data.length;i++){
        				 var item = data[i];
        				 optionHtml.push(' <option value="'+item.areaCode+'">'+item.areaName+'</option> ');
        			}
        		}
        		$("#provinceSel").html(optionHtml.join(''));
        		$("#provinceSel").trigger("chosen:updated"); 
            }
        });
    }
    
    //初始化市
    function initCity(){
    	var url = $.util.basePath + '/getAreaList';
    	var provinceCode = $("#provinceSel").val();
    	var json = {'level':2,'code':provinceCode}
    	$.util.ajax({
            url:url,
            data:json,
            async : false, // 是否同步
            success:function(data){
            	var optionHtml = [];
            	optionHtml.push(' <option value="">请选择</option> ');
        		//下拉框数据绑定
        		if(!$.util.isNull(data)){
        			for(var i = 0; i < data.length;i++){
        				 var item = data[i];
        				 optionHtml.push(' <option value="'+item.areaCode+'">'+item.areaName+'</option> ');
        			}
        		}
        		$("#citySel").html(optionHtml.join(''));
        		$("#citySel").trigger("chosen:updated"); 
            }
        });
    }
    
    //初始化县
    function initCounty(){
    	var url = $.util.basePath + '/getAreaList';
    	var cityCode = $("#citySel").val();
    	var json = {'level':3,'code':cityCode}
    	$.util.ajax({
            url:url,
            data:json,
            async : false, // 是否同步
            success:function(data){
            	var optionHtml = [];
            	optionHtml.push(' <option value="">请选择</option> ');
        		//下拉框数据绑定
        		if(!$.util.isNull(data)){
        			for(var i = 0; i < data.length;i++){
        				 var item = data[i];
        				 optionHtml.push(' <option value="'+item.areaCode+'">'+item.areaName+'</option> ');
        			}
        		}
        		$("#countySel").html(optionHtml.join(''));
        		$("#countySel").trigger("chosen:updated"); 
            }
        });
    }
    
    //初始化镇
    function initTown(){
    	var url = $.util.basePath + '/getAreaList';
    	var townCode = $("#countySel").val();
    	var json = {'level':4,'code':townCode}
    	$.util.ajax({
            url:url,
            data:json,
            async : false, // 是否同步
            success:function(data){
            	debugger;
            	var optionHtml = [];
            	optionHtml.push(' <option value="">请选择</option> ');
        		//下拉框数据绑定
        		if(!$.util.isNull(data)){
        			for(var i = 0; i < data.length;i++){
        				 var item = data[i];
        				 optionHtml.push(' <option value="'+item.areaCode+'">'+item.areaName+'</option> ');
        			}
        		}
        		$("#townSel").html(optionHtml.join(''));
        		$("#townSel").trigger("chosen:updated"); 
            }
        });
    }
    
    //初始化村
    function initVillage(){
    	var url = $.util.basePath + '/getAreaList';
    	var villageCode = $("#townSel").val();
    	var json = {'level':5,'code':villageCode}
    	$.util.ajax({
            url:url,
            data:json,
            async : false, // 是否同步
            success:function(data){
            	debugger;
            	var optionHtml = [];
            	optionHtml.push(' <option value="">请选择</option> ');
        		//下拉框数据绑定
        		if(!$.util.isNull(data)){
        			for(var i = 0; i < data.length;i++){
        				 var item = data[i];
        				 optionHtml.push(' <option value="'+item.areaCode+'">'+item.areaName+'</option> ');
        			}
        		}
        		$("#villageSel").html(optionHtml.join(''));
        		$("#villageSel").trigger("chosen:updated"); 
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
                        <h5>保存客户信息</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">客户名称：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">数据来源：</label>
                                <div class="col-sm-8">
                                	<select id="sourceSel" name="source"  style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                	<option id="op" value=''>请选择</option> 
    								<option id="op" value='1'>代理商客户</option> 
    								<option id="ep" value='2'>直接客户</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">客户手机号：</label>
                                <div class="col-sm-8">
                                    <input id="phoneNum" name="phoneNum" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">省：</label>
                                <div class="col-sm-8">
                                    <select id="provinceSel" name="province"  data-placeholder="选择省..."  style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    	
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">市：</label>
                                <div class="col-sm-8">
                                    <select id="citySel" name="city"  data-placeholder="选择市..."  style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">县区：</label>
                                <div class="col-sm-8">
                                    <select id="countySel" name="county" data-placeholder="选择县..."  style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">镇：</label>
                                <div class="col-sm-8">
                                    <select id="townSel" name="town" data-placeholder="选择镇..."  style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">村：</label>
                                <div class="col-sm-8">
                                    <select id="villageSel" name="village" data-placeholder="选择村..."  style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    </select>
                                </div>
                            </div>
                           <div class="form-group">
                                <label class="col-sm-3 control-label">详细地址：</label>
                                <div class="col-sm-8">
                                    <input id="address" name="address" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">代理商：</label>
                                <div class="col-sm-8">
                                    <select id="proxyIdSel" name="proxyId" data-placeholder="选择代理商..."  style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">销售用户：</label>
                                <div class="col-sm-8">
                                	<select id="sellUserSel" name="sellUser" data-placeholder="选择销售用户..."  style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                    </select>
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