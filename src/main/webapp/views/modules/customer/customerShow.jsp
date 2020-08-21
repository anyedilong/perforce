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
        var showUrl = $.util.basePath + '/r/customer/show';
        var json = {'id':id}
        $.util.ajax({
            url:showUrl,
            data:json,
            success:function(data){
                bindShowData(data);
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
                        <h5>客户信息详情</h5>
                    </div>
                    <div class="ibox-content show-content">
                        <div class="row show-row show-text">
                            <label class="col-sm-3 control-label font-l">客户姓名：</label>
                            <div class="col-sm-8">
                                <lable class="form-control-static show-data" data-name="name"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">客户手机号：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="phoneNum"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">数据来源：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="sourceName"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">省：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="provinceName"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">市：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="cityName"></lable>
                            </div>
                        </div>
                         <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">县区：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="countyName"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">镇：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="townName"></lable>
                            </div>
                        </div>
                         <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">村：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="villageName"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">详细地址：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="address"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">代理商：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="proxy.name"></lable>
                            </div>
                        </div>
                        <div class="row show-row">
                            <label class="col-sm-3 control-label font-l">销售用户：</label>
                            <div class="col-sm-8 show-text">
                                <lable class="form-control-static show-data" data-name="sellUserName"></lable>
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
                    </div>
                </div>
            </div>
        </div>
    </div>
	</body>
</html>