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
    	
    	//初始化数据
        var id = $.util.getUrlParam('id');
        if(!$.util.isNull(id)){
            initData(id);
        }
    	
        
    	
    });
    

    //初始化数据
    function initData(id){
        //查询数据，绑定到元素
        var showUrl = $.util.basePath + '/r/device/show';
        var json = {'id':id}
        $.util.ajax({
            url:showUrl,
            data:json,
            success:function(data){
                bindShowData(data);

                var stauts = data.status;

                if(stauts=='1'){
                	document.getElementById("status").innerText="正常";
                }else if(stauts=='2'){
                	document.getElementById("status").innerText="冻结";
                }else if(stauts=='3'){
                	document.getElementById("status").innerText="删除";
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
                        <h5>设备详情</h5>
                    </div>
                    <div class="ibox-content show-content">
                        <div class="row show-row show-text">
                            <label class="col-sm-3 control-label font-l">设备名称：</label>
                            <div class="col-sm-8">
                                <lable class="form-control-static show-data" data-name="deviceName"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">设备类型（字典）：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="dictDeviceType"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">设备型号：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="deviceModel"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">设备编码：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="deviceCode"></lable>
                            </div>
                        </div>
                         <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">设备厂家：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="deviceMaker"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">质保期（月）：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="deviceWarranty"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">厂家联系方式：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="makerPhone"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">厂家地址：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="makerAddress"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">状态：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="status" id="status"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">备注：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="remarks" id="status"></lable>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	</body>
</html>