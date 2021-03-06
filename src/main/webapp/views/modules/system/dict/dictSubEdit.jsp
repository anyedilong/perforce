<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    
    $(function(){
    	//初始化校验
    	initValidate();
    	//初始化数据
    	initData();
    });
    
    //初始化数据校验
    function initValidate(){
        var e = "<i class='fa fa-times-circle'></i> ";
        $("#saveForm").validate({
            rules: {
                text: "required",
                value: "required"
                },
            messages: {
                text: e + "请输入字典名称",
                value: e + "请输入字典值"
                }
        });
    }
    
    //初始化数据
    function initData(){
    	
        var dictId = $.util.getUrlParam('dictId');
        if(!$.util.isNull(dictId)){
        	$("#saveForm input[name=dictId]").val(dictId);
        }
    	
        var id = $.util.getUrlParam('id');
        if(!$.util.isNull(id)){
        	 //查询数据，绑定到元素
            var showUrl = $.util.basePath + '/r/dict/showSub';
            var json = {'id':id}
            $.util.ajax({
                url:showUrl,
                data:json,
                success:function(data){
                    bindData('saveForm',data);
                }
            });
        }
    }
   
    //保存
    function saveForm(){
    	var saveUrl = '/r/dict/saveSub';
        save(saveUrl,'saveForm',function(data){
            parent.refreshTable();
            parent.closeDialog(menuId);
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
                        <h5>保存字典</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="saveForm" novalidate="novalidate">
                            <input type="hidden" name="id"/>
                            <input name="dictId" class="form-control" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">字典名称：</label>
                                <div class="col-sm-8">
                                    <input id="text" name="text" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">字典值：</label>
                                <div class="col-sm-8">
                                    <input id="value" name="value" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">状态：</label>
                                <div class="col-sm-8">
                                    <select id="statusSlt" name="status" data-placeholder="选择状态..." style="width:350px;" class="chosen-select-deselect" tabindex="12">
                                        <option value="1" selected="selected">正常</option>
                                        <option value="2" >冻结</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea name="remarks" class="form-control" ></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">排序号：</label>
                                <div class="col-sm-8">
                                    <input id="orderNum" name="orderNum" class="form-control" type="number">
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